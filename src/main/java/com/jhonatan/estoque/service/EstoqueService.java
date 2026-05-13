package com.jhonatan.estoque.service;

import com.jhonatan.estoque.repository.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;
import com.jhonatan.estoque.model.MovimentacaoEstoque;
import com.jhonatan.estoque.model.Produto;
import com.jhonatan.estoque.model.TipoMovimentacao;
import com.jhonatan.estoque.exception.RegraNegocioException;
import java.util.List;
import com.jhonatan.estoque.dto.MovimentacaoEstoqueRequest;


@Service
public class EstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoRepository;
    private final ProdutoService produtoService;

    public EstoqueService(
            MovimentacaoEstoqueRepository movimentacaoRepository,
            ProdutoService produtoService
    ) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoService = produtoService;
    }

    public MovimentacaoEstoque registrarEntrada(MovimentacaoEstoqueRequest request) {
        Produto produto = produtoService.buscarPorId(request.getProdutoId());

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(TipoMovimentacao.ENTRADA);
        movimentacao.setQuantidade(request.getQuantidade());
        movimentacao.setObservacao(request.getObservacao());

        return movimentacaoRepository.save(movimentacao);
    }

    public MovimentacaoEstoque registrarSaida(MovimentacaoEstoqueRequest request) {
        Produto produto = produtoService.buscarPorId(request.getProdutoId());

        Integer saldoAtual = consultarSaldo(produto.getId());

        if (saldoAtual < request.getQuantidade()) {
            throw new RegraNegocioException("Saldo insuficiente para realizar a saída.");
        }

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(TipoMovimentacao.SAIDA);
        movimentacao.setQuantidade(request.getQuantidade());
        movimentacao.setObservacao(request.getObservacao());

        return movimentacaoRepository.save(movimentacao);
    }

    public Integer consultarSaldo(Long produtoId) {
        produtoService.buscarPorId(produtoId);

        return movimentacaoRepository.findByProdutoId(produtoId)
                .stream()
                .mapToInt(movimentacao -> {
                    if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
                        return movimentacao.getQuantidade();
                    }

                    return -movimentacao.getQuantidade();
                })
                .sum();
    }

    public List<MovimentacaoEstoque> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }

    public List<MovimentacaoEstoque> listarMovimentacoesPorProduto(Long produtoId) {
        produtoService.buscarPorId(produtoId);
        return movimentacaoRepository.findByProdutoId(produtoId);
    }
}