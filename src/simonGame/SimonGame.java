package simonGame;

import java.io.IOException;
import java.util.*;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.util.Console;

import swiftbot.SwiftBotAPI;

import static swiftbot.SwiftBotAPI.Underlight.*;

public class SimonGame {
    int lives = 3;

    private static final Map<SwiftBotAPI.Underlight, int[]> lights = Map.of(
            FRONT_LEFT, new int[]{255, 0, 0}
    );
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
        System.out.println("playyyy timmmmee");
        int counter = 2;

        //while (lives!=0){
        int[] colourArray = new int[counter];

        addColoursToArray(colourArray); //TODO move to initialise

        boolean result = userInputAndCheck(colourArray);
        System.out.println(result);
    }

    private static boolean userInputAndCheck(int[] colourArray) {
        System.out.println("user input and check");
        final boolean[] buttonPressed = new boolean[4];
        swiftBot.BUTTON_A.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isLow()) {
                buttonPressed[0] = true;
                System.out.println("red");
            }
        });
        swiftBot.BUTTON_X.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[1] = true;
                    System.out.println("green");
                }
            }
        });
        swiftBot.BUTTON_B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[2] = true;
                    System.out.println("Blue");
                }
            }
        });
        swiftBot.BUTTON_Y.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[3] = true;
                    System.out.println("Yellow");
                }
            }
        });




        boolean wrongInput = false;

        for (int x : colourArray) {
            System.out.println("NEWLINECOLOURARRAY");
            if (buttonPressed[0] == true && x != 0) {
                wrongInput = true;
                break;
            } else if (buttonPressed[1] == true && x != 1) {
                wrongInput = true;
                break;
            } else if (buttonPressed[2] == true && x != 2) {
                wrongInput = true;
                break;
            } else if (buttonPressed[3] == true && x != 3) {
                wrongInput = true;
                break;
            }

        }
        return wrongInput;
    }

    private static void displayingColoursInArray(int[] colourArray) throws IOException, InterruptedException {
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


    private static void addColoursToArray(int[] colourArray) throws IOException, InterruptedException {
        System.out.println("addColoursToArray");
        for (int i = 0; i < colourArray.length; i++) {
            int randomNum = (int) (Math.random() * 4);
            colourArray[i] = randomNum;
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







