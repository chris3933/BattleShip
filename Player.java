package battleship;

import java.util.Scanner;

public class Player {
    // gameBoard is for placing the ships and hiddenBoard is for when it's time to play
    public char[][] gameBoard = new char[10][10];
    private char[][] hiddenBoard = new char[10][10];

    // Create an array to hold each ships position and the index used for iterating over each part
    public String[][] shipPos = new String[5][5];
    private int shipIndex = 0;

    // Variables to keep track of which ships are alive
    private boolean aircraftAlive = true;
    private boolean battleshipAlive = true;
    private boolean submarineAlive = true;
    private boolean cruiserAlive = true;
    private boolean destroyerAlive = true;

    // Initialize the game board
    public Player(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                gameBoard[i][j] = '~';
                hiddenBoard[i][j] = '~';
            }
        }
    }

    public void startGame(Scanner scnr){
        printBoard();

        // Place each ship then print the new gameBoard
        placeShips(5, "Aircraft Carrier", scnr);
        printBoard();
        placeShips(4, "Battleship", scnr);
        printBoard();
        placeShips(3, "Submarine", scnr);
        printBoard();
        placeShips(3, "Cruiser", scnr);
        printBoard();
        placeShips(2, "Destroyer", scnr);
        printBoard();
    }

    // Print the gameBoard
    public void printBoard(){
        System.out.print(" ");
        for(int i = 1; i <= 10; i++){
            System.out.print(" " + i);
        }

        System.out.println();

        for(int i = 0; i < 10; i++){
            System.out.print((char) ('A' + i));
            for(int j = 0; j < 10; j++){
                System.out.print(" " + gameBoard[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Loop through the gameBoard and check if there are any O left. If there is at least one
    // return false if there is none return true
    public boolean checkWin(Player player){
        int partCount = 0;

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(player.gameBoard[i][j] == 'O'){
                    partCount++;
                }
            }
        }

        return partCount == 0;
    }

    // Print the hiddenBoard
    public void printHiddenBoard(){
        System.out.print(" ");
        for(int i = 1; i <= 10; i++){
            System.out.print(" " + i);
        }

        System.out.println();

        for(int i = 0; i < 10; i++){
            System.out.print((char) ('A' + i));
            for(int j = 0; j < 10; j++){
                System.out.print(" " + hiddenBoard[i][j]);
            }
            System.out.println();
        }
    }

    // Allow the user to place their next ship
    private void placeShips(int shipSize, String shipName, Scanner scnr){
        String startPos, endPos;
        int length = 0, startNumber, endNumber;
        char startChar, endChar;
        boolean firstLoop = true, needInput = true;

        do{
            if(firstLoop){
                System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", shipName, shipSize);
                firstLoop = false;
            }else if(length != shipSize){
                System.out.printf("\nError! Wrong length of the %s! Try again:\n\n", shipName);
            }

            startPos = scnr.next();
            endPos = scnr.next();

            // Set the variables for use in the for loop that prints out the part placements and make error checking easier
            startChar = startPos.charAt(0);
            startNumber = Integer.parseInt(startPos.substring(1));

            endChar = endPos.charAt(0);
            endNumber = Integer.parseInt(endPos.substring(1));

            if((startNumber < endNumber) && (startChar == endChar)){
                length = (endNumber - startNumber) + 1;
            }
            else if((startNumber > endNumber) && (startChar == endChar)){
                length = (startNumber - endNumber) + 1;
            }
            else if(startChar < endChar){
                length = (((int) endChar - ((int) startChar))) + 1;
            }
            else{
                length = (((int) startChar - ((int) endChar))) + 1;
            }

            if((startChar != endChar) && (startNumber != endNumber)){
                System.out.println("\nError! Wrong ship location! try again:\n");
                needInput = true;
            }
            else if((startNumber > 10) || (startNumber <= 0) || (endNumber > 10) || (endNumber <= 0)){
                System.out.println("\nError! Wrong ship location! try again:\n");
                needInput = true;
            }
            else if((startChar > 'J') || (endChar > 'J') || (startChar < 'A') || (endChar < 'A')){
                System.out.println("\nError! Wrong ship location! try again:\n");
                needInput = true;
            }
            else{
                needInput = checkNearbyShip(startChar, startNumber, endChar, endNumber);
            }

        }while((length > shipSize) || (length < shipSize) || needInput);

        if((startNumber < endNumber)){
            for(int i = 0; i < length; i++){
                if(gameBoard[(int) startChar - 'A'][(startNumber + i) - 1] == 'O'){
                    System.out.println("\nError! You can't place a ship there.");
                }else{
                    gameBoard[(int) startChar - 'A'][(startNumber + i) - 1] = 'O';
                    shipPos[shipIndex][i] = "" + startChar + (startNumber + i);
                }
            }
        }
        else if((startNumber > endNumber)){
            for(int i = 0; i < length; i++){
                if(gameBoard[(int) startChar - 'A'][(startNumber - i) - 1] == 'O'){
                    System.out.println("\nError! You can't place a ship there.");
                }else{
                    gameBoard[(int) startChar - 'A'][(startNumber - i) - 1] = 'O';
                    shipPos[shipIndex][i] = "" + startChar + (startNumber - i);
                }
            }
        }
        else if(startChar < endChar){
            for(int i = 0; i < length; i++){
                if(gameBoard[(startChar + i) - 'A'][startNumber - 1] == 'O'){
                    System.out.println("\nError! You can't place a ship there");
                }else{
                    gameBoard[(startChar + i) - 'A'][startNumber - 1] = 'O';
                    shipPos[shipIndex][i] = ((char) (startChar + i)) + String.valueOf(startNumber);
                }
            }
        }else{
            for(int i = 0; i < length; i++){
                if(gameBoard[(startChar - i) - 'A'][startNumber - 1] == 'O'){
                    System.out.println("\nError! You can't place a ship there.");
                }else{
                    gameBoard[(startChar - i) - 'A'][startNumber - 1] = 'O';
                    shipPos[shipIndex][i] = ((char) (startChar - i)) + String.valueOf(startNumber);
                }
            }
        }

        shipIndex++;
        System.out.println();
        scnr.nextLine();
    }

    // Checks that the ship being placed is not being placed next to another
    private boolean checkNearbyShip(char startChar, int startNumber, char endChar, int endNumber) {
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(((i == ((startChar - 'A') - 1)) && (j == startNumber - 1)) || ((i == (endChar - 'A' - 1) && (j == endNumber - 1)))){
                    if(gameBoard[i][j] == 'O'){
                        System.out.println("\nError! You placed it too close to another one. Try again:\n");
                        return true;
                    }
                }
                else if(((i == ((startChar - 'A') + 1)) && (j == startNumber - 1)) || ((i == (endChar - 'A' + 1) && (j == endNumber - 1)))){
                    if(gameBoard[i][j] == 'O'){
                        System.out.println("\nError! You placed it too close to another one. Try again:\n");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Allow the player to attempt to shoot the ship and make sure the target is within
    // A-J and 1-10
    public void playerShot(Scanner scnr, Player player){
        String playerShot;
        int playerRow;
        int playerCol;
        int shipsSunk = 0;
        boolean sunkShip = false;

        System.out.println("Take a shot!\n");

        do{
            playerShot = scnr.nextLine();
            playerRow = playerShot.charAt(0) - 'A';
            playerCol = Integer.parseInt(playerShot.substring(1)) - 1;

            if((playerShot.charAt(0) > 'J') || (playerCol > 9)){
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }

        }while((playerShot.charAt(0) > 'J') || (playerCol > 9));

        if((player.gameBoard[playerRow][playerCol] == 'O') || (player.gameBoard[playerRow][playerCol] == 'X')){
            player.gameBoard[playerRow][playerCol] = 'X';
            hiddenBoard[playerRow][playerCol] = 'X';
            System.out.println();
            printHiddenBoard();

            if(aircraftAlive){
                sunkShip = sunkAircraft(player);
                if(sunkShip){
                    shipsSunk++;
                }
            }
            if(cruiserAlive){
                sunkShip = sunkCruiser(player);
                if(sunkShip){
                    shipsSunk++;
                }
            }
            if(battleshipAlive){
                sunkShip = sunkBattleShip(player);
                if(sunkShip){
                    shipsSunk++;
                }
            }
            if(submarineAlive){
                sunkShip = sunkSub(player);
                if(sunkShip){
                    shipsSunk++;
                }
            }
            if(destroyerAlive){
                sunkShip = sunkDestroyer(player);
                if(sunkShip){
                    shipsSunk++;
                }
            }

            if((!checkWin(player)) && (shipsSunk == 0)){
                System.out.println("You hit a ship! Try again:\n");
            }
        }else{
            player.gameBoard[playerRow][playerCol] = 'M';
            hiddenBoard[playerRow][playerCol] = 'M';
            System.out.println();
            printHiddenBoard();
            if(!checkWin(player)){
                System.out.println("You missed! Try again:\n");
            }
        }
    }

    private boolean sunkAircraft(Player player){
        char currentChar;
        int currentPos;
        int partCount = 0;
        shipIndex = 0;

        for(int i = 0; i < 5; i++){
            currentChar = (player.shipPos[shipIndex][i]).charAt(0);
            currentPos = Integer.parseInt((player.shipPos[shipIndex][i]).substring(1));

            if(player.gameBoard[currentChar - 'A'][currentPos - 1] == 'O'){
                partCount++;
            }
        }

        if(partCount == 0){
            aircraftAlive = false;
            System.out.println("You sank a ship! Specify a new target:\n");
            return true;
        }else{
            return false;
        }
    }

    private boolean sunkBattleShip(Player player){
        char currentChar;
        int currentPos;
        int partCount = 0;
        shipIndex = 1;

        for(int i = 0; i < 4; i++){
            currentChar = (player.shipPos[shipIndex][i]).charAt(0);
            currentPos = Integer.parseInt((player.shipPos[shipIndex][i]).substring(1));

            if(player.gameBoard[currentChar - 'A'][currentPos - 1] == 'O'){
                partCount++;
            }
        }

        if(partCount == 0){
            battleshipAlive = false;
            System.out.println("You sank a ship! Specify a new target:\n");
            return true;
        }else{
            return false;
        }
    }

    private boolean sunkSub(Player player){
        char currentChar;
        int currentPos;
        int partCount = 0;
        shipIndex = 2;

        for(int i = 0; i < 3; i++){
            currentChar = (player.shipPos[shipIndex][i]).charAt(0);
            currentPos = Integer.parseInt((player.shipPos[shipIndex][i]).substring(1));

            if(player.gameBoard[currentChar - 'A'][currentPos - 1] == 'O'){
                partCount++;
            }
        }

        if(partCount == 0){
            submarineAlive = false;
            System.out.println("You sank a ship! Specify a new target:\n");
            return true;
        }else{
            return false;
        }
    }

    private boolean sunkCruiser(Player player){
        char currentChar;
        int currentPos;
        int partCount = 0;
        shipIndex = 3;

        for(int i = 0; i < 3; i++){
            currentChar = (player.shipPos[shipIndex][i]).charAt(0);
            currentPos = Integer.parseInt((player.shipPos[shipIndex][i]).substring(1));

            if(player.gameBoard[currentChar - 'A'][currentPos - 1] == 'O'){
                partCount++;
            }
        }

        if(partCount == 0){
            cruiserAlive = false;
            System.out.println("You sank a ship! Specify a new target:\n");
            return true;
        }else{
            return false;
        }
    }

    private boolean sunkDestroyer(Player player){
        char currentChar;
        int currentPos;
        int partCount = 0;
        shipIndex = 4;

        for(int i = 0; i < 2; i++){
            currentChar = (player.shipPos[shipIndex][i]).charAt(0);
            currentPos = Integer.parseInt((player.shipPos[shipIndex][i]).substring(1));

            if(player.gameBoard[currentChar - 'A'][currentPos - 1] == 'O'){
                partCount++;
            }
        }

        if(partCount == 0){
            destroyerAlive = false;
            System.out.println("You sank a ship! Specify a new target:\n");
            return true;
        }else{
            return false;
        }
    }
}

