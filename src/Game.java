import javax.swing.*;

public class Game extends JFrame {
    User user1;
    User user2;
    int tileCounter = 0;
    int[][] tileGrid = new int[6][7];
    boolean isRedTurn = true;

    public Game(LoginMenuPanel loginMenuPanel) {
        setVisible(true);
        add(loginMenuPanel);

    }

    public void placeTile(int column) {
        for (int row = 0; row < 6; row++) {
            if (tileGrid[row][column] == Tile.WHITE.getI()) {
                tileGrid[row][column] = isRedTurn ? Tile.RED.getI() : Tile.YELLOW.getI();
                tileCounter++;
                if (hasWon(row, column)) {
                    processResult();
                    return;
                }
                isRedTurn = !isRedTurn;
                return;
            }
        }
    }

    public boolean hasWon(int placedRow, int placedColumn) {
        int inARowCounter = 0;
        int correctColor = isRedTurn ? Tile.RED.getI() : Tile.YELLOW.getI();

        int startRow = Math.max(placedRow - 3, 0);
        int endRow = Math.min(placedRow + 3, 7);
        int startColumn = Math.max(placedColumn - 3, 0);
        int endColumn = Math.min(placedColumn + 3, 7);

        int currentRow;
        int currentColumn;

        //Horizontal win
        for (currentColumn = startColumn; currentColumn < endColumn; currentColumn++) {
            //Reset counter to 0 if the row of correct colors is broken
            inARowCounter = tileGrid[placedRow][currentColumn] == correctColor ? inARowCounter + 1 : 0;
            if (inARowCounter == 4) {
                return true;
            }

        }
        inARowCounter = 0;

        //Vertical win
        for (currentRow = startRow; currentRow < endRow; currentRow++) {
            inARowCounter = ((tileGrid[currentRow][placedColumn] == correctColor) ? inARowCounter + 1 : 0);
            if (inARowCounter == 4) {
                return true;
            }
        }
        inARowCounter = 0;

        //Bottom left to top right win
        currentColumn = startColumn;
        currentRow = startRow;
        while (currentRow < endRow && currentColumn < endColumn) {
            inARowCounter = ((tileGrid[currentRow][currentColumn] == correctColor) ? inARowCounter + 1 : 0);
            if (inARowCounter == 4) {
                return true;
            }
            currentColumn++;
            currentRow++;
        }
        inARowCounter = 0;

        //Top left to bottom right win
        currentColumn = Math.min(placedColumn + 3, 7);
        endColumn = Math.max(placedColumn - 3, 0);
        currentRow = startRow;
        while (currentRow < endRow && currentColumn > endColumn) {
            inARowCounter = ((tileGrid[currentRow][currentColumn] == correctColor) ? inARowCounter + 1 : 0);
            if (inARowCounter == 4) {
                return true;
            }
            currentColumn--;
            currentRow++;
        }

        return false;
    }

    public void processResult() {

    }

    public void addUser(User user) {
        if ((user1 == null)) {
            user1 = user;
        } else {
            user2 = user;
            startGame();
        }
    }

    private void startGame() {

    }


}

