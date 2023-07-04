package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BattleshipServer {
    public static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor Battleship iniciado. Aguardando jogadores...");

            Socket socketPlayer1 = serverSocket.accept();
            System.out.println("Jogador 1 conectado.");

            Socket socketPlayer2 = serverSocket.accept();
            System.out.println("Jogador 2 conectado.");

            BattleshipGame game = new BattleshipGame();

            BattleshipPlayerHandler playerHandler1 = new BattleshipPlayerHandler(socketPlayer1, game, 1);
            BattleshipPlayerHandler playerHandler2 = new BattleshipPlayerHandler(socketPlayer2, game, 2);

            playerHandler1.start();
            playerHandler2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
