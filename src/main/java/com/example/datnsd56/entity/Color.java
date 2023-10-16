package com.example.datnsd56.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Color")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    private Integer id;
    private int id;
    private String code;
    private String name;
    private boolean status;
    private Timestamp createDate;
    private Timestamp updateDate;


    @Id
    @Column(name = "id")

    public Integer getId() {
        return id;
    }

    private Integer id;


    @Column(name = "code")

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    private String code;


    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;


    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return id == color.id && status == color.status && Objects.equals(code, color.code) && Objects.equals(name, color.name) && Objects.equals(createDate, color.createDate) && Objects.equals(updateDate, color.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, status, createDate, updateDate);
    }



}
