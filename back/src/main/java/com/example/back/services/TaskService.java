package com.example.back.services;

import com.example.back.models.Task;

public interface TaskService {
    Task getTableById(int id);
    public String getName(int id);
}
