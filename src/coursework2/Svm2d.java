package coursework2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Svm2d {

	private MathUtilities utils = new MathUtilities();
	
	// Digits to identify = 0 to 9 
	private final int NUMBER_OF_DIGITS = 10; 
	
	private final double QUOTA_OF_ERROR = 0.05;
	private int maxAttemptPerDigit;
	private int tolerated_misclassification; 

	private HashMap<Integer, ArrayList<double[]>> mapDigitToVector;
	private HashMap<Double, double[]> mapDigitToMargin;

	// < [ 1, 1, 0, -1] , [ a, b, d, s], ... >
	// a = slope of line,
	// b = yItersect (so a and b define the margin)
	// d = represented digit
	// s = side of the line
	private ArrayList<double[]> listOfMargin;
	private ArrayList<double[]> simplifiedDataSet;

	private int sizeOfSample;
	private int successIdentification;
	private int failedIdentification;

	
	Svm2d(DataSet dataset, DataSet testingSet) {
		mapDigitToVector = dataset.getSimplifiedDigitToVector();
		mapDigitToMargin = new HashMap<Double, double[]>();

		listOfMargin = new ArrayList<double[]>();

		testingSet.generateSimplifiedDataSet();
		simplifiedDataSet = testingSet.getSimplifiedDataSet();
		sizeOfSample = testingSet.getDataSetSize();

		successIdentification = 0;
		failedIdentification = 0;
		
		// Sets the number of attempts to find a margin between 2 digits
		// number of digit 1 * number of digit 2 
		maxAttemptPerDigit = sizeOfSample / NUMBER_OF_DIGITS * 2;
		
		setMisclassificationTolerance(false);	
	}
	
	private void setMisclassificationTolerance(boolean modifyingTolerance) {
		
		if(modifyingTolerance) {
			tolerated_misclassification++; 
		}else {
			tolerated_misclassification = (int) Math.floor(sizeOfSample / NUMBER_OF_DIGITS * QUOTA_OF_ERROR);
			
			// Small data set still have one tolerated misclassification per digit 
			if(tolerated_misclassification == 0) {
				tolerated_misclassification++;
			}
		}
	}
	
	
	/*
	 * ----------------------------------------------- 
	 * -----------TRAINING FUNCTIONS------------------ 
	 * -----------------------------------------------
	 */


	/**
	 * @param line
	 * @param allDigit1
	 * @param allDigit2
	 * @return
	 */
	public boolean lineSplitDigit(double[] line, ArrayList<double[]> allDigit1, ArrayList<double[]> allDigit2) {

		double sideFirstCategory = utils.getSideLine(line, allDigit1.get(0));

		int misclassified = 0;
		for (double[] digit : allDigit1) {
			if (misclassified >  tolerated_misclassification) {
				return false;
			}
			if (utils.getSideLine(line, digit) != sideFirstCategory) {
				misclassified++;
			}
		}
		
		misclassified = 0;
		for (double[] digit : allDigit2) {
			if (misclassified >  tolerated_misclassification) {
				return false;
			}
			if (utils.getSideLine(line, digit) == sideFirstCategory) {
				misclassified++;
			}
		}

		System.out.println("---------------!! LINE FOUND !!----------------");
		System.out.println("-----------------------------------------------");
		return true; 
	}

	public double[] findSeparatingLine(double[] supportVector1, double[] supportVector2, double representedNumber) {
		
		double[] lineBetweenSupportVector = utils.drawLineBetween2Points(supportVector1, supportVector2);

		double[] midPoint = utils.findMidPoint(supportVector1, supportVector2);

		double[] separatingLine = new double[3];
		double[] perpendicularLine = utils.getPerpendicularLine(lineBetweenSupportVector, midPoint);
		separatingLine[0] = perpendicularLine[0];
		separatingLine[1] = perpendicularLine[1];
		separatingLine[2] = representedNumber;
//		System.out.println("separatingLine selected : " + separatingLine[0] + " : " + separatingLine[1]);

		return separatingLine;
	}

	// TODO this method can be improved by checking what vector has been chosen
	// already
	// or taking the median position and checking if a vector is between that median
	// and the other category vector
	public double[] findPotentialVector(ArrayList<double[]> allDigit) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, allDigit.size());
		return allDigit.get(randomNum);
	}

	/**
	 * Finds a margin between 2 vectors. 
	 * Adds the found margin between the two categories to
	 * the hashmap of digit -> margin The digit is the one
	 * represented by the first category (allDigit1)
	 */
	public void findMarginBetweenVectors(ArrayList<double[]> allDigit1, ArrayList<double[]> allDigit2,
			double representedNumber) {

		// Reset the misclassification tolerance to original value for next margin
		setMisclassificationTolerance(false); 
		int attemptToFindMargin = 0;
		double[] margin = { 0, 0, 0 };

		do {
			attemptToFindMargin++; 
			
			if(attemptToFindMargin > maxAttemptPerDigit){
				System.out.println("Margin was not found. Increasing the misclassification tolerance.");
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

	/*
	 * ----------------------------------------------- 
	 * -----------TESTING FUNCTIONS---------------------
	 * -----------------------------------------------
	 */

	private boolean isOnSideOfMargin(double[] vector, double[] margin) {
		int sideOfVector = utils.getSideLine(vector, margin);
		return sideOfVector == 1;
	}

	private double predictRepresentedNumber(double[] vector) {
		// 9 has no margin to define it, it's the left digit when nothing has worked
		// it's not perfect as some could be 9.0 by chance.
		double prediction = 9.0;

		for (double[] margin : listOfMargin) {
			if (isOnSideOfMargin(margin, vector)) {
				prediction = margin[2];
				break;
			}
		}
		return prediction;
	}

	public void printSuccessRate() {
		System.out.println("Success identification: " + successIdentification);
		System.out.println("Failed identification: " + failedIdentification);
		System.out.println(Math.round(successIdentification * 100 / sizeOfSample) + "% success.");
	}

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
		printSuccessRate();
	}

	/*
	 * ----------------------------------------------- 
	 * -----------DEBUG FUNCTIONS---------------------
	 * -----------------------------------------------
	 */

	public void printMargin() {
		for (double key : mapDigitToMargin.keySet()) {
			System.out.print(key + "  --> ");
			System.out.print(mapDigitToMargin.get(key)[0] + " : " + mapDigitToMargin.get(key)[1]);
			System.out.println(" to classify " + mapDigitToMargin.get(key)[2]);
		}
	}
}
