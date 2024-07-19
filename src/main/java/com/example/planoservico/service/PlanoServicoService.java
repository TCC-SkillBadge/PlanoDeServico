package com.example.planoservico.service;

import com.example.planoservico.exception.ResourceNotFoundException;
import com.example.planoservico.model.PlanoServico;
import com.example.planoservico.repository.PlanoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoServicoService {

    @Autowired
    private PlanoServicoRepository repository;

    public List<PlanoServico> getAllPlans() {
        return repository.findAll();
    }

    public PlanoServico getPlanById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlanoServico not found with id: " + id));
    }

    public PlanoServico createPlan(PlanoServico plan) {
        return repository.save(plan);
    }

    public PlanoServico updatePlan(Long id, PlanoServico planDetails) {
        PlanoServico plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlanoServico not found with id: " + id));
        plan.setTituloPlanoServico(planDetails.getTituloPlanoServico());
        plan.setFuncDisponibilizadas(planDetails.getFuncDisponibilizadas());
        plan.setFuncNaoDisponibilizadas(planDetails.getFuncNaoDisponibilizadas());
        plan.setPrecoPlanoServico(planDetails.getPrecoPlanoServico());
        plan.setPrazoPagamentos(planDetails.getPrazoPagamentos());
        plan.setSugestoesUpgrades(planDetails.getSugestoesUpgrades());
        return repository.save(plan);
    }

    public void deletePlan(Long id) {
        PlanoServico plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlanoServico not found with id: " + id));
        repository.delete(plan);
    }
}
