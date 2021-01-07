package com.example.back.services;

import com.example.back.models.Task;
import com.example.back.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl() {
    }

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTableById(int id) {
        return null;
    }

    @Override
    public String getName(int id) {
        return null;
    }
}
