package com.bnp.movimentos.application.service;

import com.bnp.movimentos.domain.entity.MovimentoManual;
import com.bnp.movimentos.domain.repository.MovimentoRepository;
import com.bnp.movimentos.infrastructure.repository.jpa.MovimentoJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentoService {

    private final MovimentoRepository movimentoRepository;

    public MovimentoService(MovimentoRepository movimentoRepository) {
        this.movimentoRepository = movimentoRepository;
    }

    public List<MovimentoManual> listar() {
        return movimentoRepository.listarTodos();
    }

    public MovimentoManual criar(MovimentoManual movimento) {
        Long prox = movimentoRepository.proximoNumeroLancamento(movimento.getMes(), movimento.getAno());
        movimento.setNumLancamento(prox);
        movimento.setCodUsuario("TESTE");
        movimento.setDataMovimento(LocalDateTime.now());
        return movimentoRepository.salvar(movimento);
    }
}
