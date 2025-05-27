// Morfidis Ioannis AM: 5740
import java.util.ArrayList;
import java.util.Collections;

public class Cell {
    private ArrayList<Cell> neighbors;
    private Animal animal;
    
    public Cell() {
        neighbors = new ArrayList<Cell>();
        animal = null;
    }
    
    public void addNeighbor(Cell cell) {
        neighbors.add(cell);
    }
    
    public Cell getRandomEmptyNeighbor() {
        Collections.shuffle(neighbors);
        for (Cell cell : neighbors) {
            if (cell.isEmpty()) {
                return cell;
            }
        }
        return null;
    }
    
    public Cell getRandomRabbitNeighbor() {
        Collections.shuffle(neighbors);
        for (Cell cell : neighbors) {
            if (cell.containsRabbit()) {
                return cell;
            }
        }
        return null;
    }
    
    public Animal getAnimal() {
        return animal;
    }
    
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    
    public void removeAnimal() {
        animal = null;
    }
    
    public boolean isEmpty() {
        return animal == null;
    }
    
    public boolean containsRabbit() {
        return animal != null && animal.isRabbit();
    }
    
    public String toString() {
        if (isEmpty()) {
            return "_";
        } else {
            return animal.toString();
        }
    }
}