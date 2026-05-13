package com.jhonatan.estoque.repository;

import com.jhonatan.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoSku(String codigoSku);

    Optional<Produto> findByCodigoSku(String codigoSku);
}