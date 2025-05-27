// Morfidis Ioannis AM: 5740
public class Rabbit extends Animal {
    
    public Rabbit(Cell cell) {
        super(cell);
    }
    
    public boolean isRabbit() {
        return true;
    }
    
    public boolean breedingTime(int timeStep) {
        return timeStep > 0 && timeStep % 3 == 0;
    }
    
    public Animal giveBirth() {
        return new Rabbit(null);
    }
    
    public String toString() {
        return "o";
    }
}