package simonGame;

import java.io.IOException;
import java.util.Scanner;

import com.pi4j.util.Console;

import swiftbot.SwiftBotAPI;

public class SimonGame {


    static SwiftBotAPI swiftBot;

    public static void main(String[] args) {


        swiftBot = new SwiftBotAPI();
        final Console console = new Console();
        console.title("PLAY ");
        Scanner reader = new Scanner(System.in);


        console.println("Would you like to play?\n1/0 >>");
        String ansRaw = reader.next();
        try {
            int ans = Integer.parseInt(ansRaw);
            switch (ans) {
                case 0:
                    console.println("too bad :(");
                    break;
                case 1:
                    play();
                    break;
                default:
                    throw new Exception();
            }


        } catch (Exception e) {
            System.out.println("\nInvalid input, please enter 0/1\n");

        }

    }


    private static void play() throws InterruptedException, IOException {
        System.out.println("playyyy timmmmee");
        int counter = 6;
        int lives = 3;
        //while (lives!=0){
        int[] colourArray = new int[counter];

        addColoursToArray(colourArray);

        ///}

    }

    private static void displayingColoursInArray(int[] colourArray) throws IOException, InterruptedException {
        System.out.println("displayingColoursInArray");
        for (int j : colourArray) {
            if (j == 0) {
                swiftBot.setUnderlight(SwiftBotAPI.Underlight.FRONT_LEFT, 255, 0, 0);
                Thread.sleep(2000);
                swiftBot.disableUnderlight(SwiftBotAPI.Underlight.FRONT_LEFT,false);
                swiftBot.updateUnderlights();
            } else if (j == 1) {
                swiftBot.setUnderlight(SwiftBotAPI.Underlight.FRONT_RIGHT, 0, 255, 0);
                Thread.sleep(2000);
                swiftBot.disableUnderlight(SwiftBotAPI.Underlight.FRONT_RIGHT,false);
                swiftBot.updateUnderlights();
            } else if (j == 2) {
                swiftBot.setUnderlight(SwiftBotAPI.Underlight.BACK_LEFT, 0, 0, 255);
                Thread.sleep(2000);
                swiftBot.disableUnderlight(SwiftBotAPI.Underlight.BACK_LEFT,false);
                swiftBot.updateUnderlights();
            } else if (j == 3) {
                swiftBot.setUnderlight(SwiftBotAPI.Underlight.BACK_RIGHT, 255, 255, 0);
                Thread.sleep(2000);
                swiftBot.disableUnderlight(SwiftBotAPI.Underlight.BACK_RIGHT,false);
                swiftBot.updateUnderlights();
            }
        }
    }


        private static void addColoursToArray ( int[] colourArray) throws IOException, InterruptedException {
            System.out.println("addColoursToArray");
            for (int i = 0; i < colourArray.length; i++) {
                int randomNum = (int) (Math.random() * 4);
                colourArray[i] = randomNum;
                System.out.println(randomNum);

            }
            displayingColoursInArray(colourArray);
        }
    }

