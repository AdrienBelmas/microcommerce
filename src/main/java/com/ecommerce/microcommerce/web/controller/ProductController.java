package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;

    //afficher tous les produits
    @GetMapping(value="/Produits")
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    //afficher tous les produits
    /*@GetMapping(value="/Produits")
    public MappingJacksonValue listeProduits() {
        List<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }*/

    //afficher un produit
    @GetMapping(value="/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product p = productDao.findById(id);
        if (p == null)
            throw new ProductNotFoundException("CA EXISTE PAS GROS FDP");
        return p;
    }

    //recherche pas nom
    @GetMapping(value="/Produits/nom/{recherche}")
    public List<Product> rechercherUnNom(@PathVariable String recherche) {
        return productDao.findByNomLike("%" + recherche + "%");
    }

    //recherche pas prix
    @GetMapping(value="/Produits/prix/{prixLimit}")
    public List<Product> rechercherUnPrix(@PathVariable double prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    //recherche pas prix
    @GetMapping(value="/Produits/petitPrix/{prixLimit}")
    public List<Product> rechercherUnPetitPrix(@PathVariable double prixLimit) {
        return productDao.priceLowerThan(prixLimit);
    }

    //calcul de marge
    @GetMapping(value="/AdminProduits")
    public Map<Product, Double> calculerMargeProduit() {
        Map m = new HashMap<Product, Double>();
        for(Product p : productDao.findAll())
            m.put(p, p.getPrix() - p.getPrixAchat());
        return m;
    }

    //ajouter un produit
    @PostMapping(value="/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
        Product p = productDao.save(product);
        if (p == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //modifier un produit
    @PutMapping(value="/Produits")
    public Product modifierProduit(@Valid @RequestBody Product product) {
        return productDao.save(product);
    }

    //supprimer un produit
    @DeleteMapping(value="/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.deleteById(id);
    }
}