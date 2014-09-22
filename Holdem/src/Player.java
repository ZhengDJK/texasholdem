/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Zack Berman
 */
public class Player implements ActionListener {
	private NumberFormat nP, sM;

    public ArrayList<Card> pocket;
    private int bank, bet;
    private String name, thingRemove;
    private boolean folded = false;
    private boolean dealerChip = false;
    private int x, y;
    public JPanel jP;
    protected JRadioButton bett;

	protected JRadioButton call;

	protected JRadioButton fold;

	protected JRadioButton check;
    protected int numActions=0;
    protected JFormattedTextField letsBetField;
    protected JPanel pleaseWork;
    private JLabel Cash;
    private int xcoord, ycoord;
    
    
    
    public Player(String n, int b, int x, int y){
    	nP = NumberFormat.getIntegerInstance();
    	sM = NumberFormat.getIntegerInstance();
        pocket = new ArrayList<>();
        name = n;
        bank = b;
        bet = 0;
        this.x=x;
        this.y=y;

        jP=new JPanel();
		jP.setLayout(new BoxLayout(jP, BoxLayout.Y_AXIS));
        jP.setBackground(Color.PINK);
        jP.setBounds(x, y, 200, 130);
        jP.setOpaque(true);
		
        JLabel Name = new JLabel(""+n);
        Name.setBounds(x, y, Name.getWidth(), Name.getHeight());
        jP.add(Name);
        Cash = new JLabel("Your Cash Money: "+bank);
        Cash.setBounds(x,(y+20), Cash.getWidth(), Cash.getHeight());
        jP.add(Cash);
        xcoord=Cash.getX();
        ycoord=Cash.getY();

        
		ButtonGroup betActions = new ButtonGroup();
		bett= createRadialButton("Bet or Raise");
		check=createRadialButton("Check");
        call = createRadialButton("Call");
        fold = createRadialButton("Fold");
		bett.setVerticalAlignment(SwingConstants.TOP);
		check.setVerticalAlignment(SwingConstants.TOP);
		call.setVerticalAlignment(SwingConstants.TOP);
		fold.setVerticalAlignment(SwingConstants.BOTTOM);
                
		betActions.add(bett);
		betActions.add(check);
		betActions.add(call);
		betActions.add(fold);
		jP.add(bett);
		jP.add(check);
		jP.add(call);
		jP.add(fold);
		
		//gotta have these mnemonics and aciton commands in order to set the action listener i guess
        bett.setMnemonic('b');
        bett.setActionCommand("bet");
        bett.addActionListener(this);
        
        call.setMnemonic('c');
        call.setActionCommand("call");
        call.addActionListener(this);
        
        fold.setMnemonic('f');
        fold.setActionCommand("fold");
        fold.addActionListener(this);
        
        check.setMnemonic('h');
        check.setActionCommand("check");
        check.addActionListener(this);    
        
        pleaseWork = new JPanel();
        letsBetField = new JFormattedTextField(sM);
//        bett.addActionListener(p);
    }
    public Player(){
    	//
    }

    public void reset(){
        bet = 0;
        folded = false;
        pocket = new ArrayList<>();
    }
    
    public void setDealer(boolean b){dealerChip = b;}
    
    public boolean isDealer(){return dealerChip;}
    
    public void addCard(Card c){pocket.add(c);}
    
    public void bet(int b){
        bank -= b;
        bet += b;
    }
/*    public void bet(Dealer d){
        //Scanner sc = new Scanner(System.in);
        String z = promptBet(d);
        if (z.equals("ch")) {
            System.out.println(toString() + " checks" + "\n");
        } else if (z.equals("c")) {
            int b = d.currentBet-getBet();
            d.addToPot(b);
            System.out.println(toString() + " bets " + (d.currentBet - getBet()) + "\n");
            bet(b);
        } else if (z.equals("r")) {
            int raise = promptRaise(d);
            d.addToPot(raise);
            bet(raise);
            d.currentBet = getBet();
            System.out.println(toString() + " bets " + raise + "\n");
        } else if (z.equals("f")) {
            fold();
            System.out.println(toString() + " folds" + "\n");
        }
    }*/

	public void setMoney(int monay){
    	bank=monay;
    }
    public void setName(String n){
    	name=n;
    }
    
    public int getBet(){return bet;}
    
    public void resetBet(){bet = 0;}
    
    public int getBank(){return bank;}
    
    public ArrayList<Card> getPocket(){return pocket;}
    
    public void fold(){folded = true;}
    
    public boolean hasFolded(){return folded;}
    
    public void win(int pot){
    	System.out.println("adding the pot of: "+pot+" to bank");
    	bank += pot;
    	}
    
    public Hand getHand(ArrayList<Card> table){
        Hand h = new Hand();
        for (Card c : pocket) h.addCard(c);
        if (table.size() > 0)
            for (Card c : table) h.addCard(c);
        return h;
    }
    
    public String toString(){
        return name + "(" + bank + ")";
    }
	public String getName() {
		return name;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e;
		String thing1=event.toString();
		//System.out.println(thing1);
		if(thing1.contains("cmd=bet")){
			
			
			System.out.println("bet selected");
			
			JLabel letsBet= new JLabel("Enter your bet: ");
			
			
			letsBetField.setValue(new Integer(0));
			letsBetField.setColumns(9);
			
			Color white = new Color(255,255,255);
			letsBetField.setBackground(white);
			letsBetField.addActionListener(this);
			//letsBetField.addActionListener(TexasHoldEm.bam.d.p);
			letsBet.setLabelFor(letsBetField);
			pleaseWork.add(letsBetField);
			pleaseWork.add(letsBet);
			pleaseWork.setBackground(white);
			pleaseWork.setBounds(920, 800, 200, 60);
			//if(getNumActions()<1)
				TexasHoldEm.bam.pane1.add(pleaseWork, new Integer(1), 0);
			pleaseWork.setVisible(true);
	    	//System.out.println("action has been recognized, but did we do stuff? if u see this then yes?");
			
			//TexasHoldEm.bam.guiBet();
			//System.out.println("betting");
		}

		if(thing1.contains("call")){
			//remove the currentBet from the player's bank
			System.out.println("called");
			System.out.println("current bet: "+TexasHoldEm.bam.d.currentBet);
			TexasHoldEm.bam.d.pot += TexasHoldEm.bam.d.currentBet;    
			bet(TexasHoldEm.bam.d.currentBet);
			 System.out.println("name: "+getName()+ " and current bank is: "+getBank()+ " and the numActions = "+getNumActions()+1);
			TexasHoldEm.bam.d.p.upDateCash();
			numActions++;
			TexasHoldEm.bam.d.rotate();
			TexasHoldEm.bam.runDaShow();
		}
		if(thing1.contains("check")){
			System.out.println("checked");
			//bet(TexasHoldEm.bam.d);
			numActions++;
			
			TexasHoldEm.bam.d.rotate();
			TexasHoldEm.bam.runDaShow();
		}
		if(thing1.contains("fold")){
			//make cards disappear
			System.out.println("folded");
			//bet(TexasHoldEm.bam.d);
			numActions++;
			fold(); 
        	TexasHoldEm.bam.d.determineWinner();
        	TexasHoldEm.bam.revealHands();
        	TexasHoldEm.bam.pane1.repaint();
        	
        	//System.out.println("!!!!!! got past determining the winner");
        	TexasHoldEm.bam.startNextRoundM();
			//update();
			TexasHoldEm.bam.d.rotate();
			TexasHoldEm.bam.runDaShow();
			
		}
		if(thing1.contains("disabledTextColor=")){  //just a thing that shows up in the event to string...
	
			int theBet = ((Number)letsBetField.getValue()).intValue();
			System.out.println("The bet is: "+theBet);
	        TexasHoldEm.bam.d.pot += theBet;
	        bet(theBet);
	        System.out.println("name: "+getName()+ " and current bank is: "+getBank()+ " and the numActions = "+getNumActions()+1);
	        TexasHoldEm.bam.d.currentBet = getBet();
	        TexasHoldEm.bam.d.rotate();
			pleaseWork.setVisible(false);
			upDateCash();

			//TexasHoldEm.bam.addPlayerInfo(this);
			//TexasHoldEm.bam.pane1.validate();
			//TexasHoldEm.bam.validate();
	    	TexasHoldEm.bam.pane1.repaint();
	    	TexasHoldEm.bam.pane1.validate();
	    	TexasHoldEm.bam.pane1.setVisible(true);
			numActions++;
			TexasHoldEm.bam.runDaShow();
		}
		
	}
	private JRadioButton createRadialButton(String s){
		JRadioButton z = new JRadioButton(s);
		return z;
	}
	public int getNumActions(){
		return numActions;
	}
	public void setNumActions(int n){
		numActions=  n;
	}
	public void removePlayer(Player p){
		p.jP.setVisible(false);
	}
	public void upDateCash(){
		jP.remove(Cash);
		Cash = new JLabel("Your Cash Money: "+bank);
		Cash.setBounds(xcoord, ycoord, Cash.getWidth(), Cash.getHeight());
		Cash.setVisible(true);
		jP.add(Cash);
		jP.revalidate();
		jP.repaint();
		TexasHoldEm.bam.upDatePot();
	}

}
