package com.example.planoservico.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
public class PlanoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tituloPlanoServico;
    private String descricaoPlano;
    private String funcDisponibilizadas;
    private String funcNaoDisponibilizadas;
    private double precoPlanoServico;
    private String prazoPagamentos;
    private String sugestoesUpgrades;
    private boolean prioridade; // Novo campo para prioridade
}
