// Morfidis Ioannis AM: 5740
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AnimalSimulator {
    
    private HashSet<Animal> animals;
    private final int NUM_OF_FOXES = 5;
    private final int NUM_OF_RABBITS = 100;
    
    public AnimalSimulator() {
        animals = new HashSet<Animal>();
    }
    
    public void populate(Grid grid) {
        for (int i = 0; i < NUM_OF_FOXES; i++) {
            Fox fox = new Fox(null);
            grid.addAnimal(fox);
            animals.add(fox);
        }
        
        for (int i = 0; i < NUM_OF_RABBITS; i++) {
            Rabbit rabbit = new Rabbit(null);
            grid.addAnimal(rabbit);
            animals.add(rabbit);
        }
    }
    
    public void moveAndBreedAnimals(int timeStep) {
        ArrayList<Animal> animalsList = new ArrayList<Animal>(animals);
        Collections.shuffle(animalsList);
        
        HashSet<Animal> deadAnimals = new HashSet<Animal>();
        HashSet<Animal> newAnimals = new HashSet<Animal>();
        
        for (Animal animal : animalsList) {
            if (!deadAnimals.contains(animal)) {
                animal.incrementTimeStep();
                Animal deadAnimal = animal.move();
                
                if (deadAnimal != null) {
                    deadAnimals.add(deadAnimal);
                }
                
                Animal baby = animal.breed();
                if (baby != null) {
                    newAnimals.add(baby);
                }
            }
        }
        
        animals.removeAll(deadAnimals);
        animals.addAll(newAnimals);
    }
    
    public void printPopulations() {
        int foxCount = 0;
        int rabbitCount = 0;
        
        for (Animal animal : animals) {
            if (animal.isRabbit()) {
                rabbitCount++;
            } else {
                foxCount++;
            }
        }
        
        System.out.println("Foxes: " + foxCount + ", Rabbits: " + rabbitCount);
    }
    
    // ADD THIS METHOD for the test class
    public PopulationCounter getPopulationCounts() {
        int foxCount = 0;
        int rabbitCount = 0;
        
        for (Animal animal : animals) {
            if (animal.isRabbit()) {
                rabbitCount++;
            } else {
                foxCount++;
            }
        }
        
        return new PopulationCounter(foxCount, rabbitCount);
    }
    
    // ADD THIS HELPER CLASS
    public static class PopulationCounter {
        public int foxes;
        public int rabbits;
        
        public PopulationCounter(int foxes, int rabbits) {
            this.foxes = foxes;
            this.rabbits = rabbits;
        }
    }
}