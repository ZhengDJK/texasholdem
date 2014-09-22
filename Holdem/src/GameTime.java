import java.awt.*;

import javax.swing.*;
public class GameTime extends JFrame {
	private ImageIcon image1;
	private JLabel label1;
	
	GameTime(){
		setLayout(new FlowLayout());
		
		image1 = new ImageIcon(getClass().getResource("Images/holdemtable.png"));
		label1 = new JLabel(image1);
		add(label1);
		
		
	}
	
	public static void main (String args[]){
		GameTime gui = new GameTime();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.pack();
		gui.setTitle("Image Program");
	}
}
