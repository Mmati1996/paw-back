package com.example.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.back.models.Id;
import org.springframework.stereotype.Repository;

@Repository
public interface IdsRepository extends JpaRepository<Id,Integer> {

}
