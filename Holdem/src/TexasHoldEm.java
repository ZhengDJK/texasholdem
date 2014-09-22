import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package texasholdem;

/**
 *
 * @author Zack Berman
 */
public class TexasHoldEm {

    /**
     * @param args the command line arguments
     */
	
	public static  GUI bam = new GUI();
    public static void main(String[] args) {

    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
           

            }
        });

    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(bam);

        frame.setSize(500, 500);
        frame.setVisible(true);
    }
 
}
