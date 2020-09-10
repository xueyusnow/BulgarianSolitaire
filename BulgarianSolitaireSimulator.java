// Name: Xueyu Wang
// USC NetID: 2670589054
// CSCI455 PA2
// Fall 2019

import java.util.*;
import java.lang.Character;
/**
   <add main program comment here>
   Output the outcome of each step of the game Bulgarian Solitaire according to different input command.
*/

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = false;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }  
      // <add code here>
      ArrayList<Integer> arraylist = new ArrayList<Integer>();
      SolitaireBoard sb = new SolitaireBoard();
      Scanner in = new Scanner(System.in); 
      if(userConfig == true){
         System.out.println("Number of total cards is " + sb.CARD_TOTAL);
         System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
         System.out.println("Please enter a space-separated list of positive integers followed by newline:");
         Boolean trueinput = inputcheck(arraylist, sb.CARD_TOTAL, in);
         while(trueinput == false){
            System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " + sb.CARD_TOTAL);
            System.out.println("Please enter a space-separated list of positive integers followed by newline:");
            trueinput = inputcheck(arraylist, sb.CARD_TOTAL, in);
         }
         if(singleStep == false){
            SolitaireBoard sbs = new SolitaireBoard(arraylist);
            userConfig(sbs);
         }
         else{
            singleStep(sb);
         }
      }
      else{
         if(singleStep == true){
            singleStep(sb);
         }
         else{
            userConfig(sb);
         }
      }
   }   
   
   // <add private static methods here>
   
   /**
      Tell the true or false of the input data.
      @param arraylist  data structure to store the initial correct user input
      @param cardtotal  number of total cards used in the game
   */
   private static Boolean inputcheck(ArrayList<Integer> arraylist, int cardtotal, Scanner in){              
      String line = in.nextLine();
      Scanner lineScanner = new Scanner(line);
      while(lineScanner.hasNext()){
         String str = lineScanner.next();
         if(Character.isLetter(str.charAt(0))){
            return false;
         }
      }  
      lineScanner = new Scanner(line);
      while(lineScanner.hasNextInt()){
         int input = lineScanner.nextInt();
         if(input <= 0 || input > cardtotal) {  
            return false;
         }
      }   
      lineScanner = new Scanner(line);
      int sum = 0;
      while(lineScanner.hasNextInt()){
         sum += lineScanner.nextInt();
      }
      if(sum != cardtotal){
         return false;
      }
      else{
         lineScanner = new Scanner(line);
         while(lineScanner.hasNextInt()){
            arraylist.add(lineScanner.nextInt());
         }
      }
      return true;
   }

   /**
      Output the outcome of the game on the basis of the initial user correct input. 
      @param sb  a instance of the class of SolitaireBoard
   */
   private static void userConfig(SolitaireBoard sb){
      System.out.print("Initial configuration: ");
      System.out.println(sb.configString());
      int j = 0;
      while(!sb.isDone()){
         sb.playRound();
         j++;
         System.out.println("[" + j +"] Current configuration: " + sb.configString());
      }
      System.out.println("Done!");
   }
   
   /**
      Output the outcome of the game on the basis of the initial random input. 
      @param sb  a instance of the class of SolitaireBoard
   */
   private static void singleStep(SolitaireBoard sb){
      System.out.print("Initial configuration: ");
      System.out.println(sb.configString());
      sb.playRound();
      System.out.println("[1] Current configuration: " + sb.configString());
      System.out.print("<Type return to continue>");
      int j = 1;
      Scanner in = new Scanner(System.in);
      aa:while(in.hasNextLine() && in.nextLine().length() == 0){
         if(!sb.isDone()){
            sb.playRound();
            j++;
            System.out.println("[" + j +"] Current configuration: " + sb.configString());
            System.out.print("<Type return to continue>");   
         }
         else{
            System.out.println("Done!");
            break aa;
         }
      }
   }
}
