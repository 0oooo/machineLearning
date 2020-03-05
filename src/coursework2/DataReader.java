package coursework2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * DataReader
 * 
 * Class that gets a file, and convert it into a DataSet Object
 * 
 * @author CC
 *
 */
public class DataReader {

	private static int DEFAULT_SPLIT_PARTITION = 50;

	private String path;
	private DataSet dataSet;

	/**
	 * Default constructor when no split partition is given
	 * 
	 * @param path of the file to read
	 * @throws IOException if there is any issue with the given file
	 */
	public DataReader(String path) throws IOException {
		this(path, DEFAULT_SPLIT_PARTITION);
	}

	/**
	 * Constructor
	 * @param path to read the file
	 * @param splitSetPartition percentage of the file to use for training
	 * and by deduction the other part is used for testing (not used)
	 * @throws IOException if there is any issue with the given file
	 */
	public DataReader(String path, int splitSetPartition) throws IOException {
		this.path = path;
		dataSet = new DataSet(splitSetPartition);
		readData(splitSetPartition);
	}

	/**
	 * Read the file and split its content into vector representing each row
	 * Add that digit vector to a dataset. 
	 * @param splitSetPartition (not used)
	 * @throws IOException if any issue arises with the file
	 */
	public void readData(int splitSetPartition) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		String row = "";
		while ((row = csvReader.readLine()) != null) {
			//Split and read each element to add them to a vector representing the digit
			DigitVector digitVector = new DigitVector();
			String[] pixelGroup = row.split(",");
			for (String pixel : pixelGroup) {
				double pixelValue = Double.parseDouble(pixel);
				digitVector.addPixelGroup(pixelValue);
			}
			//add the vector representing a digit to the dataset
			dataSet.addToDataSet(digitVector);
		}
		dataSet.splitDataSet(splitSetPartition);
		dataSet.generateSimpleVector();
		csvReader.close();
	}

	/** 
	 * @return the dataset as DataSet Object
	 */
	public DataSet getDataSet() {
		return dataSet;
	}

}
