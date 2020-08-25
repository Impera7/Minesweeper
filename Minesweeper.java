package com.internTask;

import java.util.*;


public class Main {

    public static final String START_MESSAGE = "Enter the Difficulty Level\n" +
            "Press 0 for BEGINNER (9 * 9 Cells and 10 Mines)\n" +
            "Press 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)\n" +
            "Press 2 for ADVANCED (24 * 24 Cells and 99 Mines)";
    public static final String LOSE_MSG = "You lost!";
    public static final String WIN_MSG = "You won!";
    public static final String STATUS = "Current Status of Board :";
    public static final String ENTER_MOVE_MSG = "Enter your move, (row, column)\n" +
            "->";
    public static int SIZE;
    public static int MINES;

    static boolean checkValidCell(int i, int j){
        return i >= 0 && i < SIZE && j >= 0 && j < SIZE;
    }

    static boolean isCellMine(int i, int j, char[][] gameBoard){
        return gameBoard[i][j] == '*';
    }

    static int[] userMove(){
        System.out.print(ENTER_MOVE_MSG);
        Scanner scanner = new Scanner(System.in);
        return new int[] {scanner.nextInt(), scanner.nextInt()};
    }

    static void printUserBoard(char[][] gameBoard){
        System.out.print("\t");
        for (int i=0;i<SIZE;i++){
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i=0;i<SIZE;i++){
            System.out.print(i + "\t");
            for (int j=0;j<SIZE;j++){
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int countOfAdjacentMines(int i, int j, char[][] gameBoard){
        int count = 0;

        if (checkValidCell(i - 1,j)){
            if (isCellMine(i - 1,j,gameBoard))
                count++;
        }

        if (checkValidCell(i + 1,j)){
            if (isCellMine(i + 1,j,gameBoard))
                count++;
        }

        if (checkValidCell(i,j + 1)){
            if (isCellMine(i,j + 1,gameBoard))
                count++;
        }

        if (checkValidCell(i,j - 1)){
            if (isCellMine(i,j - 1,gameBoard))
                count++;
        }

        if (checkValidCell(i - 1,j - 1)){
            if (isCellMine(i - 1,j - 1,gameBoard))
                count++;
        }

        if (checkValidCell(i - 1,j + 1)){
            if (isCellMine(i - 1,j + 1,gameBoard))
                count++;
        }

        if (checkValidCell(i + 1,j - 1)){
            if (isCellMine(i + 1,j - 1,gameBoard))
                count++;
        }

        if (checkValidCell(i + 1,j + 1)){
            if (isCellMine(i + 1,j + 1,gameBoard))
                count++;
        }

        return count;
    }

    static boolean recursiveCheckOfCells(int i, int j, char[][] userBoard, char[][] gameBoard, int[][] mines, int movesLeft){
        if (userBoard[i][j] != '-'){
            return false;
        }
        if (gameBoard[i][j] == '*'){
            userBoard[i][j] = '*';
            for (int k=0;k<MINES;k++){
                userBoard[mines[k][0]][mines[k][1]] = '*';
            }
            printUserBoard(userBoard);
            System.out.println(LOSE_MSG);
            return true;
        }else{
            int count = countOfAdjacentMines(i, j, gameBoard);
            movesLeft--;
            userBoard[i][j] =  Integer.toString(count).charAt(0);
            if(count == 0){
                if (checkValidCell(i - 1,j)){
                    if (!isCellMine(i - 1,j,gameBoard))
                        recursiveCheckOfCells(i - 1,j,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i + 1,j)){
                    if (!isCellMine(i + 1,j,gameBoard))
                        recursiveCheckOfCells(i + 1,j,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i,j + 1)){
                    if (!isCellMine(i,j + 1,gameBoard))
                        recursiveCheckOfCells(i,j + 1,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i,j - 1)){
                    if (!isCellMine(i,j - 1,gameBoard))
                        recursiveCheckOfCells(i,j - 1,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i - 1,j - 1)){
                    if (!isCellMine(i - 1,j - 1,gameBoard))
                        recursiveCheckOfCells(i - 1,j - 1,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i - 1,j + 1)){
                    if (!isCellMine(i - 1,j + 1,gameBoard))
                        recursiveCheckOfCells(i - 1,j + 1,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i + 1,j - 1)){
                    if (!isCellMine(i + 1,j - 1,gameBoard))
                        recursiveCheckOfCells(i + 1,j - 1,userBoard,gameBoard,mines,movesLeft);
                }

                if (checkValidCell(i + 1,j + 1)){
                    if (!isCellMine(i + 1,j + 1,gameBoard))
                        recursiveCheckOfCells(i + 1,j + 1,userBoard,gameBoard,mines,movesLeft);
                }
            }
            return false;
        }
    }

    static void placeMines(int[][] mines, char[][] gameBoard){
        int i = 0;
        boolean[][] helperMatrix = new boolean[SIZE][SIZE];
        while ( i < MINES ){
            Random random = new Random();
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            if (!helperMatrix[x][y]){
                mines[i][0] = x;
                mines[i][1] = y;
                gameBoard[mines[i][0]][mines[i][1]] = '*';
                helperMatrix[x][y] = true;
                i++;
            }
        }
    }

    static void init(char[][] gameBoard, char[][] userBoard){
        for (int i=0;i<SIZE;i++){
            for (int j=0;j<SIZE;j++){
                userBoard[i][j] = gameBoard[i][j] = '-';
            }
        }
    }

    static void replaceMine(int i, int j, char[][] gameBoard){
        int k = i, m = j;
        Random random = new Random();
        while ( k == i && m == j) {
            k = random.nextInt(SIZE);
            m = random.nextInt(SIZE);
        }
        gameBoard[i][j] = '-';
        gameBoard[k][m] = '*';
    }
    
    static void playGame(){
        boolean gameOver = false;
        int movesLeft = SIZE * SIZE - MINES;
        char[][] gameBoard = new char[SIZE][SIZE];
        char[][] userBoard = new char[SIZE][SIZE];
        int[][] mines = new int[MINES][2];
        init(gameBoard, userBoard);
        placeMines(mines, gameBoard);
        int movementCounter = 0;
        while(!gameOver){
            System.out.println(STATUS);
            printUserBoard(userBoard);
            int[] move = userMove();
            if (movementCounter == 0){
                if(isCellMine(move[0], move[1], gameBoard)){
                    replaceMine(move[0], move[1], gameBoard);
                }
            }
            movementCounter++;
            gameOver = recursiveCheckOfCells(move[0], move[1],userBoard, gameBoard, mines, movesLeft);
            if(!gameOver && movesLeft == 0){
                System.out.println(WIN_MSG);
                gameOver = true;
            }
        }
    }

    static public void menu(){
        int lvlDiff;
        System.out.println(START_MESSAGE);
        do {
            Scanner scanner = new Scanner(System.in);
            lvlDiff = scanner.nextInt();
            if (lvlDiff == 0) {
                SIZE = 9;
                MINES = 10;
            } else if (lvlDiff == 1) {
                SIZE = 16;
                MINES = 40;
            } else {
                SIZE = 24;
                MINES = 99;
            }
        }while ( lvlDiff < 0 || lvlDiff > 2);
    }

    public static void main(String[] args) {
        menu();
        playGame();
    }
}
