package com.aquilesd.coursemc.repositories;

import com.aquilesd.coursemc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
