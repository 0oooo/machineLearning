package coursework2;

import java.util.ArrayList;

/**
 * Math Utilities
 * 
 * Class that holds the necessary operations in regards 
 * with lines and vectors manipulation
 * 
 * @author CC
 *
 */
public class MathUtilities {

	private final int FIRST_COORDINATE_INDEX = 0;
	private final int SECOND_COORDINATE_INDEX = 1;
	private final int TWO_DIMENSIONS = 2;
	private final int PREDICTION_ERROR = 11;
	private final int PREDICTION_THRESHOLD = 0;
	private final int POSITIVE_SIDE = 1;
	private final int NEGATIVE_SIDE = -1;
	private final int NUMBER_OF_POINTS = 2; 

	// -----------2D VECTOR (LINE) FUNCTIONS----------------//

	/**
	 * Given 2 points, slope = (y2 - y1) / (x2 - x1)
	 * @param coordinatesPoint1 = [x1, y1]
	 * @param coordinatesPoint2 = [x2, y2]
	 * @return the slope of the line (or the a of the equation of the line) 
	 * (Used equation of a line = ax + b - y = 0 )
	 */
	public double getSlope(double[] coordinatesPoint1, double[] coordinatesPoint2) {
		// squared and squared root to get the absolute values
		double rise = Math
				.sqrt((coordinatesPoint2[SECOND_COORDINATE_INDEX] - coordinatesPoint1[SECOND_COORDINATE_INDEX])
						* (coordinatesPoint2[SECOND_COORDINATE_INDEX] - coordinatesPoint1[SECOND_COORDINATE_INDEX]));
		double run = Math.sqrt((coordinatesPoint2[FIRST_COORDINATE_INDEX] - coordinatesPoint1[FIRST_COORDINATE_INDEX])
				* (coordinatesPoint2[FIRST_COORDINATE_INDEX] - coordinatesPoint1[FIRST_COORDINATE_INDEX]));

		return (rise / run);
	}

	/**
	 * Given a line, y-intersect = b = y - ax
	 * @param slope = a in the above equation
	 * @param coordinatesPoint is one point located on the line
	 * @return y-intersect (or the 'b' in the equation)
	 */
	public double getYIntersect(double slope, double[] coordinatesPoint) {
		return coordinatesPoint[FIRST_COORDINATE_INDEX] - slope * coordinatesPoint[FIRST_COORDINATE_INDEX];
	}

	/**
	 * Given a line and a point, return the perpendicular line at that point
	 * Perpendicular of line 'ax + b = y' => '-1/a x + b = y'
	 * @param line given line 
	 * @param middleOfLine given middle of that given line
	 * @return the a and b (slope and y-intersect) or the perpendicular line
	 */
	public double[] getPerpendicularLine(double[] line, double[] middleOfLine) {
		double[] perpendicularLine = new double[2];
		perpendicularLine[FIRST_COORDINATE_INDEX] = -(1 / line[FIRST_COORDINATE_INDEX]);
		perpendicularLine[SECOND_COORDINATE_INDEX] = getYIntersect(perpendicularLine[FIRST_COORDINATE_INDEX],
				middleOfLine);

		return perpendicularLine;
	}

	/**
	 * Given two points on the line, find a point mid-way
	 * @param coordinatesPoint1 coordinates of the first point
	 * @param coordinatesPoint2 coordinates of the second point
	 * @return coordinates of the point mid-way
	 */
	public double[] findMidPoint(double[] coordinatesPoint1, double[] coordinatesPoint2) {
		double[] middleOfLine = new double[TWO_DIMENSIONS];
		middleOfLine[FIRST_COORDINATE_INDEX] = (coordinatesPoint1[FIRST_COORDINATE_INDEX]
				+ coordinatesPoint2[FIRST_COORDINATE_INDEX]) / NUMBER_OF_POINTS;
		middleOfLine[SECOND_COORDINATE_INDEX] = (coordinatesPoint1[SECOND_COORDINATE_INDEX]
				+ coordinatesPoint2[SECOND_COORDINATE_INDEX]) / NUMBER_OF_POINTS;

		return middleOfLine;
	}

	/**
	 * Given a line and a point, calculate the distance of the point from that line
	 * @param line given 
	 * @param coordinatesPoint  
	 * @return the distance 
	 */
	public double getDistanceFromLine(double[] line, double[] coordinatesPoint) {
		return line[FIRST_COORDINATE_INDEX] * coordinatesPoint[FIRST_COORDINATE_INDEX] + line[SECOND_COORDINATE_INDEX]
				- coordinatesPoint[SECOND_COORDINATE_INDEX]; // slope * x + y - y intercept
	}

	/**
	 * Given a line and a point, calculate the distance from the line 
	 * to find if the point is on the "positive" or on the "negative" side of the line
	 * @param line
	 * @param coordinatesPoint
	 * @return 1 if on the positive side or -1 if it's on the negative side. 
	 */
	public int getSideLine(double[] line, double[] coordinatesPoint) {
		double prediction = getDistanceFromLine(line, coordinatesPoint);
		if (prediction >= PREDICTION_THRESHOLD) {
			return POSITIVE_SIDE;
		} else if (prediction < PREDICTION_THRESHOLD) {
			return NEGATIVE_SIDE;
		}
		return PREDICTION_ERROR; // that would mean an error with the prediction.
	}

	/**
	 * Given 2 points, find the equation of the line that passes through those 2 points
	 * @param coordinatesPoint1
	 * @param coordinatesPoint2
	 * @return the slope and the y intersect of the discovered line. 
	 */
	public double[] drawLineBetween2Points(double[] coordinatesPoint1, double[] coordinatesPoint2) {
		double[] line = new double[TWO_DIMENSIONS];
		line[FIRST_COORDINATE_INDEX] = getSlope(coordinatesPoint1, coordinatesPoint2);
		line[SECOND_COORDINATE_INDEX] = getYIntersect(line[FIRST_COORDINATE_INDEX], coordinatesPoint1);

		return line;
	}

	// ------------HYPERPLANE FUNCTION - NOT CURRENTLY USED ----------------//

	/**
	 * Get the norm of a vector (or magnitude) 
	 * Can be written ||w|| for vector w
	 * @param vector we want to get the norm of
	 * @return the norm (double) of the vector.
	 */
	public double getVectorNorm(ArrayList<Double> vector) {
		double norm = 0;
		for (int coordinate = 0; coordinate < vector.size(); coordinate++) {
			norm += vector.get(coordinate) * vector.get(coordinate);
		}

		norm = Math.sqrt(norm);

		return norm;
	}

	/**
	 * Given a vector, calculate the direction of that vector
	 * direction of vector w = (w0 / ||w||, w1 / ||w||, ..., wn/ ||w||)
	 * @param vector we want to calculate the direction for
	 * @return the coordinate of a new vector that represents the direction
	 */
	public ArrayList<Double> getVectorDirection(ArrayList<Double> vector) {
		ArrayList<Double> direction = new ArrayList<Double>();
		double norm = getVectorNorm(vector);
		for (int coordinate = 0; coordinate < vector.size(); coordinate++) {
			double newCoordinate = vector.get(coordinate) / norm;
			direction.add(newCoordinate);
		}

		return direction;
	}

	/**
	 * Calculate the dot product for 2 vectors
	 * Dot product of w and x = w0 * x0 + ... + wn * xn 
	 * NB: when dot product is 0, it means the vectors are perpendicular
	 * @param vector1
	 * @param vector2
	 * @return a double representing the dot product
	 */
	public double dotProduct(ArrayList<Double> vector1, ArrayList<Double> vector2) {
		double dotProduct = 0;
		for (int coordinate = 0; coordinate < vector1.size(); coordinate++) {
			dotProduct += vector1.get(coordinate) * vector2.get(coordinate);
		}

		return dotProduct;
	}
	
	/**
	 * Checks if two vectors are perpendicular
	 * @param vector
	 * @param otherVector
	 * @return true if they are, false otherwise
	 */
	public boolean areVectorsPerpendicular(ArrayList<Double> vector, ArrayList<Double> otherVector) {
		return (dotProduct(vector, otherVector) == 0);
	}

	/**
	 * Given two augmented vector, return the side of one vector in respect to the second one
	 * @param vectorToTest is the vector we are checking the side
	 * @param baseVector is the base vector
	 * @return 1 for positive side and -1 for negative side
	 * @throws Exception if there was a problem with the input vector
	 */
	public double signAugmentedVector(ArrayList<Double> vectorToTest, ArrayList<Double> baseVector) throws Exception {
		double prediction = dotProduct(vectorToTest, baseVector);
		if (prediction >= PREDICTION_THRESHOLD) {
			return POSITIVE_SIDE;
		} else if (prediction < PREDICTION_THRESHOLD) {
			return NEGATIVE_SIDE;
		} 

		throw new Exception("Unknown input");
	}

	/**
	 * Given a vector and its magnitude, return the normalized vector
	 * which takes each element and divides it by the magnitude of the vector
	 * @param vector
	 * @param magnitude
	 * @return a vector of normalized elements. 
	 */
	public ArrayList<Double> getNormalizedVector(ArrayList<Double> vector, double magnitude) {
		ArrayList<Double> newVector = new ArrayList<Double>();
		for (double vectorElement : vector) {
			newVector.add(vectorElement / magnitude);
		}

		return newVector;
	}

	/**
	 * Given two vectors, return the side of one vector in respect to the second one
	 * @param vectorToTest is the vector we are checking the side
	 * @param baseVector is the base vector
	 * @return 1 for positive side and -1 for negative side
	 * @throws Exception if there was a problem with the input vector
	 */
	public double getSign(ArrayList<Double> vectorToTest, ArrayList<Double> hyperplane, double bias) throws Exception {
		double prediction = dotProduct(vectorToTest, hyperplane) + bias;
		if (prediction >= PREDICTION_THRESHOLD) {
			return POSITIVE_SIDE;
		} else if (prediction < PREDICTION_THRESHOLD) {
			return NEGATIVE_SIDE;
		} 

		throw new Exception("Unknown input");
	}
}
