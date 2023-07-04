package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BattleshipPlayerHandler extends Thread {
    private Socket clientSocket;
    private BattleshipGame game;
    private int playerNumber;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public BattleshipPlayerHandler(Socket socket, BattleshipGame game, int playerNumber) {
        this.clientSocket = socket;
        this.game = game;
        this.playerNumber = playerNumber;
    }

    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            game.initPlayer(playerNumber);

            while (!game.isGameOver()) {
                if (playerNumber == game.getCurrentPlayer()) {
                    System.out.println("\nJogador " + playerNumber + ", é a sua vez.");

                    System.out.print("Informe a linha do seu tiro: ");
                    int row = scanner.nextInt();
                    System.out.print("Informe a coluna do seu tiro: ");
                    int col = scanner.nextInt();

                    game.takeTurn(row, col);

                    outputStream.writeObject(game.getBoardState()); // Envia o estado do tabuleiro atualizado para o cliente

                    if (game.isGameOver()) {
                        System.out.println("\nFim de jogo! Jogador " + playerNumber + " venceu!");
                    }
                } else {
                    int[][] opponentBoard = (int[][]) inputStream.readObject(); // Aguarda o estado do tabuleiro enviado pelo outro jogador
                    game.updateOpponentBoard(opponentBoard);
                }
            }

            clientSocket.close();
            scanner.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o endereço IP do servidor: ");
        String serverIP = scanner.nextLine();

        System.out.print("Informe o número do jogador (1 ou 2): ");
        int playerNumber = scanner.nextInt();

        try {
            Socket socket = new Socket(serverIP, BattleshipServer.PORT);

            BattleshipGame game = new BattleshipGame();
            BattleshipPlayerHandler playerHandler = new BattleshipPlayerHandler(socket, game, playerNumber);
            playerHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
