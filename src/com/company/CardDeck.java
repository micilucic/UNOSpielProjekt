package com.company;

import java.util.ArrayList;
import java.util.Collections;

    public class CardDeck extends ArrayList<Card> {

        private final ArrayList<Card> cards = new ArrayList<>();

        // creating card deck
        public CardDeck() {
            createCards();
        }

        public ArrayList<Card> getCards() {
            return cards;
        }

        // filling the card deck
        public void addIntoNewCardDeck(Card c){
            cards.add(c);
        }

        // creating cards
        public void createCards() {
            for (int i = 0; i < 10; i++) {
                for (int j = 1; j <= 2; j++) {
                    Card cRot = new Card("red", i, i);
                    cards.add(cRot);
                }
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 1; j <= 2; j++) {
                    Card cGelb = new Card("yellow", i, i);
                    cards.add(cGelb);
                }
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 1; j <= 2; j++) {
                    Card cGruen = new Card("green", i, i);
                    cards.add(cGruen);
                }
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 1; j <= 2; j++) {
                    Card cBlau = new Card("blue", i, i);
                    cards.add(cBlau);
                }
            }
            // creating action cards
            for (int i = 0; i < 2; i++) {
                Card actRot = new Card("red", "+2", 20);
                cards.add(actRot);
            }
            for (int i = 0; i < 2; i++) {
                Card actYellow = new Card("yellow", "+2", 20);
                cards.add(actYellow);
            }
            for (int i = 0; i < 2; i++) {
                Card actGruen = new Card("green", "+2", 20);
                cards.add(actGruen);
            }
            for (int i = 0; i < 2; i++) {
                Card actBlau = new Card("blue", "+2", 20);
                cards.add(actBlau);
            }
            // action card reverse
            for (int i = 0; i < 2; i++) {
                Card actRotReverse = new Card("red", "<->", 20);
                cards.add(actRotReverse);
            }
            for (int i = 0; i < 2; i++) {
                Card actGelbReverse = new Card("yellow", "<->", 20);
                cards.add(actGelbReverse);
            }
            for (int i = 0; i < 2; i++) {
                Card actGruenReverse = new Card("green", "<->", 20);
                cards.add(actGruenReverse);
            }
            for (int i = 0; i < 2; i++) {
                Card actBlauReverse = new Card("blue", "<->", 20);
                cards.add(actBlauReverse);
            }
            // action card skip
            for (int i = 0; i < 2; i++) {
                Card actRotBlock = new Card("red", "Ø", 20);
                cards.add(actRotBlock);
            }
            for (int i = 0; i < 2; i++) {
                Card actGelbBlock = new Card("yellow", "Ø", 20);
                cards.add(actGelbBlock);
            }
            for (int i = 0; i < 2; i++) {
                Card actGruenBlock = new Card("green", "Ø", 20);
                cards.add(actGruenBlock);
            }
            for (int i = 0; i < 2; i++) {
                Card actBLauBlock = new Card("blue", "Ø", 20);
                cards.add(actBLauBlock);
            }
            // Action card change color
            for (int i = 0; i < 4; i++) {
                Card actColor = new Card("black", "~", 50);
                cards.add(actColor);
            }
            // Action card change color and take +4
            for (int i = 0; i < 4; i++) {
                Card actColorPlus = new Card("black", "~+4", 50);
                cards.add(actColorPlus);
            }
            Collections.shuffle(cards);
        }

        // taking card from the drop pile
        public Card drawCard(){
            return cards.remove(cards.size()-1);
        }

        // checking if card deck is empty
        public boolean isEmpty(){
            if(cards.size() == 0){
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return cards.toString();
        }
    }

