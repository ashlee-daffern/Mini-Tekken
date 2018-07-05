import java.util.Random;

/**
 *
 * @author Ash
 */
public class WinClassifier
{
   private static double m;
   private static double p;
   private static final int NUM_OF_FEATURES = 4;  //Power, Speed, Strength, Poise
   private static final int TRAINING_SIZE = 10;
   private static final int NUM_OF_CATEGORIES = 2; //Win or Lose
   private static final int TEST_SIZE = 1;
   private static Random rand = new Random();
   
   private static String trainingData[][] = {
   // Power   Speed   Strength Poise   win/lose  
      {"high", "high", "high", "low", "win"},
      {"low", "high", "low", "low", "lose"},
      {"high", "low", "low", "high", "lose"},
      {"high", "low", "high", "high", "win"},
      {"low", "low", "low", "high", "lose"},
      {"high", "high", "low", "low", "win"},
      {"low", "high", "low", "low", "lose"},
      {"high", "low", "high", "high", "win"},
      {"high", "high", "low", "high", "win"},
      {"low", "low", "high", "low", "lose"}
   };
    
   private static String[][] testData = new String[1][4];
    
   public WinClassifier() 
   {
      m = 2.0;
      p = 0.5;
   }
   
   public void runWinClassifier()
   {
      double results[] = new double[NUM_OF_CATEGORIES];
      String categories[] = {"win", "lose"};
        
      testData = createTestData();
      
      for (int k=0; k<TEST_SIZE; k++)
      {
         for (int i=0; i<NUM_OF_CATEGORIES; i++)
         {
            results[i] = calculateWinningChance(testData[k], categories[i]);
         }
            
         double max = -1000.0;
         int max_position = -1;
            
         for (int i=0; i<NUM_OF_CATEGORIES; i++)
         {
            if (results[i]> max)
            { 
               max_position = i;
               max = results[i];
            }
         }
            
         MiniTekken.getPredictionLbl().setText("I predict you will " + categories[max_position] + " this match.");
         System.out.println("You will " + categories[max_position] + " this next match!");     
      }
   }
   
   public double calculateWinningChance(String[] test, String category) 
   {
      int count[] = new int[NUM_OF_FEATURES];
        
      for (int i=0; i<NUM_OF_FEATURES; i++)
      {
         count[i]=0;
      }
        
      double probCategory = 0.0;
      int numCategory = 0;
        
      for(int j=0; j<TRAINING_SIZE; j++) 
      {
         if (category.equals(trainingData[j][NUM_OF_FEATURES]))
         {
            numCategory ++;
         }
      }
        
      probCategory = (double)numCategory / (double)TRAINING_SIZE;
        
      for(int i=0; i<NUM_OF_FEATURES; i++)
      {
         for (int j=0; j<TRAINING_SIZE; j++)
         {
            if((test[i].equals(trainingData[j][i])) && (category.equals(trainingData[j][NUM_OF_FEATURES])))
            {
               count[i] ++;
            }
         }
                
         probCategory *= ((double)count[i]+ m * p) / ((double)NUM_OF_CATEGORIES +m);       
      }
        
      return probCategory;
   }  
   
   public static String[][] createTestData()
   {
      int min = 1;
      int max = 100;
      int power, speed, strength, poise;
      String strPower, strSpeed, strStrength, strPoise;
      
      power = rand.nextInt((max-min) + 1) + min;
      speed = rand.nextInt((max-min) + 1) + min;
      strength = rand.nextInt((max-min) + 1) + min;
      poise = rand.nextInt((max-min) + 1) + min;
      
      System.out.println(power + " " + speed + " " + strength + " " + poise);
      
      strPower = highLowChooser(power);
      strSpeed = highLowChooser(speed);
      strStrength = highLowChooser(strength);
      strPoise = highLowChooser(poise);
      
      System.out.println(strPower + " " + strSpeed + " " +strStrength + " " + strPoise);
      
      testData[0][0] = strPower;
      testData[0][1] = strSpeed;
      testData[0][2] = strStrength;
      testData[0][3] = strPoise;
      
      return testData;
   }
   
   public static String highLowChooser(int i)
   {
      int range = 50;
      String value;
      
      if(i <= range)
      {
         value = "low";
      }
      
      else
      {
         value = "high";
      }
      
      return value;
   }  
}
