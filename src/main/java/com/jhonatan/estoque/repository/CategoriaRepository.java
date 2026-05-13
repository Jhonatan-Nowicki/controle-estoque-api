package com.jhonatan.estoque.repository;

import com.jhonatan.estoque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
    List<Categoria> findByAtivoTrue();
}
