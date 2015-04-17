//Stephen Em 33819914

package ir.assignments.two.a;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
	public static ArrayList<String> tokenizeFile(File input) {
		// TODO Write body!
		String line;
		try {
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> list = new ArrayList<String>();

			String text = "";
			while ((line = br.readLine()) != null) {
				text += line + " ";
			}
			String[] tokens = text.replaceAll("[^A-Za-z0-9 ]", "").split(" ");
			
			for(String token : tokens) {
				String newToken = token.toLowerCase();
				list.add(newToken);
			}
			
			br.close();
			return list;
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println("Error reading file");
		}
		
		return null;
	}
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		// TODO Write body!
		int total = 0;
		int num = getNum(frequencies.get(0));
		String data = "";
		
		for (Frequency i : frequencies) {
			total += i.getFrequency();
			data += String.format("%-15s %d\n", i.getText(), i.getFrequency());
		}
		
		if (num > 1) {
			System.out.printf("%s%s%s", 
							  String.format("Total %d-gram count: %d\n", num, total),
					          String.format("Unique %d-gram count: %d\n\n", num, frequencies.size()),
					          data);
		}
		else {
			System.out.printf("%s%s%s", 
					  String.format("Total item count: %d\n", total),
			          String.format("Unique item count: %d\n\n", frequencies.size()),
			          data);
		}
	}
	
	public static int getNum(Frequency f) {
		//Get the number of words in string
		
		return f.getText().split("\\s+").length;
	}
}
