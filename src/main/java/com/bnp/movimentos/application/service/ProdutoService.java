package com.bnp.movimentos.application.service;

import com.bnp.movimentos.domain.entity.Produto;
import com.bnp.movimentos.domain.entity.ProdutoCosif;
import com.bnp.movimentos.domain.repository.ProdutoCosifRepository;
import com.bnp.movimentos.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoCosifRepository produtoCosifRepository;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoCosifRepository produtoCosifRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoCosifRepository = produtoCosifRepository;
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.listarAtivos();
    }

    public List<ProdutoCosif> listarCosifs(String codProduto) {
        return produtoCosifRepository.buscarPorProduto(codProduto);
    }
}
