import java.awt.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class ai {
	static Data train = null;
	static Data test = null;
	static String FileName = "No-Filename-Given";
	public static void main(String[] args) {
		train = new Data("train");
		test = new Data("test");

		// Tools.overview(train);
		// Tools.overview(test);

		KnnAttempt1();
		
		test.save(FileName, false);
		test.save(FileName, true);
	}

	// Loop through each row, and column in a set
	// looking for a value of 0, then look through the train
	// set for a similar row i.e.
	// 1 1 1 1 0 1 1 1 <-- Missing 0
	// 1 1 1 1 2 1 1 1 <-- Has a 2 instead
	public static void KnnAttempt1() {
		for (int i = 0; i < test.columnLength; i++) {
			float[] row = test.getRow(i);
			int missingValLoc = -1;
			// Loop through each value in this row
			for (int o = 0; o < row.length; o++) {
				float value = row[o];
				int valInt = Math.round(value);
				if (missingValLoc != -1 && valInt == 0) {
					// test.set(i,o,5.55f);
					System.out.println("More than one missing value");
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

	public static void findRowInTrain(int rowLocation, int location) {
		float[] TestRow = test.getRow(rowLocation);
		// Get row
		// For every row in train
		// Go through each value and tally up how simular the row is value by
		// value
		int score = 0;
		int scoreOne = 0;
		int scoreTwo = 0;
		int hScore = 0;
		float[] hScoreArr = null;
		for (int i = 0; i < train.columnLength; i++) {
			score = 0;
			float[] trainRow = train.getRow(i);
			for (int o = 0; o < trainRow.length; o++) {
				// If the values match, then increase score
				if (trainRow[o] == TestRow[o]) {
					score++;
				}
			}
			// If there are multiple highscores, we should see what is the
			// highest prob of a value to appear.
			// 4nn
			// The scoreOne and scoreTwo should only be upgraded if it's on the
			// tie
			if (score > hScore) {
				hScore = score;
				hScoreArr = trainRow;
				if (Math.round(trainRow[location]) == 1) {
					scoreOne++;
				} else {
					scoreTwo++;
				}
			}
		}
		// Edged further towards a 2 than a 1. Not sure what to do with a tie
		int intGuess = 0;
		float betterGuessTotal = scoreOne + scoreTwo;
		float betterGuessOne = (scoreOne / betterGuessTotal) + 1;
		float betterGuessTwo = 2 - (scoreTwo / betterGuessTotal);
		float betterGuess = betterGuessOne;
		if (scoreOne < scoreTwo) {
			intGuess = 1;
		} else if (scoreOne > scoreTwo) {
			intGuess = 2;
		} else {
			intGuess = ThreadLocalRandom.current().nextInt(0, 2);
		}
		// System.out.println(hScore+" -"+scoreOne+"/"+scoreTwo+"-
		// i("+intGuess+") f("+betterGuessOne+") f("+betterGuessTwo+")
		// "+Arrays.toString(TestRow)+" /// "+Arrays.toString(hScoreArr));
		System.out.println("CV: " + TestRow[location] + " ->> " + betterGuess);
		TestRow[location] = betterGuess;
		test.setRow(rowLocation, TestRow);
	}

	// 4Nn
	// - Rank all lines of code from the train data in regards to top 4 matching
	// rows.
	// [12,9,8,6] for each row. The get the value at [0] that is missing and
	// compare the values
	// in all Nearest Neigbours to get a tally of 1. the closest value it could
	// be and 2. the possible integer values
	public static void Fournn(int rowLocation, int valLocation) {
		// train <- Training Data to compare
		// test <- row that is missing a 1
		float[] testRow = test.getRow(rowLocation);
		float[] trainRow = null;
		int[][] scores = new int[train.columnLength][2];
		// System.out.println("Looking for matches for this
		// row:\n\t\t>"+Arrays.toString(testRow));
		// Loop through to get rows of train data set
		for (int columnNum = 0; columnNum < train.columnLength; columnNum++) {
			// Set current row
			trainRow = train.getRow(columnNum);
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
		// tally(scores);
		// We now have an array that contains all the scores of all the rows in
		// train vs the row in test with the missing value.
		//System.out.println(Arrays.toString(testRow)+" find location "+valLocation);
		
		
		//getTallyFromScoreForNResults(scores,valLocation,30);
		
		//getTallyFromScoreForNScore(scores,valLocation,3);
		
		//Submission number 5 (2 of them are hidden). You obtained score as follows: RMSE=0.27099 and Hamming=0.6396 (as small as better)
		//Submission number 9 (5 of them are hidden). You obtained score as follows: RMSE=0.29917 and Hamming=0.8903 (as small as better)	
//		FileName = "2-dec-nn3";
//		int result[] = getTallyFromScoreForNScore(scores,valLocation,3);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		
		//Submission number 3 (1 of them are hidden). You obtained score as follows: RMSE=0.27201 and Hamming=0.6486 (as small as better)
//		Submission number 8 (5 of them are hidden). You obtained score as follows: RMSE=0.31980 and Hamming=0.954 (as small as better)
//		FileName = "2-dec-nn4";
//		int result[] = getTallyFromScoreForNScore(scores,valLocation,4);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		
		//Submission number 7 (4 of them are hidden). You obtained score as follows: RMSE=0.33572 and Hamming=0.9824 (as small as better)
//		FileName = "2-dec-nn5";
//		int result[] = getTallyFromScoreForNScore(scores,valLocation,5);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));

//    	Submission number 16 (8 of them are hidden). You obtained score as follows: RMSE=0.25323 and Hamming=0.4126 (as small as better)
//		FileName = "2-dec-NResults15";
//		int result[] = getTallyFromScoreForNResults(scores,valLocation,15);	
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		
		FileName = "23-dec-NResults15AndNScore15";
		int result[] = getTallyFromScoreForNResults(scores,valLocation,15);	
		int result2[] = getTallyFromScoreForNScore(scores,valLocation,3,15);	
		result[0] = result2[0] + result[0];result[1] = result2[1] + result[1];result[2] = result2[2] + result[2];
		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		
//      Submission number 12 (7 of them are hidden). You obtained score as follows: RMSE=0.25893 and Hamming=0.4952 (as small as better)
//		FileName = "2-dec-NResults25-check2";
//		int result[] = getTallyFromScoreForNResults(scores,valLocation,25);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
//		
//		Submission number 10 (5 of them are hidden). You obtained score as follows: RMSE=0.27202 and Hamming=0.6489 (as small as better)
//      Submission number 11 (6 of them are hidden). You obtained score as follows: RMSE=0.32140 and Hamming=0.1033 (as small as better)
//		FileName = "2-dec-NResults50";
//		int result[] = getTallyFromScoreForNResults(scores,valLocation,50);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		
		//Submission number 6 (3 of them are hidden). You obtained score as follows: RMSE=0.29648 and Hamming=0.7805 (as small as better)
//		FileName = "2-dec-NResults75";
//		int result[] = getTallyFromScoreForNResults(scores,valLocation,75);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));
		
		//Submission number 6 (3 of them are hidden). You obtained score as follows: RMSE=0.29648 and Hamming=0.7805 (as small as better)
//		FileName = "2-dec-NResults100";
//		int result[] = getTallyFromScoreForNResults(scores,valLocation,100);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));

//		Submission number 14 (7 of them are hidden). You obtained score as follows: RMSE=0.27288 and Hamming=0.6499 (as small as better)
//		FileName = "2-dec-NN_50_cm_rd_decay";
//		int result[] = getTallyFromScoreForNScore(scores,valLocation,4,50);		
//		test.set(rowLocation, valLocation, getBestResultFromTwoValues(result[1],result[2]));		
	}
	
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
	 */
	public static int[] getTallyFromScoreForNResults(int[][] scores,int valLocation, int n){
		//Sort score array into order smallest to largest i.e. [[65,4], ... ,[233,13]] 
		// where the last value is the top result trainRow[233] with score 13
		Arrays.sort(scores, Comparator.comparingInt(arr -> arr[1]));
		int[] scoreResult = new int[3];
		//Get top/last n scores from scores
		for(int i=scores.length-1;i>=scores.length-n;i--){
			//System.out.println(Arrays.toString(scores[i])+" > "+Arrays.toString(train.getRow(scores[i][0])) +" "+train.getRow(scores[i][0])[valLocation]);
			scoreResult[Math.round(train.getRow(scores[i][0])[valLocation])]++;
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
			int thisRowVal = (int) train.getRow(scores[i][0])[valLocation];
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

	public static void tally(int[] data) {
		int[] tally = new int[14];
		for (int d : data) {
			tally[d]++;
		}
		System.out.println(Arrays.toString(tally));
	}
}