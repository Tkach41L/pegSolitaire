package sk.tuke.gamestudio.game.core;

import sk.tuke.gamestudio.game.core.Tile;

public class Field {
    private final Tile[][] tiles = new Tile[9][9];

    public Field() {
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                tiles[i][j] = Tile.FIGURE;
            }
        }

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++) {
                tiles[i][j] = Tile.NONE;
                tiles[i][j+1] = Tile.NONE;
                j = j + 4;
            }
        }

        for(int i = 2; i < 5; i++){
            for(int j = 0; j < 7; j++){
                tiles[i][j] = Tile.FIGURE;
            }
        }

        tiles[3][3] = Tile.EMPTY;
    //        tiles[3][4] = Tile.FIGURE;
    //        tiles[3][1] = Tile.FIGURE;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean makeMove(int x, int y, char move){
        x -= 1;
        y -= 1;
        int newX, newY;
        int middleX, middleY;
        switch (move) {
            case 'w' -> {
                newX = x;
                newY = y - 2;
                middleX = x;
                middleY = y - 1;
            }
            case 'a' -> {
                newX = x - 2;
                newY = y;
                middleX = x - 1;
                middleY = y;
            }
            case 's' -> {
                newX = x;
                newY = y + 2;
                middleX = x;
                middleY = y + 1;
            }
            case 'd' -> {
                newX = x + 2;
                newY = y;
                middleX = x + 1;
                middleY = y;
            }
            default -> {
                System.out.println("DEF");
                return false;
            }
        }
        if(newX < 0 || newY < 0 || newX > 6 || newY > 6){
//            System.out.printf("%d %d\n", x, y);
            return false;
        }
        if(tiles[newY][newX] == Tile.NONE){
//            System.out.printf("%d %d\n", x, y);
            return false;
        }
        boolean selectedPositionCheck = tiles[y][x] == Tile.FIGURE;
        boolean middleFigureCheck = tiles[middleY][middleX] == Tile.FIGURE;
        boolean movePositionCheck = tiles[newY][newX] == Tile.EMPTY;
        if(selectedPositionCheck && movePositionCheck && middleFigureCheck){
            tiles[y][x] = Tile.EMPTY;
            tiles[middleY][middleX] = Tile.EMPTY;
            tiles[newY][newX] = Tile.FIGURE;
            return true;
        }
        return false;
    }



    public boolean checkWin(){
        for(int y = 0; y < 7; y++){
            for(int x = 0; x < 7; x++){
                if(y != 3 && x != 3 && tiles[y][x] == Tile.FIGURE){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfHasMoves(int y, int x){
        if(y + 2 < 7 && tiles[y + 2][x] == Tile.EMPTY && tiles[y + 1][x] == Tile.FIGURE){
            return true;
        }
        else if(x + 2 < 7 && tiles[y][x + 2] == Tile.EMPTY && tiles[y][x + 1] == Tile.FIGURE){
            return true;
        }
        else if(y - 2 >= 0 && tiles[y - 2][x] == Tile.EMPTY && tiles[y - 1][x] == Tile.FIGURE){
            return true;
        }
        else if(x - 2 >= 0 && tiles[y][x - 2] == Tile.EMPTY && tiles[y][x - 1] == Tile.FIGURE){
            return true;
        }
        return  false;
    }

    public boolean checkLose(){
        for(int y = 0; y < 7; y++){
            for(int x = 0; x < 7; x++){
                if (tiles[y][x] == Tile.FIGURE && checkIfHasMoves(y, x)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generate(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                tiles[i][j] = Tile.FIGURE;
            }
        }

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++) {
                tiles[i][j] = Tile.NONE;
                tiles[i][j+1] = Tile.NONE;
                j = j + 4;
            }
        }

        for(int i = 2; i < 5; i++){
            for(int j = 0; j < 7; j++){
                tiles[i][j] = Tile.FIGURE;
            }
        }

        tiles[3][3] = Tile.EMPTY;
    }

}
