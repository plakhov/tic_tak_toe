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

    private static int lastX;
    private static int lastY;

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

    private static void aiTurn() {
        char[] rowForCheck = new char[SIZE];
        //что в строке есть нужное количество символов подряд
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(map[i], 0, rowForCheck, 0, SIZE);
            int result = aiTurn(rowForCheck);
            if (result != -1) {
                if (result - 1 >= 0 && lastX != i && map[lastX][result - 1] == DOT_EMPTY) {
                    map[lastX][result - 1] = DOT_O;
                } else {
                    for (int j = result; j < SIZE; j++) {
                        if (map[i][j] == DOT_EMPTY) {
                            map[i][j] = DOT_O;
                        }
                    }
                }
                return;
            }
        }
        //что в столбце есть нужное количество символов подряд
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                rowForCheck[j] = map[j][i];
            }
            int result = aiTurn(rowForCheck);
            if (result != -1) {
                if (result - 1 >= 0 && lastY != i && map[result - 1][lastY] == DOT_EMPTY) {
                    map[result - 1][lastY] = DOT_O;
                } else {
                    for (int j = result; j < SIZE; j++) {
                        if (map[j][i] == DOT_EMPTY) {
                            map[j][i] = DOT_O;
                        }
                    }
                }
                return;
            }
        }
        //что в диагоналях есть нужное количество символов подряд
        for (int k = -SIZE + 2; k < SIZE - 1; k++) {
            int index = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i == j + k) {
                        rowForCheck[index] = map[i][j];
                        index++;
                    }
                }
            }
            int result = aiTurn(rowForCheck);
            if (result != -1) {
                if (result - 1 >= 0 && map[result - 1][result - 1] == DOT_EMPTY) {
                    map[result - 1][result - 1] = DOT_O;
                    return;
                } else if (lastX + 1 < SIZE && lastY + 1 < SIZE && map[lastX + 1][lastY + 1] == DOT_EMPTY) {
                    map[lastX + 1][lastY + 1] = DOT_O;
                    return;
                }
            }
        }
        for (int k = 1; k < (SIZE * 2 - 2); k++) {
            int index = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i + j == k) {
                        rowForCheck[index] = map[i][j];
                        index++;
                    }
                }
            }
            int result = aiTurn(rowForCheck);
            if (result != -1) {
                if (result - 1 < 0 && lastX + 1 < SIZE && lastY + 1 < SIZE && map[lastX + 1][lastY + 1] == DOT_EMPTY) {
                    map[lastX + 1][lastY + 1] = DOT_O;
                    return;
                } else if (result - 1 >= 0 && map[result - 1][result - 1] == DOT_EMPTY) {
                    map[result - 1][result - 1] = DOT_O;
                    return;
                }
            }
        }
        int x, y;
        do {
            x = new Random().nextInt(SIZE);
            y = new Random().nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[x][y] = DOT_O;
    }

    private static int aiTurn(char[] row) {
        int dotsForWin = SIZE == 3 ? DOTS_TO_WIN - 1 : DOTS_TO_WIN - 2;
        for (int j = 0; j < SIZE; j++) {
            int quantitySymbolInRow = 0;
            for (int i = 0; i < row.length; i++) {
                if (row[i] == DOT_X) {
                    quantitySymbolInRow++;
                } else if (quantitySymbolInRow > 0) {
                    quantitySymbolInRow = 0;
                }
                if (quantitySymbolInRow == dotsForWin) {
                    return i - quantitySymbolInRow + 1;
                }
            }
            char temp = row[row.length - 1];
            System.arraycopy(row, 0, row, 1, row.length - 1);
            row[0] = temp;
        }
        return -1;
    }

    private static boolean checkWin(char symbol) {
        char[] rowForCheck = new char[SIZE];
        //что в строке есть нужное количество символов подряд
        for (char[] row : map) {
            if (checkWin(symbol, row)) {
                return true;
            }
        }
        //что в столбце есть нужное количество символов подряд
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                rowForCheck[j] = map[j][i];
            }
            if (checkWin(symbol, rowForCheck)) {
                return true;
            }
        }
        //что в диагоналях есть нужное количество символов подряд
        for (int k = -SIZE + 2; k < SIZE - 1; k++) {
            int index = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i == j + k) {
                        rowForCheck[index] = map[i][j];
                        index++;
                    }
                }
            }
            if (checkWin(symbol, rowForCheck)) {
                return true;
            }
        }
        for (int k = 1; k < (SIZE * 2 - 2); k++) {
            int index = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i + j == k) {
                        rowForCheck[index] = map[i][j];
                        index++;
                    }
                }
            }
            if (checkWin(symbol, rowForCheck)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkWin(char symbol, char[] row) {
        int quantitySymbolInRow = 0;
        for (char c : row) {
            if (c == symbol) {
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

    private static void humanTurn() {
        do {
            System.out.println("Введите коордаты");
            System.out.println("Введите X");
            lastX = scanner.nextInt() - 1;
            System.out.println("Введите Y");
            lastY = scanner.nextInt() - 1;
        } while (!isCellValid(lastX, lastY));
        map[lastX][lastY] = DOT_X;
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
