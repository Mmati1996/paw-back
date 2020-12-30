package com.example.back.services;

import com.example.back.models.Label;

public interface LabelService {

    Label getLabelById(int id);
    String getName (int id);
}
