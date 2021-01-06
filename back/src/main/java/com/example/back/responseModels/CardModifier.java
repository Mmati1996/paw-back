package com.example.back.responseModels;


import com.example.back.models.Task2;
import lombok.Data;

import java.util.List;

@Data
public class CardModifier {
    List<Task2> param1;
    int param2;
}
