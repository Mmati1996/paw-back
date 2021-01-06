package com.example.back.responseModels;


import com.example.back.models.Task2;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CardModifier {
    ArrayList<Task2> tasks;
    int id;
}
