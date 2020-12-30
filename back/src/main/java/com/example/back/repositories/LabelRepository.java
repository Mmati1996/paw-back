package com.example.back.repositories;

import com.example.back.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository <Label, Integer> {
}
