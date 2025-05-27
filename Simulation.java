// Morfidis Ioannis AM: 5740
import java.util.Scanner;

public class Simulation {
    private static final int SIMULATION_STEPS = 1000;
    
    public static void main(String[] args) {
        Grid grid = new Grid();
        AnimalSimulator simulator = new AnimalSimulator();
        Scanner scanner = new Scanner(System.in);
        
        simulator.populate(grid);
        
        for (int step = 1; step <= SIMULATION_STEPS; step++) {
            
            System.out.println("Step: " + step);
            System.out.println(grid);
            simulator.printPopulations();
            
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            
            simulator.moveAndBreedAnimals(step);
        }
        
    }
}