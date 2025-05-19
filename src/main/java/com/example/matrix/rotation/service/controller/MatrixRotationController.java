package com.example.matrix.rotation.service.controller;

import com.example.matrix.rotation.service.dto.RotationRequest;
import com.example.matrix.rotation.service.dto.RotationResponse;
import com.example.matrix.rotation.service.service.MatrixRotationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rotate-matrix")
@Tag(name = "Matrix Rotation", description = "Rotate a 2D matrix by layers")
@RequiredArgsConstructor
public class MatrixRotationController {

    private final MatrixRotationService service;

    @PostMapping
    @Operation(summary = "Rotate matrix layers",
            description = "Accepts a JSON matrix and rotation count, returns the rotated matrix.")
    public ResponseEntity<RotationResponse> rotate(@RequestBody RotationRequest req) {
        if (req.getMatrix() == null) {
            return ResponseEntity.badRequest().build();
        }
        RotationResponse resp = new RotationResponse();
        resp.setRotatedMatrix(service.rotateMatrix(req.getMatrix(), req.getRotations()));
        return ResponseEntity.ok(resp);
    }

}
