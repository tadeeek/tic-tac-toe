import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create board
        char[][] arr = new char[3][3];
        Scanner scanner = new Scanner(System.in);
        char currentGamerChar = 'X';
        int currentGamer = 1; // if even - gamer O

        // Initialize Board
        initializeBoard(arr);

        while (true) {
            displayBoard(arr);
            System.out.print("Enter the coordinates: ");

            try {
                String coordinates;
                coordinates = scanner.nextLine();
                int coordX = Integer.parseInt(coordinates.substring(0, 1));
                int coordY = Integer.parseInt(coordinates.substring(2, 3));
                if (coordX >= 1 && coordX <= 3 && coordY >= 1 && coordY <= 3) {
                    // Do insertion
                    if (isFree(arr, coordX, coordY)) {
                        // Current gamer switch
                        if (currentGamer % 2 == 0) {
                            currentGamerChar = 'O';
                        } else {
                            currentGamerChar = 'X';
                        }

                        insertGamer(arr, coordX, coordY, currentGamerChar);
                        currentGamer++;
                        System.out.println();

                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                }

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (NoSuchElementException e) {
                System.out.println(e.getCause());
                System.out.println(e.getMessage());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("No input detected (null)");
            }

            if (ifWins(arr, 'X')) {
                displayBoard(arr);
                System.out.printf("X wins");
                break;
            } else if (ifWins(arr, 'O')) {
                displayBoard(arr);
                System.out.println("O wins");
                break;
            } else if (isDraw(arr)) {
                displayBoard(arr);
                System.out.println("Draw");
            }
        }
    }

    public static boolean isFree(char[][] arr, int x, int y) {
        x = x - 1;
        y = y - 1;
        if (y == 2) {
            y = 0;
        } else if (y == 0) {
            y = 2;
        }

        if (arr[y][x] == ' ') {
            return true;
        }
        return false;
    }

    private static void insertGamer(char[][] arr, int x, int y, char gamer) {
        x = x - 1;
        y = y - 1;
        if (y == 2) {
            y = 0;
        } else if (y == 0) {
            y = 2;
        }

        if (arr[y][x] == ' ') {
            arr[y][x] = gamer;
        }
    }

    private static boolean isImpossible(char[][] arr) {
        if (((checkRows(arr, 'X') || checkCols(arr, 'X') || checkCross(arr, 'X'))
                && (checkRows(arr, 'O') || checkCols(arr, 'O') || checkCross(arr, 'O'))) || hasMore(arr)) {
            return true;
        }
        return false;
    }

    private static boolean isNotFinished(char[][] arr) {
        if (!ifWins(arr, 'X') && !ifWins(arr, 'O') && isEmpty(arr)) {
            return true;
        }
        return false;
    }

    private static boolean isDraw(char[][] arr) {
        if (!ifWins(arr, 'X') && !ifWins(arr, 'O') && !isEmpty(arr)) {
            return true;
        }
        return false;
    }

    private static boolean isEmpty(char[][] arr) {
        int k;
        for (int i = 0; i < 3; i++) {
            k = 0;
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean ifWins(char[][] arr, char gamer) {
        if (checkRows(arr, gamer) || checkCols(arr, gamer) || checkCross(arr, gamer)) {
            return true;
        }
        return false;
    }

    private static boolean hasMore(char[][] arr) {
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 'X') {
                    countX++;
                } else if (arr[i][j] == 'O') {
                    countO++;
                }
            }
        }
        if ((countX - countO >= 2) || (countO - countX >= 2)) {
            return true;

        }
        return false;
    }

    private static boolean checkCross(char[][] arr, char gamer) {
        if (arr[1][1] == gamer
                && ((arr[0][0] == gamer && arr[2][2] == gamer) || (arr[2][0] == gamer && arr[0][2] == gamer))) {
            return true;
        }
        return false;
    }

    private static boolean checkRows(char[][] arr, char gamer) {
        int k;
        for (int i = 0; i < 3; i++) {
            k = 0;
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == gamer) {
                    k++;
                    if (k == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkCols(char[][] arr, char gamer) {
        int k;
        for (int i = 0; i < 3; i++) {
            k = 0;
            for (int j = 0; j < 3; j++) {
                if (arr[j][i] == gamer) {
                    k++;
                    if (k == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void initializeBoard(char[][] arr) {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = ' ';
            }
        }
    }

    private static void displayBoard(char[][] arr) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                String s = String.format(" %c", arr[i][j]);
                System.out.print(s);
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }
}
