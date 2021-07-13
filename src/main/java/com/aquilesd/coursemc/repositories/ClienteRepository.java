package com.aquilesd.coursemc.repositories;

import com.aquilesd.coursemc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.jar.JarOutputStream;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional(readOnly = true) // readOnly: Ela não necessita de ser envolvida como uma transação de BD.
    Cliente findByEmail(String email);
}
