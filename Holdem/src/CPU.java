/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Scanner;

/**
 * TODO: side pot?
 *       isAllIn() (Some way to monitor is betting is done and 
 *                  dealer should skip to the river)
 *       Catch statements for betting/raising (goAllIn() method if raise
 *       int > bank
 * @author Zack Berman
 */
public class CPU extends Player{
    //private ArrayList<Card> pocket;
    //private int bank, bet;
    //private String name;
    //private boolean folded = false;
    //private boolean dealerChip = false;
    ArrayList<Integer> outs;
    
    /**
     * Creates a CPU object just like a player with a name and bank
     * Also sets the static int scale to be the initial bank value
     * @param n name
     * @param b bank
     */
    public CPU(String n, int b, int x, int y){
        super(n,b,x,y);
        outs = new ArrayList<>();
        for (int i = 0; i < 10; i++){outs.add(0);}
    }
    
    //Big piece of AI here
    /**
     * Bet method for the AI
     * @param d Dealer
     */
    public void bet(Dealer d){
    	numActions++;
        int curBet = d.currentBet;
        int myBet = getBet();
        int diff = curBet - myBet;
        if (d.table.size() == 0){
            double value = preflopValue(getPocket());
            double r = Math.random();
            if (value < .15){
                if (diff == 0) check(d);
                else if (diff <= d.getSmallBlind()){
                    call(d);
                }else fold();
            }
            else if (value < .28){
                if (diff == 0) check(d);
                else if (diff <= d.getBigBlind()) call(d);
                else fold();
            }else if (value < .6){
                if (diff == 0){
                    if (r < .7) check(d);
                    else raise(d, d.getSmallBlind());
                }else if (diff <= getBank() / (2 * d.getBigBlind()))
                    call(d);
                else if (diff <= getBank() / (3 * d.getBigBlind())){
                    if (r < .3) call(d);
                    else if (r < .7) raise(d, diff + d.getSmallBlind());
                    else raise(d, diff + d.getBigBlind());
                }else fold();
            }else{
                if (r <= .3) call(d);
                else if (r <= .7) raise(d, diff + d.getSmallBlind());
                else raise(d, diff + d.getBigBlind());
            }
        }else{
            Hand h = getHand(d.table);
            int outW = getWeightedOuts();
            double odds = oddsOfBetterHand(d);
            double r = Math.random();
            int bet1 = (int)((Math.random() * (2*d.getBigBlind())) + d.getSmallBlind());
            int bet2 = (int)((Math.random() * (4*d.getBigBlind())) + d.getBigBlind());
            if (odds < .2){
                if (diff == 0){
                    if (r < .35) check(d);
                    else if (r < .8) raise(d, bet1);
                    else raise(d, bet2);
                }else{
                    if (r < .35) call(d);
                    else raise(d, bet2 + diff);
                }
            }else if (odds < .4){
                r += outW / 150.0;
                if (diff == 0){
                    if (r < .3) check(d);
                    else if (r < .9) raise(d, bet1);
                    else raise(d, bet2);
                }else if (diff <= d.getBigBlind() * 5){
                    if (r < .8) call(d);
                    else raise(d, bet1 + diff);
                }else{
                    if (r < odds) fold();
                    else call(d);
                }
            }else if (odds < .6){
                r += outW/ 150.0;
                if (diff == 0){
                    if (r < .7) check(d);
                    else if (r < .95) raise(d, bet1);
                    else raise(d, bet2);
                }else if (diff <= d.getBigBlind() * 3){
                    if (r < .9) call(d);
                    else raise(d, bet1 + diff);
                }else{
                    if (r < odds) fold();
                    else call(d);
                }
            }else if (odds < .8){
                r += outW/ 150.0;
                if (diff == 0){
                    if (r < .9) check(d);
                    else raise(d, bet1);
                }else if (diff <= d.getBigBlind() * 2){
                    if (r < .3) fold();
                    else call(d);
                }else{
                    if (r > .98) call(d);
                    else fold();
                }
            }else{
                if (diff == 0){
                    check(d);
                }else if (diff <= d.getSmallBlind()){
                    if (r > .9) call(d);
                    else fold();
                }else fold();
            }
        }
    }

    public void check(Dealer d){
    	System.out.println("check selected by CPU");
    	   super.check.setSelected(true);
        System.out.println(toString() + " checks" + "\n");
     
    }
    
    public void call(Dealer d){
    	System.out.println("call selected by CPU");
    	super.call.setSelected(true);
        int b = d.currentBet-getBet();
        d.addToPot(b);
        System.out.println(toString() + " bets " + (d.currentBet - getBet()) + "\n");
        bet(b);
         //the question is... does this trigger an event?
        
    }
    
    public void raise(Dealer d, int raise){
        if (raise > getBank()) goAllIn(d);
        else{
        	super.bett.setSelected(true);
        	System.out.println("CPU picked raise");
            d.addToPot(raise);
            bet(raise);
            d.currentBet = getBet();
            System.out.println(toString() + " bets " + raise + "\n");
            
        }
    }
    
    public void fold(){
        super.fold.setSelected(true);
    	TexasHoldEm.bam.d.determineWinner();
    	TexasHoldEm.bam.revealHands();
    	TexasHoldEm.bam.pane1.repaint();
    	//System.out.println("!!!!!! got past determining the winner");
    	TexasHoldEm.bam.startNextRoundM();
    	
    	//System.out.println("!!!!!! got past determining the winner");
    	TexasHoldEm.bam.startNextRoundM();
    	
    	//System.out.println("!!!!!! got past determining the winner");
    	TexasHoldEm.bam.startNextRoundM();
        System.out.println(toString() + " folds" + "\n");
    }
    
    public void goAllIn(Dealer d){
        raise(d, getBank());
        super.bett.setSelected(true);
        
    }
    /**
     * Returns a copy of the ArrayList of Integers outs
     * @return outs
     */
    public ArrayList<Integer> getOuts(){return outs;}
    
    /**
     * Returns a copy of the ArrayList of Integers which contains the outs 
     * for each particular hand (one pair to royal flush)
     * @return outs toString()
     */
    public String getOuts(Dealer d){
        numberOfOuts(d);
        String s = "";
        s += "One Pair Outs: " + outs.get(1);
        s += "\nTwo Pair Outs: " + outs.get(2);
        s += "\nThree Of A Kind Outs: " + outs.get(3);
        s += "\nStraight Outs: " + outs.get(4);
        s += "\nFlush Outs: " + outs.get(5);
        s += "\nFull House Outs: " + outs.get(6);
        s += "\nFour Of A Kind Outs: " + outs.get(7);
        s += "\nStraight Flush Outs: " + outs.get(8);
        s += "\nRoyal Flush Outs : " + outs.get(9);
        return s;
    }
    
    public int getWeightedOuts(){
        int count = 0;
        for (int i = 0; i < outs.size(); i++)
            count += outs.get(i)*i;
        return count;
    }
    
    /**
     * Given a hand h, returns an integer value of the number of cards which 
     * can increase the value of the hand
     * @param d Dealer
     * @return number of outs (int)
     */
    public int numberOfOuts(Dealer deal){
        outs.clear(); for (int i = 0; i < 10; i++){outs.add(0);}
        Deck d = new Deck();
        Hand h = getHand(deal.table);
        for (Card c : getPocket()){d.remove(c);}
        for (Card c : deal.table){d.remove(c);}
        int count = 0;
        int currentValue = h.getValue(h.getHand());
        for (Card c: d.getDeck()){
            h.addCard(c);
            int v = h.getValue(h.getHand());
            if(v > currentValue){outs.set(v, outs.get(v) + 1); count++;}
            h.remove(c);
        }
        return count;
    }
    
    /**
     * Calculates the odds of the opponent having a better hand at a given 
     * point in the round (note: doesn't predict end of the round)
     * @param deal Dealer
     * @return double value between 0 and 1
     */
    public double oddsOfBetterHand(Dealer deal){
        Deck d = new Deck();
        Hand myHand = getHand(deal.table);
        Hand oHand = new Hand();
        for (Card c : deal.table){d.remove(c); oHand.addCard(c);}
        int currentValue = myHand.getValue(myHand.chooseBestHand(myHand.getCards()));
        int betterHands = 0, count = 0;
        int nCards = d.getDeck().size();
        for (int i = 0; i < nCards-1; i++) {
            for (int j = i + 1; j < nCards; j++) {
                Card c1 = d.getDeck().get(i);
                Card c2 = d.getDeck().get(j);
                oHand.addCard(c1);
                oHand.addCard(c2);
                if (isBetter(myHand, oHand)) {
                    betterHands++;
                }
                count++;
                oHand.remove(c1);
                oHand.remove(c2);
            }
        }
        return (double)betterHands / count;
    }
    
    /**
     * Returns true if hand 2 is better than hand 1
     * @param h1 My Hand
     * @param h2 Opponent Hand
     * @return true if hand 2 is better, false if not
     */
    public static boolean isBetter(Hand h1, Hand h2){
        int v1 = h1.getValue(h1.chooseBestHand(h1.getCards()));
        int v2 = h2.getValue(h2.chooseBestHand(h2.getCards()));
        if (v2 > v1) return true;
        else if (v1 > v2) return false;
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
                    if (p2 > p1) return true;
                    else if (p1 > p2) return false;
                }
                return false;
            } else if (b > a) return true;
            else return false;
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
            }if (b2 > a2) return true;
            else if (a2 > b2) return false;
            else{
                if (b1 > a1) return true;
                else if (a1 > b1) return false;
                else{
                    for(int i = 4; i >= 0; i--){
                        int p1 = hc1.get(i).getPosition();
                        int p2 = hc2.get(i).getPosition();
                        if (p2 > p1) return true;
                        else if (p1 > p2) return false;
                    }
                    return false;  
                }
            }
        }else if (v1 == 6 || v1 == 7){
            int a = hc1.get(2).getPosition();
            int b = hc2.get(2).getPosition();
            if (b > a) return true;
            else return false;
        }else{
            for(int i = 4; i >= 0; i--){
                int p1 = hc1.get(i).getPosition();
                int p2 = hc2.get(i).getPosition();
                if (p2 > p1) return true;
                else if (p1 > p2) return false;
            }
            return false;
        }
    }
    
    /**
     * Based on a series of tests, this method returns a double between 0 and 1
     * with the value of a player's pocket
     * pocket aces: 1.0
     * 2, 7 off suit: .1
     * No pair can be below .4
     * flush draw adds .3
     * straight draw adds .08-.33
     * high card elements adds card1 + card2 / 60 (Modify??)
     * @param myPocket 2 Card ArrayList of Cards
     * @return double value between 0 and 1
     */
    public static double preflopValue(ArrayList<Card> myPocket){
        double d = 0;
        Card c1 = myPocket.get(0);
        Card c2 = myPocket.get(1);
        int p1 = c1.getPosition();
        int p2 = c2.getPosition();
        if (p1 == p2) { //pair
            d = p1 / 14.0;
            if (d < .4) return .4;
            else return d;
        }
        else{
            if (c1.getSuit().equals(c2.getSuit())) d += .3; //flush draw
            if (Math.abs(p1 - p2) < 5 || isAceToFiveStraightDraw(c1, c2)){ //straight draw
                d += opt(p1,p2) / 12.0;
            }
            d += ((p1-2) + (p2-2)) / 50.0;
            return d;
        }
    }
    
    /**
     * Returns true if Card a and Card b form an Ace to 5 straight draw
     * @param a Card 1
     * @param b Card 2
     * @return true if Cards a and b are an ace to 5 straight draw
     */
    private static boolean isAceToFiveStraightDraw(Card a, Card b) {
        return ((a.getPosition() == 14 && b.getPosition() < 6) ||
            b.getPosition() == 14 && b.getPosition() < 6);
    }

    /**
     * Determines the strength of a straight draw given two cards less 
     * than 5 spaces from one another. (1 means only 1 set of 3 cards can 
     * complete the striaght. 4 means 4 combinations of 3 cards can complete the 
     * straight. Opt of 4 increases ones chances of getting a straight)
     * @param a Card a position
     * @param b Card b position
     * @return Number of 3 card combinations that can complete the straight
     */
    public static int opt(int a, int b){
        if (a == 14 && b < 6) a = 1; if (b == 14 && a < 6) a = 1;
        int diff = Math.abs(a-b);
        int high = Math.max(a,b); int low = Math.min(a,b);
        int opt = 5 - diff;
        if (high <= 4)opt -= opt-low;
        else if (low >= 11) opt -= low-10;
        return opt;
    }
    
    /**
     * Tester for the CPU class
     * @param args 
     */
   /* public static void main(String[] args){
        
        ArrayList<Player> players = new ArrayList<>();
        CPU zcpu = new CPU("Zack", 1000,0,0);
        players.add(new Player("Ben", 1000,900,400));
        players.add(zcpu);
        Dealer d = new Dealer(2, players);
        d.deal();
        System.out.println("Zack: " + zcpu.getPocket());
        
        System.out.println("Preflop: " +preflopValue(players.get(1).getPocket()));
        d.flop();
        System.out.println("Table: " + d.table);
        System.out.println("Outs: " + zcpu.numberOfOuts(d));
        System.out.println(zcpu.getOuts().toString());
        System.out.println("Weighted: " + zcpu.getWeightedOuts());
        System.out.println("Odds: " + zcpu.oddsOfBetterHand(d));
        d.turn();
        System.out.println("Table: " + d.table);
        System.out.println("Outs: " + zcpu.numberOfOuts(d));
        System.out.println(zcpu.getOuts());
        System.out.println("Weighted: " + zcpu.getWeightedOuts());
        System.out.println("Odds: " + zcpu.oddsOfBetterHand(d));
        d.river();
        System.out.println("Ben: " + players.get(0).getPocket());
        System.out.println("Zack: " + zcpu.getPocket());
        System.out.println("Table: " + d.table);
        System.out.println("Odds: " + zcpu.oddsOfBetterHand(d));
        
        System.out.println("hit y");
        String z = TextIO.getln();
        if (z.equals("y")) main(args); */
        
        /*
        CPU me = new CPU("Zack", 1000);
        ArrayList<Player> players = new ArrayList<>();
        players.add(me);
        Dealer d = new Dealer(2, players);
        System.out.println("Input your pocket cards:");
        for (int i = 0; i < 2; i++){
            Scanner sc = new Scanner(System.in);
            System.out.print("Input the position of the card: " +
                         "\n(2-14:2-A): ");
            int b = sc.nextInt();
            System.out.print("Input the suit of the card: " +
                         "\n(1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs): ");
            int a = sc.nextInt();
            me.addCard(new Card(b,a));
        }
        System.out.println("Pocket: " + me.getPocket().toString());
        System.out.println("Preflop Value: " + me.preflopValue(me.getPocket()));
        System.out.println("\nInput the flop cards:");
        for (int i = 0; i < 3; i++){
            Scanner sc = new Scanner(System.in);
            System.out.print("Input the position of the card: " +
                         "\n(2-14:2-A): ");
            int b = sc.nextInt();
            System.out.print("Input the suit of the card: " +
                         "\n(1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs): ");
            int a = sc.nextInt();
            d.addCardToTable(new Card(b,a));
        }
        System.out.println("Table: " + d.table);
        System.out.println("Outs: " + me.getOuts(d));
        System.out.println("Odds Of Opponent's Hand Being Better: " + me.oddsOfBetterHand(d));
        System.out.println("\nInput the turn card:");
        Scanner sc = new Scanner(System.in);
        System.out.print("Input the position of the card: " +
                         "\n(2-14:2-A): ");
        int b = sc.nextInt();
        System.out.print("Input the suit of the card: " +
                         "\n(1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs): ");
        int a = sc.nextInt();
        d.addCardToTable(new Card(b,a));
        System.out.println("Table: " + d.table);
        System.out.println("Outs: " + me.getOuts(d));
        System.out.println("Odds Of Opponent's Hand Being Better: " + me.oddsOfBetterHand(d));
        System.out.println("\nInput the river card:");
        System.out.print("Input the position of the card: " +
                         "\n(2-14:2-A): ");
        int c = sc.nextInt();
        System.out.print("Input the suit of the card: " +
                         "\n(1:Hearts, 2:Diamonds, 3:Spades, 4:Clubs): ");
        int e = sc.nextInt();
        d.addCardToTable(new Card(c,e));
        System.out.println("Table: " + d.table);
        System.out.println("Odds Of Opponent's Hand Being Better: " + me.oddsOfBetterHand(d));
        */
    }
    
    /*
     * possible improvement on the methods above (don't use unless necessary)
    public double oddsOfBetterHand(Dealer deal){
        Deck d = new Deck();
        Hand myHand = getHand(deal.table);
        Hand oHand = new Hand();
        for (Card c : deal.table){d.remove(c); oHand.addCard(c);}
        int currentValue = myHand.getValue(myHand.chooseBestHand(myHand.getCards()));
        ArrayList<Card> hc = myHand.getHighCards();
        int betterHands = 0, count = 0;
        int nCards = d.getDeck().size();
        for (int i = 0; i < nCards-1; i++) {
            for (int j = i + 1; j < nCards; j++) {
                Card c1 = d.getDeck().get(i);
                Card c2 = d.getDeck().get(j);
                oHand.addCard(c1);
                oHand.addCard(c2);
                if (isBetter(oHand, currentValue, hc)){
                    betterHands++;
                }
                count++;
                oHand.remove(c1);
                oHand.remove(c2);
            }
        }
        return (double)betterHands / count;
    }
    
    public static boolean isBetter(Hand h2, int v1, ArrayList<Card> hc1){
        int v2 = h2.getValue(h2.chooseBestHand(h2.getCards()));
        if (v2 > v1) return true;
        else if (v1 > v2) return false;
        else{
            ArrayList<Card> hc2 = h2.getHighCards();
            for(int i = 4; i >= 0; i--){
                int p1 = hc1.get(i).getPosition();
                int p2 = hc2.get(i).getPosition();
                if (p2 > p1) return true;
                else if (p1 > p2) return false;
            }
            return false;
        }
    }
    */
//}
