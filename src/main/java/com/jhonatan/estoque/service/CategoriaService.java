package com.jhonatan.estoque.service;

import com.jhonatan.estoque.model.Categoria;
import com.jhonatan.estoque.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import com.jhonatan.estoque.exception.ConflitoException;
import com.jhonatan.estoque.exception.RecursoNaoEncontradoException;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria criarCategoria(Categoria categoria){
        if(categoriaRepository.existsByNome(categoria.getNome())){
            throw new ConflitoException("Já existe uma categoria com esse nome.");
        }
        categoria.setAtivo(true);

        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findByAtivoTrue();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
    }
    public Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada) {

        Categoria categoria = buscarPorId(id);

        if (!categoria.getNome().equals(categoriaAtualizada.getNome())
                && categoriaRepository.existsByNome(categoriaAtualizada.getNome())) {
            throw new ConflitoException("Já existe uma categoria com esse nome.");
        }

        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setDescricao(categoriaAtualizada.getDescricao());

        return categoriaRepository.save(categoria);
    }
    public void desativarCategoria(Long id) {
        Categoria categoria = buscarPorId(id);

        categoria.setAtivo(false);

        categoriaRepository.save(categoria);
    }
}