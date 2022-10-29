package com.example.gameoflife2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static final Random RANDOM = new Random();
    public int width,height;
    private Cell[][] board;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Cell[width][height];
        init();
    }

    private void init() {
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Cell(i, j, RANDOM.nextBoolean());
            }
        }
    }

    public Cell get(int i, int j) {
        return board[i][j];
    }

    public int nbNeighboursOf(int i, int j) {
        int nb = 0;

        for (int k = i -1; k <= i +1; k++) {
            for (int l = j - 1; l <= j +1; l++) {
                if ((k != i || l != j) && k >= 0
                        && k < width && l >=0 && l < height) {
                    Cell cell = board[k][l];
                    if (cell.alive) {
                        nb++;
                    }
                }
            }
        }
        return nb;
    }

    public void nextGeneration() {
        List<Cell> liveCells = new ArrayList<Cell>();
        List<Cell> deadCells = new ArrayList<Cell>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = board[i][j];
                int nbNeighbours = nbNeighboursOf(cell.x, cell.y);

                // rule 1 & rule 3
                if (cell.alive &&
                        (nbNeighbours < 2 || nbNeighbours > 3)) {
                    deadCells.add(cell);
                }

                // rule 2 & rule 4
                if ((cell.alive && (nbNeighbours == 3 || nbNeighbours == 2))
                        ||
                        (!cell.alive && nbNeighbours == 3)) {
                    liveCells.add(cell);
                }
            }
        }

        // update future live and dead cells
        for (Cell cell : liveCells) {
            cell.reborn();
        }
        for (Cell cell : deadCells) {
            cell.die();
        }
    }
}


