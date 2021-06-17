package com.aquilesd.coursemc.repositories;

import com.aquilesd.coursemc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
}
