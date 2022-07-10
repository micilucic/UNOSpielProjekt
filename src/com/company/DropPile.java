package com.company;

import java.util.ArrayList;

public class DropPile {
    private ArrayList<Card> dropPile = new ArrayList<>();


    public DropPile() {
        this.dropPile = dropPile;
    }

    public ArrayList<Card> getDropPile() {
        return getDropPile();
    }



    // when one card is thrown it goes on the pile into new Arraylist
    public void dropCard(Card card) {
        dropPile.add(card);
        System.out.println("All cards in dropPile: " + dropPile);
    }

    // checking which card is the top card on the drop pile
    public Card getLatestCard(){
        Card a = dropPile.get(dropPile.size()-1);
        System.out.println("This is getLatestCard: " + a);
        return a;
    }



    // removing card from drop pile after it has been drawn
    public void removeCard(){
        dropPile.remove(dropPile.size() - 1);
    }

}
