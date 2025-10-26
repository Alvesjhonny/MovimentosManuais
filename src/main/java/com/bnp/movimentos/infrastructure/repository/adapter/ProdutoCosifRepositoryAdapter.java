package com.bnp.movimentos.infrastructure.repository.adapter;

import com.bnp.movimentos.domain.entity.ProdutoCosif;
import com.bnp.movimentos.domain.repository.ProdutoCosifRepository;
import com.bnp.movimentos.infrastructure.repository.jpa.ProdutoCosifJpaRepository;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class ProdutoCosifRepositoryAdapter implements ProdutoCosifRepository {
    private final ProdutoCosifJpaRepository jpa;


    public ProdutoCosifRepositoryAdapter(ProdutoCosifJpaRepository jpa) {
        this.jpa = jpa;
    }


    @Override
    public List<ProdutoCosif> buscarPorProduto(String codProduto) {
        return jpa.findByCodProdutoAndStatus(codProduto, "A");
    }
}
