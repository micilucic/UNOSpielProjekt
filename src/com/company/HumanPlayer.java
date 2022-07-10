package com.company;

import java.io.IOException;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void playCards(DropPile drop, CardDeck deck) throws IOException {
        Player p = new HumanPlayer(null);
        System.out.println("Please write \"help\" if you want to read rules of the game or write \"play\" to start the game");
        Scanner scanner = new Scanner(System.in);

        if (scanner.nextLine().equals("help"))
            UnoApp.help();
        System.out.println();
        System.out.println("Hello from playCards, I am player: " + getName() + "and these are my cards: " + getHandCards().size());


        if (drop.getLatestCard().getZeichen() != null && drop.getLatestCard().getZeichen().equals("+2")) {
            System.out.println("the last card is +2 - you will get 2 cards and sit out for a bit.");
            System.out.println("+++++++++++++++++++++++++++++++++");
            if (deck.isEmpty()) {
                fillEmptyCardDeck(deck, drop);
            }
            takeCard(deck);
            takeCard(deck);
            System.out.println("These are your current cards" + getHandCards());
        }
        if (drop.getLatestCard().getZeichen() != null && drop.getLatestCard().getZeichen().equals("*~+4")) {
            String colorChange = scanner.nextLine();
            p.chooseColor();
            System.out.println("the last card is +4 - you will get 4 cards.");
            System.out.println("New color is: " + drop.getLatestCard().getColor());
            System.out.println("+++++++++++++++++++++++++++++++++");
            if (deck.isEmpty()) {
                fillEmptyCardDeck(deck, drop);
            }
            takeCard(deck);
            takeCard(deck);
            takeCard(deck);
            takeCard(deck);
            System.out.println("These are your current cards" + getHandCards());
            System.out.println("**************************************");
        }
        int num = 0;
        for (Card c : getHandCards()) {
            num++;
            System.out.println(num + " " + c.toString() + " ");
        }
        int points = getHandCardPoints();  // These are the points from our handcards
        System.out.println("These are my hand cards points: " + points);
        System.out.println("Please choose one card or write \"help\" if you want to read rules of the game. If you do not need help, please write no");
        String playerInput = scanner.nextLine();
        if (playerInput.equals("help")) {
            UnoApp.help();
            System.out.println();
            System.out.println("You have read the rules. Now let us play!");
        } else {
            if (deck.isEmpty()) {
                fillEmptyCardDeck(deck, drop);
            }
        }
        System.out.println("These are my cards: " + getHandCards().size());
        System.out.println("Please choose one card. If you cannot play any card, please type number 1");
        int cardIndex = scanner.nextInt() - 1;
        if (canThisCardBePlayed(drop.getLatestCard(), getHandCards().get(cardIndex)) == true && (getHandCards().get(cardIndex).getZeichen() != null) && (getHandCards().get(cardIndex).getZeichen().equals("~"))) {
            String colorChange = scanner.nextLine();
            chooseColor();
        } else if (canThisCardBePlayed(drop.getLatestCard(), getHandCards().get(cardIndex)) == false) {
            System.out.println("You could not play any card. Now you will get one from the pile. Please try again.");
            if (deck.isEmpty()) {
                fillEmptyCardDeck(deck, drop);
            }
            takeCard(deck);
            System.out.println("These are your current cards: " + getHandCards());
            System.out.println("Please play another card");
            cardIndex = scanner.nextInt() - 1;
        System.out.println("You chose the following card: " + getHandCards().get(cardIndex));
            System.out.println("This card cannot be played. Now you will get a card from pile. Please enter OK");
            playerInput = scanner.nextLine();
            if (playerInput.equals("ok") || playerInput.equals("OK")) {
                if (deck.isEmpty()) {
                    fillEmptyCardDeck(deck, drop);
                }
                takeCard(deck);
                System.out.println("These are your current cards" + getHandCards());
            } else {
                if (deck.isEmpty()) {
                    fillEmptyCardDeck(deck, drop);
                }
                takeCard(deck);
            }
        } else if (canThisCardBePlayed(drop.getLatestCard(), getHandCards().get(cardIndex)) == true) {
            drop.dropCard(getHandCards().get(cardIndex)); // Karte von Hand auf Stapel kopieren
            getHandCards().remove(cardIndex); // Karte aus Handkarten entfernen
            System.out.println("Next player´s turn");
        } else { //Karte kann nicht gespielt werden - muss gezogen werden

            if (p.getHandCards().size() == 2) {
                System.out.println("You have only two more cards left");
                String uno = scanner.next();
                if (uno.equals("uno")) {
                    p.playCards(drop, deck);
                } else {
                    System.out.println("You didnt say uno - you must take two more cards");
                    p.takeCard(deck);
                    p.takeCard(deck);
                    p.playCards(drop, deck);
                }
                try {
                    int numUno = Integer.parseInt(uno);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid input");
                }
            }
        }
    }


    public boolean handIsEmpty() {
        if (getHandCards().size() == 0) {
            return true;
        } else
            return false;
    }

    @Override
    public String chooseColor() {
        DropPile d = new DropPile();
        Player p = new HumanPlayer(null);
        Scanner inputColor = new Scanner(System.in);
        String colorInput = null;
        boolean pickedColor = false;
        String chosenColor = null;

        for (int i = 0; i < p.getHandCards().size(); i++)
        if (getHandCards().get(i).getZeichen().equals("~")) {
            System.out.println("Please choose a color!");
            if (colorInput != null && colorInput.equals("Yellow")) {
                System.out.println(p.getName() + " chose the following color: " + colorInput);
                pickedColor = true;
            } else if (colorInput != null && colorInput.equals("Green")) {
                System.out.println(p.getName() + " chose the following color: " + colorInput);
                pickedColor = true;
            } else if (colorInput != null && colorInput.equals("Blue")) {
                System.out.println(p.getName() + " chose the following color: " + colorInput);
                pickedColor = true;
            } else if (colorInput != null && colorInput.equals("Red")) {
                System.out.println(p.getName() + " chose the following color: " + colorInput);
                pickedColor = true;
            } else {
                System.out.println("this is a wrong entry! Try again.");
            }
            chosenColor = colorInput;
            d.getLatestCard().setColor(chosenColor);
            System.out.printf("The color of the latest card is: " + chosenColor);
        }
        return d.getLatestCard().getColor();
    }
}
