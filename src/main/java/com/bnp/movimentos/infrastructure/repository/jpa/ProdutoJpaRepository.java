package com.bnp.movimentos.infrastructure.repository.jpa;

import com.bnp.movimentos.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoJpaRepository extends JpaRepository<Produto, String> {
    List<Produto> findByStatus(String status);
}
