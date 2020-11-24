package com.example.back.controllers;

import com.example.back.models.Table;
import com.example.back.repositories.TableRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TableController {

    TableRepository tableRepository;

    public TableController(TableRepository repository) {
        this.tableRepository = repository;
    }

    @GetMapping("/tables/all")
    public Iterable<Table> getAllTables(){
        return tableRepository.findAll();
    }
}
