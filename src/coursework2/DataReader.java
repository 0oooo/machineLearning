package coursework2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
	
	private String path; 
	private DataSet dataSet; 
	
	public DataReader(String path) throws IOException{
		this.path = path;
		dataSet = new DataSet(); 
		readData();
	}
	
	public void readData() throws IOException{
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		String row = "";
		while ((row = csvReader.readLine()) != null) {
			Bitmap bitmap = new Bitmap();
			
		    String[] bitmapBlocks = row.split(",");
		    
		    //Each bitmap block has a number of pixels we add to that block
		    for( String block : bitmapBlocks){
		    	int numberOfPixels = Integer.parseInt(block);
		    	bitmap.addBlock(numberOfPixels);
		    }
		    dataSet.addToDataSet(bitmap);
		}
		csvReader.close();
	}
	
	public DataSet getDataSet(){
		return dataSet; 
	}

}
