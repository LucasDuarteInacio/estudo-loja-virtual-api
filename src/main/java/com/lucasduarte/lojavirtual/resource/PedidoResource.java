package com.lucasduarte.lojavirtual.resource;

import com.lucasduarte.lojavirtual.domain.Categoria;
import com.lucasduarte.lojavirtual.domain.Pedido;
import com.lucasduarte.lojavirtual.dto.CategoriaDTO;
import com.lucasduarte.lojavirtual.service.CategoriaService;
import com.lucasduarte.lojavirtual.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;


    @GetMapping("/{id}")
    public ResponseEntity<Pedido> find(@PathVariable("id") Integer id){
        Pedido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
