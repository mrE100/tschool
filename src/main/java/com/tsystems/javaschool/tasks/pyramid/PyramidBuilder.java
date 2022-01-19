package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        // TODO : Implement your solution here

        int rows = countRows(inputNumbers.size());
        try {
            Collections.sort(inputNumbers);
        } catch (Exception e) {
            throw new CannotBuildPyramidException();
        }
        int lengthOfRow = rows * 2 - 1;
        int[][] result = new int[rows][lengthOfRow];
        int count = 0;
        for (int i = 0; i < rows; i++) {
            int cellToPlace = rows - 1 - i;
            for (int j = 0; j < i + 1; j++) {
                result[i][cellToPlace] = inputNumbers.get(count);
                count++;
                cellToPlace += 2;
            }
        }
        return result;
    }
    private int countRows(int size) throws CannotBuildPyramidException {
        int remainSize = size;
        for (int i = 1; i < size; i++) {
            if (remainSize < i) {
                throw new CannotBuildPyramidException();
            }
            if (remainSize == i) {
                return i;
            }
            remainSize -= i;
        }
        return 1;
    }


}
