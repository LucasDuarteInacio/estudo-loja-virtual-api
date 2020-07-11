package com.lucasduarte.lojavirtual.resource;

import com.lucasduarte.lojavirtual.domain.Categoria;
import com.lucasduarte.lojavirtual.dto.CategoriaDTO;
import com.lucasduarte.lojavirtual.service.CategoriaService;
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
@RequestMapping("categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;


    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find(@PathVariable("id") Integer id){
        Categoria obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> list = service.findAll();
        List<CategoriaDTO>  listDTO= list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid  @RequestBody  CategoriaDTO objDTO){
        Categoria obj = service.fromDTO(objDTO);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody  CategoriaDTO objDto,@PathVariable("id") Integer id) {
        Categoria obj = service.fromDTO(objDto);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value="page",defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage",defaultValue = "24") Integer linesPerPage,
            @RequestParam(value="oderBy",defaultValue = "nome") String orderBy,
            @RequestParam(value="direction",defaultValue = "ASC") String direction ){
        Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
        Page<CategoriaDTO>  listDTO= list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

}
