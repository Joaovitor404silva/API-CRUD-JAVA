package com.me.desafio.repository;

import com.me.desafio.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    boolean existsByMatricula(String matricula);
}
