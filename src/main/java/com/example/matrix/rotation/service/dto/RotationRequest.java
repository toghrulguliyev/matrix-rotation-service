package com.example.matrix.rotation.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RotationRequest {

    private List<List<Integer>> matrix;
    private int rotations;

}
