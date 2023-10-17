package com.example.datnsd56.repository;

import com.example.datnsd56.entity.ShoeSole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeSoleRepository extends JpaRepository<ShoeSole, Integer> {
}
