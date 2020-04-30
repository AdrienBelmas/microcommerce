package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findAll();

    Product findById(int id);

    List<Product> findByPrixGreaterThan(double prixLimit);

    List<Product> findByNomLike(String nomRecherche);

    List<Product> findByOrderByNomAsc();

    Product save(Product product);

    void deleteById(int id);

    void deleteAll();

    @Query("SELECT p FROM Product p WHERE p.prix < :prixLimit")
    List<Product> priceLowerThan(@Param("prixLimit") double prix);
}