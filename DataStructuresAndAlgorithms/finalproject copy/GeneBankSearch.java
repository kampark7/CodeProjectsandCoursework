/*
 * Purpose:
 *   Search GeneBank information in a BTree.
 *
 * @author Noel Noel
 * @version 1.0.0
 * @bsu_class 2020 Spring, Hoda Mehrpouyan, CS321-3
 *
 * Submit assignment using:  submit HODAMEHRPOUYAN CS321-3-s20 p4
 *
 */

import java.lang.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class GeneBankSearch {
   public static void main(String[] args) {
      int DEBUG        = 0;
      int cacheEnabled = 0;
      int cacheSize    = 0;
      String btreeFile = "";
      String queryFile = "";

      if (args.length == 0 || args.length < 3 || args[0] == "-help" || args[0] == "--help") {
         System.out.println("Usage:  <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
         System.exit(1);
      }

      //####################################################################################################//
      // Handler command line arguments
      //####################################################################################################//
      for (int i = 0; i < args.length; i++) {
         if (i == 0) {               // with or without cache
            if (MyCommonNum.isNumberInt(args[i]) == true) {
               cacheEnabled = MyCommonNum.changeToInt(args[i]);
               if (cacheEnabled < 0 || cacheEnabled > 1) {
                  System.out.println("ERROR: Invalid choice for cache type.  Please use 0 or 1 only");
                  System.exit(1);
               }
            } else {
               System.out.println("ERROR: Bad cache input value, valid choices are 0 or 1, not " + args[i] + ".");
               System.exit(1);
            }
         } else if (i == 1) {        // BTree file
            btreeFile = args[i];     // Check on the existence of the file later
         } else if (i == 2) {        // Query file
            queryFile = args[i];     // Check on the existence of the file later
         } else if (i == 3 && cacheEnabled == 1) {        // cache size, optional
            try {
               cacheSize = MyCommonNum.changeToInt(args[i]);
               if (cacheSize < 1) {                       // TODO: Maybe use a different floor greater than 1.  Maybe 100???
                  System.out.println("ERROR: Invalid cache size value.  Must be a positive number of 1 or greater.");
                  System.exit(1);
               }
            } catch (Exception e) {
               System.out.println("ERROR: Bad cache size input. Value must be a valid positive integer, not " + args[i] + ".");
               System.exit(1);
            }
         } else if ( (i == 3 && cacheEnabled == 0) || (i == 4 && cacheEnabled == 1) ) {        // debug, optional
            if (MyCommonNum.isNumberInt(args[i]) == true ) {
               DEBUG = MyCommonNum.changeToInt(args[i]);
               if (DEBUG < 0 || DEBUG > 1) {
                  System.out.println("ERROR: Invalid choice for debugging.  Please use 0 or 1 only");
                  System.exit(1);
               }
            } else {
               System.out.println("ERROR: Bad input, valid choices are 0, or 1, not " + args[i] + ".");
               System.exit(1);
            }
         } else {
            System.out.println("ERROR: Unknown/bad input of " + args[i] + ".");
            System.exit(1);
         }
      }


      // TODO: 
      //   1) Check if btreeFile and queryFile exists, has size, and is not a directory
      //   2) Create BTree object.
      //



   }
}
