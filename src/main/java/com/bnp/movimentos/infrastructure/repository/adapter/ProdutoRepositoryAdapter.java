package com.bnp.movimentos.infrastructure.repository.adapter;

import com.bnp.movimentos.infrastructure.repository.jpa.ProdutoJpaRepository;
import com.bnp.movimentos.domain.entity.Produto;
import com.bnp.movimentos.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class ProdutoRepositoryAdapter implements ProdutoRepository {
    private final ProdutoJpaRepository jpa;


    public ProdutoRepositoryAdapter(ProdutoJpaRepository jpa) {
        this.jpa = jpa;
    }


    @Override
    public List<Produto> listarAtivos() {
        return jpa.findByStatus("A");
    }
}
