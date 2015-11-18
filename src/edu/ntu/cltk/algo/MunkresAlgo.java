package edu.ntu.cltk.algo;

import edu.ntu.cltk.data.PrimUtil;

public class MunkresAlgo {

    private int munkresStep = 1;
    private int munkresRowCount;
    private int munkresColCount;
    private double[][] munkresMatrix;
    private double[][] origMunkresMatrix;
    private int[][] munkresMark;
    private int[] munkresRowCover;
    private int[] munkresColCover;
    private int pathRow0 = 0;
    private int pathCol0 = 0;
    private int[][] path;
    private int pathCount;
    private int matchingCount = 0;

    /**
     * Find the maximal Assignment
     *
     * @param matrix
     * @return
     */
    public double maximalAssignment(double[][] matrix) {
        if (MatrixUtil.isEmpty(matrix)) {
            return 0;
        }
        double[][] rep = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rep[i][j] = -1 * matrix[i][j];
            }
        }
        return -1 * minimalAssignment(rep);
    }

    /**
     * Find the minimal assignment
     *
     * @param matrix
     * @return
     */
    public double minimalAssignment(double[][] matrix) {
        if (MatrixUtil.isEmpty(matrix)) {
            return 0;
        }
        munkresRowCount = matrix.length;
        munkresColCount = matrix[0].length;
        munkresMatrix = matrix;
        origMunkresMatrix = new double[munkresRowCount][munkresColCount];
        munkresMark = new int[munkresRowCount][munkresColCount];
        munkresRowCover = new int[munkresRowCount];
        munkresColCover = new int[munkresColCount];
        path = new int[munkresRowCount + munkresColCount][2];

        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++)
                origMunkresMatrix[i][j] = munkresMatrix[i][j];
        }
        return runMunkres();
    }

    private void showCostMatrix() {
        System.out.println("\n");
        System.out.println(String.format("------------Step %d-------------", munkresStep));
        for (int i = 0; i < munkresRowCount; i++) {
            System.out.println();
            System.out.print("     ");
            for (int j = 0; j < munkresColCount; j++) {
                System.out.print(String.format("%5d", munkresMatrix[i][j]));
            }
        }
    }

    private void showMaskMatrix() {
        System.out.println();
        System.out.print("\n     ");
        for (int i = 0; i < munkresColCount; i++)
            System.out.print(String.format("%5d", munkresColCover[i]));
        for (int i = 0; i < munkresRowCount; i++) {
            System.out.print("\n  " + munkresRowCover[i] + "  ");
            for (int j = 0; j < munkresColCount; j++) {
                System.out.print(String.format("%5d", munkresMark[i][j]));
            }
        }
    }

    private double runMunkres() {
        boolean done = false;
        int step = 1;
        while (!done) {
            //showCostMatrix();
            //showMaskMatrix();
            switch (munkresStep) {
                case 1:
                    munkresStepOne();
                    break;
                case 2:
                    munkresStepTwo();
                    break;
                case 3:
                    munkresStepThree();
                    break;
                case 4:
                    munkresStepFour();
                    break;
                case 5:
                    munkresStepFive();
                    break;
                case 6:
                    munkresStepSix();
                    break;
                case 7:
                    //munkresStepSeven();
                    done = true;
                    break;

            }
        }
        double ret = 0;
        matchingCount = 0;
        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++) {
                if (munkresMark[i][j] == 1) {
                    ret += origMunkresMatrix[i][j];
                    matchingCount++;
                }
            }
        }
        return ret;
    }

    /**
     * Get the matching count
     *
     * @return
     */
    public int getMatchingCount() {
        return this.matchingCount;
    }

    /**
     * Step 1:  For each row of the matrix, find the smallest element <br/>
     * and subtract it from every element in its row.  Go to Step 2.
     */
    private void munkresStepOne() {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < munkresRowCount; i++) {
            min = munkresMatrix[i][0];
            for (int j = 0; j < munkresColCount; j++) {
                min = Math.min(min, munkresMatrix[i][j]);
            }
            for (int j = 0; j < munkresColCount; j++) {
                munkresMatrix[i][j] -= min;
            }
        }
        this.munkresStep = 2;
    }

    /**
     * Step 2:  Find a zero (Z) in the resulting matrix.  <br/>
     * If there is no starred zero in its row or column, star Z. <br/>
     * Repeat for each element in the matrix. Go to Step 3.
     */
    private void munkresStepTwo() {
        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++) {
                if (PrimUtil.equalsToZero(munkresMatrix[i][j]) && munkresRowCover[i] == 0
                        && munkresColCover[j] == 0) {
                    munkresMark[i][j] = 1;
                    munkresRowCover[i] = 1;
                    munkresColCover[j] = 1;
                }
            }
        }
        for (int i = 0; i < munkresRowCount; i++) {
            munkresRowCover[i] = 0;
        }
        for (int i = 0; i < munkresColCount; i++) {
            munkresColCover[i] = 0;
        }
        munkresStep = 3;
    }

    /**
     * Step 3:  Cover each column containing a starred zero.  <br/>
     * If K columns are covered, the starred zeros describe a <br/>
     * complete set of unique assignments.  In this case, Go to DONE, <br/>
     * otherwise, Go to Step 4.
     */
    private void munkresStepThree() {
        int colCount = 0;
        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++) {
                if (munkresMark[i][j] == 1)
                    munkresColCover[j] = 1;
            }
        }
        for (int i = 0; i < munkresColCount; i++) {
            if (munkresColCover[i] == 1)
                colCount++;
        }
        if (colCount >= munkresColCount || colCount >= munkresRowCount)
            munkresStep = 7;
        else
            munkresStep = 4;
    }

    /**
     * Step 4:  Find a noncovered zero and prime it.  <br/>
     * If there is no starred zero in the row containing this primed zero, Go to Step 5.  <br/>
     * Otherwise, cover this row and uncover the column containing the starred zero. <br/>
     * Continue in this manner until there are no uncovered zeros left. <br/>
     * Save the smallest uncovered value and Go to Step 6.
     */
    private void munkresStepFour() {
        int row = -1;
        int col = -1;
        boolean done = false;
        while (!done) {
            int[] cor = findAZero();
            row = cor[0];
            col = cor[1];
            if (row == -1) {
                done = true;
                munkresStep = 6;
            } else {
                munkresMark[row][col] = 2;
                if (starInRow(row)) {
                    col = findStarInRow(row);
                    munkresRowCover[row] = 1;
                    munkresColCover[col] = 0;
                } else {
                    done = true;
                    munkresStep = 5;
                    pathRow0 = row;
                    pathCol0 = col;
                }
            }
        }
    }

    /**
     * In this step, statements such as "find a noncovered zero" are <br/>
     * clearly distinct operations that deserve their own functional blocks.  <br/>
     * We have decomposed this step into a main method and three subprograms <br/>
     * (2 methods and a boolean function).
     *
     * @return
     */
    private int[] findAZero() {
        int r = 0;
        int c = 0;
        int row = -1;
        int col = -1;
        boolean done = false;
        while (!done) {
            c = 0;
            while (true) {
                if (PrimUtil.equalsToZero(munkresMatrix[r][c]) && munkresRowCover[r] == 0
                        && munkresColCover[c] == 0) {
                    row = r;
                    col = c;
                    done = true;
                }
                c += 1;
                if (c >= munkresColCount || done)
                    break;
            }
            r += 1;
            if (r >= munkresRowCount)
                done = true;
        }
        return new int[]{row, col};
    }

    private boolean starInRow(int row) {
        boolean tmp = false;
        for (int i = 0; i < munkresColCount; i++) {
            if (munkresMark[row][i] == 1)
                tmp = true;
        }
        return tmp;
    }

    private int findStarInRow(int row) {
        int col = -1;
        for (int i = 0; i < munkresColCount; i++) {
            if (munkresMark[row][i] == 1)
                col = i;
        }
        return col;
    }

    /**
     * Step 5:  Construct a series of alternating primed and starred zeros as follows.  <br/>
     * Let Z0 represent the uncovered primed zero found in Step 4.  <br/>
     * Let Z1 denote the starred zero in the column of Z0 (if any). <br/>
     * Let Z2 denote the primed zero in the row of Z1 (there will always be one).  <br/>
     * Continue until the series terminates at a primed zero that has no starred zero in its column.  <br/>
     * Unstar each starred zero of the series, star each primed zero of the series, <br/>
     * erase all primes and uncover every line in the matrix.  Return to Step 3.
     */
    private void munkresStepFive() {
        boolean done = false;
        int r = -1;
        int c = -1;
        pathCount = 1;
        path[pathCount - 1][0] = pathRow0;
        path[pathCount - 1][1] = pathCol0;
        while (!done) {
            r = findStarInCol(path[pathCount - 1][1]);
            if (r > -1) {
                pathCount += 1;
                path[pathCount - 1][0] = r;
                path[pathCount - 1][1] = path[pathCount - 2][1];
            } else {
                done = true;
            }
            if (!done) {
                c = findPrimeInRow(path[pathCount - 1][0], c);
                pathCount++;
                path[pathCount - 1][0] = path[pathCount - 2][0];
                path[pathCount - 1][1] = c;
            }
        }
        augmentPath();
        clearCovers();
        erasePrimes();
        munkresStep = 3;
    }

    private int findStarInCol(int c) {
        int r = -1;
        for (int i = 0; i < munkresRowCount; i++) {
            if (munkresMark[i][c] == 1)
                r = i;
        }
        return r;
    }

    private int findPrimeInRow(int r, int c) {
        for (int i = 0; i < munkresColCount; i++) {
            if (munkresMark[r][i] == 2)
                c = i;
        }
        return c;
    }

    private void augmentPath() {
        for (int i = 0; i < pathCount; i++) {
            if (munkresMark[path[i][0]][path[i][1]] == 1)
                munkresMark[path[i][0]][path[i][1]] = 0;
            else
                munkresMark[path[i][0]][path[i][1]] = 1;
        }
    }

    private void clearCovers() {
        for (int i = 0; i < munkresRowCount; i++)
            munkresRowCover[i] = 0;
        for (int i = 0; i < munkresColCount; i++)
            munkresColCover[i] = 0;
    }

    private void erasePrimes() {
        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++)
                if (munkresMark[i][j] == 2)
                    munkresMark[i][j] = 0;
        }
    }

    /**
     * Step 6:  Add the value found in Step 4 to every element of each covered row, <br/>
     * and subtract it from every element of each uncovered column. <br/>
     * Return to Step 4 without altering any stars, primes, or covered lines.
     */
    private void munkresStepSix() {
        double minVal = findSmallest();
        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++) {
                if (munkresRowCover[i] == 1)
                    munkresMatrix[i][j] += minVal;
                if (munkresColCover[j] == 0)
                    munkresMatrix[i][j] -= minVal;
            }
        }
        munkresStep = 4;
    }

    private double findSmallest() {
        double minVal = Double.MAX_VALUE;
        for (int i = 0; i < munkresRowCount; i++) {
            for (int j = 0; j < munkresColCount; j++) {
                if (munkresRowCover[i] == 0 && munkresColCover[j] == 0)
                    minVal = Math.min(minVal, munkresMatrix[i][j]);
            }
        }
        return minVal;
    }
}
