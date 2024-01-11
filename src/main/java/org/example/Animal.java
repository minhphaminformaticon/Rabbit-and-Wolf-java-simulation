package org.example;

public abstract class Animal {
    private Position position;
    private boolean isAlive;
    private int age;
    private String name;
    private String icon;
    private String tombstone;
    private String deathIcon;

    public Animal(){
        age = 0;
        isAlive = true;
    }
    abstract public void act(Field field);
    abstract public void move(Position position, Field field, Boolean isAlive);
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public boolean isAlive(){
        return isAlive;
    }

    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position = new Position(position.x(), position.y());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTombstone() {
        return tombstone;
    }

    public void setTombstone(String tombstone) {
        this.tombstone = tombstone;
    }

    public String getDeathIcon() {
        return deathIcon;
    }

    public void setDeathIcon(String deathIcon) {
        this.deathIcon = deathIcon;
    }
}
