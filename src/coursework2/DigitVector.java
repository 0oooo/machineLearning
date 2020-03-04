package coursework2;

import java.util.ArrayList;

public class DigitVector {
	
	private MathUtilities utils = new MathUtilities(); 
	
	private ArrayList<Double> pixelGroup; 
	private double representedNumber; 
	private DigitVector closestDigit;
	private int closestDigitIndex; 
	private double distanceWithDigit; 
	
	public DigitVector(){
		pixelGroup  = new ArrayList<Double>();
		distanceWithDigit = -1;
	}
	
	public void addPixelGroup(double pixels){
		pixelGroup.add(pixels);
		if(pixelGroup.size() == 65){
			representedNumber = pixels; 
		}	
	}
	
	public double getRepresentedNumber(){
		return representedNumber;
	}
	
	public double get(int pixelAtPosition) {
		return pixelGroup.get(pixelAtPosition);
	}
	
	public int size() {
		return pixelGroup.size(); 
	}
	
	public void printDigit() {
		for(Double pixels : pixelGroup) {
			System.out.print(pixels + " " );
		}
	}
	
	
	public ArrayList<Double> getPixelsGroup() {
		return pixelGroup;
	}

	public double calculateEuclideanDistance(DigitVector otherDigit){
		
		int totalDistancePerBlock = 0; 
		int digitBlock = 0;
		
		// this.digitSize() - 1 because we don't want to calculate the last number which is the represented number
		for(digitBlock = 0; digitBlock < this.size() - 1; digitBlock++){ 
			double numberOfPixel1 = this.pixelGroup.get(digitBlock);
			double numberOfPixel2 = otherDigit.pixelGroup.get(digitBlock);
			
			totalDistancePerBlock += (numberOfPixel1 - numberOfPixel2) * (numberOfPixel1 - numberOfPixel2);
		}
		return totalDistancePerBlock; 
	}
	
	public int returnClosestDigitIndex(){
		return closestDigitIndex;
	}
	
	public DigitVector returnClosestDigit(){
		return closestDigit; 
	}
	
	public double returnSmallestDistance(){
		return distanceWithDigit; 
	}
	
	/**
	 * Calculate the distance from the hyperplane
	 * @param hyperplane
	 * @param bias
	 * @return the square of the distance to avoid issue with negative numbers. 
	 */
	public double distanceFromHyperplane(ArrayList<Double> hyperplane, double bias) {
		double distanceFromHyperplane = ( utils.dotProduct(pixelGroup, hyperplane) + bias); 
		return distanceFromHyperplane * distanceFromHyperplane;
	}

}
