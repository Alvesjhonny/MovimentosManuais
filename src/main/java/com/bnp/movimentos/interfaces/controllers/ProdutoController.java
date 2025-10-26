package com.bnp.movimentos.interfaces.controllers;

import com.bnp.movimentos.application.service.ProdutoService;
import com.bnp.movimentos.domain.entity.Produto;
import com.bnp.movimentos.domain.entity.ProdutoCosif;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(service.listarAtivos());
    }

    @GetMapping("/{codProduto}/cosifs")
    public ResponseEntity<List<ProdutoCosif>> cosifs(@PathVariable String codProduto) {
        return ResponseEntity.ok(service.listarCosifs(codProduto));
    }
}