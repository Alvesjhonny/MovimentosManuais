package com.bnp.movimentos.domain.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUTO")
public class Produto {
    @Id
    @Column(name = "COD_PRODUTO")
    private String codProduto;


    @Column(name = "DES_PRODUTO")
    private String desProduto;


    @Column(name = "STA_STATUS")
    private String status;
}