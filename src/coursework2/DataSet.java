package coursework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataSet {
	
	
	private ArrayList<DigitVector> dataSet; 
	private ArrayList<DigitVector> trainingSet; 
	private ArrayList<DigitVector> validationSet;
	private HashMap<Double, ArrayList<Integer>> mapDigitToVector;

	private HashMap<Integer, ArrayList<double[]>> simplifiedDigitToVector; 
	private ArrayList<double[]> simplifiedDataSet; 

	public DataSet(int splitSetPartition){
		dataSet = new ArrayList<DigitVector>();
		trainingSet = new ArrayList<DigitVector>();
		validationSet = new ArrayList<DigitVector>();
		
		mapDigitToVector = new HashMap<Double, ArrayList<Integer>>(); 
		
		simplifiedDigitToVector = new HashMap<Integer, ArrayList<double[]>>(); 
		
		simplifiedDataSet = new ArrayList<double[]>();
	}
	
	/**
	 * Simplified data set for the training data
	 */
	public void generateSimplifiedDataSet() {
		for(DigitVector digit : dataSet) {
			double[] simplifiedData = new double[3]; 
			simplifiedData[0] = digit.get(3);
			simplifiedData[1] = digit.get(59); 
			simplifiedData[2] = digit.get(64);
			
			simplifiedDataSet.add(simplifiedData);
		}
	}
	
	public ArrayList<double[]> getSimplifiedDataSet() {
		return simplifiedDataSet;
	}

	public void addToDataSet(DigitVector digit){
		dataSet.add(digit);
	}
	
	public void addToMapDigit(double representedNumber, ArrayList<Integer> vectorsIndexes) {
		mapDigitToVector.put(representedNumber, vectorsIndexes);
	}
	
	public DigitVector get(int indexOfVector) {
		return dataSet.get(indexOfVector);
	}
	
	public HashMap<Integer, ArrayList<double[]>> getSimplifiedDigitToVector() {
		return simplifiedDigitToVector;
	}


	public int getDataSetSize() {
		return dataSet.size(); 
	}
	
	public ArrayList<DigitVector> getFullDataSet(){
		return dataSet; 
	}
	
	public void splitDataSet(int splitSetPartition) {
		Collections.shuffle(dataSet);
		int percentOfList = splitSetPartition * dataSet.size() / 100; 
		trainingSet = new ArrayList<DigitVector>(dataSet.subList(0, percentOfList));
		validationSet = new ArrayList<DigitVector>(dataSet.subList(percentOfList, dataSet.size()));
	}
	
	public ArrayList<DigitVector> getTrainingSet() {
		return trainingSet; 
	}
	
	public ArrayList<DigitVector> getValidationSet() {
		return validationSet;
	}
	
	public HashMap<Double, ArrayList<Integer>> getMapDigitToVector() {
		return mapDigitToVector;
	}
	
	
	/**
	 * Get the array list of vectors by the represented digit. 
	 * If there is no entry yet, it creates a new array list
	 * and add it to the map of [representedDigit => arrayList of vectors]
	 * NB: The arrayList of vector is actually just their index in the data set.  
	 */
	private ArrayList<double[]> getVectorListFor(int representedDigit) {
		
		if(simplifiedDigitToVector.get(representedDigit) == null) {
			simplifiedDigitToVector.put(representedDigit, new ArrayList<double[]>()); 
		}
		return simplifiedDigitToVector.get(representedDigit); 
	}
	
	public void addSimplifyVector(int representedDigit, double[] vector) {
		getVectorListFor(representedDigit).add(vector); 
	}
	
	public void findMostSignificantPixels() {
		int mostSignificantPixel = 0; 
		int secondMostSignificantPixel = 0; 
		
		double biggestDifference = 0; 
		double secondBiggestDifference = 0; 
		double currentDifference = 0; 
		
		for(int pixelGroup = 0; pixelGroup < 64; pixelGroup++) {
			
			double differenceInPixels = 0; 
			for(int row = 0; row < dataSet.size() - 1; row++) {
				DigitVector digitVector1 = dataSet.get(row);
				DigitVector digitVector2 = dataSet.get(row);
				
				currentDifference = digitVector1.get(pixelGroup) - digitVector2.get(pixelGroup);
				currentDifference = currentDifference * currentDifference; //To get the absolute value
				differenceInPixels += currentDifference; 
			}
			if(pixelGroup == 0 || currentDifference > biggestDifference) {
				biggestDifference = currentDifference; 
				mostSignificantPixel = pixelGroup;
			}else if ( currentDifference > secondBiggestDifference) {
				secondBiggestDifference = currentDifference; 
				secondMostSignificantPixel = pixelGroup;
			}
		}
		
	}
	
	
	public ArrayList<Integer> findNonDeterminantPixel(){
		ArrayList<Integer> nonDeterminantPixel = new ArrayList<Integer>(); 
		
		return nonDeterminantPixel; 
	}
	
	/*
	 * -----------------------------------------------
	 * -----------DEBUG FUNCTIONS---------------------
	 * -----------------------------------------------
	 */
	
	
	public void printDataSet(){
		for(DigitVector digit : dataSet){
			System.out.println("Digit: ");
			digit.printDigit();
			System.out.println("\n");
		}
	}
	
	
	public void printDataMap() {	
		
		for(double key : mapDigitToVector.keySet()) {
			System.out.print(key + "  --> ");
			System.out.println(mapDigitToVector.get(key));	
		}
	}
	
	public void printSimplifiedVector() {
		
		for(int key: simplifiedDigitToVector.keySet()) {
			System.out.print(key + "  --> ");
			for(double[] doub : simplifiedDigitToVector.get(key)) {
				System.out.print("[" + doub[0] + " ");
				System.out.print(doub[1] + "]");
			}
			System.out.print("\n");
		}
	}
	
	public void printSimplifiedDataSet() {
		for(double[] vector : simplifiedDataSet) {
			System.out.print("[" + vector[0] + " - " + vector[1]);
			System.out.println(" - "+ vector[2] + "]");
		}
	}
	
	public void printDataSet(String dataSetCategory){
		int percent = 0; 
		
		switch(dataSetCategory) {
			case "training": 
				for(DigitVector digit : trainingSet){
					System.out.println("Digit: ");
					digit.printDigit();
					System.out.println("\n");
					percent = trainingSet.size() * 100 / dataSet.size(); 
				}
				System.out.println(" Thats " + percent + " % of the total set.");
				break; 
			
			case "validation": 
				for(DigitVector digit : validationSet){
					System.out.println("Digit: ");
					digit.printDigit();
					System.out.println("\n");
					percent = validationSet.size() * 100 / dataSet.size(); 
				}
				System.out.println(" Thats " + percent + " % of the total set.");
				break; 
			
			case "full":
				printDataSet(); 
				break; 
			
			default: 
				System.out.println("What dataset do you want to print? \"training\", \"validation\" or \"full\"?");
		}
		
		
	}
	

	public void test(DataSet otherList){
		DigitVector digit1 = dataSet.get(2);
		for(int otherDigit = 0; otherDigit < dataSet.size(); otherDigit++){
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
