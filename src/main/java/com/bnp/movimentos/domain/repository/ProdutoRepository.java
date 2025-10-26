package com.bnp.movimentos.domain.repository;


import com.bnp.movimentos.domain.entity.Produto;
import java.util.List;

public interface ProdutoRepository {
    List<Produto> listarAtivos();
}
