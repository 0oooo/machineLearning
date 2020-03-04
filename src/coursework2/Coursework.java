package coursework2;

import java.io.IOException;

/**
 * Main class, reads the data from the file, 
 * create a DataSet object from it and run 
 * chosen algorithm, depending on the command. 
 * @author CC
 *
 */
public class Coursework {
	
	// TODO change hard coded paths for arguments and add a read me
	// TODO setup a run configuration 
	
	private static final String DATASET_PATH_1 = "/Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/cw2DataSet1.csv";
	private static final String DATASET_PATH_2 = "/Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/cw2DataSet2.csv";
//	private static final String DATASET_LINEARLY_SEPARABLE = "/Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/linearlySeparableTrainingSet.csv";
//	private static final String DATASET_WITH_ONE_MISCLASSIFIED = "/Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/linearlySeparableWith1Misclassified0.csv";
	
	
	/**
	 * Test the formating of the file 
	 * by printing the array of digits, the map of digit to represented number, 
	 * the simplified map of represented number to vector, and the simplified array of digits 
	 * @param trainingSet is the dataset to train the algorithms
	 * @param testingSet is the dataset to test the algorithms
	 */
	public static void testDataSet(DataSet trainingSet, DataSet testingSet) {	
		trainingSet.printDataSet();
		trainingSet.printDataMap(); 
		trainingSet.printSimplifiedVector();
		
		testingSet.generateSimplifiedDataSet();
		testingSet.printSimplifiedDataSet(); 	
	}
	
	/**
	 * Run the Euclidean distance
	 * @param trainingSet: dataset Object to train the algorithms
	 * @param testingSet: dataset Object to test the algorithms
	 * In this case, both the dataset are used as training and testing
	 * There is no parameters being changed so this does not influence the efficiency
	 */
	public static void runEuclideanAlgorithm(DataSet trainingSet, DataSet testingSet) {	
		EuclideanDistanceCalculator euclideanCalculator = 
				new EuclideanDistanceCalculator(trainingSet, testingSet);	
		euclideanCalculator.run(); 
	}
	
	/**
	 * Run the Support Vector Machine algorithm
	 * @param trainingSet: dataset Object to train the algorithms
	 * @param testingSet: dataset Object to test the algorithms
	 */
	public static void runSvmAlgorithm(DataSet trainingSet, DataSet testingSet) {
		Svm2d svm = new Svm2d(trainingSet, testingSet);
		
		svm.train();
		
		svm.printMargin();
		
		svm.test(); 
	}
	
	/**
	 * Main method
	 * @param args pass: 
	 * 		- files to use for training set and testing set
	 * 		- which algorithm to use 
	 */
	public static void main (String[] args){
		
		try {
			//TODO create switch statement 
			
			//Read the data
			DataReader dataReader1 = new DataReader(DATASET_PATH_1);
			DataReader dataReader2 = new DataReader(DATASET_PATH_2);
			
			// Get the DataSet from the reader
			DataSet trainingSet = dataReader1.getDataSet();
			DataSet testingSet = dataReader2.getDataSet();
			
			//Test the good creation of the dataset and inner structure
//			testDataSet(trainingSet, testingSet); 
			
			//Run the euclidean distance algorithm
//			runEuclideanAlgorithm(trainingSet, testingSet); 
			
			//Run the Support Vector Machine algorithm
			runSvmAlgorithm(trainingSet, testingSet); 
			
			// Exception for problem with the reading of the file
		}catch (IOException ioexception ) {
			ioexception.printStackTrace();
		}
			
		
	}
}
