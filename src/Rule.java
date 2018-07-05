/**
 *
 * @author Ash
 */
public class Rule 
{
   boolean matched;
   int weight;
   
   String moveA, moveB, moveC;
   
   public Rule()
   {
      matched = false;
      weight = 0;
   }
   
   public void setRule(String a, String b, String c)
   {
      moveA = a;
      moveB = b;
      moveC = c;
   }
}
