package com.lucasduarte.lojavirtual.repository;

import com.lucasduarte.lojavirtual.domain.Cliente;
import com.lucasduarte.lojavirtual.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {
}
