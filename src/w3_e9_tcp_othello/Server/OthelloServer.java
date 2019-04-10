package w3_e9_tcp_othello.Server;

import java.net.ServerSocket;

/*
 *  Client -> Server           Server -> Client
 *  ----------------           ----------------
 *  MOVE <n>  (0 <= n <= 81)   WELCOME <String>  (String in {"Black", "White"})
 *  QUIT                       VALID_MOVE
 *                             OTHER_PLAYER_MOVED <n>
 *                             VICTORY
 *                             DEFEAT
 *                             TIE
 *                             MESSAGE <text>
 */

public class OthelloServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(8901);
        System.out.println("Othello Server is running.");
        try
        {
            while (true)
            {
                OthelloGame othelloGame = new OthelloGame();

                OthelloPlayer playerBlack = new OthelloPlayer(serverSocket.accept(), "Black", othelloGame);
                OthelloPlayer playerWhite = new OthelloPlayer(serverSocket.accept(), "White", othelloGame);

                playerBlack.setOpponent(playerWhite);
                playerWhite.setOpponent(playerBlack);

                othelloGame.initBoard(playerBlack, playerWhite);
                othelloGame.setCurrentPlayer(playerBlack);

                playerBlack.start();
                playerWhite.start();
            }
        }
        finally
        {
            serverSocket.close();
        }
    }
}