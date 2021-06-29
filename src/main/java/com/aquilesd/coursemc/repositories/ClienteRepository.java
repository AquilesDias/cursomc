package com.aquilesd.coursemc.repositories;

import com.aquilesd.coursemc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.jar.JarOutputStream;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
