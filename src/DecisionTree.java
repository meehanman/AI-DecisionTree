import java.util.Arrays;

public class DecisionTree {
//http://www.saedsayad.com/decision_tree.htm
	
	//Get a line from the test data and find it's location [Target] 
	public static void Run() {
		for (int i = 0; i < ai.test.columnLength; i++) {
			float[] row = ai.test.getRow(i);
			int missingValLoc = -1;
			// Loop through each value in this row
			for (int o = 0; o < row.length; o++) {
				float value = row[o];
				int valInt = Math.round(value);
				if (missingValLoc != -1 && valInt == 0) {
					///If the happens, all hell breaks loose
					System.out.println("More than one missing value");
					return;
				}
				if (valInt == 0) {
					missingValLoc = o;
				}
			}
			// Take this row and search through train for a similar row
			if (missingValLoc != -1) {
				// findRowInTrain(i,missingValLoc);
				CreateTree(i, missingValLoc);
				return;
			} else {
				System.out.println("No Missing Value Locations");
			}
		}
	}
	//In train data, look through each value and 
	public static void CreateTree(int rowLocation, int missingValLoc){
		float[] testRow = ai.test.getRow(rowLocation);
		float[] trainRow = null;
		int level = 0;
		
		for (int columnNum = 0; columnNum < ai.train.columnLength; columnNum++) {
			trainRow = ai.train.getRow(columnNum);
			for (int RowNum = 0; RowNum < trainRow.length; RowNum++) {
				
			}
		}
		
		System.out.println(Arrays.toString(testRow));
	}
}
