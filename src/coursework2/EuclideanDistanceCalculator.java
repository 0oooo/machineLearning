package coursework2;

public class EuclideanDistanceCalculator {
	
	private DataSet bitmapsList1;
	private DataSet bitmapsList2;
	
	private int successIdentification; 
	private int failedIdentification; 
	
	public EuclideanDistanceCalculator(DataReader dataReader1, DataReader dataReader2) {
		bitmapsList1 = new DataSet();
		bitmapsList2 = new DataSet();
		
		successIdentification = 0;
		failedIdentification = 0; 
		
		try {
			dataReader1.readData(); 
			bitmapsList1 = dataReader1.getDataSet();
			
			dataReader2.readData();
			bitmapsList2 = dataReader2.getDataSet();
			
			bitmapsList1.test(bitmapsList2);
			
		}catch(Exception generalException){
			System.out.println(generalException.getMessage());
		}
	}
	
	private void checkBitmapSimilarities(Bitmap bitmap1, Bitmap bitmap2) {
		if(bitmap1.getRepresentedNumber() == bitmap2.getRepresentedNumber()) {
			successIdentification++; 
		}else {
			failedIdentification++; 
		}
	}
	
	private void euclideanDistanceBetweenDatasets(DataSet set1, DataSet set2) {
		
		successIdentification = 0; 
		failedIdentification = 0; 
		
		for(Bitmap bitmap : set1.getList()) {
			// 16 is the maximum, squared, times 64 digits of the list of bitmap block = at max 16384
			double smallestDistance = 16385; 
			Bitmap closestBitmap = new Bitmap(); 
			
			for(Bitmap bitmapDataSet2 : set2.getList()) {
				double distanceBetweenBitmap = bitmap.calculateEuclideanDistance(bitmapDataSet2);
				
				if(distanceBetweenBitmap < smallestDistance) {
					smallestDistance = distanceBetweenBitmap; 
					closestBitmap = bitmapDataSet2; 
				}
			}
			checkBitmapSimilarities(bitmap,closestBitmap);
		}
	}
	
	
	
	public void twoFoldTest() {
		//First fold of the test
		euclideanDistanceBetweenDatasets(bitmapsList1, bitmapsList2);
		
		int successRate1 = successIdentification; 
		int failedRate1 = failedIdentification; 
		
		
		
		System.out.print("The first fold lead to " + (successRate1 * 100 / 2810) + "success"); // change that. Put constants
		System.out.println(" and  " +  (failedRate1 * 100 / 2810) + " failures.");
		
		//Second fold of the test
		euclideanDistanceBetweenDatasets(bitmapsList2, bitmapsList1);
		
		int successRate2 = successIdentification; 
		int failedRate2 = failedIdentification;
		
		System.out.print("The first fold lead to " + (successRate2 * 100 / 2810) + "success");
		System.out.println(" and  " + (failedRate2 * 100 / 2810) + " failures.");
	}
}
