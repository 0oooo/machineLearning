package coursework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * DataSet
 * 
 * class the holds the different data structure to represent the data set:
 * - a list of vectors
 * - a map of represented number and the corresponding index in the list of the vector representing that number 
 * - a list of simplified vector (2 dimensions + represented number)
 * - a map of represented number and the corresponding vectors representing that number
 * 
 * @author CC
 *
 */
public class DataSet {
	
	private final int SAMPLE_INDEX_1 = 0;
	private final int SAMPLE_INDEX_2 = 1;
	private final int SAMPLE_INDEX_3 = 2;
	private final int PERCENTAGE_DIVISOR = 100;
	private final int START_INDEX = 0; 
	private final String ROWS_TRAINING = "training";
	private final String ROWS_VALIDATION = "validation";
	private final String ROWS_FULL = "full";

	private ArrayList<DigitVector> dataSet;
	private ArrayList<DigitVector> trainingSet;
	private ArrayList<DigitVector> validationSet;
	private HashMap<Double, ArrayList<Integer>> mapDigitToVector;
	private HashMap<Integer, ArrayList<double[]>> simplifiedDigitToVector;
	private ArrayList<double[]> simplifiedDataSet;

	/**
	 * Constructor
	 * @param splitSetPartition percentage of the file to use for training
	 * and by deduction the other part is used for testing (not used)
	 */
	public DataSet(int splitSetPartition) {
		dataSet = new ArrayList<DigitVector>();
		trainingSet = new ArrayList<DigitVector>();
		validationSet = new ArrayList<DigitVector>();
		mapDigitToVector = new HashMap<Double, ArrayList<Integer>>();
		simplifiedDigitToVector = new HashMap<Integer, ArrayList<double[]>>();
		simplifiedDataSet = new ArrayList<double[]>();
	}

	/**
	 * Simplified data set for the training data
	 * Create a 3d vector (x, y, represented digit) and add it to a list of simplified vector
	 */
	public void generateSimplifiedDataSet() {
		for (DigitVector digit : dataSet) {
			double[] simplifiedData = new double[3];
			simplifiedData[SAMPLE_INDEX_1] = digit.get(3);   // TODO varialise that
			simplifiedData[SAMPLE_INDEX_2] = digit.get(59);
			simplifiedData[SAMPLE_INDEX_3] = digit.get(64);

			simplifiedDataSet.add(simplifiedData);
		}
	}

	/**
	 * Get list of simplified vector (x, y, represented digit)
	 * @return
	 */
	public ArrayList<double[]> getSimplifiedDataSet() {
		return simplifiedDataSet;
	}

	/**
	 * Add a digit vector to a list
	 * @param digit digitVector Object 
	 */
	public void addToDataSet(DigitVector digit) {
		dataSet.add(digit);
	}

	/**
	 * Add a pair [ represented number -> [indexes of vectors representing that number] ] to a map
	 * Not used
	 * @param representedNumber 0 to 9 digit
	 * @param vectorsIndexes indexes of the vectors in the dataSet list
	 */
	public void addToMapDigit(double representedNumber, ArrayList<Integer> vectorsIndexes) {
		mapDigitToVector.put(representedNumber, vectorsIndexes);
	}

	/**
	 * @return the digit vector at position <indexOfVector> in the dataSet list
	 */
	public DigitVector get(int indexOfVector) {
		return dataSet.get(indexOfVector);
	}

	/**
	 * @return the simplified map of [ represented number => [vectors representing that number] ]
	 */
	public HashMap<Integer, ArrayList<double[]>> getSimplifiedDigitToVector() {
		return simplifiedDigitToVector;
	}

	/**
	 * @return data size of the dataSet list
	 */
	public int getDataSetSize() {
		return dataSet.size();
	}

	/**
	 * @return the dataSet list
	 */
	public ArrayList<DigitVector> getFullDataSet() {
		return dataSet;
	}

	/**
	 * Not used
	 * Split the data set into 2 categories, one for training and one for validating the current state of the algorithm(s)
	 * @param splitSetPartition is the percentage of the dataset that will be used for training
	 */
	public void splitDataSet(int splitSetPartition) {
		Collections.shuffle(dataSet);
		int percentOfList = splitSetPartition * dataSet.size() / PERCENTAGE_DIVISOR;
		trainingSet = new ArrayList<DigitVector>(dataSet.subList(START_INDEX, percentOfList));
		validationSet = new ArrayList<DigitVector>(dataSet.subList(percentOfList, dataSet.size()));
	}

	/**
	 * Not used
	 * @return the training part of the dataset
	 */
	public ArrayList<DigitVector> getTrainingSet() {
		return trainingSet;
	}

	/**
	 * Not used
	 * @return the validation part of the dataset
	 */
	public ArrayList<DigitVector> getValidationSet() {
		return validationSet;
	}

	/**
	 * @return the map of [represented number => [indexes of vectors representing that number]
	 */
	public HashMap<Double, ArrayList<Integer>> getMapDigitToVector() {
		return mapDigitToVector;
	}

	/**
	 * Get the array list of vectors by the represented digit. If there is no entry
	 * yet, it creates a new array list and add it to the map of 
	 * [representedDigit => [list indexes of vectors representing that number] ] 
	 * NB: The arrayList of vector is actually just their index in the data set.
	 */
	private ArrayList<double[]> getVectorListFor(int representedDigit) {
		if (simplifiedDigitToVector.get(representedDigit) == null) {
			simplifiedDigitToVector.put(representedDigit, new ArrayList<double[]>());
		}
		
		return simplifiedDigitToVector.get(representedDigit);
	}

	
	/**
	 * Add a simplified vector to the map of [represented number => [vectors of that number] ]
	 * @param representedDigit represented number
	 * @param vector to add to the map
	 */
	public void addSimplifyVector(int representedDigit, double[] vector) {
		getVectorListFor(representedDigit).add(vector);
	}

	
	// TODO : todo 
	//TODO: split and use Standard Deviation and variance  https://www.mathsisfun.com/data/standard-deviation.html
	public void findMostSignificantPixels() {
		int mostSignificantPixel = 0;
		int secondMostSignificantPixel = 0;

		double biggestDifference = 0;
		double secondBiggestDifference = 0;
		double currentDifference = 0;

		for (int column = 0; column < 64; column++) {

			double maxDifference = 0;
			for (int row = 0; row < dataSet.size() - 1; row++) {
				DigitVector digitVector1 = dataSet.get(row);
				DigitVector digitVector2 = dataSet.get(row + 1);

				double numberPixelVector1 = digitVector1.get(column);
				double numberPixelVector2 = digitVector2.get(column);

				currentDifference = numberPixelVector1 - numberPixelVector2;
				currentDifference = currentDifference * currentDifference; // To get the absolute value
				maxDifference += currentDifference;
			}

			if (maxDifference > biggestDifference) {
				secondBiggestDifference = biggestDifference;
				secondMostSignificantPixel = mostSignificantPixel;

				biggestDifference = maxDifference;
				mostSignificantPixel = column;
			} else if (maxDifference > secondBiggestDifference) {
				secondBiggestDifference = maxDifference;
				secondMostSignificantPixel = column;
			}
		}
		System.out.println(
				"Most Significant Pixel = " + mostSignificantPixel + " with difference of " + biggestDifference);
		System.out.println("Second MostSignificant Pixel = " + secondMostSignificantPixel + " with difference of "
				+ secondBiggestDifference);

	}

		
	//---------------------DEBUG--------------------//
	
	/**
	 * Print list of full digits
	 */
	public void printDataSet() {
		for (DigitVector digit : dataSet) {
			System.out.println("Digit: ");
			digit.printDigit();
			System.out.println("\n");
		}
	}

	/**
	 * Print map of represented number to vector
	 */
	public void printDataMap() {
		for (double key : mapDigitToVector.keySet()) {
			System.out.print(key + "  --> ");
			System.out.println(mapDigitToVector.get(key));
		}
	}

	/**
	 * Print the map of represented number and corresponding simplified vectors 
	 */
	public void printSimplifiedVector() {
		for (int key : simplifiedDigitToVector.keySet()) {
			System.out.print(key + "  --> ");
			for (double[] doub : simplifiedDigitToVector.get(key)) {
				System.out.print("[" + doub[0] + " ");
				System.out.print(doub[1] + "]");
			}
			System.out.print("\n");
		}
	}

	
	/**
	 * Print the simplified list of vectors (x, y, represented number)
	 */
	public void printSimplifiedDataSet() {
		for (double[] vector : simplifiedDataSet) {
			System.out.print("[" + vector[0] + " - " + vector[1]);
			System.out.println(" - " + vector[2] + "]");
		}
	}

	/**
	 * Not used
	 * Print a part of the data set
	 * @param dataSetCategory is the category we want to print (can be training, testing or full set)
	 */
	public void printDataSet(String dataSetCategory) {
		int percent = 0;

		switch (dataSetCategory) {
		case ROWS_TRAINING:
			for (DigitVector digit : trainingSet) {
				System.out.println("Digit: ");
				digit.printDigit();
				System.out.println("\n");
				percent = trainingSet.size() * PERCENTAGE_DIVISOR / dataSet.size();
			}
			System.out.println(" Thats " + percent + " % of the total set.");
			break;

		case ROWS_VALIDATION:
			for (DigitVector digit : validationSet) {
				System.out.println("Digit: ");
				digit.printDigit();
				System.out.println("\n");
				percent = validationSet.size() * PERCENTAGE_DIVISOR / dataSet.size();
			}
			System.out.println(" Thats " + percent + " % of the total set.");
			break;

		case ROWS_FULL:
			printDataSet();
			break;

		default:
			System.out.println("What dataset do you want to print? \"training\", \"validation\" or \"full\"?");
		}

	}

	
	/**
	 * Test function for the euclidean distance
	 * @param otherList
	 */
	public void test(DataSet otherList) {
		DigitVector digit1 = dataSet.get(2);
		for (int otherDigit = 0; otherDigit < dataSet.size(); otherDigit++) {
			DigitVector digit2 = otherList.getFullDataSet().get(otherDigit);
			digit1.calculateEuclideanDistance(digit2);
		}
		System.out.print("1: ");
		digit1.printDigit();
		System.out.print("\n");
		System.out.print("closest Digit: ");
		int indexOfClosest = digit1.returnClosestDigitIndex();
		System.out.print("(index " + indexOfClosest + "): ");
		otherList.getFullDataSet().get(indexOfClosest).printDigit();
	}

}
