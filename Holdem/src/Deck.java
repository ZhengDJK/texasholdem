/*
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Random;

/**
 * A deck should be implemented to be an ArrayList
 * of Card objects.
 * 
 * @author Zack Berman
 * @version September 30, 2012
 */
public class Deck {
    private ArrayList<Card> deck;
    
    /**
     * Constructor of a Deck object
     * Constructs a Standard 52 card deck where each 
     * Card is a Card object
     */
    public Deck(){
        deck = new ArrayList<Card>();
        for (int i = 0; i < 52; i++){
            if (((i+1)%13) != 0)
                 if ((i+1)%13 == 1) deck.add(new Card(14, (i/13) + 1));
                 else deck.add(new Card(((i+1)%13), (i/13) + 1));
            else
                 deck.add(new Card (13, (i+1)/13));
        }
    }
    
    /**
     * Get method that returns the deck
     * @return ArrayList<Card> deck
     */
    public ArrayList<Card> getDeck(){return deck;}
    
    /**
     * Shuffle method for shoe
     */
    public void shuffleDeck(){
        ArrayList<Card> shuffled = new ArrayList<Card>();
        Random gen = new Random();
        int x = deck.size();
        for (int i = 0; i<x; i++)
            shuffled.add(deck.remove(gen.nextInt(deck.size())));
        for (int i = 0; i<x; i++)
            deck.add(shuffled.remove(0));
    }
    
    /**
     * Get method that returns the top card of the deck
     * @return Card object at index 0 of Deck
     */
    public Card draw(){return deck.remove(0);}
    
    /**
     * Removes a specific card from the deck
     * @param c Card to be removed
     */
    public void remove(Card c){
        boolean done = false;
        for (int i = 0; i < 52 && !done; i++){
            Card c2 = deck.get(i);
            if (c2.getRank().equals(c.getRank()) && 
                    c2.getSuit().equals(c.getSuit()))
                { deck.remove(i); done = true;}
        }
    }
    
    /**
     * Creates and returns a string representation of the
     * Deck
     * @return deck.toString()
     */
    public String toString(){return deck.toString();}
    
    /**
     * Tester for the Deck class
     * @param args 
     */
    public static void main(String[] args){
        Deck d = new Deck();
        System.out.println(d);
        d.shuffleDeck();
        System.out.println("Shuffled: " + d);
        for (int i = 0; i<10; i++){System.out.println(d.draw());}
        System.out.println("Without top 10 cards: " + d);
    }
}
