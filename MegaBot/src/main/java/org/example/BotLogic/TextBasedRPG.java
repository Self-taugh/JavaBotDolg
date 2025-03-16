package org.example.BotLogic;
import org.example.Structures.Module;

import java.util.Random;
import java.util.Scanner;

public class TextBasedRPG implements Module {
    Game game;
    int Stage = 0;
    String bufer;
    public TextBasedRPG(){
        game = new Game();
    }

    public void Do(String[] args) {
        System.out.println("Welcome to the Text-Based RPG!\nEnter your command:");
    }

    @Override
    public String TakeResult() {
        return bufer;
    }

    @Override
    public void Input(String input) {
        if (Stage == 0){
            bufer = "Welcome to the Text-Based RPG!\nEnter your command:";
            Stage = 1;
        }
        else {
            bufer = game.processCommand(input);
        }
    }
}

class Game {
    private Player player;
    private Random random;
    private boolean waitingForContinue;

    public Game() {
        player = new Player("Hero", 100, 10);
        random = new Random();
        waitingForContinue = false;
    }

    public String processCommand(String command) {

        if (waitingForContinue) {
            waitingForContinue = false;
            return "Enter your next command:";
        }

        switch (command.toLowerCase()) {
            case "look":
                return "You are in a dark forest. You see a path to the north.";
            case "north":
                return encounter();
            case "attack":
                return player.attack();
            case "status":
                return player.getStatus();
            default:
                return "Unknown command. Try 'look', 'north', 'attack', or 'status'.";
        }
    }

    private String encounter() {
        int event = random.nextInt(3);

        switch (event) {
            case 0:
                waitingForContinue = true;
                return "You walk north and encounter a wild goblin! (enter any command to continue)";
            case 1:
                player.changeHealth(-10);
                waitingForContinue = true;
                return "You fall into a trap and lose 10 health! (enter any command to continue)";
            case 2:
                player.changeHealth(+20);
                waitingForContinue = true;
                return "You find a healing potion and gain 20 health! (enter any command to continue)";
            default:
                return "An uneventful journey north.";
        }
    }
}

class Player {
    private String name;
    private int health;
    private int attackPower;

    public Player(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String attack() {
        return "You attack the enemy with " + attackPower + " power!";
    }

    public String getStatus() {
        return "Player: " + name + "\nHealth: " + health + "\nAttack Power: " + attackPower;
    }

    public void changeHealth(int amount) {
        health += amount;
        if (health < 0) health = 0;
    }
}