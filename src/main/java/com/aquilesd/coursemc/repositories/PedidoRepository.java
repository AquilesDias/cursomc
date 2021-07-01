package com.aquilesd.coursemc.repositories;

import com.aquilesd.coursemc.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
