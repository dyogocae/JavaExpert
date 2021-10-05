package io.github.HomeSec.domain.repository;

import io.github.HomeSec.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
