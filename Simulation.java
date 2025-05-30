// Morfidis Ioannis AM: 5740
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Simulation {
    private static final int SIMULATION_STEPS = 1000;
    private Grid grid;
    private AnimalSimulator simulator;
    private Scanner scanner;
    private boolean useGUI;
    private int currentStep;
    
    public Simulation() {
        this.grid = new Grid();
        this.simulator = new AnimalSimulator();
        this.scanner = new Scanner(System.in);
        this.useGUI = false;
        this.currentStep = 0;
        simulator.populate(grid);
    }
    
    public Simulation(boolean useGUI) {
        this.grid = new Grid();
        this.simulator = new AnimalSimulator();
        this.useGUI = useGUI;
        this.currentStep = 0;
        simulator.populate(grid);
    }
    
    public static void main(String[] args) {
        // Check if GUI mode is requested
        boolean useGUI = args.length > 0 && args[0].equals("--gui");
        
        if (useGUI) {
            Simulation simulation = new Simulation(true);
            SwingUtilities.invokeLater(() -> new SimulationGUI(simulation));
        } else {
            runConsoleMode();
        }
    }
    
    private static void runConsoleMode() {
        Simulation simulation = new Simulation(false);
        Scanner scanner = simulation.scanner;
        
        for (int step = 1; step <= SIMULATION_STEPS; step++) {
            System.out.println("Step: " + step);
            System.out.println(simulation.grid);
            simulation.simulator.printPopulations();
            
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            
            simulation.step();
        }
    }
    
    public void step() {
        currentStep++;
        simulator.moveAndBreedAnimals(currentStep);
    }
    
    public Grid getGrid() {
        return grid;
    }
    
    public int getFoxCount() {
        int foxCount = 0;
        for (Animal animal : simulator.getAnimals()) {
            if (!animal.isRabbit()) {
                foxCount++;
            }
        }
        return foxCount;
    }
    
    public int getRabbitCount() {
        int rabbitCount = 0;
        for (Animal animal : simulator.getAnimals()) {
            if (animal.isRabbit()) {
                rabbitCount++;
            }
        }
        return rabbitCount;
    }
}