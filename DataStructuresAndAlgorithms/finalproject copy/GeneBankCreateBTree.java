/*
 * Purpose:
 *   Creates a BTree from a given GeneBank file
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

public class GeneBankCreateBTree {
   public static void main(String[] args) {
      int DEBUG        = 0;
      int cacheEnabled = 0;
      int cacheSize    = 0;
      int degree       = 0;
      int seqLen       = 0;
      String gbkFile   = "";

      if (args.length == 0 || args.length < 4 || args[0] == "-help" || args[0] == "--help") {
         System.out.println("Usage:  <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
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
         } else if (i == 1) {        // degree
            try {
               degree = MyCommonNum.changeToInt(args[i]);
               if (degree < 0) {
                  System.out.println("ERROR: Invalid degree value.  Must be a positive number.");
                  System.exit(1);
               }
            } catch (Exception e) {
               System.out.println("ERROR: Bad degree input. Value must be a valid positive integer, not " + args[i] + ".");
               System.exit(1);
            }
         } else if (i == 2) {        // gbk file
            gbkFile = args[i];       // Check on the existence of the file later
         } else if (i == 3) {        // sequence length
            try {
               seqLen = MyCommonNum.changeToInt(args[i]);
               if (seqLen < 1 || seqLen > 31) {
                  System.out.println("ERROR: Invalid sequence length value.  Must be a positive integer between 1 and 31.");
                  System.exit(1);
               }
            } catch (Exception e) {
               System.out.println("ERROR: Bad sequence length input. Value must be a valid positive integer, not " + args[i] + ".");
               System.exit(1);
            }
         } else if (i == 4 && cacheEnabled == 1) {        // cache size, optional
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
         } else if ( (i == 4 && cacheEnabled == 0) || (i == 5 && cacheEnabled == 1) ) {        // debug, optional
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
      //   1) Check if gbkFile exists, has size, and is not a directory
      //   2) If the degree is zero, then calculate optimum degree based on 4096 bytes.
      //   3) Create BTree object.
      //   4) Read data from gbk file.  Note the meta data as the top of file, tells you how much data to expect.
      //     a) $ grep 5028 BTree/data/test1.gbk
      //        LOCUS       SCU49845                5028 bp    DNA     linear   PLN 21-JUN-1999
      //        REFERENCE   1  (bases 1 to 5028)
      //        REFERENCE   2  (bases 1 to 5028)
      //        REFERENCE   3  (bases 1 to 5028)
      //             source          1..5028
      //     b) $ tail -87 BTree/data/test1.gbk |head -84|sed "s/ *[0-9]\+ //"|sed "s/ //g" |perl  -ne 's/\n//g;print'|wc -c
      //        5028
      //     c) Create TreeObject from data.
      //     d) Add TreeObject to BTree object.


   }
}
