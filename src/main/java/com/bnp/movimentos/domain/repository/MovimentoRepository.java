package com.bnp.movimentos.domain.repository;


import com.bnp.movimentos.domain.entity.MovimentoManual;
import java.util.List;


public interface MovimentoRepository {
    MovimentoManual salvar(MovimentoManual movimento);
    List<MovimentoManual> listarTodos();
    Long proximoNumeroLancamento(Integer mes, Integer ano);
}
