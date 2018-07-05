/**
 *
 * @author Ash
 */
public class PredictionSystem 
{
   public static final int NUM_OF_RULES = 27;
   private Rule Rules[] = new Rule[NUM_OF_RULES];
   int previousRuleFired;
   String predictedMove;
   int N;
   int successes;
   
   public void init()
   {
      // For each space in the array add a new Rule object.
      for(int i=0; i<NUM_OF_RULES; i++)
      {
         Rule rule = new Rule();
         Rules[i] = rule;
      }
      
      Rules[0].setRule("Punch", "Punch", "Punch");
      Rules[1].setRule("Punch", "Punch", "LowKick");
      Rules[2].setRule("Punch", "Punch", "HighKick");
      Rules[3].setRule("Punch", "LowKick", "Punch");
      Rules[4].setRule("Punch", "LowKick", "LowKick");
      Rules[5].setRule("Punch", "LowKick", "HighKick");
      Rules[6].setRule("Punch", "HighKick", "Punch");
      Rules[7].setRule("Punch", "HighKick", "LowKick");
      Rules[8].setRule("Punch", "HighKick", "HighKick");
      Rules[9].setRule("LowKick", "Punch", "Punch");
      Rules[10].setRule("LowKick", "Punch", "LowKick");
      Rules[11].setRule("LowKick", "Punch", "HighKick");
      Rules[12].setRule("LowKick", "LowKick", "Punch");
      Rules[13].setRule("LowKick", "LowKick", "LowKick");
      Rules[14].setRule("LowKick", "LowKick", "HighKick");
      Rules[15].setRule("LowKick", "HighKick", "Punch");
      Rules[16].setRule("LowKick", "HighKick", "LowKick");
      Rules[17].setRule("LowKick", "HighKick", "HighKick");
      Rules[18].setRule("HighKick", "Punch", "Punch");
      Rules[19].setRule("HighKick", "Punch", "LowKick");
      Rules[20].setRule("HighKick", "Punch", "HighKick");
      Rules[21].setRule("HighKick", "LowKick", "Punch");
      Rules[22].setRule("HighKick", "LowKick", "LowKick");
      Rules[23].setRule("HighKick", "LowKick", "HighKick");
      Rules[24].setRule("HighKick", "HighKick", "Punch");
      Rules[25].setRule("HighKick", "HighKick", "LowKick");
      Rules[26].setRule("HighKick", "HighKick", "HighKick");
      
      MovesMemory.setMoveA("unknown");
      MovesMemory.setMoveB("unknown");
      MovesMemory.setMoveC("unknown");
      previousRuleFired = -1;
      N = 0;
      successes = 0;
   }
   
   public String processMove(String move, Boolean t)
   {
      String unknown = "unknown";
      int i;
      int ruleToFire = -1;
      Boolean turn = t;
      
      if((MovesMemory.getMoveA()).equals(unknown))
      {
         MovesMemory.setMoveA(move);
         predictedMove = unknown;
         MiniTekken.getMovesLog().append("\n\n " + move);
         
         return unknown;
      }
      
      if((MovesMemory.getMoveB()).equals(unknown))
      {
         MovesMemory.setMoveB(move);
         predictedMove = unknown;
         MiniTekken.getMovesLog().append("\n " + move);
         
         return unknown;
      }
      
      MiniTekken.getMovesLog().append("\n " + move);
      
      N++;
      
      System.out.println("Move A is: " + MovesMemory.getMoveA());
      System.out.println("Move B is: " + MovesMemory.getMoveB());
      System.out.println("Current move is: " + move);
      
      //Check for matching Rules using moveA and moveB.
      for(i=0; i<NUM_OF_RULES; i++)
      {
         if(Rules[i].moveA.equalsIgnoreCase(MovesMemory.getMoveA()) && 
                 Rules[i].moveB.equalsIgnoreCase(MovesMemory.getMoveB()))
         {
            Rules[i].matched = true;
         }
         
         else
         {
            Rules[i].matched = false;
         }
      }
      
      //Pick the rule with the highest weight.
      for(i=0; i<NUM_OF_RULES; i++)
      {
         if(Rules[i].matched)
         {
            if(ruleToFire == -1)
            {
               ruleToFire = i;
            }
            
            else if(Rules[i].weight > Rules[ruleToFire].weight)
            {
               ruleToFire = i;
            }
         }
      }
      
      //Use the rule.
      if(ruleToFire != -1)
      {
         MovesMemory.setMoveC(Rules[ruleToFire].moveC);
         previousRuleFired = ruleToFire;
         predictedMove = MovesMemory.getMoveC();
         MiniTekken.getMovesLog().append("\n\n Predicted move is " + predictedMove + "!");
         MiniTekken.getMovesLog().append("\n Actual move is " + move + "!");
      }
      
      else
      {
         MovesMemory.setMoveC(unknown);
         previousRuleFired = -1;
      }
      
      //Check if the predicted move is the same as the chosen move.
      if(move.equals(predictedMove))
      {
         successes++;
         
         if(previousRuleFired != -1)
         {
            if(turn)
            {
               MiniTekken.getMovesLog().append("\n\n Move Blocked!\n Try Again!\n\n *****AI Turn*****");
               MiniTekken.getMovesLog().setCaretPosition(MiniTekken.getMovesLog().getDocument().getLength()); 
               Rules[previousRuleFired].weight++;
               MiniTekken.isPlayerTurn = false;
            }
            
            if(!turn)
            {
               MiniTekken.getMovesLog().append("\n\n AI Blocked Move!\n\n *****Player Turn*****");
               MiniTekken.getMovesLog().setCaretPosition(MiniTekken.getMovesLog().getDocument().getLength()); 
               Rules[previousRuleFired].weight++;
               MiniTekken.isPlayerTurn = true;
            }
         }
      }
      
      else
      {
         if(previousRuleFired != -1)
         {
            if(turn)
            {
               if(MiniTekken.getOpponentHealth() != 10)
               {
                  MiniTekken.getMovesLog().append("\n\n Successful Hit!\n\n *****AI Turn*****");
                  MiniTekken.setOpponentHealth(MiniTekken.getOpponentHealth() - 10);
                  MiniTekken.getOppHealthLbl().setText("Opponent Health: " + MiniTekken.getOpponentHealth());
                  MiniTekken.getMovesLog().setCaretPosition(MiniTekken.getMovesLog().getDocument().getLength());
                  Rules[previousRuleFired].weight--;
               }

               else
               {
                  MiniTekken.setWins(MiniTekken.getWins() + 1);
                  MiniTekken.calculateRatio(MiniTekken.getWins(), MiniTekken.getLosses());
                  String s = String.format("%.2f", MiniTekken.getRatio());
                  MiniTekken.getRatioLabel().setText("<html>Wins: " + MiniTekken.getWins() + "<br>Losses: " + MiniTekken.getLosses() +"<br>W/L Ratio: " + s);
                  MiniTekken.setPlayerHealth(50);
                  MiniTekken.setOpponentHealth(50);
                  MiniTekken.getPlayerHealthLbl().setText(MiniTekken.getName() + " Health: " + MiniTekken.getPlayerHealth());
                  MiniTekken.getOppHealthLbl().setText("Opponent Health: " + MiniTekken.getOpponentHealth());
                  MiniTekken.getMovesLog().setText("");
                  MiniTekken.getMovesLog().append(" Fight Won!\n\n Fight!");
                  MiniTekken.wc.runWinClassifier();
                  MiniTekken.numberOfMoves = 0;
                  
                  for(i=0; i<NUM_OF_RULES; i++)
                  {
                     Rules[i].weight = 0;
                  }
               }
               
               if(MiniTekken.getOpponentHealth() == 50)
               {
                  MiniTekken.isPlayerTurn = true;
               }
               
               else
               {
                  MiniTekken.isPlayerTurn = false;
               }
            }
            
            else if(!turn)
            {
               if(MiniTekken.getPlayerHealth() != 10)
               {
                  MiniTekken.getMovesLog().append("\n\n AI Hit Successful!\n\n *****Players Turn*****");
                  MiniTekken.setPlayerHealth(MiniTekken.getPlayerHealth() - 10);
                  MiniTekken.getPlayerHealthLbl().setText(MiniTekken.getName() + " Health: " + MiniTekken.getPlayerHealth());
                  MiniTekken.getMovesLog().setCaretPosition(MiniTekken.getMovesLog().getDocument().getLength());
                  Rules[previousRuleFired].weight--;
               }

               else
               {
                  MiniTekken.setLosses(MiniTekken.getLosses() + 1);
                  MiniTekken.calculateRatio(MiniTekken.getWins(), MiniTekken.getLosses());
                  String s = String.format("%.2f", MiniTekken.getRatio());
                  MiniTekken.getRatioLabel().setText("<html>Wins: " + MiniTekken.getWins() + "<br>Losses: " + MiniTekken.getLosses() +"<br>W/L Ratio: " + s);
                  MiniTekken.setPlayerHealth(50);
                  MiniTekken.setOpponentHealth(50);
                  MiniTekken.getPlayerHealthLbl().setText(MiniTekken.getName() + " Health: " + MiniTekken.getPlayerHealth());
                  MiniTekken.getOppHealthLbl().setText("Opponent Health: " + MiniTekken.getOpponentHealth());
                  MiniTekken.getMovesLog().setText("");
                  MiniTekken.getMovesLog().append(" Fight Lost!\n\n Fight!");
                  MiniTekken.wc.runWinClassifier();
                  MiniTekken.numberOfMoves = 0;
                  
                  for(i=0; i<NUM_OF_RULES; i++)
                  {
                     Rules[i].weight = 0;
                  }
               }
               
               MiniTekken.isPlayerTurn = true;
            }
         }
         
         for(i=0; i<NUM_OF_RULES; i++)
         {
            if(Rules[i].matched && (Rules[i].moveC.equals(move)))
            {
               Rules[i].weight++;
               break;
            }
         }
      }
      
      MovesMemory.setMoveA("unknown");
      MovesMemory.setMoveB("unknown");
      
      return MovesMemory.getMoveC();
   }
}
