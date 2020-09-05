import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
	/**
	 * This is the main method of the class and it takes in/reads a given file
	 * and checks the cache to determine a hit or miss. It will check each word line
	 * by line to determine if a given word is a hit in the cache. It will check for either
	 * two test cache level one or cache level two. It will also check if a file is valid. If
	 * it or the line entered is incorrect it will print a usage statement.
	 * 
	 * @param args takes in the command line arguments
	 */
	public static void main(String[] args) {
		int cacheSize; //given cache size by the user
		int cacheSizeTwo; //given cache size by the user for second level cache
		double cacheHitOne = 0.0;
		double cacheHitTwo = 0.0;
		double cacheRefOne = 0.0;
		double cacheRefTwo = 0.0;


		if (args.length == 3 || args.length == 4) { 			//check for if there are 3 or 4 arguments
			if (args[0].equals("1") && args.length == 3) { 		// checks for cache test 1 and args length of 3
				cacheSize = Integer.parseInt(args[1]); 		//creates cache size integer for the 2nd argument
				String inputFile = args[2]; 				//creates a file input for the last argument
				File newFile = new File(inputFile); 		//new file for the input file
				try {
					Scanner scnr = new Scanner(newFile); 	//try catch statement for if the file actually exists
					Cache<Object> cache = new Cache<Object>(cacheSize);
					while (scnr.hasNextLine()) {
						String cacheLine = scnr.nextLine();
						String[] cacheWord = cacheLine.split("\\s+"); //Splitting the words by whitespace in an array
						for(int i = 0; i < cacheWord.length; i++) { //going through the array to index at each word
							if(cacheWord[i].isEmpty()) { //if it runs into a word that is empty it will skip
								continue;
							}
							cacheRefOne++;
							if(cache.getObject(cacheWord[i])){ //cacheWord[i] is indexing at that specific word
								cacheHitOne++;
								cache.removeObject(cacheWord[i]);
								cache.addObject(cacheWord[i]);
							}else {
								cache.addObject(cacheWord[i]);
							}
						}
					}
					scnr.close();
				} catch (FileNotFoundException e) {
					System.out.println(newFile + " does not exist");
					e.printStackTrace();
				}
				System.out.println("Cache 1 Hit Ratio: " + cacheHitOne/cacheRefOne);
				System.out.println("Cache 1 Total Hits: " + cacheHitOne);
				System.out.println("Cache 1 Total References: " + cacheRefOne);

			}else if (args[0].equals("2") && args.length == 4) { // checks for cache test 2 and args length of 4				
				cacheSize = Integer.parseInt(args[1]);		 	//creates cache size integer for the 2nd argument
				cacheSizeTwo = Integer.parseInt(args[2]); 		//creates cache size integer for the 3rd argument
				String inputFile = args[3];  					//creates a file input for the last argument
				File newFile = new File(inputFile);				//new file for the input file
				try {
					Scanner scnr = new Scanner(newFile);		//try catch statement for if the file actually exists
					Cache<Object> cacheOne = new Cache<Object>(cacheSize);
					Cache<Object> cacheTwo = new Cache<Object>(cacheSizeTwo);
					while (scnr.hasNextLine()) { //getting whole lines with it
						String cacheLine = scnr.nextLine();
						String[] cacheWords = cacheLine.split("\\s+"); //Splitting the words by whitespace in an array
						for(int i = 0; i < cacheWords.length; i++) {
							if(cacheWords[i].isEmpty()) { //if it runs into a word that is empty it will skip
								continue;
							}
							cacheRefOne++;
							if(cacheOne.getObject(cacheWords[i])){
								cacheHitOne++;
								cacheOne.moveObject(cacheWords[i]);
								cacheTwo.moveObject(cacheWords[i]);
							}else {
								cacheRefTwo++; //increment cache two references
								if(cacheTwo.getObject(cacheWords[i])) {
									cacheHitTwo++;
									cacheTwo.moveObject(cacheWords[i]); //move to top of caches 2
									cacheOne.addObject(cacheWords[i]); //add to top of cache 1
								}else {
									cacheTwo.addObject(cacheWords[i]);
									cacheOne.addObject(cacheWords[i]);

								}
							}
						}
					}
					scnr.close();
				} catch (FileNotFoundException e) {
					System.out.println(newFile + " does not exist");
					e.printStackTrace();
				}
				double globalRefRatio = (cacheHitOne + cacheHitTwo) / cacheRefOne;
				System.out.println("Global references: " + cacheRefOne);
				System.out.println("Global cache hits: " + (cacheHitOne + cacheHitTwo));
				System.out.println("Global hit ratio " + globalRefRatio);
				System.out.println("---------------------------------------------");
				System.out.println("Cache Level 1 references " + cacheRefOne);
				System.out.println("Cache Level 1 hits " + cacheHitOne);
				System.out.println("Cache Level 1 ratio " + cacheHitOne/cacheRefOne);
				System.out.println("---------------------------------------------");
				System.out.println("Cache Level 2 references " + cacheRefTwo);
				System.out.println("Cache Level 2 hits " + cacheHitTwo);
				System.out.println("Cache Level 2 ratio " + cacheHitTwo/cacheRefTwo);
			}else {
				printUsage();
				System.exit(1);
			}
		}else {
			printUsage();
			System.exit(1);
		}

	}
	private static void printUsage() {
		System.out.println("Invalid Usage: $ java Test [1] [cache size] [filename]");
		System.out.println("or");
		System.out.println("Invalid Usage: $ java Test [2] [cache 1 size] [cache 2 size] [filename]");
	}

}
