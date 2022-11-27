package simonGame;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.util.Console;

import swiftbot.SwiftBotAPI;

import static swiftbot.SwiftBotAPI.Underlight.*;

public class SimonGame {


    static SwiftBotAPI swiftBot;

    public static void main(String[] args) {
        swiftBot = new SwiftBotAPI();
        swiftBot.fillButtonLights();
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
        int lives = 3;
        //while (lives!=0){
        int[] colourArray = new int[counter];

        addColoursToArray(colourArray);
        userInputAndCheck(colourArray);
        ///}

    }

    private static void userInputAndCheck(int[] colourArray) {
        System.out.println("user input and check");
        final boolean[] buttonPressed = new boolean[4];
        swiftBot.BUTTON_A.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[0] = true;
                }
            }
        });
        swiftBot.BUTTON_X.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[1] = true;
                }
            }
        });
        swiftBot.BUTTON_B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[2] = true;
                }
            }
        });
        swiftBot.BUTTON_Y.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isLow()) {
                    buttonPressed[3] = true;
                }
            }
        });

        for (int x: colourArray){
        }
    }


        /* for (int x : colourArray) {

                    System.out.println("A PRESSED");
                    try {
                        displayColourSimplifiyer(255, 0, 0, "FL", 750);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (x != 0) {
                        boolean y = false;
                    }
                }

            }
        });

        swiftBot.BUTTON_A.removeAllListeners();
        swiftBot.BUTTON_X.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                if (event.getState().isLow()) {

                    try {
                        displayColourSimplifiyer(0, 255, 0, "FR", 750);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (x != 1) {
                        boolean y = false;
                    }
                }

            }
        });
        swiftBot.BUTTON_X.removeAllListeners();
        swiftBot.BUTTON_B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                if (event.getState().isLow()) {

                    try {
                        displayColourSimplifiyer(0, 0, 255, "BR", 750);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (x != 2) {
                        boolean y = false;
                    }
                }

            }
        });
        swiftBot.BUTTON_B.removeAllListeners();
        swiftBot.BUTTON_Y.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                if (event.getState().isLow()) {

                    try {
                        displayColourSimplifiyer(255, 255, 0, "BL", 750);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (x != 3) {
                        boolean y = false;
                    }
                }

            }
        });
        swiftBot.BUTTON_Y.removeAllListeners();
        /////}
    }
*/

        private static void displayingColoursInArray ( int[] colourArray) throws IOException, InterruptedException {
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


        private static void addColoursToArray ( int[] colourArray) throws IOException, InterruptedException {
            System.out.println("addColoursToArray");
            for (int i = 0; i < colourArray.length; i++) {
                int randomNum = (int) (Math.random() * 4);
                colourArray[i] = randomNum;
                System.out.println(randomNum);

            }
            displayingColoursInArray(colourArray);
        }

        public static void displayColourSimplifiyer (int r, int g, int b, SwiftBotAPI.Underlight whichLight, int time) throws
        IOException, InterruptedException {

                swiftBot.setUnderlight(whichLight, r, g, b);
                Thread.sleep(time);
                swiftBot.disableUnderlight(whichLight, false);
                swiftBot.updateUnderlights();
            }
            }







