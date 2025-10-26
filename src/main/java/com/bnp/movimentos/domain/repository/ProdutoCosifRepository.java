package com.bnp.movimentos.domain.repository;

import com.bnp.movimentos.domain.entity.ProdutoCosif;
import java.util.List;

public interface ProdutoCosifRepository {
    List<ProdutoCosif> buscarPorProduto(String codProduto);
}
