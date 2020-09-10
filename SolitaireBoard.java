// Name: Xueyu Wang
// USC NetID: 2670589054
// CSCI455 PA2
// Fall 2019

import java.util.ArrayList;
import java.util.Random;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total 
  number of cards is for the game by changing NUM_FINAL_PILES, below.  
  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.  (See comments 
  below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

   // Note to students: you may not use an ArrayList -- see assgt 
   // description for details.
   
   
   /**
      Representation invariant:
      <put rep. invar. comment here>
      
      NUM_FINAL_PILES > 0;
      0 <= numboard <= solitaireboard.length == CARD_TOTAL
      every element in solitaireboard : [1, CARD_TOTAL]
      the sum of the whole elements in solitaireboard is CARD_TOTAL
   */

   // <add instance variables here>
   private int[] solitaireboard = new int[CARD_TOTAL];   
   private int numboard;
 
   /**
      Creates a solitaire board with the configuration specified in piles.
      piles has the number of cards in the first pile, then the number of 
      cards in the second pile, etc.
      PRE: piles contains a sequence of positive numbers that sum to 
      SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) {   
      for(int i = 0; i < piles.size(); i++){
         solitaireboard[i] = piles.get(i);
      }
      numboard = piles.size();
      // sample assert statement (you will be adding more of these calls)
      // this statement stays at the end of the constructor.
      assert isValidSolitaireBoard();   
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
   */
   public SolitaireBoard() {
      int sum = 0;
      numboard = 0;
      Random generator = new Random();
      while(sum != CARD_TOTAL && numboard < CARD_TOTAL){
         solitaireboard[numboard] = generator.nextInt(CARD_TOTAL) + 1;
         sum += solitaireboard[numboard];  
         while(sum > CARD_TOTAL){
            sum -= solitaireboard[numboard];
            solitaireboard[numboard] = generator.nextInt(CARD_TOTAL) + 1;
            sum += solitaireboard[numboard];
         }
         numboard++;
      }
      assert isValidSolitaireBoard();
   }
  
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration 
      according to the rules of Bulgarian solitaire: Takes one card from each
      pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
   */
   public void playRound() {
      int newpile = numboard;
      int minus = 0;
      int i = 0;
      while(i < numboard){
         if(solitaireboard[i] == 1){
            minus++;  
            i++;
         }
         else{
            solitaireboard[i - minus] = solitaireboard[i] - 1;
            i++;
         }
      }
      solitaireboard[i-minus] = newpile;
      numboard = numboard - minus + 1;
      assert isValidSolitaireBoard();
   }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, 
      there are NUM_FINAL_PILES piles that are of sizes 
      1, 2, 3, . . . , NUM_FINAL_PILES, 
      in any order.
   */
   
   public boolean isDone() {
      int i = 0;
      int sum = 0;
      int[] repeatnum = new int[CARD_TOTAL+1];
      for(i = 0; i < NUM_FINAL_PILES; i++){
         sum += solitaireboard[i];
         int value = solitaireboard[i];
         if(value > NUM_FINAL_PILES){
            return false;
         }
         else{
            repeatnum[value]++;
            if(repeatnum[value]>1){
               return false;
            }
         }
      }
      if(sum != CARD_TOTAL){
         return false;
      }
      assert isValidSolitaireBoard();
      return true;  // dummy code to get stub to compile
   }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
   */
   public String configString() {
      String output = "";
      if(numboard == 1)
         output += solitaireboard[0];
      else{
         for(int i = 0; i < numboard - 1; i++){
            output = output + solitaireboard[i] + " ";
         }
         output += solitaireboard[numboard - 1];
         }
      assert isValidSolitaireBoard();
      return output;   // dummy code to get stub to compile
   }
   
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
   */
   private boolean isValidSolitaireBoard() {
      int sum = 0;
      for(int i = 0; i < numboard; i++){
         if(solitaireboard[i] <= 0 || solitaireboard[i] > CARD_TOTAL){
            return false;
         }
      }
      for(int i = 0; i < numboard; i++){
         sum += solitaireboard[i];
      }
      if(sum != CARD_TOTAL){
         return false;
      }
      return true;  // dummy code to get stub to compile
   }

}
