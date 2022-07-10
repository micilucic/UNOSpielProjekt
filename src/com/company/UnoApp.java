package com.company;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.DemoApp.*;

public class UnoApp {
    public static Player[] allPlayers = new Player[4];
    private int session = 0;
    private CardDeck deck = new CardDeck();                  // first our card deck
    public static ArrayList<Player> players = new ArrayList<>();    // player
    private DropPile drop = new DropPile();// we need drop pile for the first and other cards that are played
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private int currentPlayerIndex = 0;
    int direction = 0;
    private String cardInput;
    private boolean clockwise = true; // merkt sich in welche Richtung das Spiel läuft
    public static int round = 1;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public UnoApp(Scanner input, PrintStream output) {       //constructor is required
        this.input = input;
        this.output = output;
    }

    public static int getRound() {
        return round;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    //adding player to play the game
    public void addPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter number of players: ");
        int num = scanner.nextInt();
        while ((num < 1) || (num > 4)) {
            System.out.println("Maximum 4 players are allowed. Please choose from 1 to 4.");
            num = scanner.nextInt();

        }
        scanner.nextLine();
        System.out.println("You entered: " + num);
        for (int i = 0; i < num; i++) { //4 players are maximum, that is why i<4
            System.out.println("Please enter your name");
            String name = scanner.nextLine();
            Player player1 = new HumanPlayer(name);
            System.out.println("Hello " + name);
            players.add(player1);
        }

        System.out.println("Anzahl der menschlichen Spieler: " + players.size());
        System.out.println("4 - players.size() " + (4 - players.size()));
        int botSize = 4 - players.size();
        for (int i = 0; i < botSize; i++) {
            //  System.out.println("hallo " + i);
            String name = "Bot " + (i + 1);
            Player player1 = new BotPlayer(name);
            players.add(player1);
            System.out.println(name + " will be joining you as well");
        }
        System.out.println(players);
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 7; j++) {
                players.get(i).addCards(deck.drawCard());
            }
            System.out.println(players.get(i).getName() + " " + players.get(i).getHandCards());
        }
    }

    public void cicleTroughPlayers() throws IOException {
        if (clockwise) {
            currentPlayerIndex++;
            if (currentPlayerIndex == 4) {
                currentPlayerIndex = 0;
            }
        } else if (!clockwise) {
            currentPlayerIndex--;
            if (currentPlayerIndex < 0) {
                currentPlayerIndex = 3;
            }
        }
        System.out.println("cicleTroughPlayers, index= " + currentPlayerIndex);
        //todo: direction abrafeg, überlauf
    }

    public static void help() throws IOException {
        // System.out.println("If you want to read the rules of the game, please enter \"help\"");
        //  Scanner scannerHelp = new Scanner(System.in);
        //  String help = scannerHelp.nextLine();
        File rulesOfTheGame = new File("C:\\Users\\s51638\\IdeaProjects\\ProjektI\\src\\UnoSpielregeln.txt");
        FileReader fileReader = new FileReader(rulesOfTheGame);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        //  if (help.equals("help")) {
        while (line != null) {
            System.out.println(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        fileReader.close();
    }

    public void UnoButton() throws IOException {
        Scanner scanner = new Scanner(System.in);
        if (players.get(currentPlayerIndex).getHandCards().size() == 2) {
            System.out.println("You have only two more cards left");
            String uno = scanner.next();
            if (uno.equals("uno")) {
                players.get(currentPlayerIndex).playCards(drop, deck);
            } else {
                System.out.println("You didnt say uno - you must take two more cards");
                players.get(currentPlayerIndex).takeCard(deck);
                players.get(currentPlayerIndex).takeCard(deck);
                players.get(currentPlayerIndex).playCards(drop, deck);
            }
            try {
                int num = Integer.parseInt(uno);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input");
            }
        }
    }


    public void firstCardOpen() {                     //erste Karte wenn der Spiel start
        Card c = new Card(null, null, 0);
        c = deck.drawCard();
        while (c.getZeichen() != null && !c.getZeichen().equals("<->")) {
            System.out.println("Your card is: " + c + " First card cannot be a symbol, unless it is reverse card, return card");
            c = deck.drawCard();
            deck.remove(c);
        }
        if (c.getZeichen() != null && c.getZeichen().equals("<->") || (c.getZeichen() != null && (c.getZeichen().equals("<->") && c.getColor().equals(drop.getLatestCard().getColor())))) {
            if (clockwise == true) {
                clockwise = false;
            } else {
                clockwise = true;
            }
        }
        if (c.getZeichen() != null && c.getZeichen().equals("~")) {
            players.get(currentPlayerIndex).chooseColor();
        }
        drop.dropCard(c);
    }

    public void startNewRound() {
        round++;
        System.out.println("Current player has no cards left. This round is over. Let´s start new round!");
        System.out.println("Round: " + round);

    }

    public void thisRoundIsOver() {
        collectPointsFromThisRound();

    }

    public static int collectPointsFromThisRound() {
        int sum = 0;
        for (int i = 0; i < players.size(); i++) { // um die Punkte zusammenzuzählen
            sum = sum + players.get(i).getHandCardPoints();
        }
        System.out.println("You won this round, and these are your points: " + sum);
        return sum;

    }

    public void Run() throws IOException, SQLException {
        initialize(); // aks players for name, write names for human players, create bots, create handcards
        firstCardOpen();

        while (!exit) {
            System.out.println("This is UnaApp.currentPlayerIndex: " + currentPlayerIndex + "executing player.playCards");
            players.get(currentPlayerIndex).playCards(drop, deck);
            System.out.println("Checking if current player has cards left");
            if (players.get(currentPlayerIndex).getHandCards().size() == 0) {
                thisRoundIsOver();
                System.out.println("Start new round");
                startNewRound();

            }

            if (drop.getLatestCard().getZeichen() != null && drop.getLatestCard().getZeichen().equals("Ø")) {
                if (clockwise) {
                    if (currentPlayerIndex == 0) {
                        currentPlayerIndex = 3;
                    } else if (currentPlayerIndex == 1) {
                        currentPlayerIndex = 0;
                    } else if (currentPlayerIndex == 2) {
                        currentPlayerIndex = 1;
                    } else {
                        currentPlayerIndex = 2;
                    }
                } else {
                    if (currentPlayerIndex == 0) {
                        currentPlayerIndex = 1;
                    } else if (currentPlayerIndex == 1) {
                        currentPlayerIndex = 2;
                    } else if (currentPlayerIndex == 2) {
                        currentPlayerIndex = 3;
                    } else {
                        currentPlayerIndex = 0;
                    }
                }
                System.out.println("This is skip player function! Next player is: " + currentPlayerIndex);
            }
            if (drop.getLatestCard().getZeichen() != null && drop.getLatestCard().getZeichen().equals("<->")) {
                if (clockwise) {
                    clockwise = false;
                    System.out.println("Direction is changed to counter clockwise.");
                } else if (!clockwise) {
                    clockwise = true;
                    System.out.println("Direction is changed to clockwise.");
                }
            } else {
                System.out.println("clockwise is: " + clockwise);
                System.out.println("next player: " + currentPlayerIndex);
                //     readUserInput();
                //     updateState();
                //     printState(); //Nur die Ausgabe
            }
            UnoButton();
            cicleTroughPlayers();
            if (drop.getLatestCard().getZeichen() != null && drop.getLatestCard().getZeichen().equals("+2")) {
                getPlayers().get(currentPlayerIndex).takeCard(deck);
                getPlayers().get(currentPlayerIndex).takeCard(deck);
                if (clockwise) {
                    if (currentPlayerIndex == 0) {
                        currentPlayerIndex = 1;
                    } else if (currentPlayerIndex == 1) {
                        currentPlayerIndex = 2;
                    } else if (currentPlayerIndex == 2) {
                        currentPlayerIndex = 3;
                    } else {
                        currentPlayerIndex = 0;
                    }
                } else {
                    if (currentPlayerIndex == 0) {
                        currentPlayerIndex = 3;
                    } else if (currentPlayerIndex == 1) {
                        currentPlayerIndex = 0;
                    } else if (currentPlayerIndex == 2) {
                        currentPlayerIndex = 1;
                    } else {
                        currentPlayerIndex = 2;
                    }

                }
            }

        }
    }

    public void initialize() throws SQLException {
        //TODO: Initialisierungen hier durchführen
        //Speieler und Karten anlegen !!! - man initialisiert Sachen, die nur einmal intialisert werden müssen
        deck.createCards();
        addPlayers();
        currentPlayerIndex = 0;
    }
}

