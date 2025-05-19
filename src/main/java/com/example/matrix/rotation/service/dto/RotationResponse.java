package com.example.matrix.rotation.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RotationResponse {

    private List<List<Integer>> rotatedMatrix;

}
