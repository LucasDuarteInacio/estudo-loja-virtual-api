package com.lucasduarte.lojavirtual.repository;

import com.lucasduarte.lojavirtual.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
}
