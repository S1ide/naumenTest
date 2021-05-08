import jdk.jshell.execution.Util;

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
                        Utils.printField(globalMap);
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
            int toleft = x - 1;
            if (x == 0 || globalMap[toleft][y] == block) {
                left = null;
            } else {
                if (massOfCells[toleft][y] == null) {
                    left = new Cell(toleft, y, this);
                    massOfCells[toleft][y] = left;
                    neighbours.add(left);
                }
            }
            int toTop = y - 1;
            if (y == 0 || globalMap[x][toTop] == block) {
                top = null;
            } else {
                if (massOfCells[x][toTop] == null) {
                    top = new Cell(x, toTop, this);
                    massOfCells[x][toTop] = top;
                    neighbours.add(top);
                }
            }
            int toRight = x + 1;
            if (x == globalMap.length - 1 || globalMap[toRight][y] == block) {
                right = null;
            } else {
                if (massOfCells[toRight][y] == null) {
                    right = new Cell(toRight, y, this);
                    massOfCells[toRight][y] = right;
                    neighbours.add(right);
                }
            }
            int toBottom = y + 1;
            if (y == globalMap.length - 1 || globalMap[x][toBottom] == block) {
                bottom = null;
            } else {
                if (massOfCells[toBottom][y] == null) {
                    bottom = new Cell(x, toBottom, this);
                    massOfCells[x][toBottom] = bottom;
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


