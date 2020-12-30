package com.example.back.repositories;

import com.example.back.models.LabelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelIdRepository extends JpaRepository<LabelId, Integer> {
}
