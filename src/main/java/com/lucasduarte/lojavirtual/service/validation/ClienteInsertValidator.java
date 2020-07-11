package com.lucasduarte.lojavirtual.service.validation;

import com.lucasduarte.lojavirtual.domain.Cliente;
import com.lucasduarte.lojavirtual.domain.enuns.TipoCliente;
import com.lucasduarte.lojavirtual.dto.ClienteNewDTO;
import com.lucasduarte.lojavirtual.repository.ClienteRepository;
import com.lucasduarte.lojavirtual.resource.exception.FieldMessage;
import com.lucasduarte.lojavirtual.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert ann) {
    }
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
        }
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido"));
        }
        Cliente cliente = repository.findByEmail(objDto.getEmail());
        if(null != cliente){
            list.add(new FieldMessage("email", "Email ja cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}