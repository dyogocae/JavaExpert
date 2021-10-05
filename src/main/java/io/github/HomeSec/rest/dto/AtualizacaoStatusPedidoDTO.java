package io.github.HomeSec.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AtualizacaoStatusPedidoDTO {
    @NotNull(message = "{campo.status-pedido.obrigatorio}")
    private String novoStatus;
}
