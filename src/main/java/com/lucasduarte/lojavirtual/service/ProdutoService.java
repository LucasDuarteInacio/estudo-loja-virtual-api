package com.lucasduarte.lojavirtual.service;

import com.lucasduarte.lojavirtual.domain.Categoria;
import com.lucasduarte.lojavirtual.domain.Pedido;
import com.lucasduarte.lojavirtual.domain.Produto;
import com.lucasduarte.lojavirtual.repository.CategoriaRepository;
import org.springframework.data.domain.Sort.Direction;
import com.lucasduarte.lojavirtual.repository.ProdutoRepository;
import com.lucasduarte.lojavirtual.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id){
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow( () ->  new ObjectNotFoundException(
                "Objeto não encontrado! " + id + ", Tipo: "+ Pedido.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return  repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias,pageRequest);
    }
}
