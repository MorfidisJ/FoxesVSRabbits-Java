// Morfidis Ioannis AM: 5740
public class SimulationTest {
    private static final int SIMULATION_STEPS = 1000;
    private static final int TEST_RUNS = 1000;
    public static void main(String[] args) {
        int extinctionCount = 0;
        
        System.out.println("Running " + TEST_RUNS + " simulations...");
        
        for (int run = 1; run <= TEST_RUNS; run++) {
            Grid grid = new Grid();
            AnimalSimulator simulator = new AnimalSimulator();
            
            simulator.populate(grid);
            
            // Run the simulation
            for (int step = 1; step <= SIMULATION_STEPS; step++) {
                simulator.moveAndBreedAnimals(step);
            }
            
            // Count final populations
            AnimalSimulator.PopulationCounter counter = simulator.getPopulationCounts();
            int foxCount = counter.foxes;
            int rabbitCount = counter.rabbits;
            
            // Check if rabbits >= 400 and foxes = 0
            if (rabbitCount >= 400 && foxCount == 0) {
                extinctionCount++;
            }
            
            // Print progress every 10 runs
            if (run % 10 == 0) {
                System.out.println("Completed " + run + " runs. Current extinction rate: " + 
                    (extinctionCount * 100.0 / run) + "%");
            }
        }
        
        double extinctionPercentage = (extinctionCount * 100.0) / TEST_RUNS;
        
        System.out.println("\n=== SIMULATION RESULTS ===");
        System.out.println("Total runs: " + TEST_RUNS);
        System.out.println("Runs with 400+ rabbits and 0 foxes: " + extinctionCount);
        System.out.println("Extinction percentage: " + extinctionPercentage + "%");
    }
}