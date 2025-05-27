// Morfidis Ioannis AM: 5740
public abstract class Animal {
    private Cell cell;
    private int timeStep;
    
    public Animal(Cell cell) {
        this.cell = cell;
        this.timeStep = 0;
        if (cell != null) {
            cell.setAnimal(this);
        }
    }
    
    public abstract boolean isRabbit();
    
    public abstract boolean breedingTime(int timeStep);
    
    public abstract Animal giveBirth();
    
    public Cell getCell() {
        return cell;
    }
    
    public void setCell(Cell cell) {
        this.cell = cell;
    }
    
    public void die() {
        if (cell != null) {
            cell.removeAnimal();
            cell = null;
        }
    }
    
    public void incrementTimeStep() {
        timeStep++;
    }
    
    public int getTimeStep() {
        return timeStep;
    }
    
    public Animal move() {
        if (cell != null) {
            Cell newCell = cell.getRandomEmptyNeighbor();
            if (newCell != null) {
                cell.removeAnimal();
                newCell.setAnimal(this);
                this.cell = newCell;
            }
        }
        return null;
    }
    
    public Animal breed() {
        if (cell != null && breedingTime(timeStep)) {
            Cell newCell = cell.getRandomEmptyNeighbor();
            if (newCell != null) {
                Animal baby = giveBirth();
                baby.setCell(newCell);
                newCell.setAnimal(baby);
                return baby;
            }
        }
        return null;
    }
}