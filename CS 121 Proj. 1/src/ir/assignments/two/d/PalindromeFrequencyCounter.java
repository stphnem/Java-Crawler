package ir.assignments.two.d;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
		// TODO Write body!
		// You will likely want to create helper methods / classes to help implement this functionality
		List<Frequency> list = new ArrayList<Frequency>();
		List<String> found = new ArrayList<String>();
		
		String palin = "";
		for (String i : words) {
			if (palin == "")
				palin += i;
			else
				palin += " " + i;
			if (isPalindrome(palin)) {
				if (!found.isEmpty() && found.contains(palin)) {
					list.get(found.indexOf(palin)).incrementFrequency();
				}
				else {
					found.add(palin);
					Frequency word = new Frequency(palin, 1);
					list.add(word);
					palin = "";
				}
			}
		}
		Collections.sort(list, new FreqComparator());
		return list;
	}
	
	private static boolean isPalindrome(String word) {
		//Returns true if palindrome 
		
		String reversed = reverseWord(word);
		return word.replaceAll(" ", "").equals(reversed);
	}
	
	private static String reverseWord(String word) {
		//Strip spaces and return reverse of word
		
		String reversed = new StringBuilder(word).reverse().toString();
		return reversed.replaceAll(" ", "");
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}

class FreqComparator implements Comparator<Frequency> {
	@Override
	public int compare(Frequency w1, Frequency w2) {
		String word1 = w1.getText();
		String word2 = w2.getText();
 
		if (word1.split("\\s+").length < word2.split("\\s+").length)
			return 1;
		else if (word1.split("\\s+").length > word2.split("\\s+").length) 
			return -1;
		else if (w1.getFrequency() < w2.getFrequency()) 
			return 1;
		else if (w1.getFrequency() > w2.getFrequency()) 
			return -1;
		else 
			return w1.getText().compareTo(w2.getText());
	}
}
