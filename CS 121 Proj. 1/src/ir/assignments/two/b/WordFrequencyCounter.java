//Stephen Em 33819914

package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
		// TODO Write body!
		
		List<Frequency> list = new ArrayList<Frequency>();
		List<String> found = new ArrayList<String>();
		
		for (String i : words) {
			if (!found.isEmpty() && found.contains(i)) {
				list.get(found.indexOf(i)).incrementFrequency();
			}
			else {
				found.add(i);
				Frequency word = new Frequency(i, 1);
				list.add(word);
			}
		}
		Collections.sort(list, new FreqComparator());
		return list;
	}
	
	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
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
