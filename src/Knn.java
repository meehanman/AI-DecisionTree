import java.util.Arrays;
import java.util.Comparator;

public class KNN {

	// Loop through each row, and column in a set
		// looking for a value of 0, then look through the train
		// set for a similar row i.e.
		// 1 1 1 1 0 1 1 1 <-- Missing 0
		// 1 1 1 1 2 1 1 1 <-- Has a 2 instead
		public static void KnnAttempt1() {
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
					Fournn(i, missingValLoc);
					//return;
				} else {
					System.out.println("No Missing Value Locations");
				}
			}
		}

		/**
		 * Takes in a row and position of 0
		 * Compares the row to every row in train and forwards the results to 
		 * to another function to run tests. 
		 * @param rowLocation Location of missing row of (0)
		 * @param valLocation Location of missing position in the row
		 * 
		 * EXAMPLE: Fournn(2,10)
		 * -------- getTallyFromScoreForNResults(  [[65,4], ... ,[233,13]], 10, 
		 */
		public static void Fournn(int rowLocation, int valLocation) {
			// train <- Training Data to compare
			// test <- row that is missing a 1
			float[] testRow = ai.test.getRow(rowLocation);
			float[] trainRow = null;
			int[][] scores = new int[ai.train.columnLength][2];
			// System.out.println("Looking for matches for this
			// row:\n\t\t>"+Arrays.toString(testRow));
			// Loop through to get rows of train data set
			for (int columnNum = 0; columnNum < ai.train.columnLength; columnNum++) {
				// Set current row
				trainRow = ai.train.getRow(columnNum);
				int thisRowScore = 0;
				for (int RowNum = 0; RowNum < trainRow.length; RowNum++) {
					// If the values match, then increase score
					if (trainRow[RowNum] == testRow[RowNum]) {
						thisRowScore++;
					}
				}
				// Row Calculated for row n
				scores[columnNum][0] = columnNum;
				scores[columnNum][1] = thisRowScore;
			}
			
			//WINNER 🤶🏿for Zero-One (0.1033) vs 0.0709
			ai.FileName = "14-dec-16-NResults50-ZeroOne"; 
			int result[] = getTallyFromScoreForNResults(scores,valLocation,50);		
			ai.test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
			
			//Black Santa (0.25323) vs 0.23612
			//ai.FileName = "14-dec-NResults15-BlackSanta";
			//int result[] = getTallyFromScoreForNResults(scores,valLocation,15);	
			//ai.test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		}
		
		/**
		 * 
		 * @param one
		 * @param two
		 * @return
		 */
		public static float getBestResultFromTwoValues(int one, int two){
			int total = one + two;
			float oneDtwoPlusOne = ((float)two/(float)total);
			oneDtwoPlusOne=(float)oneDtwoPlusOne+1f;
			System.out.println(one+"+"+two+"="+total+"; two/total="+(oneDtwoPlusOne-1f)+" +1="+(oneDtwoPlusOne));
			return oneDtwoPlusOne;
		}
		/**
		 * Input an array [[65,4], ... ,[233,13]] and this 
		 * function will sort it in reverse order of score,
		 * then return a tally of the value of valLocation
		 * found in the top n results.
		 * @param scores
		 * @param valLocation
		 * @param n - The number of results to compare
		 */
		public static int[] getTallyFromScoreForNResults(int[][] scores,int valLocation, int n){
			//Sort score array into order smallest to largest i.e. [[65,4], ... ,[233,13]] 
			// where the last value is the top result trainRow[233] with score 13
			Arrays.sort(scores, Comparator.comparingInt(arr -> arr[1]));
			int[] scoreResult = new int[3];
			//Get top/last n scores from scores
			for(int i=scores.length-1;i>=scores.length-n;i--){
				//System.out.println(Arrays.toString(scores[i])+" > "+Arrays.toString(train.getRow(scores[i][0])) +" "+train.getRow(scores[i][0])[valLocation]);
				scoreResult[Math.round(ai.train.getRow(scores[i][0])[valLocation])]++;
			}
			//This will output an array like [0, 28, 2] denoting 0 0's found, 28 1's found and 2 2's found
			System.out.println(Arrays.toString(scoreResult));
			return scoreResult;
		}
		/**
		 * Input an array [[65,4], ... ,[233,13]] and this 
		 * function will sort it in reverse order of score,
		 * then return a tally of the value of valLocation
		 * found in the top n scores ie n=3 would return the 
		 * tally for the rows with a score of 14,12 and 10 (ie top 3 scores)
		 * n=4 would return the tally for rows with a score of 14,12,10,9
		 * @param scores
		 * @param valLocation
		 * @param n - The number of nearest Neigbours to compare
		 */
		public static int[] getTallyFromScoreForNScore(int[][] scores,int valLocation, int n, int max){
			//Sort score array into order smallest to largest i.e. [[65,4], ... ,[233,13]] 
			// where the last value is the top result trainRow[233] with score 13
			Arrays.sort(scores, Comparator.comparingInt(arr -> arr[1]));
			int[] scoreResult = new int[3];
			int currentScore = -1;
			//Get top/last n scores from scores
			for(int i=scores.length-1;i>=scores.length-51;i--){
				//Expose the values
				int[] thisTrainRowAndScore = scores[i];
				int thisRowLocation = thisTrainRowAndScore[0];
				int thisRowScore = thisTrainRowAndScore[1];
				int thisRowVal = (int) ai.train.getRow(scores[i][0])[valLocation];
				//System.out.println(Arrays.toString(thisTrainRowAndScore)+" > "+Arrays.toString(train.getRow(thisRowLocation)) +" "+thisRowVal);
				//If statement to decreate N if the next score is different
				if(currentScore != thisRowScore){
					currentScore = thisRowScore;
					//System.out.println("Score is "+currentScore);
					n--;	
				}
				//If we have captured n different scores
				if(n<0){break;}
				scoreResult[thisRowVal]++;
			}
			//This will output an array like [0, 28, 2] denoting 0 0's found, 28 1's found and 2 2's found
			System.out.println(Arrays.toString(scoreResult));
			return scoreResult;
		}
}
