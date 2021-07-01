package com.aquilesd.coursemc.repositories;

import com.aquilesd.coursemc.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
