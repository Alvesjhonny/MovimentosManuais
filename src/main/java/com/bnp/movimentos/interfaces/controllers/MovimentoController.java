package com.bnp.movimentos.interfaces.controllers;

import com.bnp.movimentos.application.service.MovimentoService;
import com.bnp.movimentos.domain.entity.MovimentoManual;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentos")
public class MovimentoController {
    private final MovimentoService service;

    public MovimentoController(MovimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovimentoManual>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<MovimentoManual> criar(@RequestBody MovimentoManual movimento) {
        MovimentoManual criado = service.criar(movimento);
        return ResponseEntity.ok(criado);
    }
}

