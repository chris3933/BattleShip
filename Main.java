package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();
        boolean gameWin = false;
        boolean player1Turn = true;

        // Each player places their ships and moves to the next players turn by pressing enter
        System.out.println("Player 1, place your ships on the game field\n");
        player1.startGame(scnr);
        System.out.println("Press Enter and pass the move to another player");
        scnr.nextLine();

        System.out.println("Player 2, place your ships to the game field\n");
        player2.startGame(scnr);
        System.out.println("Press Enter and pass the move to another player");
        scnr.nextLine();

        // While no one has won the game let each player take turns taking shots
        // On each turn the players board (with enemy shots) along with their hidden board is printed
        while(!gameWin){
            if(player1Turn){
                player1.printHiddenBoard();
                System.out.println("---------------------");
                player1.printBoard();

                System.out.println("Player 1, it's your turn:\n");
                player1.playerShot(scnr, player2);
                gameWin = player1.checkWin(player2);
                player1Turn = false;
            }else{
                player2.printHiddenBoard();
                System.out.println("---------------------");
                player2.printBoard();

                System.out.println("Player 2, it's your turn:\n");
                player2.playerShot(scnr, player1);
                gameWin = player2.checkWin(player1);
                player1Turn = true;
            }

            if(!gameWin){
                System.out.println("Press Enter and pass the move to another player");
                scnr.nextLine();
            }
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
        scnr.close();
    }
}
