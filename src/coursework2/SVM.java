package coursework2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * SVM
 * 
 * Not used. Attempt to create a svm. 
 * @author CC
 *
 */
public class SVM {
	
	public int FUNCTIONAL_MARGIN = 1;
	public int GEOMETRIC_MARGIN = 2; 

	private MathUtilities utils = new MathUtilities();
	private DataSet trainingDataset;
	private HashMap<Double, ArrayList<Integer>> mapDigitToVector;

	SVM(DataSet dataset) {
		trainingDataset = dataset;
		mapDigitToVector = dataset.getMapDigitToVector();
	}

	public void findHyperplane() {

	}

	private double getSide(DigitVector digitVector, ArrayList<Double> hyperplane, double bias) {
		double side = 2;
		try {
			side = utils.getSign(digitVector.getPixelsGroup(), hyperplane, bias);
		} catch (Exception unknownInput) {
			unknownInput.printStackTrace();
		}
		return side;
	}

	/**
	 * TODO call this method with only the digits for the 0s for ex. (using the map)
	 * 
	 * @param representedNumber
	 * @param digitVector
	 * @param hyperplane
	 * @param bias
	 * @return
	 */
	public boolean areDigitVectorsOnSameSide(double representedNumber, ArrayList<Double> hyperplane, double bias) {

		ArrayList<Integer> vectorsForSpecificRepresentedNumber = mapDigitToVector.get(representedNumber);

		int firstVectorIndex = vectorsForSpecificRepresentedNumber.get(0);
		DigitVector firstVector = trainingDataset.get(firstVectorIndex);
		double firstSide = getSide(firstVector, hyperplane, bias);

		for (int vectorFromMap = 1; vectorFromMap < vectorsForSpecificRepresentedNumber.size(); vectorFromMap++) {

			int vectorIndex = vectorsForSpecificRepresentedNumber.get(vectorFromMap);
			DigitVector digitVector = trainingDataset.get(vectorIndex);
			double sideCurrVector = getSide(digitVector, hyperplane, bias);

			if (firstSide != sideCurrVector) {
				return false;
			}
		}
		return true;

	}

	public boolean isOnHyperplane(ArrayList<Double> vectorToTest, ArrayList<Double> hyperplane, double bias) {
		double sign = 1;

		try {
			sign = utils.getSign(vectorToTest, hyperplane, bias);
		} catch (Exception unknownInput) {
			unknownInput.printStackTrace();
		}

		if (sign == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Select the closest vector representing a particular number to a selected
	 * hyperplane
	 * 
	 * @param representedNumber is the represented number. Only the vectors
	 *                          representing that number will be used
	 * @param hyperplane        is the current analysed hyperplane
	 * @param bias              is the bias associated with the current hyperplane
	 * @return the closest DigitVector from the hyperplane.
	 */
	@SuppressWarnings("unused")
	private DigitVector getClosestVectorFromHyperplane(double representedNumber, ArrayList<Double> hyperplane,
			double bias) {

		ArrayList<Integer> vectorIndexesForNumber = mapDigitToVector.get(representedNumber);

		DigitVector closestDigitVector = new DigitVector();
		double shortestDistanceVectorHyperplane = -1; 											

		for (int vector = 0; vector < vectorIndexesForNumber.size(); vector++) {
			int indexOfVectorToPosition = vectorIndexesForNumber.get(vector);
			DigitVector currentDigitVector = trainingDataset.get(indexOfVectorToPosition);

			double distanceVectorHyperplane = currentDigitVector.distanceFromHyperplane(hyperplane, bias);

			if(vector == 0 || distanceVectorHyperplane < shortestDistanceVectorHyperplane) {
				closestDigitVector = currentDigitVector;
				shortestDistanceVectorHyperplane = distanceVectorHyperplane;
			}
		}

		return closestDigitVector;
	}

	/**
	 * Constraint function
	 * @param digitVector
	 * @param hyperplane
	 * @param bias
	 * @param expectedSign
	 * @return
	 */
	private double calculateFunctionalMargin(ArrayList<Double> hyperplane, double bias, DigitVector digitVector,
			int expectedSign) {
		ArrayList<Double> digitVectorAsList = digitVector.getPixelsGroup();

		return (expectedSign + (utils.dotProduct(digitVectorAsList, hyperplane) + bias));

	}

	/**
	 * Calculate the distance of a vector from an hyperplane and multiply by its
	 * expected sign. Scale invariant method.
	 * 
	 * @param digitVector
	 * @param hyperplane   is normalised to make the calculation scale invariant =>
	 *                     using the unit vector => unit vector = w / ||w|| (vector
	 *                     / norm of the vector)
	 * @param bias         follows the same procedure (bias / ||w||
	 * @param expectedSign expected side of the vector in respect to the hyperplane.
	 * @return the distance from the hyperplane. The answer is positive if the
	 *         vector is correctly classified, negative if incorrect
	 */
	private double calculateGeometricMargin(DigitVector digitVector, ArrayList<Double> hyperplane, double bias,
			int expectedSign) {

		ArrayList<Double> digitVectorAsList = digitVector.getPixelsGroup();
		double hyperplaneNorm = utils.getVectorNorm(hyperplane);
		ArrayList<Double> normalizedHyperplane = utils.getNormalizedVector(hyperplane, hyperplaneNorm);
		bias = bias / hyperplaneNorm;
		
		return (expectedSign + (utils.dotProduct(digitVectorAsList, normalizedHyperplane) + bias));
	}

	// TODO understand how to get the expectedSign at first. Is it an hypothesis?

	/**
	 * "when comparing two hyperplanes, we will still select the one for which the
	 * functional margin is the largest" TODO call this method from one with
	 * different hyperplanes and select the one with the biggest margin
	 * 
	 * @param representedNumber
	 * @param hyperplane
	 * @param bias
	 * @param expectedSign
	 * @param typeOfMargin      to precise if we get the functional or the geometric
	 *                          margin (see methods)
	 * @return the functional margin of an hyperplane. We then want to select the
	 *         highest
	 */
	public double getMargin(double representedNumber, ArrayList<Double> hyperplane, double bias, int expectedSign,
			String typeOfMargin) {

		double margin = 0.0; // TODO think about a better number to start with . A dot that would be on the
								// hyperplane can return 0.0 so its no good. no no no.
		double currentMargin;

		ArrayList<Integer> vectorIndexesForNumber = mapDigitToVector.get(representedNumber);

		for (int vector = 0; vector < vectorIndexesForNumber.size(); vector++) {
			int indexOfVectorToPosition = vectorIndexesForNumber.get(vector);
			DigitVector currentDigitVector = trainingDataset.get(indexOfVectorToPosition);

			if (typeOfMargin.equalsIgnoreCase("functional")) {
				currentMargin = calculateFunctionalMargin(hyperplane, bias, currentDigitVector, expectedSign);
			} else {
				currentMargin = calculateGeometricMargin(currentDigitVector, hyperplane, bias, expectedSign);
			}

			if (margin == 0.0 || currentMargin < margin) {
				margin = currentMargin;
			}

		}
		return margin;
	}
	

	public boolean isHardConstraintSatisfied(ArrayList<Double> hyperplane, double bias, DigitVector digitVector, int expectedSign) {
		return calculateFunctionalMargin(hyperplane, bias, digitVector, expectedSign) >= 1; 
	} 
	
	public boolean isSoftConstraintSatisfied(ArrayList<Double> hyperplane, double bias, DigitVector digitVector, int expectedSign, double zeta) {
		return calculateFunctionalMargin(hyperplane, bias, digitVector, expectedSign) >= (1 - zeta); 
	} 
}
