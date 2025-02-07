package src.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import src.main.models.Game;
import src.main.models.Team;

public class Main {

    static Game game;
    static final String TEAMS_FILE = "src/main/teams.txt";
    static final String PLAYS_FILE = "src/main/plays.txt";

    public static void main(String[] args) throws FileNotFoundException{

        try {

            String[][] data = getData();

            game = new Game (
                new Team(data[0][0], data[0][1], data[0][2], new String[] {data[0][3], data[0][4], data[0][5]}),
                new Team(data[1][0], data[1][1], data[1][2], new String[] {data[1][3], data[1][4], data[1][5]})
            );

            startGame();
            printResult();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        


    }


    public static String[][] getData() throws  FileNotFoundException{
        FileInputStream fis = new FileInputStream(TEAMS_FILE);
        Scanner filescaner = new Scanner(fis);
        String[] lines = new String[] {filescaner.nextLine(), filescaner.nextLine()};

        filescaner.close();

        return new String[][] {
            lines[0].trim().split(","), lines[1].trim().split(",")
        };

    }



    public static void startGame() throws FileNotFoundException{
        FileInputStream fis = new FileInputStream(PLAYS_FILE);
        Scanner scanfile = new Scanner(fis);

        while (scanfile.hasNextLine()) {
            wait(3);
            System.out.println("\n" + game.simulate(scanfile.nextLine())  + "\n");
        }
        
       scanfile.close(); 
    }


    public static void printResult(){
        Team grif = game.getTeam("GRYFFINDOR");
        Team slit = game. getTeam("SLYTHERIN");
        System.out.println("\nGRYFFINDOR: " + game.getScore(grif) + " SLYTHERIN: " + game.getScore(slit));
    }
    



    public static void wait(int sec) {
        try{
            TimeUnit.SECONDS.sleep(sec);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    
    }
  }
