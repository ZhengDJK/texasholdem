/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Component;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * tasks: 
 * side pots**
 * @author Zack Berman
 */
public class Dealer {
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
    
    public ArrayList<Card> table;
    int pot = 0;
    public int currentBet = 0;
    private Deck deck;
    ArrayList<Player> players;
    private int smallBlind;
    private int bigBlind;
    private int index = 0;
    private boolean gameOver = false;
    private Scanner sc;
    private int specialNumber;
    public Player p;
    
    /**
     * Creates a dealer object so that it can use a Scanner.
     * Also creates a new Deck object and a blank ArrayList of Cards to be
     * filled with the flop, turn and river cards. Also sets the small and big 
     * blinds and gives the Dealer access to an ArrayList of players
     * @param sB Small Blind value
     * @param p ArrayList of players in the game
     */
    public Dealer(int sB, ArrayList<Player> p){
        sc = new Scanner(System.in);
        table = new ArrayList<>();
        deck = new Deck();
        players = p;
        smallBlind = sB;
        bigBlind = sB*2;
    }
    
    public void addCardToTable(Card c){table.add(c);}
    
    /**
     * Adds an int value to the pot
     * @param a Value added to pot
     */
    public void addToPot(int a){pot += a;}
    
    /**
     * Given an ArrayList of players still in the game, this method returns 
     * an ArrayList of players with the best value hand (if only one, that means 
     * that player has one. if more that one, that means there is either a tie or 
     * a high card will break the tie)
     * @param players ArrayList of players still playing
     * @param table ArrayList of cards on the table
     * @return ArrayList of Player(s) with best hand
     */
    public ArrayList<Player> bestHands(ArrayList<Player> players, ArrayList<Card> table){
        int best = getValue(players.get(players.size()-1).getHand(table).getHand());
        ArrayList<Player> bestPlayers = new ArrayList<>();
        for (Player p : players){
            int x = getValue(p.getHand(table).getHand());
            if (x >= best){ best = x; bestPlayers.add(p);}
        }
        return bestPlayers;
    }
    
    /**
     * Breaks the tie of the ArrayList of players with the best hand. Given an 
     * ArrayList of Player(s) with equal value hand, this method returns an 
     * ArrayList of Integers with the index/indices of the player(s) with the 
     * actual winning hand
     * @param bestHands ArrayList of tied players
     * @return ArrayList of index/indices of winning hand(s)
     */
    public ArrayList<Integer> bestOfBest(ArrayList<Player> bestHands){
        ArrayList<Integer> bestIndices = new ArrayList<>();
        if (bestHands.size() == 1){ bestIndices.add(0); return bestIndices;}
        else if (bestHands.size() == 2){
            Hand h1 = bestHands.get(0).getHand(table);
            Hand h2 = bestHands.get(1).getHand(table);
            int v1 = h1.getValue(h1.chooseBestHand(h1.getCards()));
            int v2 = h2.getValue(h2.chooseBestHand(h2.getCards()));
            ArrayList<Card> hc1 = h1.getHighCards();
            ArrayList<Card> hc2 = h2.getHighCards();
            if (v1 == 1 || v1 == 3){
                int a = 0, b = 0;
                for (int i = 0; i < 4; i++){
                    if (hc1.get(i).getPosition() == hc1.get(i+1).getPosition())
                        a = hc1.get(i).getPosition();
                    if (hc2.get(i).getPosition() == hc2.get(i+1).getPosition())
                        b = hc2.get(i).getPosition();
                }
                if (a == b){
                    for(int i = 4; i >= 0; i--){
                        int p1 = hc1.get(i).getPosition();
                        int p2 = hc2.get(i).getPosition();
                        if (p2 > p1) {bestIndices.add(1); return bestIndices;}
                        else if (p1 > p2) {bestIndices.add(0); return bestIndices;}
                    }
                    bestIndices.add(0); bestIndices.add(1); return bestIndices;
            } else if (b > a) {bestIndices.add(1); return bestIndices;}
            else {bestIndices.add(0); return bestIndices;}
        }else if (v1 == 2){
            int a1 = 0, a2 = 0, b1 = 0, b2 = 0;
            for (int i = 0; i < 4; i++){
                if (hc1.get(i).getPosition() == hc1.get(i+1).getPosition()) {
                    if (a1 == 0) a1 = hc1.get(i).getPosition();
                    else a2 = hc1.get(i).getPosition();
                }
                if (hc2.get(i).getPosition() == hc2.get(i+1).getPosition()) {
                    if (b1 == 0) b1 = hc2.get(i).getPosition();
                    else b2 = hc2.get(i).getPosition();
                }
            }if (b2 > a2) {bestIndices.add(1); return bestIndices;}
            else if (a2 > b2) {bestIndices.add(0); return bestIndices;}
            else{
                if (b1 > a1) {bestIndices.add(1); return bestIndices;}
                else if (a1 > b1) {bestIndices.add(0); return bestIndices;}
                else{
                    for(int i = 4; i >= 0; i--){
                        int p1 = hc1.get(i).getPosition();
                        int p2 = hc2.get(i).getPosition();
                        if (p2 > p1) {bestIndices.add(1); return bestIndices;}
                        else if (p1 > p2) {bestIndices.add(0); return bestIndices;}
                    }
                    bestIndices.add(0); bestIndices.add(1); return bestIndices; 
                }
            }
        }   else if (v1 == 6 || v1 == 7){
                int a = hc1.get(2).getPosition();
                int b = hc2.get(2).getPosition();
                if (b > a) {bestIndices.add(1); return bestIndices;}
                else {bestIndices.add(0); return bestIndices;}
            }else{
                for(int i = 4; i >= 0; i--){
                        int p1 = hc1.get(i).getPosition();
                        int p2 = hc2.get(i).getPosition();
                        if (p2 > p1) {bestIndices.add(1); return bestIndices;}
                        else if (p1 > p2) {bestIndices.add(0); return bestIndices;}
                    }
                    bestIndices.add(0); bestIndices.add(1); return bestIndices;
            }
        }
        else{
            for (int g = 0; g < bestHands.size(); g++){
                bestIndices.add(g);
            }
            for (int i = 4; i >= 0; i--){
                Hand h1 = bestHands.get(bestIndices.get(bestIndices.size()-1)).getHand(table);
                h1.chooseBestHand(h1.getCards());
                ArrayList<Card> hc1 = h1.getHighCards();
                int best = hc1.get(i).getPosition();
                for (Integer a : bestIndices){
                    Hand hTemp = bestHands.get(a).getHand(table);
                    hTemp.chooseBestHand(hTemp.getCards());
                    ArrayList<Card> hcTemp = hTemp.getHighCards();
                    int temp = hcTemp.get(i).getPosition();
                    if (temp >= best) best = temp;
                }
                for (int j = 0; j < bestIndices.size(); j++){
                    Hand hTemp2 = bestHands.get(j).getHand(table);
                    hTemp2.chooseBestHand(hTemp2.getCards());
                    ArrayList<Card> hcTemp2 = hTemp2.getHighCards();
                    int temp2 = hcTemp2.get(i).getPosition();
                    if (temp2 < best){bestIndices.remove(j); j--;}
                }
                if (bestIndices.size() == 1) return bestIndices;
            }
            return bestIndices;
        }
    }
    
    /**
     * Determines whether or not the betting in a round is complete. 
     * @return True of betting has completed
     */
   public boolean bettingIsDone(){
        ArrayList<Player> a = stillPlaying();
        if (a.size() == 1) return true;
        else{
            boolean b = true;
            for (int i = 0; i < a.size()-1 && b; i++){
                if (a.get(i).getBet() != a.get(i+1).getBet())
                    b = false;
            }
            return b;
        }
    }
   
   /**
    * After a round, this method resets the deck and table.
    * It also determines if someone has won or if any player has gone 
    * bankrupt and is now out of the game.  It also rotates the dealerChip 
    * among the players and then begins the round left of the new dealerChip location
    */
   public void cleanUp(){
    			Component [] search =  TexasHoldEm.bam.pane1.getComponents();
    			//System.out.println("Stuff on pane 1");
    			for(int i=0; i<search.length; i++){
    				//System.out.println(search[i].toString());
    				String s =search[i].toString();
    				if(s.contains("850,300")||s.contains("750,300")||s.contains("650,300")||s.contains("550,300")||s.contains("950,300"))
    					TexasHoldEm.bam.pane1.remove(search[i]);
    			}
    			
    			
    		
    		TexasHoldEm.bam.pane1.repaint();
    	
        table = new ArrayList<>();
        deck = new Deck();
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getBank() <= 0){ players.remove(i); i--; index--;}
            else players.get(i).reset();
        }
        if (players.size() == 1){System.out.println(players.get(0).toString() + " Wins!");}
        else{
            index++;
            for (int i = 0; i < index % players.size(); i++){rotate();}
        }
        startLeftOfDealer();
        players.get(0).setDealer(true);
        players.get(players.size()-1).setDealer(false);
    }
   
   /**
    * Shuffles the deck and gives each player two pocket cards
    */
   public void deal(){
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++){
            for (Player p : players){
               // p.addCard(deck.draw());
                Card c = new Card(deck.draw());
                p.addCard(c);
                if(p.getName().equals(TexasHoldEm.bam.getName())){
                	TexasHoldEm.bam.addToTable(c, (p.getX()+150+60*(1+i)), (p.getY()+60*(i)), 2);
                }
                else{
                	TexasHoldEm.bam.addToTableBack("back-blue-75-3", (p.getX()+150+60*(1+i)), (p.getY()+60*(i)));
                }
            }
        }
    }
   
   /**
    * Determines the winner of a round.  If only one player is left, he/she wins.
    * Otherwise, this method determines which player has the best hand.  In the event 
    * of a tie, this method splits the pot. Otherwise the winner receives the pot
    */
   public void determineWinner(){
	      ArrayList<Player> a = stillPlaying();
/*       System.out.println(a.get(0).toString());
       System.out.println(a.get(0).getPocket().get(0).toString());
       System.out.println(a.get(0).getPocket().get(1).toString());
       System.out.println(a.get(1).toString());
       System.out.println(a.get(1).getPocket().get(0).toString());
       System.out.println(a.get(1).getPocket().get(1).toString());*/
	   //System.out.println("there are "+table.size()+ " cards on the table");
  
        if (a.size() == 1){ 
        	System.out.println("Hey the size is 1");
            System.out.println(a.get(0).toString() + " wins!");
            TexasHoldEm.bam.addToTable(a.get(0).toString()+" wins!", 700,600,3);
            a.get(0).win(pot);
            a.get(0).upDateCash();
        }
        else{
        	System.out.println("size isn't one");
            for (Player p : a){System.out.println(p.toString() + " " + p.getHand(table).toString());}
            ArrayList<Player> winners = bestHands(players, table);
            ArrayList<Integer> indices = bestOfBest(winners);
            
            int winnings = pot / indices.size();
            for (Integer i : indices){
                System.out.println(winners.get(i).toString() + " wins!");
                TexasHoldEm.bam.addToTable(winners.get(i).toString()+" wins!", 700,600,3);
                winners.get(i).win(winnings);
                winners.get(i).upDateCash();
            }
            
        }

        standings();
        pot = 0;
    }
   
   /**
    * Burns a card and adds three cards to the table for the flop
    */
   public void flop(){
        deck.draw();
        for (int i = 0; i < 3; i++){
        	Card c = new Card(deck.draw());
        	TexasHoldEm.bam.addToTable(c, (550+(i*100)), 300,2);
        	TexasHoldEm.bam.pane1.repaint();
        	TexasHoldEm.bam.pane1.validate();
        	TexasHoldEm.bam.pane1.setVisible(true);
        	//System.out.println("stuff tossed on table");
            table.add(i,c);
        }
    }
    
   /**
    * Burns a card and adds the fourth card to the table
    */
    public void turn(){
    	deck.draw();
        Card x = new Card(deck.draw());
        TexasHoldEm.bam.addToTable(x, 850,300,2);
    	TexasHoldEm.bam.pane1.repaint();
    	TexasHoldEm.bam.pane1.validate();
    	TexasHoldEm.bam.pane1.setVisible(true);
        table.add(3,x);
    }
    
    /**
     * Burns a card and adds the fifth and final card to the table
     */
    public void river(){
    	deck.draw();
        Card y = new Card(deck.draw());
        TexasHoldEm.bam.addToTable(y, 950,300,2);
    	TexasHoldEm.bam.pane1.repaint();
    	TexasHoldEm.bam.pane1.validate();
    	TexasHoldEm.bam.pane1.setVisible(true);
        table.add(4,y);
        //specialNumber ++;
        
        TexasHoldEm.bam.finishUp();
    }
    
    /**
     * Returns an integer value for the big blind
     * @return BigBlind
     */
    public int getBigBlind(){return bigBlind;}
    /**
     * Returns an integer value for the small blind
     * @return SmallBlind
     */
    public int getSmallBlind(){return smallBlind;}
    
    /**
     * Returns an integer value for the money in the pot
     * @return pot
     */
    public int getPot(){return pot;}
    
    /**
     * Returns an ArrayList of Cards containing the cards on the table
     * It is either empty, or contains 3-5 cards
     * @return table
     */
    public ArrayList<Card> getTable(){return table;}
    
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
     * Starts the round left of the dealer chip
     * has the small and big blind spost
     * deals pocket cards to player and betting commences. Cards are added to the 
     * table until the round is over with a showdown or all but one players fold.
     * At the end of the round, a winner is determined
     */
    public void handlePlayersHands(){
      //  startLeftOfDealer();
        //post();
        //deal(); 
    	handleRoundOfBetting();
        if (!roundIsOver())
            {flop(); System.out.println("The Flop: " + table); currentBet = 0; miniCleanUp(); 
            startLeftOfDealer(); handleRoundOfBetting();}
        if (!roundIsOver())
            {turn(); System.out.println("The Turn: " + table); currentBet = 0; miniCleanUp(); 
            startLeftOfDealer(); handleRoundOfBetting();}
        if (!roundIsOver())
            {river(); System.out.println("The River: " + table); currentBet = 0; miniCleanUp(); 
            startLeftOfDealer(); handleRoundOfBetting();}
        determineWinner();
    }
    
    /**
     * For each player still left, the dealer prompts each to each to bet until 
     * betting has completed
     */
    public void handleRoundOfBetting(){
        int count = 0;
        int numPlayers = stillPlaying().size();
        boolean done = false;
        while (!done){
            Player p = players.get(0);
            if (!p.hasFolded()){
                if (roundIsOver()){done = true;}
                else{p.bet(0); rotate(); count++;} //note the 0 used to be "this"
            }
            else rotate();
            if (count >= numPlayers) done = bettingIsDone();
        }   
    }
    
    /**
     * Returns a boolean value of true is the game is over
     * @return gameOver
     */
    public boolean isGameOver(){return gameOver;}
    
    /**
     * Dealer performs a mini Clean up by only resetting all of the players' bets
     */
    public void miniCleanUp(){
        for (Player p : players) p.resetBet();
    }
    
    /**
     * The players directly left of the dealer chip are responsible for the small 
     * and big blinds in that order. This method makes it so each of those players 
     * post their blinds
     */
    public void post(){
        players.get(0).bet(smallBlind); pot += smallBlind;
        System.out.println(players.get(0).toString() + " bets " + smallBlind + " (Small Blind)");
        p.upDateCash();
        rotate();
        players.get(0).bet(bigBlind); pot += bigBlind;
        System.out.println(players.get(0).toString() + " bets " + bigBlind + " (Big Blind)");
        currentBet = bigBlind;
        p.upDateCash();
        rotate();
    }
    
    /**
     * Returns a string of the Player p's decision to either check, call, 
     * raise or fold depending on the game's circumstances.  Runs until the
     * player gives a valid input
     * @param p Player
     * @return String of bet decision
     */
    public String promptBet(Player p){
        String s = "";
        s += p.toString() + " --> ";
        if (currentBet == p.getBet()) s += "ch to Check, ";
        else s += "c to Call, ";
        s += "r to Raise, f to Fold";
        System.out.println(s);
        System.out.println("Pocket: " + p.getPocket() + " " + "Table: " + table);
        System.out.println("My Bet: " + p.getBet() + ", Current Bet: " + currentBet + ", Pot: " + pot);
        String z = TextIO.getWord();
        if (z.equals("")) return promptBet(p);
        else return z;
    }
    
    /**
     * If the player decides to bet, this method is used to determine how much they 
     * would like to raise. Runs until a valid input is given
     * @param p Player
     * @return int value of raise
     */
    public int promptRaise(Player p){
        System.out.println("Bet amount: (Must be more than " + 
                                (currentBet - p.getBet()) + ")");
        int raise = TextIO.getInt();
        if (raise <= currentBet - p.getBet()) return promptRaise(p);
        else return raise;
    }
    
    /**
     * Rotates the ArrayList of players by placing the player at the 
     * beginning at the end.
     */
    public void rotate(){
        p = players.remove(0);
        players.add(p);
    }
    
    /**
     * Determines if the round is over.  If all but one player has folded, 
     * the round of betting is done
     * @return Boolean round is over variable
     */
    public boolean roundIsOver(){
        int count = 0;
        for (int i = 0; i < players.size(); i++){
            if (!players.get(i).hasFolded()) count++;
        }
        return count == 1;
    }
    
    /**
     * Prints out the current standings of the game in between
     * rounds. Prints out each player in the form of their toString() methods
     */
    public void standings(){
        System.out.println("\nStandings: ");
        TexasHoldEm.bam.addToTable("Standings", 700,650,3);
        for (Player p : players){
       //     TexasHoldEm.bam.addToTable(p.toString(), 700,700+50*(players.indexOf(p)),3);
            System.out.println(p.toString());
        }
        pot=0;
        //System.out.println("\nClick <enter> to continue");
        //sc.nextLine();
       // System.out.println("\f");
        setSpecialNumber(2);
        TexasHoldEm.bam.runDaShow();
    }
    
    /**
     * Rotates the players ArrayList until the player with the dealerChip 
     * is last in the list
     */
    public void startLeftOfDealer(){
        while (!players.get(0).isDealer()) rotate();
        rotate();
    }
    
    /**
     * Creates an ArrayList of players containing only those left in the round
     * @return ArrayList of players still playing
     */
    public ArrayList<Player> stillPlaying(){
        ArrayList<Player> stillPlaying = new ArrayList<>();
        for (Player p : players){
            if (!p.hasFolded()) stillPlaying.add(p);
        }
        return stillPlaying;
    }
    
    /**
     * All encompassing method that allows dealer to handle betting and clean 
     * up to perform one round of Texas Hold 'em
     */
    public void takeCareOfBusiness(){
        handlePlayersHands(); 
        cleanUp();
    }
        public int getSpecialNum(){
    	return specialNumber;
    }
    public void finalizee(){
    	TexasHoldEm.bam.finishUp();
    }
    public void setSpecialNumber(int n){
    	specialNumber=n;
    }
}
