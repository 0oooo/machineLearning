package coursework2;

public class EuclideanDistanceCalculator {
	
	private DataSet dataSet1;
	private DataSet dataSet2;
	
	private int sizeOfSample; 
	
	private int successIdentification; 
	private int failedIdentification; 
	
	public EuclideanDistanceCalculator(DataSet dataSet1, DataSet dataSet2) {
		this.dataSet1 = dataSet1;
		this.dataSet2 = dataSet2;
		
		sizeOfSample = dataSet1.getNumberOfBitmaps(); 
		
		successIdentification = 0;
		failedIdentification = 0; 
	}
	
	private void euclideanDistanceBetweenDatasets(DataSet set1, DataSet set2) {
		
		successIdentification = 0; 
		failedIdentification = 0; 
		
		for(Bitmap bitmap : set1.getDataSet()) {
			// 16 is the maximum, squared, times 64 digits of the list of bitmap block = at max 16384
			double smallestDistance = 16385; 
			Bitmap closestBitmap = new Bitmap(); 
			
			for(Bitmap bitmapDataSet2 : set2.getDataSet()) {
				double distanceBetweenBitmap = bitmap.calculateEuclideanDistance(bitmapDataSet2);
				
				if(distanceBetweenBitmap < smallestDistance) {
					smallestDistance = distanceBetweenBitmap; 
					closestBitmap = bitmapDataSet2; 
				}
			}
			checkBitmapSimilarities(bitmap,closestBitmap);
		}
	}
	
	private void checkBitmapSimilarities(Bitmap bitmap1, Bitmap bitmap2) {
		if(bitmap1.getRepresentedNumber() == bitmap2.getRepresentedNumber()) {
			successIdentification++; 
		}else {
			failedIdentification++; 
		}
	}
	
	private double convertToPercentage(int rateToConvert) {
		return (rateToConvert * 100 / sizeOfSample);
	}
	
	public void twoFoldTest() {
		//First fold of the test
		euclideanDistanceBetweenDatasets(dataSet1, dataSet2);
		
		double successRate1 = convertToPercentage(successIdentification); 
		
		System.out.print("The first fold lead to " + successIdentification + " successes"); // change that. Put constants
		System.out.print(" and  " +  failedIdentification + " failures.");
		System.out.println(" which is " + successRate1 + "% success.");
		
		//Second fold of the test
		euclideanDistanceBetweenDatasets(dataSet2, dataSet1);
		
		double successRate2 = convertToPercentage(successIdentification); 
		
		System.out.print("The second fold lead to " + successIdentification + " successes"); // change that. Put constants
		System.out.print(" and  " +  failedIdentification + " failures.");
		System.out.println(" which is " + successRate2 + "% success.");
	}
}
