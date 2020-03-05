package coursework2;

import java.io.IOException;

/**
 * MachineLearningRunner
 * 
 * Main class, reads the data from the file, create a DataSet object from it and
 * run chosen algorithm, depending on the command.
 * 
 * @author CC
 */
public class MachineLearningRunner {

	private static final int ARG_INDEX_TRAINING_SET = 0;
	private static final int ARG_INDEX_TESTING_SET = 1;
	private static final int ARG_INDEX_TRAINING_ALGORITHM = 2;

	private static final String MODE_EUCLIDEAN = "euclidean";
	private static final String MODE_SVM = "svm";
	private static final String MODE_DEBUG = "debug";

	/**
	 * Test the formating of the file by printing the array of digits, the map of
	 * digit to represented number, the simplified map of represented number to
	 * vector, and the simplified array of digits
	 * 
	 * @param trainingSet is the dataset to train the algorithms
	 * @param testingSet  is the dataset to test the algorithms
	 */
	public static void debugDataSet(DataSet trainingSet, DataSet testingSet) {
//		trainingSet.printDataSet();
//		trainingSet.printDataMap(); 
//		trainingSet.printSimplifiedVector();
//
//		testingSet.generateSimplifiedDataSet();
//		testingSet.printSimplifiedDataSet(); 
	
	}

	/**
	 * Run the Euclidean distance
	 * 
	 * @param trainingSet: dataset Object to train the algorithms
	 * @param testingSet: dataset Object to test the algorithms In this case, both
	 *        the dataset are used as training and testing There is no parameters
	 *        being changed so this does not influence the efficiency
	 */
	public static void runEuclideanAlgorithm(DataSet trainingSet, DataSet testingSet) {
		EuclideanDistanceCalculator euclideanCalculator = new EuclideanDistanceCalculator(trainingSet, testingSet);
		euclideanCalculator.run();
	}

	/**
	 * Run the Support Vector Machine algorithm
	 * 
	 * @param trainingSet: dataset Object to train the algorithms
	 * @param testingSet: dataset Object to test the algorithms
	 */
	public static void runSvmAlgorithm(DataSet trainingSet, DataSet testingSet) {
		Svm2d svm = new Svm2d();
		svm.run(trainingSet, testingSet); 
	}

	/**
	 * Main method
	 * 
	 * @param args pass: 
	 * - files to use for training set and testing set 
	 * - which algorithm to use
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			throw new Exception("Error. Two arguments required: path to training set, path to testing set.");
		}

		String trainingSetPath = args[ARG_INDEX_TRAINING_SET];
		String testingSetPath = args[ARG_INDEX_TESTING_SET];

		String mode = "";
		if (args.length == 3) {
			mode = args[ARG_INDEX_TRAINING_ALGORITHM];
		} else {
			mode = MODE_EUCLIDEAN;
		}
		
		try {
			// Read the data
			DataReader dataReader1 = new DataReader(trainingSetPath);
			DataReader dataReader2 = new DataReader(testingSetPath);

			// Get the DataSet from the reader
			DataSet trainingSet = dataReader1.getDataSet();
			DataSet testingSet = dataReader2.getDataSet();

			switch (mode) {
			case MODE_EUCLIDEAN:
				runEuclideanAlgorithm(trainingSet, testingSet);
				break;
			case MODE_SVM:
				runSvmAlgorithm(trainingSet, testingSet);
				break;
			case MODE_DEBUG:
				debugDataSet(trainingSet, testingSet);
				break;
			default:
				throw new Exception("Invalid mode (valid modes: euclidean, svm, debug).");
			}

			// Exception for problem with the reading of the file
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		} catch (Exception exception) {

		}
	}
}
