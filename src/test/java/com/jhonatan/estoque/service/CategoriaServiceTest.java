package com.jhonatan.estoque.service;

import com.jhonatan.estoque.exception.ConflitoException;
import com.jhonatan.estoque.exception.RecursoNaoEncontradoException;
import com.jhonatan.estoque.exception.RegraNegocioException;
import com.jhonatan.estoque.model.Categoria;
import com.jhonatan.estoque.repository.CategoriaRepository;
import com.jhonatan.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveCriarCategoriaQuandoNomeNaoExistir() {

        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.existsByNome("Eletrônicos"))
                .thenReturn(false);

        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoria);

        Categoria resultado = categoriaService.criarCategoria(categoria);

        assertNotNull(resultado);
        assertEquals("Eletrônicos", resultado.getNome());

        verify(categoriaRepository).save(categoria);
    }

    @Test
    void deveLancarConflitoQuandoNomeJaExistir() {

        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.existsByNome("Eletrônicos"))
                .thenReturn(true);

        assertThrows(ConflitoException.class, () -> {
            categoriaService.criarCategoria(categoria);
        });

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveBloquearDesativacaoQuandoExistiremProdutosAtivos() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.findById(1L))
                .thenReturn(java.util.Optional.of(categoria));

        when(produtoRepository.existsByCategoriaIdAndAtivoTrue(1L))
                .thenReturn(true);

        assertThrows(RegraNegocioException.class, () -> {
            categoriaService.desativarCategoria(1L);
        });

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.findById(1L))
                .thenReturn(java.util.Optional.of(categoria));

        Categoria resultado = categoriaService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Eletrônicos", resultado.getNome());
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoForEncontrada() {
        when(categoriaRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaService.buscarPorId(99L);
        });
    }
}
