package com.example.back.services;

import com.example.back.models.Label;
import com.example.back.repositories.LabelRepository;

public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

    public LabelServiceImpl() {
    }

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Label getLabelById(int id) {
        return labelRepository.findById(id).get();
    }

    @Override
    public String getName(int id) {
        return labelRepository.findById(id).get().getName();
    }
}
