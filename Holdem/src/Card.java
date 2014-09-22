/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 * Card class for blackjack
 * A card is the fundamental unit of play, and is used 
 * directly and indirectly by every other class in the 
 * application. As such, it is arguably the most important 
 * piece of code in this project. A card has two 
 * fundamental fields (a position and a suit).
 * 
 * @author Zack Berman
 * @version September 30, 2012
 */
public class Card {
    private int position;
    private int suit;
    
    /**
     * Constructs a Card with position p (1-13) and 
     * suit s (1-4).
     * @param int p Position (2-14:A-K)
     * @param int s Suit (1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs) */
    public Card(int p, int s){
        position = p;
        suit = s;
    }
    
    public Card(Card r) {
    	position=r.position;
    	suit = r.suit;
	}

	/**
     * Returns a copy of the suit of the Card
     * returns "unknown" if error occurs
     * @return Suit of the card */
    public String getSuit(){
        if (suit == 1){return "Hearts";}
        else if (suit == 2){return "Diamonds";}
        else if (suit == 3){return "Spades";}
        else if (suit == 4){return "Clubs";}
        else{return "Unknown";}
    }
    
    public int getPosition(){return position;}
    
    /**
     * Returns a copy of the rank of the Card
     * Returns "unknown" if error occurs
     * @return Rank of the card */
    public String getRank(){
        if (position == 2){return "2";}
        else if (position == 3){return "3";}
        else if (position == 4){return "4";}
        else if (position == 5){return "5";}
        else if (position == 6){return "6";}
        else if (position == 7){return "7";}
        else if (position == 8){return "8";}
        else if (position == 9){return "9";}
        else if (position == 10){return "10";}
        else if (position == 11){return "Jack";}
        else if (position == 12){return "Queen";}
        else if (position == 13){return "King";}
        else if (position == 14 || position == 1){return "Ace";}
        else {return "Unknown";}
    }
    
    /**
     * Mutator method that sets an Ace's position to 1
     */
    public void setAceTo1(){position = 1;}
    
    /**
     * Mutator method that sets an Ace's position to 14
     */
    public void setAceTo14(){position = 14;}
    
    /**
     * Returns a String with the card's suit, rank and value
     * @return String with the card's suit, rank and value
     */
    public String toString(){
        return getRank() + " of " + getSuit();
    }
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input the suit of a card: " +
                         "\n(1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs): ");
        int a = sc.nextInt();
        System.out.print("Input the position of that card: " +
                         "\n(2-14:2-A): ");
        int b = sc.nextInt();
        Card card = new Card(b,a);

        System.out.println(card);
        
        System.out.print("\nWould you like to input another card? \n(Y for yes, N for no) ");
        sc.nextLine();
        String d = sc.nextLine();
        if (d.equalsIgnoreCase("y")) {
            main(args);
        }
        else {
            System.out.println("\nHave a nice day!");
        }
    }
}
