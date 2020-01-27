package coursework2;

import java.io.IOException;

public class Coursework {
	
	private static final String DATASET_PATH_1 = "/Users/CC/Documents/CS/Year_03/AI/coursework2.2/cw2DataSet1.csv";
	private static final String DATASET_PATH_2 = "/Users/CC/Documents/CS/Year_03/AI/coursework2.2/cw2DataSet2.csv";
	
	public static void main (String[] args){
		
		try {
			DataReader dataReader1 = new DataReader(DATASET_PATH_1);
			DataReader dataReader2 = new DataReader(DATASET_PATH_2);
			
			DataSet dataSet1 = dataReader1.getDataSet();
			DataSet dataSet2 = dataReader2.getDataSet();
			
			EuclideanDistanceCalculator euclideanCalculator = 
					new EuclideanDistanceCalculator(dataSet1, dataSet2);
			
			euclideanCalculator.twoFoldTest(); 
			
		}catch (IOException ioexception ) {
			ioexception.printStackTrace();
		}
			
		
	}
}
