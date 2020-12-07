package com.example.back.repositories;

import com.example.back.models.TrelloList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloListRepository extends JpaRepository<TrelloList, Integer> {
}
