// Morfidis Ioannis AM: 5740
import java.util.Random;

public class Grid {
    private final int SIZE = 20;
    private Cell[][] cells;
    private Random random;
    
    public Grid() {
        cells = new Cell[SIZE][SIZE];
        random = new Random();
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new Cell();
            }
        }
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i > 0) {
                    cells[i][j].addNeighbor(cells[i-1][j]);
                    cells[i-1][j].addNeighbor(cells[i][j]);
                }
                if (j > 0) {
                    cells[i][j].addNeighbor(cells[i][j-1]);
                    cells[i][j-1].addNeighbor(cells[i][j]);
                }
            }
        }
    }
    
    public void addAnimal(Animal animal) {
        int i, j;
        do {
            i = random.nextInt(SIZE);
            j = random.nextInt(SIZE);
        } while (!cells[i][j].isEmpty());
        
        cells[i][j].setAnimal(animal);
        animal.setCell(cells[i][j]);
    }
    
    public String toString() {
        String result = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result += cells[i][j].toString();
            }
            result += "\n";
        }
        return result;
    }
    
    public Cell getCell(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return cells[y][x];  // Note: y is first because that's how the grid is stored
        }
        return null;
    }
    
    public int getSize() {
        return SIZE;
    }
}