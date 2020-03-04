package coursework2;

import java.util.ArrayList;

public class MathUtilities {
	
	
	/*
	 * -----------------------------------------------
	 * -----------2D VECTOR (AKA LINE)----------------
	 * ----------------FUNCTIONS----------------------
	 */
	
	

	/**
	 * Given 2 vector Slope = (y2 - y1) / (x2 - x1)
	 * 
	 * @param vector1 = [x1, y1]
	 * @param vector2 = [x2, y2]
	 * @return the slope of the line (or the a of the equation of the line)
	 *         (REMINDER equation of a line = ax + b - y = 0 )
	 */

	public double getSlope(double[] point1, double[] point2) {
		
		//squared and squared root to get the absolute values
		double rise = Math.sqrt((point2[1] - point1[1]) * (point2[1] - point1[1])); 
		double run =  Math.sqrt((point2[0] - point1[0]) * (point2[0] - point1[0])); 
		
		return (rise/ run);
	}
	
	public double getYIntersect(double slope, double[] point) {
		return point[1] - slope * point[0]; 
	}
	
	public double[] getPerpendicularLine (double[] line, double[] midPoint) {
		double[] perpendicularLine = new double[2];
		
		perpendicularLine[0] = - (1 / line[0]);
		perpendicularLine[1] = getYIntersect(perpendicularLine[0], midPoint);
		
		return perpendicularLine; 
	}
	
	public double[] findMidPoint(double[] point1, double[] point2) {
		double[] midPoint = new double[2];
		
		midPoint[0] = (point1[0] + point2[0]) / 2; 
		midPoint[1] = (point1[1] + point2[1]) / 2;
		
		return midPoint; 
	}
	
	public double getDistanceFromLine(double[] line, double[] digit) {
			 // slope  *     x  +  y intercept - y
		return line[0] * digit[0] + line[1] - digit[1]; 
	}
	
	public int getSideLine(double[] line, double[] digit) {
		double prediction = getDistanceFromLine( line, digit); 
		if (prediction > 0) {
			return 1;
		} else if (prediction < 0){
			return -1;
		} else if (prediction == 0) {
			return 0; 
		}
		return 11; //that would mean an error with the prediction.  
	}
	
	public double[] drawLineBetween2Points(double[] point1, double[] point2) {
		double[] line = new double[2];
		
		line[0] = getSlope(point1, point2);
		line[1] = getYIntersect(line[0], point1);
		
		return line; 
	}
	
	
	/*
	 * -----------------------------------------------
	 * -----------VECTOR AND HYPERPLANE---------------
	 * ----------------FUNCTIONS----------------------
	 */
	
	

	/**
	 * Get the norm of a vector (or magnitude)
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

//	public ArrayList<Integer> getVectorDirection(ArrayList<Double> vector) {
//
//		ArrayList<Integer> direction = new ArrayList<Integer>();
//
//		double norm = getVectorNorm(vector);
//
//		for (int coordinate = 0; coordinate < vector.size(); coordinate++) {
//			double newCoordinate = vector.get(coordinate) / norm;
//			direction.add(newCoordinate);
//		}
//
//		return direction;
//	}

	// when dot product is 0, it means the vectors are perpendicular
	public double dotProduct(ArrayList<Double> vector1, ArrayList<Double> vector2) {

		double dotProduct = 0;

		for (int coordinate = 0; coordinate < vector1.size(); coordinate++) {
			dotProduct += vector1.get(coordinate) * vector2.get(coordinate);
		}

		return dotProduct;
	}

	
	public boolean areVectorsPerpendicular(ArrayList<Double> vector, ArrayList<Double> hyperplane) {
		return (dotProduct(vector, hyperplane) == 0 ); 
	}
	
	/**
	 * Equation of an hyperplane = w . x = 0
	 * 
	 * @param vector1
	 * @param vector2
	 */
	public void hyperplane(ArrayList<Double> vector1, ArrayList<Double> vector2) {
		double b = -1;
		b = dotProduct(vector1, vector2) * b;
	}
	
	
	public double signAugmentedVector(ArrayList<Double> vectorToTest, ArrayList<Double> hyperplane) throws Exception {
		double prediction = dotProduct(vectorToTest, hyperplane);
		if (prediction > 0) {
			return 1;
		} else if (prediction < 0){
			return -1;
		} else if (prediction == 0) {
			return 0; 
		}
		throw new Exception("Unknown input");
	}
	
	public ArrayList<Double> getNormalizedVector (ArrayList<Double> vector, double norm){
	    ArrayList<Double> newVector = new ArrayList<Double>(); 
	    for (double vectorElement : vector){
	        newVector.add(vectorElement / norm);
	    }
	    return newVector; 
	}
	
	public double getSign(ArrayList<Double> vectorToTest, ArrayList<Double> hyperplane, double bias) throws Exception {
		double prediction = dotProduct(vectorToTest, hyperplane) + bias;
		if (prediction > 0) {
			return 1;
		} else if (prediction < 0){
			return -1;
		} else if (prediction == 0) {
			return 0; 
		}
		throw new Exception("Unknown input");
	}
}
