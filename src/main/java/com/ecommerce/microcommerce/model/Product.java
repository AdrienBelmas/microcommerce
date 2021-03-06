package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

//@JsonIgnoreProperties(value = {"id", "prixAchat"})
//@JsonFilter("monFiltreDynamique")
@Entity
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @Length(min=3, max=20, message="Le format du nom n'est pas respecté (entre 3 et 20 caractères)")
    private String nom;

    //@Min(value=1) conflit avec l'exception du TP 7
    private double prix;

    //@Min(value=1) conflit avec l'exception du TP 7
    private double prixAchat;

    public Product() {
    }

    public Product(int id, String nom, double prix, double prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", nom='" + nom + "', prix=" + prix + "}";
    }
}