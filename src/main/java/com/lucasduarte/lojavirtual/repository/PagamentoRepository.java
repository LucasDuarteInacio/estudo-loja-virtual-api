package com.lucasduarte.lojavirtual.repository;

import com.lucasduarte.lojavirtual.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento,Integer> {
}
