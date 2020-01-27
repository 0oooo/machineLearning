package coursework2;

import java.util.ArrayList;

public class DataSet {
	
	private ArrayList<Bitmap> dataSet; 
	
	public DataSet(){
		dataSet = new ArrayList<Bitmap>();
	}
	
	public void addToDataSet(Bitmap bitmap){
		dataSet.add(bitmap);
	}
	
	public ArrayList<Bitmap> getDataSet(){
		return dataSet; 
	}
	
	public void printDataSet(){
		for(Bitmap bitmap : dataSet){
			System.out.println("Bitmap: ");
			bitmap.printBitmap();
			System.out.println("\n");
		}
	}
	
	public int getNumberOfBitmaps() {
		return dataSet.size(); 
	}
	
	public void test(DataSet otherList){
		Bitmap bitmap1 = dataSet.get(2);
		for(int otherBitmap = 0; otherBitmap < dataSet.size(); otherBitmap++){
			Bitmap bitmap2 = otherList.getDataSet().get(otherBitmap);
			bitmap1.calculateEuclideanDistance(bitmap2);
		}
		System.out.print("1: "); 
		bitmap1.printBitmap();
		System.out.print("\n");
		System.out.print("closest Bitmap: ");
		int indexOfClosest = bitmap1.returnClosestBitmapIndex();
		System.out.print("(index " + indexOfClosest + "): ");
		otherList.getDataSet().get(indexOfClosest).printBitmap();
	}
	
	public void testBitmap() {
		double distance = dataSet.get(0).calculateEuclideanDistance(dataSet.get(1));
		System.out.println("distance between 1 and 2 is " + distance);	
	}
}
