package com.example.datnsd56.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Image {
    private Integer id;
    private String url;

    private Products productsId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id && Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    public Products getProductsId() {
        return productsId;
    }

    public void setProductsId(Products products) {
        this.productsId = products;
    }
}
