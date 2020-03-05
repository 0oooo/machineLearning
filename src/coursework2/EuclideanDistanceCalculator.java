package coursework2;

/**
 * EuclideanDistanceCalculator 
 * 
 * implements the nearest neighbour algorithm using Euclidean distance 
 * to find what number are the most similar between two datasets.
 */
public class EuclideanDistanceCalculator {

	private final int MAX_DISTANCE = 1000000;
	private final int PERCENT = 100;

	private DataSet dataSet1;
	private DataSet dataSet2;
	private int sizeOfSample;
	private int successIdentification;
	private int failedIdentification;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param dataSet1 DataSet Object to store the digit vectors of one file
	 * @param dataSet2 DataSet Object to store the digit vectors of the other file
	 */
	public EuclideanDistanceCalculator(DataSet dataSet1, DataSet dataSet2) {
		this.dataSet1 = dataSet1;
		this.dataSet2 = dataSet2;

		sizeOfSample = dataSet1.getDataSetSize();

		successIdentification = 0;
		failedIdentification = 0;
	}

	/**
	 * Calculate the distance between each digit vector from one set to the other
	 * Once the most similar digits are establishes, checks if the prediction is
	 * correct and increase the success or failed rate.
	 * 
	 * @param dataSet1 DataSet Object to store the digit vectors of one file
	 * @param dataSet2 DataSet Object to store the digit vectors of the other file
	 */
	private void euclideanDistanceBetweenDatasets(DataSet dataSet1, DataSet dataSet2) {
		successIdentification = 0;
		failedIdentification = 0;

		for (DigitVector actualDigit : dataSet1.getFullDataSet()) {
			double smallestDistance = MAX_DISTANCE;
			DigitVector mostSimilarDigit = new DigitVector();

			for (DigitVector digitToCompareTo : dataSet2.getFullDataSet()) {
				double distanceBetweenDigit = actualDigit.calculateEuclideanDistance(digitToCompareTo);

				if (distanceBetweenDigit < smallestDistance) {
					smallestDistance = distanceBetweenDigit;
					mostSimilarDigit = digitToCompareTo;
				}
			}
			areRepresentedNumberSimilar(actualDigit, mostSimilarDigit);
		}
	}

	/**
	 * Check if the actual number and the predicted number are similar
	 * 
	 * @param digitVector      is the actual digit
	 * @param otherDigitVector is the most similar digit
	 */
	private void areRepresentedNumberSimilar(DigitVector digitVector, DigitVector otherDigitVector) {
		if (digitVector.getRepresentedNumber() == otherDigitVector.getRepresentedNumber()) {
			successIdentification++;
		} else {
			failedIdentification++;
		}
	}

	/**
	 * Get the percentage of success and failure of the euclidean distance
	 */
	private double convertToPercentage(int rateToConvert) {
		return (rateToConvert * PERCENT / sizeOfSample);
	}

	/**
	 * Run two-fold test, checking the digit of one dataset to the other, and then
	 * inversing.
	 */
	public void run() {
		euclideanDistanceBetweenDatasets(dataSet1, dataSet2);
		double successRate1 = convertToPercentage(successIdentification);
		System.out.print("The first fold lead to " + successIdentification + " successes");
		System.out.print(" and  " + failedIdentification + " failures.");
		System.out.println(" which is " + successRate1 + "% success.");

		euclideanDistanceBetweenDatasets(dataSet2, dataSet1);
		double successRate2 = convertToPercentage(successIdentification);
		System.out.print("The second fold lead to " + successIdentification + " successes");
		System.out.print(" and  " + failedIdentification + " failures.");
		System.out.println(" which is " + successRate2 + "% success.");
	}

}
