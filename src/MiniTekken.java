import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

/**
 * 
 * @author Ash
 */
public class MiniTekken implements ActionListener
{
   private JFrame mainFrame;
   private JPanel topPanel, textPanel, infoPanel, buttonsPanel;
   private static JTextArea mainTextArea;
   private JButton buttonHighkick, buttonLowkick, buttonPunch;
   private static JLabel infoLbl, pHealthLbl, opHealthLbl, ratioLbl, predictionLbl;
   public static String name;
   private static int playerHealth = 50, opponentHealth = 50;
   private String move;
   public static int numberOfMoves = 0; //Used to count how many moves the user has made as part of a combo.
   private static int wins, losses;
   private static double ratio;
   public static Boolean isPlayerTurn = true;
   private static PredictionSystem ps, ops;
   public static WinClassifier wc;
   private String[] moves = {"HighKick", "LowKick", "Punch"};
   
   public MiniTekken()
   {
      initGUI();
   }
   
   public static void main(String[] args) 
   {
      MiniTekken miniTekken = new MiniTekken();
      
      name = getNameDialog();
      pHealthLbl.setText(name + " Health: " + playerHealth);
      mainTextArea.append(" Fight!");
      ps = new PredictionSystem();
      ps.init();
      ops = new PredictionSystem();
      ops.init();
      wc = new WinClassifier();
      wc.runWinClassifier();
   }
   
   public void actionPerformed(ActionEvent e)
   {
      if(e.getSource() == buttonHighkick)
      {
         move = "HighKick";
         numberOfMoves++;
         ps.processMove(move, isPlayerTurn);
            
         if(numberOfMoves == 3)
         {
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            numberOfMoves = 0;
         }
      }
      
      if(e.getSource() == buttonLowkick)
      {
         move = "LowKick";
         numberOfMoves++;
         ps.processMove(move, isPlayerTurn);
            
         if(numberOfMoves == 3)
         {
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            numberOfMoves = 0;
         }
      }
      
      if(e.getSource() == buttonPunch)
      {
         move = "Punch";
         numberOfMoves++;
         ps.processMove(move, isPlayerTurn);
            
         if(numberOfMoves == 3)
         {
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            ops.processMove(chooseRandomMove(), isPlayerTurn);
            numberOfMoves = 0;
         }
      }
   }
   
   private void initGUI()
   {
      Border empty = new EmptyBorder(10, 10, 10, 10);
      Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
      
      mainFrame = new JFrame("Mini Tekken");
      mainFrame.setSize(500,500);
      mainFrame.setLayout(new GridLayout(2,1));
      
      topPanel = new JPanel();
      topPanel.setBorder(empty);
      topPanel.setLayout( new GridLayout(1,2));
      
      textPanel = new JPanel();
      textPanel.setBorder(blackBorder);
      textPanel.setLayout( new GridLayout(1,1));
      
      infoPanel = new JPanel();
      infoPanel.setBorder(empty);
      infoPanel.setLayout( new GridLayout(5,1));
      
      buttonsPanel = new JPanel();
      buttonsPanel.setLayout( new GridLayout(1,3));
      
      mainTextArea = new JTextArea();
      mainTextArea.setEditable(false);
      JScrollPane scroll = new JScrollPane(mainTextArea);
      
      infoLbl = new JLabel("<html>Mini Tekken<br> How to play:<br> Press a three button combo to fight."
              + "<br> The computer will try to guess your last move."
              + "<br> If it guesses correctly you won't deal any damage.</html>");
      pHealthLbl = new JLabel("Player Health: " + playerHealth);
      opHealthLbl = new JLabel("Opponent Health: " + opponentHealth);
      String s = String.format("%.2f", ratio);
      ratioLbl = new JLabel("<html>Wins: " + wins + "<br>Losses: " + losses +"<br>W/L Ratio: " + s);
      predictionLbl = new JLabel();
      
      buttonHighkick = new JButton("High Kick");
      buttonHighkick.addActionListener(this);
      buttonLowkick = new JButton("Low Kick");
      buttonLowkick.addActionListener(this);
      buttonPunch = new JButton("Punch");
      buttonPunch.addActionListener(this);
      
      
      textPanel.add(scroll);
      infoPanel.add(infoLbl);
      infoPanel.add(pHealthLbl);
      infoPanel.add(opHealthLbl);
      infoPanel.add(ratioLbl);
      infoPanel.add(predictionLbl);
      topPanel.add(textPanel);
      topPanel.add(infoPanel);
      buttonsPanel.add(buttonHighkick);
      buttonsPanel.add(buttonLowkick);
      buttonsPanel.add(buttonPunch);
      mainFrame.add(topPanel);
      mainFrame.add(buttonsPanel);
      mainFrame.pack();
      mainFrame.setVisible(true);
      mainFrame.setResizable(false);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
   public static String getNameDialog()
   {
      String name = "Player";
      String[] options = {"OK"};
      JPanel panel = new JPanel();
      JLabel lbl = new JLabel("What is your name? ");
      JTextField txt = new JTextField(15);
      panel.add(lbl);
      panel.add(txt);
      int selectedOption = JOptionPane.showOptionDialog(null, panel, "Enter you name.", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
      
      if(selectedOption == 0)
      {
         name = txt.getText();
         return name;
      }
      
      return name;
   } 
   
   private String chooseRandomMove() 
   {
      String m = moves[(int)(Math.random() * moves.length)];
      return m;
   }
   
   public static JTextArea getMovesLog()
   {
      return mainTextArea;
   }
   
   public static JLabel getOppHealthLbl()
   {
      return opHealthLbl;
   }
   
   public static JLabel getPlayerHealthLbl()
   {
      return pHealthLbl;
   }
   
   public static JLabel getPredictionLbl()
   {
      return predictionLbl;
   }
   
   public static JLabel getRatioLabel()
   {
      return ratioLbl;
   }
   
   public static int getPlayerHealth()
   {
      return playerHealth;
   }
   
   public static int getOpponentHealth()
   {
      return opponentHealth;
   }
   
   public static int getWins()
   {
      return wins;
   }
   
   public static int getLosses()
   {
      return losses;
   }
   
   public static double getRatio()
   {
      return ratio;
   }
   
   public static void setOpponentHealth(int health)
   {
      opponentHealth = health;
   }
   
   public static void setPlayerHealth(int health)
   {
      playerHealth = health;
   }
   
   public static void setWins(int w)
   {
      wins = w;
   }
   
   public static void setLosses(int l)
   {
      losses = l;
   }
   
   public static void calculateRatio(int w, int l)
   {
      if(w == 0 || l ==0)
      {
         ratio = 0;
      }
      
      else
      {
         ratio = (float)w / l;
      }
   }
   
   public static String getName()
   {
      return name;
   }
}
