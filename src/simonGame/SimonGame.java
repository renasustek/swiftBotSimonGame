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
        System.out.println("playyyy timmmmee MISTER");
        int counter = 2;

        //while (lives!=0){
        List<Integer> colourArray = new ArrayList<>();

        addColoursToArray(colourArray); //TODO move to initialise
        userButtonInput(colourArray);
    }


    private static void inputCheck(int buttonPressed, List<Integer> colourArray) {
        boolean wrongInput = false;
        if (colourArray.size() > 0) {
            if (buttonPressed == colourArray.get(0)) {
                colourArray.remove(0);
            } else {
                wrongInput = true;
                lives -=1;

            }
        } else if (colourArray.size() == 1) {
            System.out.println("you won");
        }
        returnTrueWrongInput(wrongInput);

    }


    private static boolean returnTrueWrongInput(boolean wrongInput) {
        return wrongInput;
    }

    private static void userButtonInput(List<Integer> colourArray) {

        System.out.println("user input and check");

        swiftBot.BUTTON_A.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isLow()) {
                System.out.println("red");
                inputCheck(0, colourArray);
            }
        });
        swiftBot.BUTTON_X.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    System.out.println("green");
                    inputCheck(1, colourArray);
                }
            }
        });
        swiftBot.BUTTON_B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    System.out.println("Blue");
                    inputCheck(2, colourArray);
                }
            }
        });
        swiftBot.BUTTON_Y.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    System.out.println("Yellow");
                    inputCheck(3, colourArray);
                }
            }
        });
    }

    private static void displayingColoursInArray(List<Integer> colourArray) throws IOException, InterruptedException {
        System.out.println("displayingColoursInArray");
        for (int j : colourArray) {
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

    List<Integer> colourArray
    private static void addColoursToArray() throws IOException, InterruptedException {
        int[] colourArray = Arr
        System.out.println("addColoursToArray");
        for (int i = 0; i < 3; i++) {
            int randomNum = (int) (Math.random() * 4);
            colourArray.set(i, randomNum);
            System.out.println(randomNum);
        }
        displayingColoursInArray(colourArray);
    }

    public static void displayColourSimplifiyer(int r, int g, int b, SwiftBotAPI.Underlight whichLight, int time) throws
            IOException, InterruptedException {
        swiftBot.setUnderlight(whichLight, r, g, b);
        Thread.sleep(time);
        swiftBot.disableUnderlight(whichLight, false);
        swiftBot.updateUnderlights();
    }
}






