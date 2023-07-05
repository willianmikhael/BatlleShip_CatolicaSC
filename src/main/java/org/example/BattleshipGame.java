package org.example;

import java.io.Serializable;

public class BattleshipGame implements Serializable {
    private static final int BOARD_SIZE = 10;
    private static final int EMPTY = 0;
    private static final int SHIP = 1;
    private static final int HIT = 2;
    private static final int MISS = 3;

    private int[][] boardPlayer1 = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] boardPlayer2 = new int[BOARD_SIZE][BOARD_SIZE];

    private int currentPlayer;
    private boolean gameOver;

    public void initBoards() {
        // Inicializa os tabuleiros dos jogadores com água (EMPTY)
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardPlayer1[i][j] = EMPTY;
                boardPlayer2[i][j] = EMPTY;
            }
        }
    }

    public void placeShip(int playerNumber, int row, int col) {
        int[][] board;

        if (playerNumber == 1) {
            board = boardPlayer1;
        } else {
            board = boardPlayer2;
        }

        board[row][col] = SHIP;
    }


    public void initPlayer(int playerNumber) {
        currentPlayer = playerNumber;
        gameOver = false;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void takeTurn(int row, int col) {
        int[][] currentBoard;
        int[][] opponentBoard;

        if (currentPlayer == 1) {
            currentBoard = boardPlayer1;
            opponentBoard = boardPlayer2;
        } else {
            currentBoard = boardPlayer2;
            opponentBoard = boardPlayer1;
        }

        if (opponentBoard[row][col] == SHIP) {
            System.out.println("Jogador " + currentPlayer + ", você acertou um navio!");
            opponentBoard[row][col] = HIT;
        } else {
            System.out.println("Jogador " + currentPlayer + ", você errou o tiro.");
            opponentBoard[row][col] = MISS;
        }

        currentPlayer = currentPlayer == 1 ? 2 : 1;
        if (isGameOver()) {
            gameOver = true;
        }
    }

    public void updateOpponentBoard(int[][] opponentBoard) {
        if (currentPlayer == 1) {
            boardPlayer2 = opponentBoard;
        } else {
            boardPlayer1 = opponentBoard;
        }
    }

    public int[][] getBoardState() {
        if (currentPlayer == 1) {
            return boardPlayer1;
        } else {
            return boardPlayer2;
        }
    }
}
