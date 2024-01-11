package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rabbit extends Animal{
    //attributes of all rabbits (class variables)
    //the age at which a rabbit is capable of giving birth
    private static final int AGE_OF_BIRTH = 5;
    //the maximum age of a rabbit
    private static final int MAX_AGE = 40;
    //the probability that a rabbit is able to breed
    public static double breedProbability = 0.15;
    //the max of birth at once from a rabbit
    public static int maxBirth = 3;
    private static int nameCounter = 0;
    Random random = new Random();
    List<Animal>animalList;

    List<Animal>deadAnimalList;
    public Rabbit(boolean randomAge){
        super();
        animalList = new ArrayList<>();
        deadAnimalList = new ArrayList<>();
        setIcon("\uD83D\uDC30");
        setTombstone("\uD83E\uDEA6" + "\uD83D\uDE35");
        setDeathIcon("☠\uFE0F");
        if(randomAge) {
            setAge(new Random().nextInt(MAX_AGE));
        }
        setAlive(true);
        setName(generateName());

    }
    private void increaseAge(Field field, Rabbit rabbit){
        setAge(getAge()+ 1);
        if (getAge() > MAX_AGE){
            die(field, rabbit);
        }
    }
    public void die(Field field, Rabbit rabbit){
        System.out.println(getClass().getSimpleName() + getIcon() + getDeathIcon() + " has died (setAlive(false) " + getName());
        setAlive(false);
        field.fieldGrid[getPosition().x()][getPosition().y()] = null;
        handleDeath(animalList, deadAnimalList, rabbit, field);
    }
    public void handleDeath(List<Animal> animals, List<Animal> deadAnimals, Rabbit rabbit, Field field) {
            animals.remove(rabbit); // Remove from active rabbits list
            deadAnimals.add(rabbit); // Add to dead rabbits list
    }
    public void breedNewBorn(Field field) {
        int amountOfBirth = 0;
        if (canBreed()) {
            double chanceOfBirth = random.nextDouble();
            if (chanceOfBirth <= breedProbability) {
                amountOfBirth = random.nextInt(maxBirth);
                for (int b = 0; b < amountOfBirth; b++) {
                    Rabbit rabbit = new Rabbit(false); // Create a new rabbit
                    animalList.add(rabbit);
                    Position newPosition = field.randomPosition(getPosition());
                    rabbit.setPosition(newPosition);
                    field.place(rabbit); // Place the newborn rabbit in the grid
                    rabbit.move(newPosition, field, true); // Trigger movement for the newborn

                    System.out.println(getClass().getSimpleName() + getIcon() + getName() + " can breed: newborn name = [" + rabbit.getName() + "]");
                }
            }
        }
    }
    public boolean canBreed(){
        return getAge() >= AGE_OF_BIRTH;
    }
    private String generateName() {
        return String.format("%06d", nameCounter++);
    }
    public List<Animal> getAnimalList(){
        return animalList;
    }
    public void act(Field field) {
        if (isAlive()) {
            System.out.println(getClass().getSimpleName() + getIcon() + " act " + getName());
            increaseAge(field, this);
            breedNewBorn(field);
            move(getPosition(), field, true); // Move after breeding
        } else {
            System.out.println(getClass().getSimpleName() + getIcon() + getTombstone() + " is already dead, can't perform action " + getName());
            field.fieldGrid[getPosition().x()][getPosition().y()] = null;
        }
    }
    @Override
    public void move(Position position, Field field, Boolean isAlive) {
        if (isAlive) {
            field.moveToNewPosition(position, this);
        }
    }
}
