/**
 *
 * @author Ash
 */
public class MovesMemory 
{
   public static String moveA, moveB, moveC; //First move, second move, the move to be predicted.
   
   public MovesMemory()
   {
      moveA = "unknown";
      moveB = "unknown";
      moveC = "unknown";
   }
   
   public static void setMoveA(String a)
   {
      moveA = a;
   }
   
   public static void setMoveB(String b)
   {
      moveB = b;
   }
   
   public static void setMoveC(String c)
   {
      moveC = c;
   }
   
   public static String getMoveA()
   {
      return moveA;
   }
   
   public static String getMoveB()
   {
      return moveB;
   }
   
   public static String getMoveC()
   {
      return moveC;
   }
}
