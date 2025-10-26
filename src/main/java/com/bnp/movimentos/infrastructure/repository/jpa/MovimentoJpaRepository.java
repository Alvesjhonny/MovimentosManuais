package com.bnp.movimentos.infrastructure.repository.jpa;

import com.bnp.movimentos.domain.entity.MovimentoManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimentoJpaRepository extends JpaRepository<MovimentoManual, Long> {
    @Query("SELECT COALESCE(MAX(m.numLancamento),0) FROM MovimentoManual m WHERE m.mes = :mes AND m.ano = :ano")
    Long findMaxNumLancamentoByMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
}
