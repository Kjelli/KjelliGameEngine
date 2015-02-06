package no.kjelli.julekalender.luke5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BigNumber {
	private static final int SET_LIMIT_MAX = 362880;
	private static final int SET_LIMIT = SET_LIMIT_MAX;
	private static final String BIGNUM_FILENAME = "bignum.txt";
	private static final String PRIMES_FILENAME = "primes.txt";
	private static final String LARGEST_PRIMES_FILENAME = "largest.txt";
	private static final int MAX_PRIME = 35000;

	private static ArrayList<Long> primes = new ArrayList<Long>();
	private static ArrayList<Long> bigNumbers = new ArrayList<Long>();
	private static HashMap<Long, Long> largestPrimeFactors = new HashMap<Long, Long>();

	private static final int[] valueset = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	private static ArrayList<Long> generateBigNumbers() {
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (Integer i : valueset) {
			values.add(i);
		}
		while (map.size() < SET_LIMIT) {
			Collections.shuffle(values);
			long n = assemble(values);
			if (map.get(n) == null)
				map.put(n, 1);
			else
				continue;
		}

		return new ArrayList<Long>(map.keySet());
	}

	private static long assemble(ArrayList<Integer> values) {
		StringBuilder b = new StringBuilder();
		for (int i : values) {
			b.append(i);
		}
		return Long.parseLong(b.toString());
	}

	private static void writeBigNumberFile(ArrayList<Long> list) {
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new FileWriter(BIGNUM_FILENAME));
			for (Long b : list) {
				fw.write(b.toString());
				fw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done writing " + list.size() + " lines!");
	}

	private static HashMap<Long, Long> findSmallestLargestPrimeFactor() {
		HashMap<Long, Long> largestPrimes = new HashMap<Long, Long>();
		for (long l : bigNumbers) {
			long prime = findLargestPrimeFactor(l);
			largestPrimes.put(l, prime);
		}
		return largestPrimes;
	}

	private static void writeLargestPrimes() {
		BufferedWriter fw = null;
		List<Long> keys = getLargestPrimeFactorsSortedKeys();
		try {
			fw = new BufferedWriter(new FileWriter(LARGEST_PRIMES_FILENAME));
			for (Iterator<Long> i = keys.iterator(); i.hasNext();) {
				Long l = i.next();
				fw.write(l.toString() + " : " + largestPrimeFactors.get(l));
				fw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done writing " + largestPrimeFactors.size()
				+ " lines!");
	}

	private static void writePrimesFile(ArrayList<Long> primes) {
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new FileWriter(PRIMES_FILENAME));
			for (Long b : primes) {
				fw.write(b.toString());
				fw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done writing " + primes.size() + " lines!");
	}

	/*
	 * TODO: Minste til nå: 211
	 */

	private static ArrayList<Long> generatePrimes() {

		boolean[] nP = new boolean[MAX_PRIME];
		nP[0] = true;
		for (long p = 2; p < MAX_PRIME; p++) {
			if (!nP[(int) p]) {
				for (long i = p * p; i < MAX_PRIME; i += p) {
					nP[(int) i] = true;
				}
			}
		}

		ArrayList<Long> primes = new ArrayList<Long>();
		for (long i = 2; i < nP.length; i++) {
			if (!nP[(int) i]) {
				primes.add(i);
			}
		}
		return primes;
	}

	private static long findLargestPrimeFactor(long l) {
		List<Integer> factors = primeFactors((int) l);
		int largestPrimeFactor = factors.get(factors.size() - 1);
		return largestPrimeFactor;
	}

	private static ArrayList<Long> readBigNumberFile() {
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(BIGNUM_FILENAME));
			while (fr.ready()) {
				bigNumbers.add(Long.parseLong(fr.readLine()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bigNumbers;
	}

	private static HashMap<Long, Long> readLargestPrimeFactorFile() {
		HashMap<Long, Long> largestPrimes = new HashMap<Long, Long>();
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(LARGEST_PRIMES_FILENAME));
			while (fr.ready()) {
				String line = fr.readLine();
				String[] elements = line.split("[ : ]+");
				largestPrimes.put(Long.parseLong(elements[0]),
						Long.parseLong(elements[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return largestPrimes;
	}

	private static ArrayList<Long> readPrimesFile() {
		ArrayList<Long> primes = new ArrayList<Long>();

		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(PRIMES_FILENAME));
			while (fr.ready()) {
				primes.add(Long.parseLong(fr.readLine()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return primes;
	}

	public static void main(String[] args) {
		// writeBigNumberFile(generateBigNumbers());
		// writePrimesFile(generatePrimes());

		System.out.println("Generating big numbers...");
		bigNumbers = generateBigNumbers();
		// primes = readPrimesFile();
		// largestPrimeFactors = readLargestPrimeFactorFile();

		System.out.println("Computing all largest primefactors...");
		largestPrimeFactors = findSmallestLargestPrimeFactor();

		System.out.println("Saving all largest primefactors to "
				+ LARGEST_PRIMES_FILENAME + "...");
		writeLargestPrimes();
	}

	private static List<Long> getLargestPrimeFactorsSortedKeys() {
		List<Long> keys = new ArrayList<Long>(largestPrimeFactors.keySet());
		final Map<Long, Long> primesForComp = largestPrimeFactors;

		Collections.sort(keys, new Comparator<Long>() {

			@Override
			public int compare(Long left, Long right) {
				return primesForComp.get(left).compareTo(
						primesForComp.get(right));
			}

		});
		return keys;
	}

	public static List<Integer> primeFactors(int numbers) {
		int n = numbers;
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n / i; i++) {
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		if (n > 1) {
			factors.add(n);
		}
		return factors;
	}
}
