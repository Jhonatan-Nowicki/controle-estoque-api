package com.jhonatan.estoque.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovimentacaoEstoqueRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    @Positive
    private Integer quantidade;

    private String observacao;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}