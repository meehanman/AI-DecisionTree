
public class ZeroR {
	// ai.test = testData
	// ai.train = trainData

	// What this method will do is scan the train data, finding the most likely
	// value for each position.
	// It then can go through train and fill in the data that it most likely will
	// be

	public static void Run() {
		// Loop through each Column to find mean value for each column
		int[] columnValue = new int[ai.train.rowLength];
		for (int column = 0; column < ai.train.rowLength; column++) {
			int[] values = new int[2];
			for (int row = 0; row < ai.train.columnLength; row++) {
				int thisRowValue = (int) ai.train.get(row, column);
				values[thisRowValue - 1]++;
			}
			// Check which value is more likely
			if (values[0] == values[1]) {
				columnValue[column] = 3;
			} else if (values[0] >= values[1]) {
				columnValue[column] = 1;
			} else if (values[1] >= values[0]) {
				columnValue[column] = 2;
			}
		}

		// Look through each row in test and fill out the new data based on mean
		// value of column
		for (int row = 0; row < ai.test.columnLength; row++) {
			for (int column = 0; column < ai.test.rowLength; column++) {
				// If the value is missing
				if ((int) ai.test.get(row, column) == 0) {
					ai.test.set(row, column, columnValue[column]);
				}
			}
		}
	}

	// Instead of getting an exact value of 1 or 2, assign the likelyhood of the
	// value to appear
	public static void RunZeroRFloat() {
		// Loop through each Column to find mean value for each column
		float[] columnValue = new float[ai.train.rowLength];
		for (int column = 0; column < ai.train.rowLength; column++) {
			int[] values = new int[2];
			for (int row = 0; row < ai.train.columnLength; row++) {
				int thisRowValue = (int) ai.train.get(row, column);
				values[thisRowValue - 1]++;
			}
			// Assign a double value to the most likely value
			columnValue[column] = getBestResultFromTwoValues(values[0], values[1]);
		}

		// Look through each row in test and fill out the new data based on mean
		// value of column
		for (int row = 0; row < ai.test.columnLength; row++) {
			for (int column = 0; column < ai.test.rowLength; column++) {
				// If the value is missing
				if ((int) ai.test.get(row, column) == 0) {
					ai.test.set(row, column, columnValue[column]);
				}
			}
		}
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
