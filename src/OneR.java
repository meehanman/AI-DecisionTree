public class OneR {

	// ai.test = testData
	// ai.train = trainData

	public static void Run() {

		// Loop through each value
		for (int row = 0; row < ai.test.columnLength; row++) {
			for (int column = 0; column < ai.test.rowLength; column++) {
				// Find location of 0.0
				if ((int) ai.test.get(row, column) == 0) {
					RunRules(row,column);
				}
			}

		}
	}

	// Build a frequency table for each value that matches each value of the row
	// with the unknown value in train
	public static void RunRules(int row, int column) {
		float[] testRow = ai.test.getRow(row);
		float[] results = new float[testRow.length];
		
		//Look through each column getting the highest frequency for 1 to appear and save in array.
		for(int col = 0;  col < ai.test.rowLength; col++) {
			results[col] = RunRule(col,(int) testRow[col],column);
		}
		
		//Get the highest and lowest values of the list and whatever is closest to 1 or 2, thats the value we're keeping
		float max = results[0];
		float min = results[0];
		
		for (int i = 1; i < results.length; i++) {
			if(results[i]>max){
				max = results[i];
			}
			if(results[i]<min){
				min = results[i];
			}
		}
		
		//How far are the values away from 0
		max = Math.abs(max-2);
		min = Math.abs(min-1);
		
		//If max is further away from 0 than min, then value=1, else value=2
		if(min<max){
			ai.test.set(row, column, 1);
		}else{
			ai.test.set(row, column, 2);
		}
		
	}

	// Compares two columns, row by row, the change of CompareLocB equalling 1
	public static float RunRule(int ColumnToCompare, int ValueOfColumn, int ColumnOfMissingValue) {
		// Loop through each value
		int[] result = new int[2];
		for (int row = 0; row < ai.train.columnLength; row++) {
			//If val at column is valtoFind, get LocationOfMissingValue from current Row
			if((int)ai.train.get(row, ColumnToCompare) == ValueOfColumn){
				result[(int) (ai.train.get(row, ColumnOfMissingValue)-1)]++;
			}
		}
		//Result now will show the frequency of 1 or 2 appearing for our missing value from that column
		return getBestResultFromTwoValues(result[0],result[1]);
	}
	
	//Takes two values and returns a value of probability between the two values
	public static float getBestResultFromTwoValues(int one, int two) {
		int total = one + two;
		float oneDtwoPlusOne = ((float) two / (float) total);
		oneDtwoPlusOne = (float) oneDtwoPlusOne + 1f;
		//System.out.println(
		//		one + "+" + two + "=" + total + "; two/total=" + (oneDtwoPlusOne - 1f) + " +1=" + (oneDtwoPlusOne));
		return oneDtwoPlusOne;
	}
}
