import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class MyRouteFinder implements RouteFinder {
    public char[][] globalMap;
    public Cell[][] massOfCells;
    private static char start = '@';
    private static char finish = 'X';
    private static char road = '.';
    private static char block = '#';

    @Override
    public char[][] findRoute(char[][] map) {
        globalMap = map;
        massOfCells = new Cell[map.length][map.length];
        Cell start = getStart(map);
        Queue<Cell> cells = new ArrayDeque<>();
        cells.add(start);
        try {
            while (cells.peek().ch != finish) {
                Cell cell = cells.poll();
                for (Cell neighbour : cell.getNeighbours()) {
                    if (!neighbour.isVisit()) {
                        cells.add(neighbour);
                        neighbour.getVisit();
                        neighbour.getNeighbours();
                    }
                }
            }
        } catch (NullPointerException ex) {
            return null;
        }
        cells.poll().getBack();
        return globalMap;
    }

    public Cell getStart(char[][] chars) {
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (chars[i][j] == start) {
                    Cell cell = new Cell(i, j, true);
                    massOfCells[i][j] = cell;
                    return cell;
                }
            }
        }
        return null;
    }

    class Cell {
        private ArrayList<Cell> neighbours = new ArrayList<>();
        private Cell top;
        private Cell right;
        private Cell bottom;
        private Cell left;
        private Cell previous;
        private boolean visit = false;
        private int x;
        private int y;
        private char ch;

        public ArrayList<Cell> getNeighbours() {
            if (y == 0 || globalMap[x][y - 1] == block) { //left
                left = null;
            } else {
                if (massOfCells[x][y - 1] == null) {
                    left = new Cell(x, y - 1, this);
                    massOfCells[x][y - 1] = left;
                    neighbours.add(left);
                }
            }
            if (x == 0 || globalMap[x -1][y] == block) { //top
                top = null;
            } else {
                if (massOfCells[x - 1][y] == null) {
                    top = new Cell(x - 1, y, this);
                    massOfCells[x -1][y] = top;
                    neighbours.add(top);
                }
            }
            if (y == globalMap.length - 1 || globalMap[x][y + 1] == block) { //right
                right = null;
            } else {
                if (massOfCells[x][y + 1] == null) {
                    right = new Cell(x, y + 1, this);
                    massOfCells[x][y + 1] = right;
                    neighbours.add(right);
                }
            }

            if (x == globalMap.length - 1 || globalMap[x + 1][y] == block) { //bottom
                bottom = null;
            } else {
                if (massOfCells[x + 1][y] == null) {
                    bottom = new Cell(x + 1, y, this);
                    massOfCells[x + 1][y] = bottom;
                    neighbours.add(bottom);
                }
            }
            return neighbours;

        }

        public Cell(int x, int y, Cell previous) {
            this.previous = previous;
            ch = globalMap[x][y];
            this.x = x;
            this.y = y;
        }

        public Cell(int x, int y, boolean visit) {
            this.visit = visit;
            ch = globalMap[x][y];
            this.x = x;
            this.y = y;
        }

        public void getVisit() {
            visit = true;
        }

        public boolean isVisit() {
            return visit;
        }

        private void getBack() {
            if (globalMap[this.x][this.y] != finish && globalMap[this.x][this.y] != start) {
                globalMap[this.x][this.y] = '+';
            }
            if (previous != null) previous.getBack();
        }

    }
}


