package com.jhonatan.estoque.controller;

import com.jhonatan.estoque.service.EstoqueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jhonatan.estoque.model.MovimentacaoEstoque;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jhonatan.estoque.dto.MovimentacaoEstoqueRequest;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoEstoque> registrarEntrada(
            @RequestBody @Valid MovimentacaoEstoqueRequest request
    ) {
        return ResponseEntity.ok(estoqueService.registrarEntrada(request));
    }

    @PostMapping("/saida")
    public ResponseEntity<MovimentacaoEstoque> registrarSaida(
            @RequestBody @Valid MovimentacaoEstoqueRequest request
    ) {
        return ResponseEntity.ok(estoqueService.registrarSaida(request));
    }

    @GetMapping("/saldo/{produtoId}")
    public ResponseEntity<Integer> consultarSaldo(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.consultarSaldo(produtoId));
    }

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoque>> listarMovimentacoes() {
        return ResponseEntity.ok(estoqueService.listarMovimentacoes());
    }

    @GetMapping("/movimentacoes/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoque>> listarMovimentacoesPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.listarMovimentacoesPorProduto(produtoId));
    }
}