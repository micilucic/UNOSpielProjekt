package com.company;


public class BotPlayer extends Player {

    public BotPlayer(String name) {
        super(name);
    }

    @Override
    public void playCards(DropPile drop, CardDeck deck) {
        Player p = new BotPlayer(null);
        System.out.println("Hello from playCards, I am player :" + getName());
        System.out.println("These are my cards: " + getHandCards().size() + " " + getHandCards());
        int num = 0;
        int points = getHandCardPoints();  // These are the points from our handcards
        System.out.println("These are my hand cards points: " + points);
        int cardIndex = 0;
        boolean playOrDont = false;
        Card cardtoberemoved = null;
        for (int i = 0; i < getHandCards().size(); i++) {
            if (canThisCardBePlayed(drop.getLatestCard(), getHandCards().get(i))) {//wenn die Karte gespielt werden kann -> true
                drop.dropCard(getHandCards().get(i));// Karte von Hand auf Stapel kopieren
                cardtoberemoved = getHandCards().get(i);
                break;
            }
            if (cardtoberemoved != null) {
                getHandCards().remove(cardtoberemoved); // Karte aus Handkarten entfernen
                System.out.println("You chose the following card: " + cardtoberemoved);
                playOrDont = true;
                System.out.println(" ----------- ");
            } else {
                System.out.println("I do not have any valid card to play, so I will take one from the drop pile");
                takeCard(deck);
                break;
            }
        }
        System.out.println("These are my cards: " + getHandCards().size());
        System.out.println("**************************************");
        if (canThisCardBePlayed(drop.getLatestCard(), getHandCards().get(cardIndex)) == true && (getHandCards().get(cardIndex).getZeichen() != null) && (getHandCards().get(cardIndex).getZeichen().equals("~"))) {
            chooseColor();
            if (!playOrDont) {
                if (deck.isEmpty()) {           //wenn Karten stapel leer ist
                    fillEmptyCardDeck(deck, drop);
                }
                takeCard(deck);              // ich habe genug Karten auf meinen Stapel, nehme eine Karte
                System.out.println("You took one card" + getHandCards());
                //break;
                //Karte kann nicht gespielt werden - muss gezogen werden
                System.out.println("Next player´s turn");

                if (getHandCards().size() == 2) {
                    System.out.println("uno!");
                    playCards(drop, deck);
                }
                // Help dort wo der Player nach einer Information gefragt wird und die Frage wiederholen
            }
        }
        if (drop.getLatestCard().getZeichen() != null && drop.getLatestCard().getZeichen().equals("+2")){
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
            p.chooseColor();
            System.out.println("the last card is +4 - you will get 4 cards.");
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
        Player p = new BotPlayer(null);

        boolean pickedColor = false;
        String chosenColor = null;

        d.getLatestCard().setColor("Blue");
        System.out.printf("The color of the latest card is: " + chosenColor);
        return chosenColor;
    }
}