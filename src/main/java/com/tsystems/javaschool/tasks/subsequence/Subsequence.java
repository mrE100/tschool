package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        if (y.size() < x.size()) {
            return false;
        }
        int startPosition = 0;
        int count = 0;
        for (int i = 0; i < x.size(); i++) {
            if (y.size() - startPosition < x.size() - i) {
                return false;
            }
            for (int j = startPosition; j <y.size(); j++) {
                if (y.get(j).equals(x.get(i))) {
                    startPosition = j;
                    count++;
                    break;
                }
            }
        }
        if (count == x.size()) {
            return true;
        }
        return false;
    }
}
