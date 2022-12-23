
package simonGame;

import java.io.IOException;
import java.util.*;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.util.Console;

import swiftbot.SwiftBotAPI;

import static swiftbot.SwiftBotAPI.Underlight.*;

public class SimonGame {
    private static int lives = 3;
    private static int turn = 0;

    static SwiftBotAPI swiftBot;

    public static void main(String[] args) {
        swiftBot = new SwiftBotAPI();
        swiftBot.fillButtonLights();
        userInputTerminal();

    }

    private static void userInputTerminal() {
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
        System.out.println("play method");
        int counter = 2;
        //while (lives!=0){
        List<Integer> colourArrayList = new ArrayList<>();

        addColoursToArray(colourArrayList); //TODO move to initialise

        do {
            userButtonInput(colourArrayList);
        } while (colourArrayList.size() > 0 && isInputCorrect == false);
    }

    private static void addColoursToArray(List<Integer> colourArrayList) throws IOException, InterruptedException {
        System.out.println("addColoursToArray method");
        for (int i = 0; i < 3; i++) {
            int randomNum = (int) (Math.random() * 4);
            colourArrayList.set(i, randomNum);
            System.out.println(randomNum);
        }
        displayingColoursInArray(colourArrayList);
    }

    private static void displayingColoursInArray(List<Integer> colourArrayList) throws IOException, InterruptedException {
        System.out.println("displayingColoursInArray");
        for (int j : colourArrayList) {
            if (j == 0) {
                displayColourSimplifiyer(255, 0, 0, FRONT_LEFT, 1000);
            } else if (j == 1) {
                displayColourSimplifiyer(0, 255, 0, FRONT_RIGHT, 1000);
            } else if (j == 2) {
                displayColourSimplifiyer(0, 0, 255, BACK_LEFT, 1000);
            } else if (j == 3) {
                displayColourSimplifiyer(255, 255, 0, BACK_RIGHT, 1000);
            }
        }
    }

    public static void displayColourSimplifiyer(int r, int g, int b, SwiftBotAPI.Underlight whichLight, int time) throws
            IOException, InterruptedException {
        swiftBot.setUnderlight(whichLight, r, g, b);
        Thread.sleep(time);
        swiftBot.disableUnderlight(whichLight, false);
        swiftBot.updateUnderlights();
    }

    private static void userButtonInput(List<Integer> colourArrayList) {
        boolean wrongInput;
        System.out.println("user input and check");

        swiftBot.BUTTON_A.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isLow()) {
                System.out.println("red");
                wrongInput = verifyUserButtonInput(0, colourArrayList);
            }
        });
        swiftBot.BUTTON_X.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    System.out.println("green");
                    verifyUserButtonInput(1, colourArrayList);
                }
            }
        });
        swiftBot.BUTTON_B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    System.out.println("Blue");
                    verifyUserButtonInput(2, colourArrayList);
                }
            }
        });
        swiftBot.BUTTON_Y.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    System.out.println("Yellow");
                    verifyUserButtonInput(3, colourArrayList);
                }
            }
        });
    }

    private static boolean verifyUserButtonInput(int buttonPressed, List<Integer> colourArrayList) {
        if (colourArrayList.size() > 0) {
            if (buttonPressed == colourArrayList.get(turn)) {
                return true;
                turn++;
            } else {
                return false;
                lives -= 1;

            }
        }
    }

}

    
