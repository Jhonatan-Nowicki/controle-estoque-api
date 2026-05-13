package com.jhonatan.estoque.service;

import com.jhonatan.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import com.jhonatan.estoque.exception.ConflitoException;
import com.jhonatan.estoque.model.Categoria;
import com.jhonatan.estoque.model.Produto;
import com.jhonatan.estoque.exception.RecursoNaoEncontradoException;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public Produto criarProduto(Produto produto) {

        if (produtoRepository.existsByCodigoSku(produto.getCodigoSku())) {
            throw new ConflitoException("Já existe um produto com esse SKU.");
        }

        Categoria categoria = categoriaService.buscarPorId(produto.getCategoria().getId());

        produto.setCategoria(categoria);
        produto.setAtivo(true);

        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
    }

    public Produto buscarPorSku(String sku) {
        return produtoRepository.findByCodigoSku(sku)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .filter(Produto::getAtivo)
                .toList();
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id);

        if (!produto.getCodigoSku().equals(produtoAtualizado.getCodigoSku())
                && produtoRepository.existsByCodigoSku(produtoAtualizado.getCodigoSku())) {
            throw new ConflitoException("Já existe um produto com esse SKU.");
        }

        Categoria categoria = categoriaService.buscarPorId(produtoAtualizado.getCategoria().getId());

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setCodigoSku(produtoAtualizado.getCodigoSku());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidadeMinima(produtoAtualizado.getQuantidadeMinima());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public void desativarProduto(Long id) {
        Produto produto = buscarPorId(id);

        produto.setAtivo(false);

        produtoRepository.save(produto);
    }
}