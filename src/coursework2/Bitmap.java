package coursework2;

import java.util.ArrayList;

public class Bitmap {
	
//	private ArrayList<BitmapBlock> bitmapBlocks; 
	
	private ArrayList<Integer> numberOfPixels; 
	private int representedNumber; 
	private Bitmap closestBitmap;
	private int closestBitmapIndex; 
	private double distanceWithBitmap; 
	
	public Bitmap(){
//		bitmapBlocks = new ArrayList<BitmapBlock>();
		numberOfPixels  = new ArrayList<Integer>();
		distanceWithBitmap = -1;
	}
	
//	public void addBlock(BitmapBlock block){
//		if(bitmapBlocks.size() == 65){
//			representedNumber = block.getPixels(); 
//		}
//		bitmapBlocks.add(block);
//	}
	
	public void addBlock(int pixels){
		if(numberOfPixels.size() == 65){
			representedNumber = pixels; 
		}
		numberOfPixels.add(pixels);
	}
	
	public int getRepresentedNumber(){
		return representedNumber;
	}
	
//	public int bitmapSize(){
//		return bitmapBlocks.size(); 
//	}
	
	public int listOfPixelsSize() {
		return numberOfPixels.size(); 
	}
	
//	public void printBitmap(){
//		for(BitmapBlock block : bitmapBlocks){
//			System.out.print(block.getPixels() + " ");
//		}
//	}
	
	public void printBitmap() {
		for(Integer pixels : numberOfPixels) {
			System.out.print(pixels);
		}
	}
	
//	public double calculateEuclideanDistance(Bitmap otherBitmap){
//			
//		int totalDistancePerBlock = 0; 
//		int bitmapBlock = 0;
//		
//		// this.bitmapSize() - 1 because we don't want to calculate the last number which is the represented number
//		for(bitmapBlock = 0; bitmapBlock < this.bitmapSize() - 1; bitmapBlock++){ 
//			int numberOfPixel1 = this.bitmapBlocks.get(bitmapBlock).getPixels();
//			int numberOfPixel2 = otherBitmap.bitmapBlocks.get(bitmapBlock).getPixels();
//			
//			totalDistancePerBlock += (numberOfPixel1 - numberOfPixel2) * (numberOfPixel1 - numberOfPixel2);
//		}
//		return totalDistancePerBlock; 
//	}
	
	public double calculateEuclideanDistance(Bitmap otherBitmap){
		
		int totalDistancePerBlock = 0; 
		int bitmapBlock = 0;
		
		// this.bitmapSize() - 1 because we don't want to calculate the last number which is the represented number
		for(bitmapBlock = 0; bitmapBlock < this.listOfPixelsSize() - 1; bitmapBlock++){ 
			int numberOfPixel1 = this.numberOfPixels.get(bitmapBlock);
			int numberOfPixel2 = otherBitmap.numberOfPixels.get(bitmapBlock);
			
			totalDistancePerBlock += (numberOfPixel1 - numberOfPixel2) * (numberOfPixel1 - numberOfPixel2);
		}
		return totalDistancePerBlock; 
	}
	
	public int returnClosestBitmapIndex(){
		return closestBitmapIndex;
	}
	
	public Bitmap returnClosestBitmap(){
		return closestBitmap; 
	}
	
	public double returnSmallestDistance(){
		return distanceWithBitmap; 
	}

}
