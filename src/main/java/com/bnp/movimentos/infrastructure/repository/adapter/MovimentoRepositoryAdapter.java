package com.bnp.movimentos.infrastructure.repository.adapter;

import com.bnp.movimentos.infrastructure.repository.jpa.MovimentoJpaRepository;
import com.bnp.movimentos.domain.entity.MovimentoManual;
import com.bnp.movimentos.domain.repository.MovimentoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovimentoRepositoryAdapter implements MovimentoRepository {


    private final MovimentoJpaRepository jpa;


    public MovimentoRepositoryAdapter(MovimentoJpaRepository jpa) {
        this.jpa = jpa;
    }


    @Override
    public MovimentoManual salvar(MovimentoManual movimento) {
        return jpa.save(movimento);
    }


    @Override
    public List<MovimentoManual> listarTodos() {
        return jpa.findAll();
    }


    @Override
    public Long proximoNumeroLancamento(Integer mes, Integer ano) {
        Long max = jpa.findMaxNumLancamentoByMesAno(mes, ano);
        return (max == null ? 1L : max + 1);
    }
}
