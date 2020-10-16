package life;

import java.util.Arrays;
import java.util.Random;

public class GameOfLifeModel {

    protected static int size;
    int[][] grid;
    int aliveCells = 0;
    int generation = 0;

    GameOfLifeModel(int size) {
        GameOfLifeModel.size = size;
        initializeLife();
    }

    private void initializeLife() {
        grid = new int[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    grid[i][j] = 1;
                    aliveCells++;
                } else grid[i][j] = 0;
            }
        }
    }

    private int countNeighbours (int x, int y) {
        int aliveNeighbours = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                int col = (x + i + size) % size;
                int row = (y + j + size) % size;
                aliveNeighbours += grid[col][row];
            }

        aliveNeighbours -= grid[x][y];
        return aliveNeighbours;
    }

    void nextGeneration() {

        int[][] futureGrid = new int[size][size];
        aliveCells = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int aliveNeighbours = countNeighbours(i, j);

                if (grid[i][j] == 1 && (aliveNeighbours < 2 || aliveNeighbours > 3))
                    futureGrid[i][j] = 0;
                else if (grid[i][j] == 0 && aliveNeighbours == 3) {
                    futureGrid[i][j] = 1;
                    aliveCells++;
                } else {
                    futureGrid[i][j] = grid[i][j];
                    if (grid[i][j] == 1) aliveCells++;
                }

            }
        }
        grid = Arrays.stream(futureGrid).map(int[]::clone).toArray(int[][]::new);
        generation++;
    }
}

