package coursework2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Svm2d 
 * 
 * Implementation of the Support Vector Machine algorithm for 2 dimensions vectors. 
 * @author CC
 *
 */
public class Svm2d {

	private final int NUMBER_OF_DIGITS = 10; // Digits to identify = 0 to 9
	private final double QUOTA_OF_ERROR = 0.05;
	private final int POSITIVE_SIDE = 1; 
	private final double LAST_POSSIBLE_DIGIT = 9.0;
	private final int PERCENTAGE_DIVISOR = 100;
	
	private MathUtilities utils = new MathUtilities();
	private int maxAttemptPerDigit;
	private int toleratedMisclassification;
	private HashMap<Integer, ArrayList<double[]>> mapDigitToVector;
	private HashMap<Double, double[]> mapDigitToMargin;
	private ArrayList<double[]> listOfMargin;
	private ArrayList<double[]> simplifiedDataSet;
	private int sizeOfSample;
	private int successIdentification;
	private int failedIdentification;

	/**
	 * Initialise the data structure necessary for running the svm. 
	 * @param trainingSet
	 * @param testingSet
	 */
	private void initialiseSvmDataStructures(DataSet trainingSet, DataSet testingSet) {
		mapDigitToVector = trainingSet.getSimplifiedDigitToVector();
		mapDigitToMargin = new HashMap<Double, double[]>();
		listOfMargin = new ArrayList<double[]>();
		testingSet.generateSimplifiedDataSet();
		simplifiedDataSet = testingSet.getSimplifiedDataSet();
		sizeOfSample = testingSet.getDataSetSize();
		successIdentification = 0;
		failedIdentification = 0;
		maxAttemptPerDigit = sizeOfSample / NUMBER_OF_DIGITS * 2; // number of attempts to find a margin between 2 digits
		setMisclassificationTolerance(false);
	}

	/**
	 * Set the tolerance to error to allow to generate soft margin between vectors. 
	 * @param modifyingTolerance flag is true if this method is called because a margin
	 * was not found fast enough and we need to increment the number of vectors that can be misclassified
	 */
	private void setMisclassificationTolerance(boolean modifyingTolerance) {
		if (modifyingTolerance) {
			toleratedMisclassification++;
		} else {
			toleratedMisclassification = (int) Math.floor(sizeOfSample / NUMBER_OF_DIGITS * QUOTA_OF_ERROR);
			// Small data set still have one tolerated misclassification per digit
			if (toleratedMisclassification == 0) {
				toleratedMisclassification++;
			}
		}
	}

	
	//--------------TRAINING FUNCTIONS-----------------//

	/**
	 * Check if the digit of one category get classified on one side of a line
	 * and the the digit of the other category get classified on the other side 
	 * Has a tolerance to misclassification
	 * @param line a potential separator to be checked
	 * @param allDigit1 all vectors of one category (one represented number) 
	 * @param allDigit2 all vectors of the other category (one represented number) 
	 * @return true if the line split the data properly, false otherwise 
	 */
	public boolean lineSplitDigit(double[] line, ArrayList<double[]> allDigit1, ArrayList<double[]> allDigit2) {
		double sideFirstCategory = utils.getSideLine(line, allDigit1.get(0));
		int misclassified = 0;
		for (double[] digit : allDigit1) {
			if (misclassified > toleratedMisclassification) {
				return false;
			}
			if (utils.getSideLine(line, digit) != sideFirstCategory) {
				misclassified++;
			}
		}
		misclassified = 0;
		for (double[] digit : allDigit2) {
			if (misclassified > toleratedMisclassification) {
				return false;
			}
			if (utils.getSideLine(line, digit) == sideFirstCategory) {
				misclassified++;
			}
		}
		
		System.out.println("---------------!! LINE FOUND !!----------------");
		System.out.println("Tolerated misclassification: " + toleratedMisclassification);
		System.out.println("-----------------------------------------------");
		return true;
	}

	/** 
	 * Find a potential line to separate two vectors representing different digits
	 * Draw a line between 2 given points, find the middle, draw a perpendicular line 
	 * and adds the number that line is meant to separate positively 
	 * (when using that line and vectors of that represented number, the result should be positive)
	 * @param supportVector1 potential support vector
	 * @param supportVector2 potential support vector
	 * @param representedNumber that will be associated to the line
	 * @return a line perpendicular to the line that passes through 2 potential support vectors
	 */
	public double[] findSeparatingLine(double[] supportVector1, double[] supportVector2, double representedNumber) {
		double[] lineBetweenSupportVector = utils.drawLineBetween2Points(supportVector1, supportVector2);
		double[] midPoint = utils.findMidPoint(supportVector1, supportVector2);
		double[] perpendicularLine = utils.getPerpendicularLine(lineBetweenSupportVector, midPoint);
		double[] separatingLine = new double[3];
		separatingLine[0] = perpendicularLine[0];
		separatingLine[1] = perpendicularLine[1];
		separatingLine[2] = representedNumber;

		return separatingLine;
	}

	/**
	 * Return random vector out of the list of all digits
	 */
	public double[] findPotentialVector(ArrayList<double[]> allDigit) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, allDigit.size());
		return allDigit.get(randomNum);
	}

	/**
	 * Tries to generate new line between two new vectors
	 * while it has not found a line that separates two categories (represented numbers).
	 * If a proper separator is found, it adds it to a map
	 * Else it readjusts the tolerance to accepted misclassification . 
	 * 
	 * NB: list of margin =  < [ 1, 1, 0] , [ a, b, d], ... >
	 * a = slope of line,
	 * b = yItersect (so a and b define the margin)
	 * d = represented digit
	 */
	public void findMarginBetweenVectors(ArrayList<double[]> allDigit1, ArrayList<double[]> allDigit2,
			double representedNumber) {
		// Reset the misclassification tolerance to original value for next margin
		setMisclassificationTolerance(false);
		int attemptToFindMargin = 0;
		double[] margin = { 0, 0, 0 };

		do {
			attemptToFindMargin++;
			if (attemptToFindMargin > maxAttemptPerDigit) {
//				System.out.println("Margin was not found. Increasing the misclassification tolerance.");
				setMisclassificationTolerance(true);
				attemptToFindMargin = 0;
			}
			double[] potentialSupportVector1 = findPotentialVector(allDigit1);
			double[] potentialSupportVector2 = findPotentialVector(allDigit2);
			margin = findSeparatingLine(potentialSupportVector1, potentialSupportVector2, representedNumber);
		} while (!lineSplitDigit(margin, allDigit1, allDigit2));

		listOfMargin.add(margin);
		mapDigitToMargin.put(representedNumber, margin);
	}

	/**
	 * Run the training part of the algorithm
	 */
	public void train() {
		System.out.println("Training starting...");

		for (int representedNumber = 0; representedNumber < 9; representedNumber++) {
			int nextRepresentedNumber = representedNumber + 1;
			System.out.println("");
			System.out.println("-----------------------------------------------");
			System.out.println("MARGIN FOR " + representedNumber + " AND " + nextRepresentedNumber);
			// Get the index for 2 contiguous represented number
			ArrayList<double[]> currentVectorsOfRepresentedNumber = mapDigitToVector.get(representedNumber);
			ArrayList<double[]> nextVectorsOfRepresentedNumber = mapDigitToVector.get(nextRepresentedNumber);

			findMarginBetweenVectors(currentVectorsOfRepresentedNumber, nextVectorsOfRepresentedNumber,
					representedNumber);
		}
	}

	
	//-------------- TESTING -----------------//

	/**
	 * Checks if a vector is on the positive side of the margin
	 * @param vector to check 
	 * @param margin 
	 * @return true if it is, else otherwise
	 */
	private boolean isOnSideOfMargin(double[] vector, double[] margin) {
		int sideOfVector = utils.getSideLine(vector, margin);
		return sideOfVector == POSITIVE_SIDE;
	}

	/**
	 * Attempt to predict what is the represented number of a vector 
	 * using the margin found by the training part of the algorithm
	 * @param vector to check
	 * @return the predicted represented number
	 */
	private double predictRepresentedNumber(double[] vector) {
		// Left digit when nothing has worked
		double prediction = LAST_POSSIBLE_DIGIT;

		for (double[] margin : listOfMargin) {
			if (isOnSideOfMargin(margin, vector)) {
				prediction = margin[2];
				break;
			}
		}
		return prediction;
	}

	/**
	 * Print the rate of success and failed identification 
	 */
	public void printPredictionRate() {
		System.out.println("Success identification: " + successIdentification);
		System.out.println("Failed identification: " + failedIdentification);
		System.out.println(Math.round(successIdentification * PERCENTAGE_DIVISOR / sizeOfSample) + "% success.");
	}

	/**
	 * Run the test part of the algorithm
	 * Check for each vector what number they represent according to the set of recorded margins
	 * Increase a success of failed identification number. 
	 */
	public void test() {
		System.out.println("Testing starting...");
		successIdentification = 0;
		failedIdentification = 0;
		for (double[] vector : simplifiedDataSet) {
			double verificationNumber = vector[2];
			double[] coordinate = { vector[0], vector[1] };

			double prediction = predictRepresentedNumber(coordinate);

			if (prediction == verificationNumber) {
				successIdentification++;
			} else {
				failedIdentification++;
//				System.out.println("Prediction = " + prediction + " and actual number is " + verificationNumber);
			}
		}
	}

	
	//-------------- DEBUG -----------------//

	/**
	 * Print the list of found margins
	 */
	public void printMargin() {
		for (double key : mapDigitToMargin.keySet()) {
			System.out.print(key + "  --> ");
			System.out.print(mapDigitToMargin.get(key)[0] + " : " + mapDigitToMargin.get(key)[1]);
			System.out.println(" to classify " + mapDigitToMargin.get(key)[2]);
		}
	}

	
	//-------------- RUN -----------------//
	
	public void run(DataSet trainingSet, DataSet testingSet) {
		initialiseSvmDataStructures(trainingSet, testingSet);
		train();
		test();
		
		System.out.print("First fold.");
		printPredictionRate();
		

		initialiseSvmDataStructures(testingSet, trainingSet);
		train();
		test();
		
		System.out.print("Second fold.");
		printPredictionRate();
		
	}
}
