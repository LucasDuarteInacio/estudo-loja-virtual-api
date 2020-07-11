package com.lucasduarte.lojavirtual.service;

import com.lucasduarte.lojavirtual.domain.Categoria;
import com.lucasduarte.lojavirtual.dto.CategoriaDTO;
import com.lucasduarte.lojavirtual.repository.CategoriaRepository;
import com.lucasduarte.lojavirtual.service.exceptions.DataIntegrityException;
import com.lucasduarte.lojavirtual.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria find(Integer id){
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElseThrow( () ->  new ObjectNotFoundException(
                "Objeto não encontrado! " + id + ", Tipo: "+ Categoria.class.getName()));
    }
    public List<Categoria> findAll(){
        return repository.findAll();
    }

    public  Categoria insert(Categoria obj){
        return repository.save(obj);
    }

    public Categoria update(Categoria obj){
        Categoria newObj = find(obj.getId());
        updateData(newObj,obj);
        find(obj.getId());
        return repository.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try {
            repository.deleteById(id);
        }
       catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não e possivel excluir uma categoria que contem produtos");
       }
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return  repository.findAll(pageRequest);
    }


    public Categoria fromDTO (CategoriaDTO objDTO){
        return new Categoria(objDTO.getId(),objDTO.getNome());
    }
    private void updateData(Categoria newObj, Categoria obj){
        newObj.setNome(obj.getNome());

    }
}
