/*
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A Hand is composed of Card objects
 * The hand will be populated with Card objects as the 
 * dealer adds Cards to the table.  Once the 6th and 7th 
 * Cards are added to the table, that Hand will only 
 * contain the 5 best cards.
 * @author Zack Berman
 */
public class Hand {
    private static int HIGH_CARD = 0;
    private static int ONE_PAIR = 1;
    private static int TWO_PAIR = 2;
    private static int THREE_OF_A_KIND = 3;
    private static int STRAIGHT = 4;
    private static int FLUSH = 5;
    private static int FULL_HOUSE = 6;
    private static int FOUR_OF_A_KIND = 7;
    private static int STRAIGHT_FLUSH = 8;
    private static int ROYAL_FLUSH = 9;
    
    private ArrayList<Card> cards;
    private ArrayList<Card> highCards;
    
    /**
     * Constructs a blank hand with a value set to 
     * the HIGH_CARD value of 0.  Also instantiates 
     * an empty ArrayList of Card objects and an empty ArrayList 
     * of Card objects for the highCards list
     */
    public Hand(){cards = new ArrayList<>(); highCards = new ArrayList<>();}
    
    /**
     * Method that adds a Card object to hand
     * @param c Card to be added to the hand
     */
    public void addCard(Card c){cards.add(c);}
    
    /**
     * Method that determines the best possible combination of 5 
     * cards for a given set of cards. (if cards.size() <= 5, this 
     * method simply returns cards
     * @param cards Set of cards in a hand
     * @return bestHand Best possible combination of cards
     */
    public ArrayList<Card> chooseBestHand(ArrayList<Card> cards) {
        if (getNumCards() <= 5){ 
            sortHand(cards);
            setHighCards(cards);
            return cards;    
        }
        else if (getNumCards() == 6){
            ArrayList<ArrayList<Card>> cards2 = new ArrayList<>();
            for (int j = 0; j < cards.size(); j++){
                cards = rotate(cards);
                cards2.add(j, new ArrayList<Card>());
                for (int p = 0; p < cards.size() - 1; p++){
                    cards2.get(j).add(cards.get(p));
                }
                sortHand(cards2.get(j));
            }
            
            ArrayList<Card> bestHand = new ArrayList<>();
            int max = 0;
            for (ArrayList<Card> h : cards2){
                if (getValue(h) > max)
                    {max = getValue(h);}
            }
            ArrayList<ArrayList<Card>> bestValues = new ArrayList<>();
            for (ArrayList<Card> h : cards2){
                if (getValue(h) == max)
                    {bestValues.add(h);}
            }
            int max2 = 0;
            for (ArrayList<Card> h : bestValues){
                int totalValue = 0;
                for (Card c : h){ totalValue+= c.getPosition();}
                if (totalValue > max2){ max2 = getValue(h); bestHand = h;}
            }
            setHighCards(bestHand);
            return bestHand;
        }
        else{
            ArrayList<ArrayList<Card>> cards2 = new ArrayList<>();
            ArrayList<ArrayList<Card>> cards3 = new ArrayList<>();
            for (int j = 0; j < cards.size(); j++){
                cards = rotate(cards);
                cards2.add(j, new ArrayList<Card>());
                for (int p = 0; p < cards.size() - 1; p++){
                    cards2.get(j).add(cards.get(p));
                }
            }
            for (int k = 0; k < cards2.size(); k++){
                ArrayList<Card> s = cards2.get(k);
                for (int l = 0; l < cards.size() - 1; l++){
                    s = rotate(s);
                    ArrayList<Card> sH = new ArrayList<>();
                    for (int i = 0; i < s.size()-1; i++){
                        sH.add(s.get(i));
                    }
                    SelectionSorter ss = new SelectionSorter(sH);
                    ss.sort();  
                    if (!inSaved(sH,cards3)) {
                        cards3.add(sH);
                    }
                }
            }
            ArrayList<Card> bestHand = new ArrayList<>();
            int max = 0;
            for (ArrayList<Card> h : cards3){
                if (getValue(h) > max)
                    {max = getValue(h);}
            }
            ArrayList<ArrayList<Card>> bestValues = new ArrayList<>();
            for (ArrayList<Card> h : cards3){
                if (getValue(h) == max)
                    {bestValues.add(h);}
            }
            int max2 = 0;
            for (ArrayList<Card> h : bestValues){
                int totalValue = 0;
                for (Card c : h) {totalValue+= c.getPosition();}
                if (totalValue > max2) {
                    max2 = totalValue;
                    bestHand = h;
                }
            }
            setHighCards(bestHand);
            return bestHand;
        }
    }
    
    /**
     * Returns a copy of the ArrayList of cards
     * @return cards
     */
    public ArrayList<Card> getCards(){return cards;}
    
    /**
     * Chooses the best possible hand out of the cards in 
     * ArrayList<Card> cards, and returns a copy of that
     * ArrayList<Card>
     * @return chooseBestHand(cards)
     */
    public ArrayList<Card> getHand(){return chooseBestHand(cards);}
    
    /**
     * Returns a copy of the highCards ArrayList
     * @return highCards
     */
    public ArrayList<Card> getHighCards(){return highCards;}
    
    /**
     * Computes the size of the hand and returns an 
     * integer value for its length
     * @return number of Card objects in hand
     */
    public int getNumCards(){return cards.size();}
    
    /**
     * Determines the type of hand a set of 5 cards 
     * is in a game of Texas Hold 'Em
     * @param cards 5 card hand
     * @return int value of that hand
     */
    public int getValue(ArrayList<Card> cards){
        int value;
        boolean straight = true;
        boolean flush = true;
        
        int count1 = 0;
        int count2 = 0;
        boolean stop = false;
        for (int i = 0; i < 4; i++){
            if(cards.get(i).getPosition() == cards.get(i+1).getPosition()){
                if (!stop){count1++;}
                else {count2++;}
            }
            else{
                if (count1 >= 1){ stop = true;}
            }
            
            if(!cards.get(i).getSuit().equals(cards.get(i+1).getSuit()))
                {flush = false;}
            if(cards.get(i).getPosition() != cards.get(i+1).getPosition() -1)
                {straight = false;}
        }
        if (count1 == 1 && count2 == 0) {value = ONE_PAIR;}
        else if (count1 == 1 && count2 == 1) {value = TWO_PAIR;}
        else if (count1 == 2 && count2 == 0) {value = THREE_OF_A_KIND;}
        else if (count1 == 2 && count2 == 1) {value = FULL_HOUSE;}
        else if (count1 == 3) {value = FOUR_OF_A_KIND;}
        else if (flush && straight) {
            value = STRAIGHT_FLUSH;
            if (cards.get(0).getPosition() == 10)
                {value = ROYAL_FLUSH;}
        }
        else if (flush) {value = FLUSH;}
        else if (straight) {value = STRAIGHT;}
        else {value = HIGH_CARD;}
        
        return value;
    }
    
    /**
     * Determines whether s has already been added to saved
     * @param s ArrayList<Card>
     * @param saved ArrayList<ArrayList<Card>>
     * @return true if s is already in saved
     */
    public static boolean inSaved(ArrayList<Card> s, ArrayList<ArrayList<Card>> saved){
        boolean a = false;
        for (ArrayList<Card> str : saved){
            if (s.equals(str))
                {return true;}
        }
        return a;
    }
    
    /**
     * Creates and returns an ArrayList of Integers that contains the index (or 
     * indices) of the best hand(s) in the players ArrayList
     * @param players Still Playing
     * @param table Cards on table
     * @return index/indices of best hand(s)
     */
    public ArrayList<Integer> indexOfBestHand(ArrayList<Player> players, ArrayList<Card> table){
        ArrayList<ArrayList<Card>> bestHands = new ArrayList<>();
        for (Player p : players){
            bestHands.add(p.getHand(table).getHand());
        }
        int best = 0;
        for (int i = 0; i < bestHands.size(); i++){
            int v = getValue(bestHands.get(i));
            if (v > best) best = v;
        }
        int best2 = 0;
        for (ArrayList<Card> h : bestHands){
            if (getValue(h) != best);
            else{
                int totalValue = 0;
                for (Card c : h) {totalValue+= c.getPosition();}
                if (totalValue > best2) {
                    best2 = totalValue;
                }
            }
        }
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < bestHands.size(); i++){
            if (getValue(bestHands.get(i)) != best);
            else{
                int totalValue = 0;
                for (Card c : bestHands.get(i)) {totalValue+= c.getPosition();}
                if (totalValue == best2){index.add(i);}
            }
        }
        return index;
    }
    
    /**
     * Determines whether or not a set of 5 cards contains 
     * an Ace through 5 straight
     * @param cards ArrayList<Card> of 5 cards
     * @return true if is a Straight from Ace to 5
     */
    public boolean isAceTo5Straight(ArrayList<Card> cards){
        return cards.get(0).getPosition() == 2 &&
            cards.get(1).getPosition() == 3 &&
            cards.get(2).getPosition() == 4 &&
            cards.get(3).getPosition() == 5 &&
            cards.get(4).getPosition() == 14;
    }
    
    /**
     * Removes a specific card from the Hand
     * @param c Card to be removed
     */
    public void remove(Card c){
        for (int i = 0; i < cards.size(); i++){
            if (c.getSuit().equals(cards.get(i).getSuit())
                    && c.getRank().equals(cards.get(i).getRank()))
            { cards.remove(i); return;}
        }
    }
    
    /**
     * Rotates an ArrayList<Card> by one spot
     * @param orig ArrayList<Card> to be rotated
     * @return orig, rotated over by one slot
     */
    public static ArrayList<Card> rotate(ArrayList<Card> orig){
        Card temp = orig.remove(0);
        orig.add(orig.size(), temp);
        return orig;
    }
    
    /**
     * Clears the highCards list and sets it to be a list of the five cards 
     * in h in ascending order (card 1 is the lowest, etc)
     * @param h ArrayList of Cards with 5 cards
     */
    public void setHighCards(ArrayList<Card> h){
        for (int i = 0; i < highCards.size(); i++){
            highCards.remove(0);
        }
        for (int i = 0; i < h.size(); i++){
            highCards.add(h.get(i));
        }
    } 
    
    /**
     * Mutator method that sorts a hand's Cards from
     * lowest value to highest value
     */
    public void sortHand(ArrayList<Card> cards){
        
        SelectionSorter ss = new SelectionSorter(cards);
        ss.sort();
        if (isAceTo5Straight(cards)){
                cards.get(4).setAceTo1();
                sortHand(cards);
        }
    }
    
    /**
     * Creates and returns a string representation of the 
     * best possible hand.
     * @return String representation of the Hand
     */
    @Override
    public String toString(){
        ArrayList<Card> bestHand = getHand();
        
        int a = bestHand.size()-1;
        String s = "";
        for (int i = 0; i < a; i++){
            s += bestHand.get(i) + ", ";
        }
        s+= bestHand.get(a) + "\n";
        int x = getValue(bestHand);
        
        if (x == 0){s += "HIGH CARD (" + bestHand.get(4).getRank() + ")";}
        else if (x == 1) {s += "ONE PAIR";}
        else if (x == 2) {s += "TWO PAIR";}
        else if (x == 3) {s += "THREE OF A KIND (" + bestHand.get(2).getRank() + "s)";}
        else if (x == 4) {s += "STRAIGHT (" + bestHand.get(0).getRank() + " to "
                + bestHand.get(4).getRank() + ")";}
        else if (x == 5) {s += "FLUSH (" + bestHand.get(0).getSuit() + ")";}
        else if (x == 6) {s += "FULL HOUSE";}
        else if (x == 7) {s += "FOUR OF A KIND (" + bestHand.get(2).getRank() + "s)";}
        else if (x == 8) {s += "STRAIGHT FLUSH (" + bestHand.get(0).getRank() + " to "
                + bestHand.get(4).getRank() + ", " + bestHand.get(0).getSuit() + ")";}
        else if (x == 9) {s += "ROYAL FLUSH";}
        return s;
    }
    
    /**
     * Tester for the Hand class
     * @param args 
     */
    public static void main(String[] args){
        //Hand h = new Hand();
        /*
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 7; i++){
            System.out.print("Input the position of that card: " +
                         "\n(2-14:2-A): ");
            int b = sc.nextInt();
            System.out.print("Input the suit of a card: " +
                         "\n(1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs): ");
            int a = sc.nextInt();
            
            h.addCard(new Card(b,a));
        }
        */
        for (int i = 0; i < 100; i++){
            Deck d = new Deck();
            d.shuffleDeck();
            for (int j = 0; j < 7; j++){
             Hand h = new Hand();
                for (int k = 0; k < 6; k++){
                 h.addCard(d.draw());
              }
                System.out.println(h.getCards());
             System.out.println(h.toString());
            }
        }
    }
}