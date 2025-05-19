package com.example.matrix.rotation.service.service;

import com.example.matrix.rotation.service.service.exception.InvalidRotationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixRotationServiceTest {

    private MatrixRotationService service;

    @BeforeEach
    void setUp() {
        service = new MatrixRotationService();
    }

    @Nested
    @DisplayName("Valid rotations")
    class ValidRotations {

        @Test
        @DisplayName("2×2 matrix rotated by 1")
        void rotate2x2ByOne() {
            List<List<Integer>> matrix = List.of(
                    new ArrayList<>(List.of(1, 2)),
                    new ArrayList<>(List.of(3, 4))
            );
            var rotated = service.rotateMatrix(matrix, 1);

            assertEquals(2, rotated.get(0).get(0));
            assertEquals(4, rotated.get(0).get(1));
            assertEquals(3, rotated.get(1).get(1));
            assertEquals(1, rotated.get(1).get(0));
        }

        @Test
        @DisplayName("3×3 matrix rotated by 2")
        void rotate3x3ByTwo() {
            var matrix = new ArrayList<List<Integer>>();
            matrix.add(new ArrayList<>(List.of(1, 2, 3)));
            matrix.add(new ArrayList<>(List.of(4, 5, 6)));
            matrix.add(new ArrayList<>(List.of(7, 8, 9)));

            var rotated = service.rotateMatrix(matrix, 2);

            assertEquals(List.of(3, 6, 9), rotated.get(0));
            assertEquals(List.of(2, 5, 8), rotated.get(1));
            assertEquals(List.of(1, 4, 7), rotated.get(2));
        }
    }

    @Nested
    @DisplayName("No-op and empty inputs")
    class NoOpOrEmpty {

        @Test
        @DisplayName("0 rotations returns same instance")
        void zeroRotationsReturnsSame() {
            var matrix = new ArrayList<List<Integer>>();
            matrix.add(new ArrayList<>(List.of(10, 20)));
            matrix.add(new ArrayList<>(List.of(30, 40)));

            var result = service.rotateMatrix(matrix, 0);
            assertSame(matrix, result);
        }

        @Test
        @DisplayName("Empty matrix returns empty list")
        void emptyMatrix() {
            var empty = Collections.<List<Integer>>emptyList();
            var result = service.rotateMatrix(empty, 5);
            assertTrue(CollectionUtils.isEmpty(result));
        }

        @Test
        @DisplayName("Null matrix returns empty list")
        void nullMatrix() {
            var result = service.rotateMatrix(null, 3);
            assertTrue(CollectionUtils.isEmpty(result));
        }
    }

    @Nested
    @DisplayName("Invalid rotation counts")
    class InvalidRotations {

        @Test
        @DisplayName("Negative rotation throws")
        void negativeThrows() {
            List<List<Integer>> matrix = List.of(List.of(1, 2, 3));
            var ex = assertThrows(
                    InvalidRotationException.class,
                    () -> service.rotateMatrix(matrix, -1)
            );
            assertTrue(ex.getMessage().contains("non-negative"));
        }
    }

}