package com.lucasduarte.lojavirtual.resource;

import com.lucasduarte.lojavirtual.domain.Cliente;
import com.lucasduarte.lojavirtual.dto.ClienteDTO;
import com.lucasduarte.lojavirtual.dto.ClienteNewDTO;
import com.lucasduarte.lojavirtual.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;


    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find(@PathVariable("id") Integer id){
        Cliente obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = service.findAll();
        List<ClienteDTO>  listDTO= list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
        Cliente obj = service.fromDTO(objDTO);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody  ClienteDTO objDto,@PathVariable("id") Integer id) {
        Cliente obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value="page",defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage",defaultValue = "24") Integer linesPerPage,
            @RequestParam(value="oderBy",defaultValue = "nome") String orderBy,
            @RequestParam(value="direction",defaultValue = "ASC") String direction ){
        Page<Cliente> list = service.findPage(page,linesPerPage,orderBy,direction);
        Page<ClienteDTO>  listDTO= list.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

}
