// Morfidis Ioannis AM: 5740
public class Fox extends Animal {
    private int stepsWithoutFood;

    public Fox(Cell cell) {
        super(cell);
        stepsWithoutFood = 0;
    }
    public boolean isRabbit() {
        return false;
    }
    public boolean breedingTime(int timeStep) {
        return timeStep > 0 && timeStep % 8 == 0; 
    }
    public Animal giveBirth() {
        return new Fox(null); 
    }
    public Animal move() {
        if (getCell() == null) { 
            return null;
        }

       
        Cell currentCell = getCell();
        Cell rabbitCell = currentCell.getRandomRabbitNeighbor(); 

        if (rabbitCell != null) {
            Animal rabbit = rabbitCell.getAnimal();
            currentCell.removeAnimal(); 
            rabbitCell.setAnimal(this);
            setCell(rabbitCell); 
            stepsWithoutFood = 0;  
            return rabbit; 
        } else {
            stepsWithoutFood++;
            if (stepsWithoutFood >= 3) { 
                die(); 
                return this; 
            } else {
                
                return super.move(); 
            }
        }
    }

    public String toString() {
        return "X";
    }
}