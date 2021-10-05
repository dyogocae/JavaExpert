package io.github.HomeSec.rest.controller;


import io.github.HomeSec.domain.entity.Produto;
import io.github.HomeSec.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos repository;

    public ProdutoController (Produtos repository) { this.repository = repository; }

    /* Busca de produtos pelo ID */
    @GetMapping("{id}")
    public Produto GetProdutoById ( @PathVariable Integer id ) {
        return repository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException( HttpStatus.NOT_FOUND, "Produto não encontrado" ) );

    }

    /* Lista de produtos cadastrados */
    @GetMapping
    public List<Produto> find (Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    /* Insere um produto na base de dados */
    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save ( @RequestBody @Valid Produto produto) {
        return repository.save(produto);
    }

    /* Altera um produto na base de dados */
    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update ( @PathVariable Integer id, @RequestBody @Valid Produto produto) {
        repository
                .findById(id)
                .map( p -> {
                    produto.setId(p.getId());
                    repository.save(produto);
                    return produto;
                } ).orElseThrow( () ->
                        new ResponseStatusException( NOT_FOUND, "Produto não encontrado" ) );
    }

    /* remove um produto na base de dados */
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete ( @PathVariable Integer id) {
        repository
                .findById(id)
                .map( p -> {
                    repository.delete(p);
                    return Void.TYPE;
                })
                .orElseThrow( () ->
                        new ResponseStatusException( NOT_FOUND, "Produto não encontrado" ) );
    }
}