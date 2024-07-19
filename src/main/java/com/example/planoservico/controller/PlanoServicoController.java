package com.example.planoservico.controller;

import com.example.planoservico.model.PlanoServico;
import com.example.planoservico.service.PlanoServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanoServicoController {

    @Autowired
    private PlanoServicoService service;

    @GetMapping
    public ResponseEntity<List<PlanoServico>> getAllPlans() {
        List<PlanoServico> plans = service.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoServico> getPlanById(@PathVariable Long id) {
        PlanoServico plan = service.getPlanById(id);
        return ResponseEntity.ok(plan);
    }

    @PostMapping
    public ResponseEntity<PlanoServico> createPlan(@RequestBody PlanoServico plan) {
        PlanoServico createdPlan = service.createPlan(plan);
        return ResponseEntity.ok(createdPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoServico> updatePlan(@PathVariable Long id, @RequestBody PlanoServico planDetails) {
        PlanoServico updatedPlan = service.updatePlan(id, planDetails);
        return updatedPlan != null ? ResponseEntity.ok(updatedPlan) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        service.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
