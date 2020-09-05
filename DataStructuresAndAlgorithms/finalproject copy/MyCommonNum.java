/**
 * Purpose:
 *   Provide a string to number library, so to prevent duplicating code over
 *   and over for all assignments.
 *
 * @author Noel Noel
 * @version 1.2.0
 * @bsu_class 2020 Spring
 *
 */

import java.lang.NumberFormatException;

public class MyCommonNum {

   /**
    * Simple check to test if a String object is a number or not.
    * 
    * @param text      Pass in a string to test if it's a number
    * @return boolean	Return true if is a number and false if not.
    */
   public static boolean isNumberInt (String text) {
      try {
         Integer.parseInt(text);				// Throws an exception if text is not a number.  Data type mis-match...
         return true;
      } catch (Exception e) {	}

      return false;
   }

   /**
    * The function will take a string text value and try and convert it to a 
    * double number value.  If successful returns the number, else throws 
    * a number format exceptions (means its not a number value).
    * 
    * @author Noel Noel
    * 
    * @since 1.2.0
    * @param string
    * @return
    * @throws
    * 
    */
   public static int changeToInt(String string) throws NumberFormatException {
      int number = 0;
      try {
         number = Integer.parseInt(string);
      } catch (Exception e) {                   // Any error, return number format exception
         throw new NumberFormatException(e.getMessage());
      }
      return number;
   }

   /**
    * The function will take a string text value and try and convert it to a 
    * double number value.  If successful returns the number, else throws 
    * a number format exceptions (means its not a number value).
    * 
    * @author Noel Noel
    * 
    * @param string
    * @return
    * @throws
    * 
    */
   public static double changeToDouble(String string) throws NumberFormatException {
      double number = 0;
      try {
         number = Double.parseDouble(string);
      } catch (Exception e) {                   // Any error, return number format exception
         throw new NumberFormatException(e.getMessage());
      }
      return number;
   }

   /**
    * A simple method that checks if a string is a valid number to be used 
    * with the magic square.
    * 
    * Natural odd numbers, IE: 3, 5, 7, 9, 11, ...
    * 
    * @param strNum     A string that might contain a number, whole numbers without any decimal points (not even 1.0)
    * @return           If its a valid number returns TRUE, otherwise FALSE.
    */
   public static boolean isNaturalOddNum(String strNum) {
      if ( strNum.matches("^\\d+$") ) {
         int size = Integer.parseInt(strNum);
         if ((size >= 3 && size % 2 == 1) ) {
            return(true);
         }
      }
      return(false);
   }
}
