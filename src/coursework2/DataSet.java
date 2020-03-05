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
	private final int SIZE_OF_DIGIT_VECTOR = 63; 
	private final int REPRESENTED_NUMBER = 64; 

	private ArrayList<DigitVector> dataSet;
	private ArrayList<DigitVector> trainingSet;
	private ArrayList<DigitVector> validationSet;
	private HashMap<Double, ArrayList<Integer>> mapDigitToVector;
	private HashMap<Integer, ArrayList<double[]>> simplifiedDigitToVector;
	private ArrayList<double[]> simplifiedDataSet;
	private int mostSignificantPixel; 
	private int secondMostSignificantPixel; 

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
			simplifiedData[SAMPLE_INDEX_1] = digit.get(mostSignificantPixel);  
			simplifiedData[SAMPLE_INDEX_2] = digit.get(secondMostSignificantPixel);
			simplifiedData[SAMPLE_INDEX_3] = digit.get(REPRESENTED_NUMBER);

			simplifiedDataSet.add(simplifiedData);
		}
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
	
	
	//--------------------GETTERS------------------//
	
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
	 * Get list of simplified vector (x, y, represented digit)
	 * @return
	 */
	public ArrayList<double[]> getSimplifiedDataSet() {
		return simplifiedDataSet;
	}


	
	
	//------------------SIMPLIFICATION BY STANDARD VARIATION------------------//
	
	/**
	 * Add a simplified vector to the map of [represented number => [vectors of that number] ]
	 * @param representedDigit represented number
	 * @param vector to add to the map
	 */
	public void addSimplifyVector(int representedDigit, double[] vector) {
		getVectorListFor(representedDigit).add(vector);
	}

	/**
	 * @return the index of the pixel group with the biggest variations
	 */
	public int getMostSignificantPixel() {
		return mostSignificantPixel;
	}

	/**
	 * @return the index of the pixel group with the second biggest variations
	 */
	public int getSecondMostSignificantPixel() {
		return secondMostSignificantPixel;
	}

	/**
	 * For each pixel group (column of the data set), calculate the mean
	 * @return a list of means
	 */
	private ArrayList<Double> calculateMeans() {
		ArrayList<Double> listOfMeans = new ArrayList<Double>();
		
		for (int column = 0; column <= SIZE_OF_DIGIT_VECTOR; column++) {
			double currentMean = 0.0; 
			for (int row = 0; row < dataSet.size(); row++) {
				DigitVector digitVector = dataSet.get(row);
				double numberPixel = digitVector.get(column);
				currentMean += numberPixel; 
			}
			currentMean = currentMean / SIZE_OF_DIGIT_VECTOR;
			listOfMeans.add(currentMean);
		}

		return listOfMeans; 
	}
	
	/**
	 * For each pixel group (column of the data set), calculate the standard variation 
	 * @param listOfMeans means of each column
	 * @return a list of standard variation per pixel group (column)
	 */
	private ArrayList<Double> getStandardVariationPerPixelGroup(ArrayList<Double> listOfMeans){
		ArrayList<Double> listOfStandardVariation = new ArrayList<Double>();
		
		double standardVariation = 0.0; 
		double totalSquaredDifference = 0.0;
		
		for (int column = 0; column <= SIZE_OF_DIGIT_VECTOR; column++) {
			double meanForThatColumn = listOfMeans.get(column);
			double currentSquaredDifference = 0.0; 
			for (int row = 0; row < dataSet.size(); row++) {
				DigitVector digitVector = dataSet.get(row);
				double numberPixel = digitVector.get(column);
				currentSquaredDifference = numberPixel - meanForThatColumn; 
				currentSquaredDifference = currentSquaredDifference * currentSquaredDifference; 
				totalSquaredDifference += currentSquaredDifference;
			}		
			totalSquaredDifference = totalSquaredDifference / SIZE_OF_DIGIT_VECTOR; 
			standardVariation = Math.sqrt(totalSquaredDifference);
			listOfStandardVariation.add(standardVariation);
		}
	
		return listOfStandardVariation; 
	}

	/**
	 * Find the pixels that change the most in the column
	 */
	public void findMostSignificantPixels() {
		ArrayList<Double> meansPerPixelGroup = calculateMeans(); 
		ArrayList<Double> listOfStandardVariationsPerPixelGroup = getStandardVariationPerPixelGroup(meansPerPixelGroup); 
		
		int mostSignificantPixel = 0;
		int secondMostSignificantPixel = 0;

		double biggestVariation = 0;
		double secondBiggestVariation = 0;
		
		for(int standardVariationIndex = 0; standardVariationIndex < SIZE_OF_DIGIT_VECTOR; standardVariationIndex++) {
			double currentStandardVariation = listOfStandardVariationsPerPixelGroup.get(standardVariationIndex); 
			if(currentStandardVariation > biggestVariation) {
				secondBiggestVariation = biggestVariation; 
				secondMostSignificantPixel = mostSignificantPixel; 
				biggestVariation = currentStandardVariation; 
				mostSignificantPixel = standardVariationIndex; 
			}else if(currentStandardVariation > secondBiggestVariation) {
				secondBiggestVariation = currentStandardVariation; 
				secondMostSignificantPixel = standardVariationIndex; 
			}
		}
		this.mostSignificantPixel = mostSignificantPixel; 
		this.secondMostSignificantPixel = secondMostSignificantPixel;
	}
	
	/**
	 * From the regular digitVector, creates a reduces to 2D digit vector
	 * @param digitVector regular 64 dimensions vector
	 * @return a 2d digit vector
	 */
	private double[] simplifyDigitVector(DigitVector digitVector) {
		double[] simplifiedVector = { digitVector.get(mostSignificantPixel), digitVector.get(secondMostSignificantPixel) }; 
																				
		return simplifiedVector;
	}
	
	/**
	 * For each digitVector, simplify them to 2d 
 	 * and add it to a hashmap that associates each vector to its represented number
	 */
	public void generateSimpleVector() {
		findMostSignificantPixels();
		for(int row = 0; row < dataSet.size(); row++) {
			DigitVector currentDigitVector = dataSet.get(row);
			double[] simpleDigitVector = simplifyDigitVector(currentDigitVector);
			int representedNumber = (int) currentDigitVector.get(REPRESENTED_NUMBER);
			addSimplifyVector(representedNumber, simpleDigitVector);
		}			 
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
