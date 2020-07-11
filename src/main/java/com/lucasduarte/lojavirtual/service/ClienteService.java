package com.lucasduarte.lojavirtual.service;

import com.lucasduarte.lojavirtual.domain.Cidade;
import com.lucasduarte.lojavirtual.domain.Cliente;
import com.lucasduarte.lojavirtual.domain.Endereco;
import com.lucasduarte.lojavirtual.domain.enuns.TipoCliente;
import com.lucasduarte.lojavirtual.dto.ClienteDTO;
import com.lucasduarte.lojavirtual.dto.ClienteNewDTO;
import com.lucasduarte.lojavirtual.repository.CidadeRepository;
import com.lucasduarte.lojavirtual.repository.ClienteRepository;
import com.lucasduarte.lojavirtual.repository.EnderecoRepository;
import com.lucasduarte.lojavirtual.service.exceptions.DataIntegrityException;
import com.lucasduarte.lojavirtual.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow( () ->  new ObjectNotFoundException(
                "Objeto não encontrado! " + id + ", Tipo: "+ Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return repository.findAll();
    }

    @Transactional
    public  Cliente insert(Cliente obj){
        repository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public  Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
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
            throw new DataIntegrityException("Não e possivel excluir uma Cliente que contem produtos");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return  repository.findAll(pageRequest);
    }


    public Cliente fromDTO (ClienteDTO objDTO){
        return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
    }

    public Cliente fromDTO (ClienteNewDTO objDTO){
        Cliente cli = new Cliente(null,objDTO.getNome(),objDTO.getEmail(),objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
        Cidade cid = cidadeRepository.findById(objDTO.getCidadeId()).get();
        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(),cli,cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDTO.getTelefone1());
        if(null != objDTO.getTelefone2()){
            cli.getTelefones().add(objDTO.getTelefone2());
        }
        if(null != objDTO.getTelefone3()){
            cli.getTelefones().add(objDTO.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());

    }
}


