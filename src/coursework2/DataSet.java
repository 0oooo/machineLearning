package coursework2;

import java.util.ArrayList;

public class DataSet {
	
	private ArrayList<Bitmap> bitmapsList; 
	
	public DataSet(){
		bitmapsList = new ArrayList<Bitmap>();
	}
	
	public void addToList(Bitmap bitmap){
		bitmapsList.add(bitmap);
	}
	
	public ArrayList<Bitmap> getList(){
		return bitmapsList; 
	}
	
	public void printList(){
		for(Bitmap bitmap : bitmapsList){
			System.out.println("Bitmap: ");
			bitmap.printBitmap();
			System.out.println("\n");
		}
	}
	
	public void test(DataSet otherList){
		Bitmap bitmap1 = bitmapsList.get(2);
		for(int otherBitmap = 0; otherBitmap < bitmapsList.size(); otherBitmap++){
			Bitmap bitmap2 = otherList.getList().get(otherBitmap);
			bitmap1.calculateEuclideanDistance(bitmap2);
		}
		System.out.print("1: "); 
		bitmap1.printBitmap();
		System.out.print("\n");
		System.out.print("closest Bitmap: ");
		int indexOfClosest = bitmap1.returnClosestBitmapIndex();
		System.out.print("(index " + indexOfClosest + "): ");
		otherList.getList().get(indexOfClosest).printBitmap();
	}
}
