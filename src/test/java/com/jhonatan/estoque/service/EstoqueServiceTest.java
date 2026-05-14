package com.jhonatan.estoque.service;

import com.jhonatan.estoque.dto.MovimentacaoEstoqueRequest;
import com.jhonatan.estoque.exception.RegraNegocioException;
import com.jhonatan.estoque.model.MovimentacaoEstoque;
import com.jhonatan.estoque.model.Produto;
import com.jhonatan.estoque.model.TipoMovimentacao;
import com.jhonatan.estoque.repository.MovimentacaoEstoqueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private EstoqueService estoqueService;


    @Test
    void deveBloquearSaidaQuandoSaldoForInsuficiente() {
        Produto produto = new Produto();
        produto.setId(1L);

        MovimentacaoEstoque entrada = new MovimentacaoEstoque();
        entrada.setTipo(TipoMovimentacao.ENTRADA);
        entrada.setQuantidade(5);

        MovimentacaoEstoqueRequest request = new MovimentacaoEstoqueRequest();
        request.setProdutoId(1L);
        request.setQuantidade(10);
        request.setObservacao("Tentativa de saída");

        when(produtoService.buscarPorId(1L)).thenReturn(produto);
        when(movimentacaoRepository.findByProdutoId(1L)).thenReturn(List.of(entrada));

        assertThrows(RegraNegocioException.class, () -> {
            estoqueService.registrarSaida(request);
        });

        verify(movimentacaoRepository, never()).save(any(MovimentacaoEstoque.class));
    }

    @Test
    void deveRegistrarEntradaComSucesso() {
        Produto produto = new Produto();
        produto.setId(1L);

        MovimentacaoEstoqueRequest request = new MovimentacaoEstoqueRequest();
        request.setProdutoId(1L);
        request.setQuantidade(10);
        request.setObservacao("Entrada inicial");

        MovimentacaoEstoque movimentacaoSalva = new MovimentacaoEstoque();
        movimentacaoSalva.setProduto(produto);
        movimentacaoSalva.setTipo(TipoMovimentacao.ENTRADA);
        movimentacaoSalva.setQuantidade(10);
        movimentacaoSalva.setObservacao("Entrada inicial");

        when(produtoService.buscarPorId(1L)).thenReturn(produto);
        when(movimentacaoRepository.save(any(MovimentacaoEstoque.class)))
                .thenReturn(movimentacaoSalva);

        MovimentacaoEstoque resultado = estoqueService.registrarEntrada(request);

        assertEquals(TipoMovimentacao.ENTRADA, resultado.getTipo());
        assertEquals(10, resultado.getQuantidade());
        verify(movimentacaoRepository).save(any(MovimentacaoEstoque.class));
    }

    @Test
    void deveConsultarSaldoCorretamente() {
        MovimentacaoEstoque entrada = new MovimentacaoEstoque();
        entrada.setTipo(TipoMovimentacao.ENTRADA);
        entrada.setQuantidade(10);

        MovimentacaoEstoque saida = new MovimentacaoEstoque();
        saida.setTipo(TipoMovimentacao.SAIDA);
        saida.setQuantidade(3);

        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoService.buscarPorId(1L)).thenReturn(produto);
        when(movimentacaoRepository.findByProdutoId(1L))
                .thenReturn(List.of(entrada, saida));

        Integer saldo = estoqueService.consultarSaldo(1L);

        assertEquals(7, saldo);
    }


}