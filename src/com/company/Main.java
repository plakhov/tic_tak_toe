package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] map;
    public static final int SIZE = 3;
    public static final int DOTS_TO_WIN = 3;

    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initMap();
        printMap();
        boolean humanWin;
        boolean aiWin = false;
        do {
            humanTurn();
            printMap();
            humanWin = checkWin(DOT_X);
            if (humanWin) {
                break;
            }
            aiTurn();
            printMap();
            aiWin = checkWin(DOT_O);
            if (aiWin) {
                break;
            }
        } while (!mapIsFull());
        if (humanWin) {
            System.out.println("Победил человек");
        }
        if (aiWin) {
            System.out.println("Победил компьютер");
        }
        if (!humanWin && !aiWin) {
            System.out.println("Ничья");
        }
    }

    private static boolean checkWin(char symbol) {
        //что в строке есть нужное количество символов подряд
        for (char[] row : map) {
            int quantitySymbolInRow = 0;
            for (int i = 0; i < SIZE; i++) {
                if (row[i] == symbol) {
                    quantitySymbolInRow++;
                } else if (quantitySymbolInRow > 0) {
                    quantitySymbolInRow = 0;
                }
                if (quantitySymbolInRow == DOTS_TO_WIN) {
                    return true;
                }
            }
        }
        //что в столбце есть нужное количество символов подряд
        for (int i = 0; i < SIZE; i++) {
            int quantitySymbolInRow = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[j][i] == symbol) {
                    quantitySymbolInRow++;
                } else if (quantitySymbolInRow > 0) {
                    quantitySymbolInRow = 0;
                }
                if (quantitySymbolInRow == DOTS_TO_WIN) {
                    return true;
                }
            }
        }
        //что в диагоналях есть нужное количество символов подряд
        int quantitySymbolInRow = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == symbol) {
                quantitySymbolInRow++;
            } else if (quantitySymbolInRow > 0) {
                quantitySymbolInRow = 0;
            }
            if (quantitySymbolInRow == DOTS_TO_WIN) {
                return true;
            }
        }
        quantitySymbolInRow = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][SIZE-i-1] == symbol) {
                quantitySymbolInRow++;
            } else if (quantitySymbolInRow > 0) {
                quantitySymbolInRow = 0;
            }
            if (quantitySymbolInRow == DOTS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static boolean mapIsFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void aiTurn() {
        int x, y;
        do {
            x = new Random().nextInt(SIZE);
            y = new Random().nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[x][y] = DOT_O;
    }

    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите коордаты");
            System.out.println("Введите X");
            x = scanner.nextInt() - 1;
            System.out.println("Введите Y");
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[x][y] = DOT_X;
    }

    private static boolean isCellValid(int x, int y) {//true если условие валидно, т.е. мы можем поставить символ в эту ячейку, false в иных случаях
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && map[x][y] == DOT_EMPTY;
    }

    private static void printMap() {
        for (int i = 0; i < SIZE; i++) {
            System.out.println();
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
        }
        System.out.println();
    }

    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (char[] row : map) {
            Arrays.fill(row, DOT_EMPTY);
        }
    }


}
