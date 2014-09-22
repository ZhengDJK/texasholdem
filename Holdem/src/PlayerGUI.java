/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Zack Berman
 */
public class PlayerGUI implements ActionListener {
    public ArrayList<Card> pocket;
    private int bank, bet;
    private String name, thingRemove;
    private boolean folded = false;
    private boolean dealerChip = false;
    private int x, y;
    public JPanel jP;
    private JRadioButton bett, call, fold, check;
    
    public PlayerGUI(String n, int b, int x, int y){
        pocket = new ArrayList<>();
        name = n;
        bank = b;
        bet = 0;
        this.x=x;
        this.y=y;


		jP.setLayout(new BoxLayout(jP, BoxLayout.Y_AXIS));
        jP.setBackground(Color.PINK);
        jP.setBounds(x, y, 200, 130);
        jP.setOpaque(true);
		
        JLabel Name = new JLabel(""+n);
        Name.setBounds(x, y, Name.getWidth(), Name.getHeight());
        jP.add(Name);
        JLabel Cash = new JLabel("Your Cash Money: "+bank);
        Cash.setBounds(x,(y+20), Cash.getWidth(), Cash.getHeight());
        jP.add(Cash);

        
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
//        bett.addActionListener(p);
    }
    public PlayerGUI(){
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
    
    public void win(int pot){bank += pot;}
    
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
		if((event.toString().indexOf("text="))!=-1)
			 thing1 = event.toString().substring(event.toString().indexOf("text="));
		String thingBet = TexasHoldEm.bam.bet.toString();
		String thing2 = thingBet.substring(thingBet.indexOf("text="));

		//System.out.println("event is" +thing1);
	

		System.out.println(thingBet);
		if (thing1.equals(thing2)){
			//System.out.println("recognized event and action type");
			//TexasHoldEm.bam.guiBet();
			thingRemove = TexasHoldEm.bam.letsBetField.toString();
		}
		if(thing1.contains("cmd=")){
			//TexasHoldEm.bam.guiLetsBet();
		}
		
	}
	private JRadioButton createRadialButton(String s){
		JRadioButton z = new JRadioButton(s);
		return z;
	}
}