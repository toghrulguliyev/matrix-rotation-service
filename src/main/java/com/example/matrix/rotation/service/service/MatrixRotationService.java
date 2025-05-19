package com.example.matrix.rotation.service.service;

import com.example.matrix.rotation.service.service.exception.InvalidRotationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Service
public class MatrixRotationService {

    /**
     * This method rotates the matrix by layers. It rotates the outermost layer first, then the next layer, and so on.
     *
     * @param matrix the matrix to be rotated
     * @param r      the number of rotations
     */
    public List<List<Integer>> rotateMatrix(List<List<Integer>> matrix, int r) {
        if (CollectionUtils.isEmpty(matrix)) {
            return Collections.emptyList();
        }
        if (r < 0) {
            throw new InvalidRotationException("Rotation count must be non-negative, got: " + r);
        }
        if (r == 0) {
            return matrix;
        }
        int rowSize = matrix.size();
        int columnSize = matrix.get(0).size();
        int layers = Math.min(rowSize, columnSize) / 2;

        for (int layer = 0; layer < layers; layer++) {
            int heightSteps = rowSize - 1 - 2 * layer;
            int widthSteps = columnSize - 1 - 2 * layer;
            int layerLength = 2 * (widthSteps + heightSteps);

            int requiredRotation = r % layerLength;
            if (requiredRotation == 0) {
                continue;
            }

            int cycles = greatestCommonDivisor(layerLength, requiredRotation);
            for (int index = 0; index < cycles; index++) {
                rotateCycleValues(matrix, requiredRotation, layerLength, layer, index);
            }
        }

        matrix.stream()
                .map(row -> row.stream()
                        .map(String::valueOf)
                        .collect(joining(" ")))
                .forEach(System.out::println);

        return matrix;
    }

    /**
     * This method returns the greatest common divisor of layerLength and requiredRotations. Euclidean algorithm is used to find the GCD.
     * Result represents the number of cycles in a layer.
     *
     * @param layerLength       first number
     * @param requiredRotations second number
     * @return cycles in a layer
     */
    private static int greatestCommonDivisor(int layerLength, int requiredRotations) {
        return requiredRotations == 0 ? layerLength : greatestCommonDivisor(requiredRotations, layerLength % requiredRotations);
    }

    /**
     * This method rotates the values in a cycle of the matrix layer.
     *
     * @param matrix           the matrix to be rotated
     * @param requiredRotation the number of rotations required
     * @param layerLength      the length of the layer
     * @param layer            the current layer
     * @param initialIndex     the initialIndex of the current value
     */
    private static void rotateCycleValues(List<List<Integer>> matrix, int requiredRotation, int layerLength, int layer,
                                          int initialIndex) {
        int previousIndex = initialIndex;
        int previousValue = getLayerValue(matrix, layer, initialIndex);
        do {
            int rotatedPositionIndex = (previousIndex - requiredRotation + layerLength) % layerLength;
            int rotatedPositionValue = getLayerValue(matrix, layer, rotatedPositionIndex);
            setLayerValue(matrix, layer, rotatedPositionIndex, previousValue);

            previousValue = rotatedPositionValue;
            previousIndex = rotatedPositionIndex;
        } while (previousIndex != initialIndex);
    }

    /**
     * This method retrieves the value at a specific 1D index in a layer of the 2D matrix.
     *
     * @param matrix the matrix to be rotated
     * @param layer  the current layer
     * @param idx    the index of the value to be retrieved
     * @return the value at the specified 1D index in the layer
     */
    private static int getLayerValue(List<List<Integer>> matrix, int layer, int idx) {
        int[] coordinates = locateCellCoordinates(matrix, layer, idx);
        return matrix.get(coordinates[0]).get(coordinates[1]);
    }

    /**
     * This method sets the value at a specific 1D index in a layer of the 2D matrix.
     *
     * @param matrix the matrix to be rotated
     * @param layer  the current layer
     * @param idx    the index of the value to be set
     * @param val    the value to be set at the specified index in the layer
     */
    private static void setLayerValue(List<List<Integer>> matrix, int layer, int idx, int val) {
        int[] coordinates = locateCellCoordinates(matrix, layer, idx);
        matrix.get(coordinates[0]).set(coordinates[1], val);
    }

    /**
     * This method locates the coordinates of a cell in a layer of the matrix based on the given 1D index.
     * Basically, it converts the 1D index to 2D coordinates (row and column) in the layer.
     *
     * @param matrix the 2D matrix
     * @param layer  the cycled layer of the matrix
     * @param index  the 1D index of the value to be located
     * @return an array containing the row and column indexes of the cell in the layer
     */
    private static int[] locateCellCoordinates(List<List<Integer>> matrix, int layer, int index) {
        int rowSize = matrix.size();
        int colSize = matrix.get(0).size();

        int top = layer;
        int left = layer;
        int bottom = rowSize - 1 - layer;
        int right = colSize - 1 - layer;

        int width = right - left;
        int height = bottom - top;

        int topLength = width + 1;
        int rightLength = height - 1;
        int bottomLength = width + 1;

        if (index < topLength) {
            return new int[]{top, left + index};
        }
        index -= topLength;

        if (index < rightLength) {
            return new int[]{top + 1 + index, right};
        }
        index -= rightLength;

        if (index < bottomLength) {
            return new int[]{bottom, right - index};
        }
        index -= bottomLength;

        return new int[]{bottom - 1 - index, left};
    }

}
