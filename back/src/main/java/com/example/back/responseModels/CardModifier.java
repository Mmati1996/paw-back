package com.example.back.responseModels;


import com.example.back.models.TaskBuilder;
import lombok.Data;

import java.util.List;

@Data
public class CardModifier {
    public List<TaskBuilder> param1;
    public int param2;
}
