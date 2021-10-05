package io.github.HomeSec.service.impl;

import io.github.HomeSec.domain.entity.Cliente;
import io.github.HomeSec.domain.entity.ItemPedido;
import io.github.HomeSec.domain.entity.Pedido;
import io.github.HomeSec.domain.entity.Produto;
import io.github.HomeSec.domain.enums.StatusPedido;
import io.github.HomeSec.domain.repository.Clientes;
import io.github.HomeSec.domain.repository.ItensPedido;
import io.github.HomeSec.domain.repository.Pedidos;
import io.github.HomeSec.domain.repository.Produtos;
import io.github.HomeSec.exception.PedidoNaoEncontradoException;
import io.github.HomeSec.exception.RegraNegocioException;
import io.github.HomeSec.rest.dto.ItemPedidoDTO;
import io.github.HomeSec.rest.dto.PedidoDTO;
import io.github.HomeSec.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Cliente cliente = clientesRepository
                .findById(dto.getCliente())
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(dto.getTotal());
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                })
                .orElseThrow( () -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens (Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível criar um pedido sem itens.");
        }
        return itens
                .stream()
                .map( dto -> {
                    Produto produto = produtosRepository
                            .findById(dto.getProduto())
                            .orElseThrow( () ->
                                    new RegraNegocioException("Código de produto inválido: " + dto.getProduto()) );
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
