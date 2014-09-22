import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI extends JPanel implements ActionListener {
	Player guy = new Player();
	public static final int FRAME_WIDTH=500;
	public static final int FRAME_HEIGHT=500;
	
	private JButton start, begin, startNextRound;
	private JPanel panel, haha;
	
	private JLabel namePrompt, playerPrompt, moneyPrompt;
	private JTextField nameField;
	private JFormattedTextField numPlayers, startMoney;
	JFormattedTextField letsBetField;
	private JTextField textField;
	private final static String newline = "\n";
	private String name = "";
	URL url;
	Image picture;
	private ImageIcon image1;
	private JLabel label1, pott;
	public JLayeredPane pane1;
	private JPanel panelGreen, background, startUp, potHolder, nextRound;
	private int money;
	JRadioButton bet;
	CPU p1;
	private JPanel pleaseWork;
	Dealer d;
	private Player p3;
	private NumberFormat nP, sM;  //This allows for the input fields to be later set to only accept numbers
	
	
	//private NumberFormat numPlayers, startMoney;
	
	public GUI(){


		super(new BorderLayout());
		nP = NumberFormat.getIntegerInstance();
		sM = NumberFormat.getIntegerInstance();
		
		
		createTextField();
		creatButton();
		createPanel();

		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

    
	private void createTextField(){
		//these four lines set up a label for where to enter a name and the corresponding text field to take the name as an input
		namePrompt= new JLabel("Enter your name: ");
		nameField = new JTextField(20);
		nameField.addActionListener(this);
		namePrompt.setLabelFor(nameField);
		
/*		//these four lines set up a label for where to enter the number of players...
		playerPrompt = new JLabel("Enter desired # of opponents: ");
		numPlayers = new JFormattedTextField (nP);
		numPlayers.setValue(new Integer(0));
		numPlayers.setColumns(3);
		numPlayers.addActionListener(this);
		playerPrompt.setLabelFor(numPlayers);*/
		
		//these four lines set up a label for where to enter the inital starting funds
		moneyPrompt = new JLabel("Enter money you wish to play with: ");
		startMoney = new JFormattedTextField(sM);
		startMoney.setValue(new Integer(100));
		startMoney.setColumns(15);
		startMoney.addActionListener(this);
		moneyPrompt.setLabelFor(startMoney);
	}
	
	private void creatButton(){
		start = new JButton("Click here to Start");
		start.addActionListener(this);
	}
	private JRadioButton createRadialButton(String s){
		JRadioButton z = new JRadioButton(s);
		return z;
	}
	
	private void createPanel(){
		panel = new JPanel();
		//These panel.add etc things add the fields that were declared/initialized above to the actual output of the program
		//it basically allows them to be seen
		panel.add(namePrompt);
		panel.add(nameField);
		panel.add(moneyPrompt);
		panel.add(startMoney);
		
		panel.add(start);
		Color var = new Color (0,255,0);
		panel.setBackground(var);

		add(panel);
		
	}


	@Override
	public void actionPerformed(ActionEvent evt) {
		Object whereFrom = evt.getSource();
		


		
		if(whereFrom==start){
/*			int xyz=0;
			while(xyz<1000000000){             //this was added to show that when a while loop is entered, updates get put on hold?
				xyz++;
				System.out.println(xyz);
			}*/
			String text = nameField.getText();
			name = text;
			//System.out.println(name);
			startUp = new JPanel();
			begin= new JButton("Click here to play");
			begin.addActionListener(this);
			
			money = ((Number)startMoney.getValue()).intValue();

			JFrame desktop = new JFrame("Game On!");

			background = new JPanel();
			image1 = new ImageIcon(getClass().getResource("Images/holdemtable.png"));
			label1 = new JLabel(image1);
			

			
			
			pane1=new JLayeredPane();
			desktop.add(pane1);			
			panelGreen= new JPanel();      //these four lines of code work to make a green panel on the new desktop
	        background.setBounds(-50,25,1800,1100);
			background.add(label1);
			//adding the cards to the panel so they appear
			//this should be done methodically
			//background.add(backblue);
			background.setOpaque(true);
			
			startUp.setBounds(800,100, 200, 100);
			startUp.setBackground(Color.WHITE);
			startUp.add(begin);
			startUp.setOpaque(true);
			
			//pane1.add(panelGreen, new Integer(1), 0);
			pane1.add(background, new Integer(0), 0);
			pane1.add(startUp, new Integer(1),0);

			
			
			//below is a test to try setting up the player stat windows
			desktop.setSize(1800, 1100);
			desktop.setVisible(true);

			
			
		}


		if(whereFrom==begin){
			startUp.setVisible(false);
			System.out.println(startUp.isVisible());
	        p1 = new CPU("Elvis", 100, 900, 100);
	       // Player p2 = new Player("Bono", 100, 100, 100);
	        p3 = new Player(name, money, 50, 775);
	        
	        ArrayList<Player> players = new ArrayList<>();
	        players.add(p1);// players.add(p2); 
	        players.add(p3); 
	        d = new Dealer(2, players);
	        showPot();
	        d.players.get(0).setDealer(true);

	        
	        d.startLeftOfDealer();
	        for(int i=0; i<d.players.size(); i++){
	        	addPlayerInfo(d.players.get(i));
	        }
	        d.post();
	        d.deal();
	        p1.upDateCash();
	        p1.setNumActions(1);

		}
		if(whereFrom==startNextRound){
        	d.setSpecialNumber(0);
        	d.cleanUp();
        	for(int i=0; i<d.players.size(); i++){
        		d.players.get(i).setNumActions(0);
        	}
        	System.out.println("num actions reset to 0");
	        d.post();
	        d.deal();
	        p1.bet(d);
	        System.out.println("new cards dealt");
	        for(int i=0; i<d.players.size(); i++){
	        	addPlayerInfo(d.players.get(i));
	        }
	        pane1.remove(nextRound);
	        pane1.remove(haha);
	        pane1.remove(3);
	        //System.out.println("looking for the box near 700, 650");
	        Component [] zzz = pane1.getComponents();
	        for(int i=0; i<zzz.length; i++){
	        	//System.out.println(zzz[i].toString());
	        	String s= zzz[i].toString();
	        	if(s.contains("700,600")||s.contains("700,500"))
	        		pane1.remove(zzz[i]);
	        }
	        hideHand();
	        

		}
		
	}
	
	public void addToTable(Card c, int x, int y, int z){
		
		String cName = c.toString();
		ImageIcon c1= new ImageIcon(getClass().getResource("Images/"+cName+".png"));
		JLabel C1=new JLabel(c1);
		//System.out.println(c1.getIconWidth()+"height: "+c1.getIconHeight()+" starting coords: "+x+ ","+y);
		C1.setBounds(x,y, c1.getIconWidth(), c1.getIconHeight());
		C1.setVisible(true);
		pane1.repaint();
		pane1.add(C1, new Integer(z), 0);
	}
	public void addToTableBack(String s, int x, int y){
		
		ImageIcon c1= new ImageIcon(getClass().getResource("Images/"+s+".png"));
		JLabel C1=new JLabel(c1);
		//System.out.println(c1.getIconWidth()+"height: "+c1.getIconHeight()+" starting coords: "+x+ ","+y);
		C1.setBounds(x,y, c1.getIconWidth(), c1.getIconHeight());
		C1.setVisible(true);
		pane1.repaint();
		pane1.add(C1, new Integer(1), 0);
	}
	public void addToTable(String s, int x, int y, int z){
		JLabel boom = new JLabel(s);
		haha = new JPanel();
		
		boom.setBounds(x,y,boom.getWidth(), boom.getHeight());
		haha.setBounds(x,y-100,300, 250);
		boom.setVisible(true);
		haha.setVisible(true);
		haha.add(boom);
		pane1.add(haha, new Integer (z), 0);
		pane1.repaint();
	}

	
	public void removeFromTable(Component o){
		pane1.remove(o);
		pane1.repaint();
	}
	public void addPlayerInfo(Player p){
		pane1.add(p.jP, new Integer(1), 0);

	}
	
	

	public void runDaShow(){
		
        if(d.players.get(0).getNumActions()==1&&d.players.get(1).getNumActions()==1){//&&d.players.get(2).getNumActions()==1
        	System.out.println("recognized that flop should happen");
        	d.flop(); d.miniCleanUp(); d.startLeftOfDealer(); p1.bet(d);  p1.upDateCash(); p3.upDateCash();
        	
        }
       
        if(d.players.get(0).getNumActions()==2&&d.players.get(1).getNumActions()==2){//&&d.players.get(2).getNumActions()==2
        	d.turn(); d.miniCleanUp(); d.startLeftOfDealer();p1.bet(d);p1.upDateCash();
        }
        if(d.players.get(0).getNumActions()==3&&d.players.get(1).getNumActions()==3&&d.getSpecialNum()==0){//d.players.get(2).getNumActions()==3&&
        	d.river();p1.bet(d);p1.upDateCash();p3.upDateCash();

        }
        if(d.players.get(0).getNumActions()==4&&d.players.get(1).getNumActions()==4&&d.getSpecialNum()==0){//d.players.get(2).getNumActions()==4&&
        	System.out.println("last round of betting");
        	p1.bet(d);p1.upDateCash();p3.upDateCash();
       // 	d.finalizee();
        	System.out.println("speical number set called");
        	d.setSpecialNumber(1);
        	//System.out.println("player at index 0 num actions: "+d.players.get(0).getNumActions()+ " other player num: "+d.players.get(1).getNumActions()+ " special num "+d.getSpecialNum());
        	runDaShow();
        	
        }
        if(d.players.get(0).getNumActions()>=4&&d.players.get(1).getNumActions()>=4&&d.getSpecialNum()==1){//d.players.get(2).getNumActions()==4&&
        	System.out.println("winner should be determined here");
        	d.determineWinner();
        	revealHands();
        	pane1.repaint();
        	
        	//System.out.println("!!!!!! got past determining the winner");
        	startNextRoundM();
        	
        	//d.setSpecialNumber(2);
        }

        

	}
	
	public void finishUp(){
        if(d.getSpecialNum()==1){
        	d.miniCleanUp(); d.startLeftOfDealer(); d.determineWinner();
        }
	}

	

	public String getName(){
		return name;
	}

	public void showPot(){
		pott = new JLabel("The Pot is: $"+d.pot);
		pott.setBounds(800, 250, pott.getWidth(), pott.getHeight());
		pott.setVisible(true);
		potHolder = new JPanel();
		potHolder.setBackground(Color.WHITE);
		potHolder.setBounds(0, 250, 200, 200);
		potHolder.add(pott);
		potHolder.setVisible(true);

		pane1.add(potHolder, new Integer(2), 0);
		pane1.repaint();
		
	}
	public void upDatePot(){
		potHolder.remove(pott);
		pott = new JLabel("The Pot is: $"+d.pot);
		pott.setBounds(800, 250, pott.getWidth(), pott.getHeight());
		pott.setVisible(true);
		potHolder.add(pott);
		potHolder.revalidate();
		potHolder.repaint();
	}

	public void startNextRoundM(){
		
		startNextRound = new JButton("Click here to Start the Next Round");
		startNextRound.addActionListener(this);
		startNextRound.setVisible(true);
		nextRound = new JPanel();
		nextRound.setBounds(850, 900, 250, 100);
		nextRound.setBackground(Color.WHITE);
		nextRound.add(startNextRound);
		nextRound.setVisible(true);
		pane1.add(nextRound, new Integer(3), 0);
		//System.out.println("just about to remove the win panel");
		pane1.remove(haha);
		pane1.revalidate();
		pane1.repaint();
	}
	public void revealHands(){
		Card c4 = p1.getPocket().get(0);
		Card c5 = p1.getPocket().get(1);
		addToTable(c4, 600, 600, 4);
		addToTable(c5, 650, 650, 4);
	}
	public void hideHand(){
        Component [] zzz = pane1.getComponents();
        for(int i=0; i<zzz.length; i++){
        //	System.out.println(zzz[i].toString());
        	String s= zzz[i].toString();
        	if(s.contains("600,600")||s.contains("650,650"))
        		pane1.remove(zzz[i]);
        }
	}
	


}
