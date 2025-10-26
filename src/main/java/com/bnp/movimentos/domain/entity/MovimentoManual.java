package com.bnp.movimentos.domain.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MOVIMENTO_MANUAL")
public class MovimentoManual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "O mês deve ser maior ou igual a 1")
    @Max(value = 12, message = "O mês deve ser menor ou igual a 12")
    @Column(name = "DAT_MES")
    private Integer mes;


    @Column(name = "DAT_ANO")
    private Integer ano;


    @Column(name = "NUM_LANCAMENTO")
    private Long numLancamento;


    @Column(name = "COD_PRODUTO")
    private String codProduto;


    @Column(name = "COD_COSIF")
    private String codCosif;


    @Column(name = "DES_DESCRICAO")
    private String descricao;


    @Column(name = "VAL_VALOR")
    private BigDecimal valor;


    @Column(name = "COD_USUARIO")
    private String codUsuario;


    @Column(name = "DAT_MOVIMENTO")
    private LocalDateTime dataMovimento;

}
