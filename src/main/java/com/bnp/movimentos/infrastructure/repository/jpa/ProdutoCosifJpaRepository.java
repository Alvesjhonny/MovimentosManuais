package com.bnp.movimentos.infrastructure.repository.jpa;

import com.bnp.movimentos.domain.entity.ProdutoCosif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoCosifJpaRepository extends JpaRepository<ProdutoCosif, Long> {
    List<ProdutoCosif> findByCodProdutoAndStatus(String codProduto, String status);
}
