package org.example;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Wolves extends Animal{
    private static int nameCounter = 0;
    Random random = new Random();
    List<Animal> animalList;

    public Wolves(){
        super();
        animalList = new ArrayList<>();
        setName(generateName());
        setIcon("\uD83D\uDC3A");

        int randomX = random.nextInt(100);
        int randomY = random.nextInt(100);
        setPosition(new Position(randomX, randomY));
    }
    public List<Animal> getAnimalList(){
        return animalList;
    }
    private String generateName() {
        return String.format("%06d", nameCounter++);
    }
    public void move(Position position, Field field, Boolean isAlive) {
        if (isAlive) {
            field.moveToNewPosition(position, this);
        }
    }

    public void act(Field field) {
        System.out.println(getClass().getSimpleName() + getIcon() + " act " + getName());
        move(getPosition(), field, true);
    }
}