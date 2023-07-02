package org.example;

import java.util.Scanner;

public class BattleshipGame {
    private static final int BOARD_SIZE = 10;
    private static final int EMPTY = 0;
    private static final int SHIP = 1;
    private static final int HIT = 2;
    private static final int MISS = 3;

    private static final int AMOUNT = 1;

    private int[][] boardPlayer1 = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] boardPlayer2 = new int[BOARD_SIZE][BOARD_SIZE];

    public void printBoard(int[][] board) {
        System.out.print("");
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i][0] = 0;
            System.out.print(board[i][0]);
        }
        System.out.println("\n");

        for (int i = 0; i < BOARD_SIZE; i++) {
            int a = 65;
            a += i;
            char ch = (char) a;
            System.out.print(" " + ch + "  ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print("0");
                if (board[i][j] == EMPTY) {
                    System.out.print(" ");
                } else if (board[i][j] == SHIP) {
                    System.out.print("S");
                } else if (board[i][j] == HIT) {
                    System.out.print("#");
                } else if (board[i][j] == MISS) {
                    System.out.print("@");
                }
            }
            System.out.print("\n\n");
        }
    }

    public void placeShip(int row, int col, int[][] board) {
        board[row][col] = SHIP;
    }

    public boolean isGameOver(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Batalha Naval - Jogo para 2 jogadores");
        System.out.println("Jogador 1, posicione seus navios.");
        for (int i = 0; i < AMOUNT; i++) {
            System.out.print("Informe a linha para o navio " + (i + 1) + ": ");
            int row = scanner.nextInt();
            System.out.print("Informe a coluna para o navio " + (i + 1) + ": ");
            int col = scanner.nextInt();
            placeShip(row, col, boardPlayer1);
        }

        System.out.println("\nJogador 2, posicione seus navios.");
        for (int i = 0; i < AMOUNT; i++) {
            System.out.print("Informe a linha para o navio " + (i + 1) + ": ");
            int row = scanner.nextInt();
            System.out.print("Informe a coluna para o navio " + (i + 1) + ": ");
            int col = scanner.nextInt();
            placeShip(row, col, boardPlayer2);
        }

        int currentPlayer = 1;
        int[][] currentBoard;
        while (!isGameOver(boardPlayer1) && !isGameOver(boardPlayer2)) {
            if (currentPlayer == 1) {
                System.out.println("\nJogador " + currentPlayer + ", é a sua vez.");
                currentBoard = boardPlayer2;
            } else {
                System.out.println("\nJogador " + currentPlayer + ", é a sua vez.");
                currentBoard = boardPlayer1;
            }

            System.out.print("Informe a linha do seu tiro: ");
            int row = scanner.nextInt();
            System.out.print("Informe a coluna do seu tiro: ");
            int col = scanner.nextInt();

            if (currentBoard[row][col] == SHIP) {
                System.out.println("Jogador " + currentPlayer + ", você acertou um navio!");
                currentBoard[row][col] = HIT;
            } else {
                System.out.println("Jogador " + currentPlayer + ", você errou o tiro.");
                currentBoard[row][col] = MISS;
            }

            if (currentPlayer == 1) {
                printBoard(boardPlayer2);
            } else {
                printBoard(boardPlayer1);
            }

            currentPlayer = currentPlayer == 1 ? 2 : 1;
        }

        System.out.println("\nFim de jogo! Jogador " + currentPlayer + " venceu!");
        scanner.close();
    }
}
