import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

	static int tableSize = (new Prime()).getPrime();
	static int inputNum;
	static int debugNum;
	static int insertions = 0;
	static int insertUnique = 0;
	static double loadFactor;

	public static void main(String[] args) throws IOException {

		if(tableSize < 0) {
			System.out.println("Prime table size could not be found");
			System.exit(1);
		}
		try {
			inputNum = Integer.parseInt(args[0]);
			loadFactor = Double.parseDouble(args[1]);
			if (args.length == 3) {
				debugNum = Integer.parseInt(args[2]);
			}else {
				debugNum = 0;
			}
		}catch(NumberFormatException e) {
			printUsage();
			System.exit(1);
		}
		if (debugNum != 1 && debugNum != 0) {
			printUsage();
			System.exit(1);
		}

		if (args.length < 2)
		{
			printUsage();
			System.exit(1);
		}else if (args.length > 4) {
			printUsage();
			System.exit(1);	
		}else{
			if(inputNum == 1) { //random
				
				if(loadFactor > 1 || loadFactor < 0) {
					printUsage();
					System.exit(1);
				}
				HashTable<Integer> linear = new HashTable<Integer>(tableSize);
				HashTable<Integer> doubleTable = new HashTable<Integer>(tableSize);
				InsertInts(linear,doubleTable);
				SummaryReport(linear, doubleTable);
				if (debugNum == 1) {
					FileWriter(linear, doubleTable);
				}
			}else if(inputNum == 2) { //long currentTimeMillis

				if(loadFactor > 1 || loadFactor < 0) {
					printUsage();
					System.exit(1);
				}
				HashTable<Long> linear = new HashTable<Long>(tableSize);
				HashTable<Long> doubleTable = new HashTable<Long>(tableSize);
				InsertLongs(linear,doubleTable);
				SummaryReport(linear, doubleTable);
				if (debugNum == 1) {
					FileWriter(linear, doubleTable);
				}
			}else if(inputNum == 3){ //word file
				if(loadFactor > 1 || loadFactor < 0) {
					printUsage();
					System.exit(1);
				}
				HashTable<String> linear = new HashTable<String>(tableSize);
				HashTable<String> doubleTable = new HashTable<String>(tableSize);
				InsertStrings(linear,doubleTable);
				SummaryReport(linear, doubleTable);
				if (debugNum == 1) {
					FileWriter(linear, doubleTable);
				}
			}
		}
		
	}


	
	private static int InsertInts(HashTable<Integer> linear, HashTable<Integer> doubleTable) {
		Random rand = new Random();
		while( (double) insertUnique / tableSize < loadFactor) {
			int i = rand.nextInt(); //returns the next random integer
			HashObject<Integer> linearRand = new HashObject<Integer>(i);
			HashObject<Integer> doubleRand = new HashObject<>(i);
			int linearInt = linear.linearHash(linearRand);
			int doubleInt = doubleTable.DoubleHash(doubleRand);
			insertions++;
			if(linearInt >= 0) { //only have to do one of the checks because if there is a duplicate in linear then we know that double has the same parameters
				insertUnique++;
			}
		}
		return insertUnique;
	}
	private static int InsertLongs(HashTable<Long> linear, HashTable<Long> doubleTable) {
		while((double) insertUnique / tableSize < loadFactor) {
			long i = System.currentTimeMillis(); //returns the next random integer
			HashObject<Long> linearL = new HashObject<>(i);
			HashObject<Long> doubleL = new HashObject<>(i);
			long linearLong = linear.linearHash(linearL);
			long doubleLong = doubleTable.DoubleHash(doubleL);
			insertions++;
			if(linearLong >= 0) { //only have to do one of the checks because if there is a duplicate in linear then we know that double has the same parameters
				insertUnique++;
			}
		}
		return insertUnique;
	}
	private static int InsertStrings(HashTable<String> linear, HashTable<String> doubleTable) {
		File argFile = new File("word-list");
		Scanner contentScanner = null;
		try {
			contentScanner = new Scanner(argFile);
		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
			System.exit(1);
		}
		while((double) insertUnique / tableSize < loadFactor) {
			String i = contentScanner.nextLine();

			HashObject<String> linearString = new HashObject<>(i);
			HashObject<String> doubleString = new HashObject<>(i);
			long linearS = linear.linearHash(linearString);
			long doubleS = doubleTable.DoubleHash(doubleString);
			insertions++;
			if(linearS >= 0) { //only have to do one of the checks because if there is a duplicate in linear then we know that double has the same parameters
				insertUnique++;
			}
		}
		return insertUnique;
	}

	public static void FileWriter(HashTable<?> linear, HashTable<?> doubleTable) {
		try {
			File linearFile = new File("linear-dump");
			File doubleFile = new File("double-dump");
			if(linearFile.exists()) {
				linearFile.delete();
			}
			if(doubleFile.exists()){
				doubleFile.delete();
			}

			FileWriter writerLinear = new FileWriter(linearFile,true);
			FileWriter writerDouble = new FileWriter(doubleFile,true);
			BufferedWriter linearWrite = new BufferedWriter(writerLinear);
			BufferedWriter doubleWrite = new BufferedWriter(writerDouble);
			linearWrite.write(linear.toString());
			doubleWrite.write(doubleTable.toString());

			linearWrite.close();
			doubleWrite.close();	
		}catch (FileNotFoundException e){
			System.out.println("File was not found");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error occurred in creating file");
			System.exit(1);
		}
	}
	public static void SummaryReport(HashTable<?> linear, HashTable<?> doubleTable) {
		String dataSource = null;
		switch(inputNum) {
		case 1: dataSource = "Random Integers";
		break;
		case 2: dataSource = "Current Time";
		break;
		case 3: dataSource = "word-list";
		}
		System.out.println("A good table size was found: " + tableSize);
		System.out.println("Data source type: " + dataSource);
		System.out.println("");
		System.out.println("Using Linear Hashing....");
		System.out.println("Input " + insertions + " of which " + linear.getDuplicateValues() + " duplicates");
		System.out.println("load factor = " + loadFactor + ", Avg. no. of probes " + ( (double) linear.getProbeValues()/insertUnique));
		System.out.println("");
		System.out.println("Using Double Hashing....");
		System.out.println("Input " + insertions + " of which " + doubleTable.getDuplicateValues() + " duplicates" );
		System.out.println("load factor = " + loadFactor + ", Avg. no. of probes " + ((double) doubleTable.getProbeValues() /insertUnique));
	}


	private static void printUsage() {
		System.out.println
		("Usage: java HashTest <input type> <load factor> [<debug level>]");
		System.out.println
		("<input type>: Data Source to be hasshed the values of data sources are 1, 2, 3");
		System.out.println
		("<load factor>: varried number between 0 and 1");
		System.out.println
		("[<debug level>]: 0 or 1, 0: prints summary to console, 1: prints summary to console and dumps to a file with hash, duplicates and probes "); 
	}


}
