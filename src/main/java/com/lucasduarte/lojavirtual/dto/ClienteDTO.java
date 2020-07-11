package com.lucasduarte.lojavirtual.dto;

import com.lucasduarte.lojavirtual.domain.Cliente;
import com.lucasduarte.lojavirtual.service.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ClienteUpdate
public class ClienteDTO {

    private Integer id;

    @NotEmpty(message = "Preencimento Obrigatorio")
    @Length(min=5,max=80,message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    @NotEmpty(message = "Preencimento Obrigatorio")
    @Email(message = "Email Invalido")
    private String email;

    public ClienteDTO(){}

    public ClienteDTO(Cliente obj){
        id = obj.getId();
        nome= obj.getNome();
        email= obj.getEmail();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
