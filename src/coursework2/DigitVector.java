package coursework2;

import java.util.ArrayList;

/**
 * DigitVector
 * 
 * Class representing each row in the data set.
 * It is composed by a list of group of pixel and a represented number 
 * @author CC
 *
 */
public class DigitVector {
	
	private final int LAST_PIXEL_GROUP_INDEX = 65; 
	private final int DEFAULT_DISTANCE_FROM_OTHER_DIGIT = -1; 
	private final int LAST_NUMBER = 1; 
	
	private MathUtilities utils; 
	private ArrayList<Double> pixelGroup; 
	private double representedNumber; 
	private DigitVector closestDigit;
	private int closestDigitIndex; 
	private double distanceWithDigit; 
	
	/**
	 * Constructor
	 */
	public DigitVector(){
		pixelGroup  = new ArrayList<Double>();
		distanceWithDigit = DEFAULT_DISTANCE_FROM_OTHER_DIGIT;
		utils = new MathUtilities(); 
	}
	
	/**
	 * Add a group of pixels to the list of group of pixels
	 */
	public void addPixelGroup(double pixels){
		pixelGroup.add(pixels);
		if(pixelGroup.size() == LAST_PIXEL_GROUP_INDEX){
			representedNumber = pixels; 
		}	
	}
	
	/**
	 * @return the represented number of a vector
	 */
	public double getRepresentedNumber(){
		return representedNumber;
	}
	
	/**
	 * Get the group of pixel at index <pixelAtPosition> in the list
	 * @return the corresponding group of pixels
	 */
	public double get(int pixelAtPosition) {
		return pixelGroup.get(pixelAtPosition);
	}
	
	/**
	 * @return the size of the list of pixels
	 */
	public int size() {
		return pixelGroup.size(); 
	}
	
	/**
	 * print each group of pixel related to that digit
	 */
	public void printDigit() {
		for(Double pixels : pixelGroup) {
			System.out.print(pixels + " " );
		}
	}
	
	/**
	 * @return the full list of pixels
	 */
	public ArrayList<Double> getPixelsGroup() {
		return pixelGroup;
	}

	/**
	 * Calculate the euclidean distance with another DigitVector Object
	 * Distance = digit1 x * digit2 x + digit1 y * digit2 y ...
	 * @param otherDigit DigitVector Object to compare with
	 * @return the distance between the two objects. 
	 */
	public double calculateEuclideanDistance(DigitVector otherDigit){
		int totalDistancePerBlock = 0; 
		int digitBlock = 0;
		
		// we don't want to use the last number in the calculation because it's the represented number
		for(digitBlock = 0; digitBlock < this.size() - LAST_NUMBER; digitBlock++){ 
			double numberOfPixel1 = this.pixelGroup.get(digitBlock);
			double numberOfPixel2 = otherDigit.pixelGroup.get(digitBlock);
			
			totalDistancePerBlock += (numberOfPixel1 - numberOfPixel2) * (numberOfPixel1 - numberOfPixel2);
		}
		return totalDistancePerBlock; 
	}
	
	/**
	 * @return the index of the digitVector that is the closest to the current one 
	 */
	public int returnClosestDigitIndex(){
		return closestDigitIndex;
	}
	
	/**
	 * @return the digitVector that is the closest to the current one 
	 */
	public DigitVector returnClosestDigit(){
		return closestDigit; 
	}
	
	/**
	 * @return the smallest distance between this digitVector and another one
	 */
	public double returnSmallestDistance(){
		return distanceWithDigit; 
	}
	
	/**
	 * Calculate its distance from a given hyperplane
	 * @param hyperplane vector of the hyperplane
	 * @param bias defines the hyperplane
	 * @return the square of the distance to avoid issue with negative numbers. 
	 */
	public double distanceFromHyperplane(ArrayList<Double> hyperplane, double bias) {
		double distanceFromHyperplane = ( utils.dotProduct(pixelGroup, hyperplane) + bias); 
		return distanceFromHyperplane * distanceFromHyperplane;
	}
}
