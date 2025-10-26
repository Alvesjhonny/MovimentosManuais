package com.bnp.movimentos.domain.entity;

import lombok.*;


import jakarta.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUTO_COSIF")
public class ProdutoCosif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "COD_PRODUTO")
    private String codProduto;


    @Column(name = "COD_COSIF")
    private String codCosif;


    @Column(name = "COD_CLASSIFICACAO")
    private String codClassificacao;


    @Column(name = "STA_STATUS")
    private String status;
}