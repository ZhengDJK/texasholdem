/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author Zack Berman
 */
public class SelectionSorter {
    private ArrayList<Card> hand;

   /**
      Constructs a selection sorter.
      @param anArray the array to sort */
   public SelectionSorter(ArrayList<Card> a){hand = a;}

   /**
      Sorts the array managed by this selection sorter. */
   public void sort(){  
      for (int i = 0; i < hand.size() - 1; i++){  
         int minPos = minimumPosition(i);
         swap(minPos, i);
      }
   }

   /**
      Finds the smallest element in a tail range of the array.
      @param from the first position in a to compare
      @return the position of the smallest element in the
      range a[from] . . . a[a.length - 1] */
   private int minimumPosition(int from){  
      int minPos = from;
      for (int i = from + 1; i < hand.size(); i++)
         if (hand.get(i).getPosition() < hand.get(minPos).getPosition()) minPos = i;
      return minPos;
   }

   /**
      Swaps two entries of the array.
      @param i the first position to swap
      @param j the second position to swap*/
   private void swap(int i, int j){
      Card temp1 = hand.get(i);
      Card temp2 = hand.get(j);
      hand.remove(i);
      hand.add(i,temp2);
      hand.remove(j);
      hand.add(j,temp1);
   }
}
