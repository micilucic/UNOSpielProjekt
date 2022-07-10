package com.company;

public class Card {
    private String color;
    private int cardNr;
    private String zeichen;
    private int value;

    public Card(String color, int cardNr, int value) {
        this.color = color;
        this.cardNr = cardNr;
        this.value = value;

    }
    public Card (String color, String zeichen,int value) {
        this.color = color;
        this.zeichen = zeichen;
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCardNr() {
        return cardNr;
    }

    public void setCardNr(int cardNr) {
        this.cardNr = cardNr;
    }

    public String getZeichen() {
        return zeichen;
    }

    public void setZeichen(String zeichen) {
        this.zeichen = zeichen;
    }

    @Override
    public String toString() {
        if (zeichen == null) {
            return "Card{" +
                    "color='" + color + '\'' +
                    ", cardNr=" + cardNr +
                    '}';
        } else {
            return "Card{" +
                    "color='" + color + '\'' +
                    ", zeichen=" + zeichen +
                    '}';
        }
    }
}

