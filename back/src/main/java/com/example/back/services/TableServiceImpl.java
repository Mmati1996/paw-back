package com.example.back.services;

import com.example.back.models.Table;
import com.example.back.repositories.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService {

    private TableRepository tableRepository;

    public TableServiceImpl(){
    }

    public TableServiceImpl(TableRepository tableRepository){
        this.tableRepository = tableRepository;
    }

    @Override
    public Table getTableById(int id) {
        return tableRepository.findById(id).get();
    }

    @Override
    public String getName(int id) {
        return tableRepository.findById(id).get().getName();
    }

    @Override
    public void addTable(Table table) {
        tableRepository.save(table);
    }
}
