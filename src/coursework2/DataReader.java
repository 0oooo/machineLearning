package coursework2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
	
	private static int DEFAULT_SPLIT_PARTITION = 50; 
	
	private String path; 
	private DataSet dataSet;
	
	public DataReader(String path) throws IOException{
		this(path, DEFAULT_SPLIT_PARTITION); 
	}
	
	public DataReader(String path, int splitSetPartition) throws IOException{
		this.path = path;
		dataSet = new DataSet(splitSetPartition); 
		readData(splitSetPartition);
	}
	
	// TODO can implement a function to get the most significant indexes 
	// (AKA the ones that changes the most between rows). 
	// The ones selected at the moment have the highest median, 
	// so we assume there is more information for us to work with 
	private double[] simplifyDigitVector(DigitVector digitVector) {
		double[] simplifiedVector = {digitVector.get(3), digitVector.get(59)}; 
		return simplifiedVector;
	}
	
	public void readData(int splitSetPartition) throws IOException{
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		String row = "";
		while ((row = csvReader.readLine()) != null) {
			DigitVector digitVector = new DigitVector();
			
		    String[] pixelGroup = row.split(",");
		    
		    //Each digit block has a number of pixels we add to that block
		    for(String pixel : pixelGroup){
		    	double pixelValue = Double.parseDouble(pixel);
		    	digitVector.addPixelGroup(pixelValue);
		    }
		    dataSet.addToDataSet(digitVector);
		    
		    double[] simpleDigitVector =  simplifyDigitVector(digitVector);
		    int representedNumber = (int) digitVector.get(64); 
		    dataSet.addSimplifyVector(representedNumber, simpleDigitVector ); // Map to split easily per category in svm 
		}
		dataSet.splitDataSet(splitSetPartition);
		csvReader.close();
	}
	
	public DataSet getDataSet(){
		return dataSet;
	}

}
