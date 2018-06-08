package plagiarismdetector.impl;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

import plagiarismdetector.IPlagiarismDetector;

public class PlagiarismDetector implements IPlagiarismDetector {

	private int ngramSize;
	private List<String> fileNames = new ArrayList<String>();
	private SparseGrid grid = new SparseGrid();
	private Map<String, Set<String>> ngrams = new HashMap<String, Set<String>>();
	private Map<String, Map<String, Integer>> connections = new HashMap<String, Map<String, Integer>>();

	public PlagiarismDetector(int n) {
		this.ngramSize = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plagiarismdetector.IPlagiarismDetector#getN()
	 */
	@Override
	public int getN() {
		return this.ngramSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plagiarismdetector.IPlagiarismDetector#getFilenames()
	 */
	@Override
	public Collection<String> getFilenames() {
		return this.fileNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plagiarismdetector.IPlagiarismDetector#getNumNGramsInCommon(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
		return grid.connectionStrength(file1, file2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plagiarismdetector.IPlagiarismDetector#getNgramsInFile(java.lang.String)
	 */
	@Override
	public Collection<String> getNgramsInFile(String filename) {
		//System.out.println(ngrams);
		return ngrams.get(filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plagiarismdetector.IPlagiarismDetector#getNumNgramsInFile(java.lang.String
	 * )
	 */
	@Override
	public int getNumNgramsInFile(String filename) {
		//System.out.println(ngrams);
		int res = ngrams.get(filename).size();
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plagiarismdetector.IPlagiarismDetector#processDirectory(java.io.File)
	 */
	@Override
	public void readFilesInDirectory(File dir) throws IOException {
		// iterate through all the files
		for (File f : dir.listFiles()) {

			Scanner scan = new Scanner(new FileInputStream(f));

			this.fileNames.add(f.getName());

			Set<String> set = new HashSet<>();

			String ngram = "";

			for (int i = 0; i < this.ngramSize - 1; i++) {
				ngram += scan.next() + " ";
			}

			ngram = ngram.trim();

			while (scan.hasNext()) {
				ngram += " " + scan.next();
				set.add(ngram);
				ngram = ngram.substring(ngram.indexOf(' ') + 1);
			}

			this.ngrams.put(f.getName(), set);
		}
		//System.out.println(this.ngrams);

		// compare all files
		for (String file1 : fileNames) {
			for (String file2 : fileNames) {
				if (!file1.equals(file2)) {
					Set<String> set1 = new HashSet<String>(ngrams.get(file1));
					Set<String> set2 = new HashSet<String>(ngrams.get(file2));
					set1.retainAll(set2);
					int n = set1.size();
					grid.addConnection(file1, file2, n);
				}
			}
		}

		//System.out.println(this.ngrams);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plagiarismdetector.IPlagiarismDetector#getSuspiciousPairs(int)
	 */
	@Override
	public Collection<String> getSuspiciousPairs(int minNgrams) {
		System.out.println(grid.getMinConnections(minNgrams));
		return grid.getMinConnections(minNgrams);
	}

}
