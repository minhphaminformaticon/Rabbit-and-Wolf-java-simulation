package org.example;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.stream.Collectors;

public class Simulation {
  public static List<Animal> animals = new ArrayList<>();
    static Field field;

    static {
        try {
            field = new Field();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Simulation(int steps, int startingRabbits, int startingWolves) throws InterruptedException {
        // The current step in the simulation
        for (int i = 0; i < startingRabbits; i++ ) {
            Rabbit rabbit = new Rabbit(true);

            Random random = new Random();
            int randomX = random.nextInt(100);
            int randomY = random.nextInt(100);
            rabbit.setPosition(new Position(randomX, randomY));

            animals.add(rabbit);
        }
        for (int j = 0; j < startingWolves; j++) {
            Wolves wulf = new Wolves();
            field.place(wulf);
            animals.add(wulf);
        }
        startingSteps(steps);
    }
    public void startingSteps(int steps) throws InterruptedException {
        field.updateGrid();
        int exactSteps = steps + 1;
        simulateSimulation(exactSteps);
    }
    public void simulateSimulation(int steps) throws InterruptedException {
        int step;
        int rabbitCount = 0;
        int wolfCount = 0;
        int winCondition = 20;
        boolean rabbitsContaining = animals.stream()
                .anyMatch(animal -> animal instanceof Rabbit);
        for (step = 1; step <= steps; step++){
            simulateOneStep();
            TimeUnit.NANOSECONDS.sleep(500000000);
            System.out.println("Step: " + step + "//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
            if ((step + 1) % 10 == 0){
                System.out.println("Wolves spawned");
                Wolves wulf = new Wolves();
                field.place(wulf);
                animals.add(wulf);
            }else {
                System.out.println("no wolves spawned");
            }
            updateAnimalList();
            rabbitsContaining = animals.stream()
                    .anyMatch(animal -> animal instanceof Rabbit);
            if (!rabbitsContaining){
                break;
            }
            field.updateGrid();
        }
        field.clearGrid();
        if (steps <= 20){
            winCondition = 10;
        } else if (steps <= 50){
            winCondition = 20;
        } else if (steps <= 100){
            winCondition = 30;
        }


        for (Animal rabbit : animals.stream().filter(animal -> animal instanceof Rabbit).toList()){
            rabbitCount++;
        }
        for (Animal wolf : animals.stream().filter(animal -> animal instanceof Wolves).toList()){
            wolfCount++;
        }
        if (wolfCount * winCondition < rabbitCount){
            field.rabbitWins();
        } else if ((wolfCount * winCondition > rabbitCount) || !rabbitsContaining) {
            field.wolfWins();
        }else {
            field.noOneWins();
        }
    }
    public static void simulateOneStep(){
           for (Animal rabbit : animals.stream().filter(animal ->
                animal instanceof Rabbit).toList()) {
               rabbit.act(field);
           }
            for (Animal wolves : animals.stream().filter(animal ->
                    animal instanceof Wolves).toList()) {
                wolves.act(field);
            }

    }

    public void importNewBorn() {
        List<Animal> allNewBorns = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                allNewBorns.addAll(rabbit.getAnimalList());
            }
        }

        for (Animal newBorn : allNewBorns) {
            if (!animals.contains(newBorn)) {
                animals.add(newBorn);
            }
        }
    }

    public void updateAnimalList(){
        importNewBorn();
        removeDeadRabbits();
    }

    private void removeDeadRabbits() {

    }
}
