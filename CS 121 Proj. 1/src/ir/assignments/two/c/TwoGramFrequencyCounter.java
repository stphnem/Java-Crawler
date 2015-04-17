package ir.assignments.two.c;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Count the total number of 2-grams and their frequencies in a text file.
 */
public final class TwoGramFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private TwoGramFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique 2-gram in the original list. The frequency of each 2-grams
	 * is equal to the number of times that two-gram occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied 2-grams sorted
	 * alphabetically. 
	 * 
	 * 
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["you", "think", "you", "know", "how", "you", "think"]
	 * 
	 * The output list of 2-gram frequencies should be 
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of two gram frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computeTwoGramFrequencies(ArrayList<String> words) {
		// TODO Write body!
		
		List<Frequency> list = new ArrayList<Frequency>();
		List<String> found = new ArrayList<String>();
		
		for (int i = 0; i < words.size()-1; i++) {
			String newWord = words.get(i) + " " + words.get(i+1);
			if (!found.isEmpty() && found.contains(newWord)) {
				list.get(found.indexOf(newWord)).incrementFrequency();
			}
			else {
				found.add(newWord);
				Frequency word = new Frequency(newWord, 1);
				list.add(word);
			}
		}
		
		Collections.sort(list, new FreqComparator());
		return list;
	}

	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}

class FreqComparator implements Comparator<Frequency> {
	@Override
	public int compare(Frequency w1, Frequency w2) {
		int word1 = w1.getFrequency();
		int word2 = w2.getFrequency();
 
		if (word1 < word2) {
			return 1;
		} else if (word1 > word2) {
			return -1;
		} else {
			return w1.getText().compareTo(w2.getText());
		}
	}
}

 
