package com.example.datnsd56.repository;

import com.example.datnsd56.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepository extends JpaRepository<Material, Integer> {
}
