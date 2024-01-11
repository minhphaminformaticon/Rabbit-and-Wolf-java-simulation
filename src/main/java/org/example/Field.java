package org.example;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class Field extends JPanel {
        private final int CELL_SIZE = 10;
        private final int RADIUS = 5;
        private final int RADIUS2 = 8;
        private int gridSizeX;
        private int gridSizeY;
        public Animal[][] fieldGrid;
        private List<Animal> animals;
        int rabbitsCounter;
        int wolvesCounter;
        int stepsCount = -1;
        Random random = new Random();
        JFrame frame = new JFrame("Predator-Prey Simulation");
        JLabel winnerRabbit = new JLabel("Rabbits win!");
        JLabel winnerWolf = new JLabel("Wolves win!");
        JLabel tie = new JLabel("It's a tie!");
        JLabel displayRabbits = new JLabel();
        JLabel displayWolves = new JLabel();
        JLabel steps = new JLabel();

        public Field() throws InterruptedException {
            animals = new ArrayList<>();
            Dimension screenSize = new Dimension(1000, 1000);
            gridSizeY = screenSize.height / CELL_SIZE;
            gridSizeX = screenSize.width / CELL_SIZE;

            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set the size of the panel (Field)
            setPreferredSize(screenSize);
            frame.add(this, BorderLayout.CENTER);
            frame.pack();
            // Maximizing the frame to full screen
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            Border border = BorderFactory.createLineBorder(Color.WHITE);

            JPanel bottomStats = new JPanel();
            bottomStats.setBackground(Color.DARK_GRAY);
            displayRabbits.setBorder(border);
            displayRabbits.setPreferredSize(new Dimension(150, 100));
            displayRabbits.setText("pasta");
            displayRabbits.setForeground(Color.WHITE);
            displayRabbits.setVerticalAlignment(JLabel.BOTTOM);
            displayRabbits.setHorizontalAlignment(JLabel.CENTER);
            bottomStats.add(displayRabbits);

            displayWolves.setBorder(border);
            displayWolves.setPreferredSize(new Dimension(150, 100));
            displayWolves.setText("spaghetti");
            displayWolves.setForeground(Color.WHITE);
            displayWolves.setHorizontalAlignment(JLabel.CENTER);
            displayWolves.setVerticalAlignment(JLabel.BOTTOM);
            bottomStats.add(displayWolves);

            steps.setBorder(border);
            steps.setPreferredSize(new Dimension(100,100));
            steps.setText("steps");
            steps.setForeground(Color.WHITE);
            steps.setHorizontalAlignment(JLabel.CENTER);
            steps.setVerticalAlignment(JLabel.BOTTOM);
            bottomStats.add(steps);

            frame.add(bottomStats, BorderLayout.PAGE_END);

            fieldGrid = new Animal[gridSizeX][gridSizeY];

        }
    public void place(Animal animal)
    {
        Position position = animal.getPosition();
        fieldGrid[position.x()][position.y()] = animal;
    }


    public Position randomPosition(Position position)
    {
        int x = position.x();
        int y = position.y();

        int nextX = random.nextInt(100);
        int nextY = random.nextInt(100);

        if(nextX < 0 || nextX >= gridSizeY
                || nextY < 0 || nextY >= gridSizeX || fieldGrid[nextX][nextY] != null) {
            return position;
        }
        else if(nextX != x || nextY != y ) {
            return new Position(nextX, nextY);
        }
        else {
            return position;
        }
    }
    public void moveToNewPosition(Position position, Animal animal) {
        Random random = new Random();
        int newX = 0;
        int newY = 0;

        if (animal instanceof Rabbit) {
            do {
                newX = position.x() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
                newY = position.y() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
            } while (!isValidMove(newX, newY));
        } else if (animal instanceof Wolves) { // For Wolves
            boolean rabbitFound = false;
            int startX = Math.max(0, position.x() - 3);
            int endX = Math.min(gridSizeX - 1, position.x() + 3);
            int startY = Math.max(0, position.y() - 3);
            int endY = Math.min(gridSizeY - 1, position.y() + 3);

            // Check if a rabbit is in the 5x5 radius
            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    if (fieldGrid[x][y] instanceof Rabbit) {
                        rabbitFound = true;
                        fieldGrid[x][y].setAlive(false);
                        newX = x;
                        newY = y;
                        System.out.println("a wolf has killed rabbit " + fieldGrid[x][y].getName()
                                + fieldGrid[x][y].getIcon());
                        fieldGrid[x][y] = null;
                        break;
                    }
                }
                if (rabbitFound) {
                    break;
                }
            }

            // If no rabbit found in the radius, move to a random free spot
            if (!rabbitFound) {
                do {
                    newX = position.x() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
                    newY = position.y() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
                } while (!isValidMove(newX, newY));
            }
        }

        if (animal.isAlive()) {
            animal.setPosition(new Position(newX, newY));

            fieldGrid[position.x()][position.y()] = null;
            fieldGrid[newX][newY] = animal;
        } else {
            fieldGrid[newX][newY] = null;
        }
    }
    public boolean isValidMove(int x, int y) {
        boolean withinBounds = x >= 0 && x < gridSizeX && y >= 0 && y < gridSizeY;

        if (!withinBounds) {
            return false;
        }

        if (fieldGrid[x][y] == null) {
            return true; // Empty cell, any animal can move here
        }

        if (fieldGrid[x][y] instanceof Rabbit && fieldGrid[x][y] != null) {
            if (fieldGrid[x][y] instanceof Wolves) {
                return true; // Wolf can move and eat Rabbit
            }
            return false; // Rabbit present, other animals cannot move here
        }

        return true;
    }
    public void updateGrid() {
        displayRabbits.setText("Rabbits " + "\uD83D\uDC30" +  ": " + rabbitsCounter);
        displayWolves.setText("Wolves " + "\uD83D\uDC3A" +":" + wolvesCounter);
        stepsCount++;
        steps.setText("Steps: " + stepsCount);
        frame.repaint();
    }
    public void clearGrid() {
        frame.getContentPane().removeAll();
        frame.repaint();
    }
    public void rabbitWins(){
        Dimension size = winnerRabbit.getPreferredSize();
        winnerRabbit.setBounds(150, 100, size.width, size.height);
        frame.add(winnerRabbit);
        frame.repaint();
    }
    public void wolfWins(){
            Dimension size = winnerWolf.getPreferredSize();
            winnerWolf.setBounds(150, 100, size.width, size.height);
            frame.add(winnerWolf);
            frame.repaint();
    }
    public void noOneWins(){
        Dimension size = tie.getPreferredSize();
        tie.setBounds(150, 100, size.width, size.height);
        frame.add(tie);
        frame.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        rabbitsCounter = 0;
        wolvesCounter = 0;

        for (int y = 0; y < gridSizeY; y++) {
            for (int x = 0; x < gridSizeX; x++) {
                int cellWidth = width / gridSizeX;
                int cellHeight = height / gridSizeY;


                if (fieldGrid[x][y] != null) {
                    if (fieldGrid[x][y] instanceof Rabbit) {
                        g.setColor(Color.BLUE);
                        rabbitsCounter++;
                    } else if (fieldGrid[x][y] instanceof Wolves) {
                        g.setColor(Color.RED);
                        wolvesCounter++;
                    }
                } else{
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }
        System.out.println("Rabbits: " + rabbitsCounter);
        System.out.println("Wolves: " + wolvesCounter);
    }
}
