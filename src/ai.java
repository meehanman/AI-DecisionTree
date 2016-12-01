import java.awt.List;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ai {
	static Data train = null;
	static Data test = null;
	
	public static void main(String[] args){
		train = new Data("train");
		test = new Data("test");

		//Tools.overview(train);
		//Tools.overview(test);
		
		KnnAttempt1();
	}
	
	//Loop through each row, and column in a set
	//looking for a value of 0, then look through the train
	//set for a similar row i.e. 
	//1 1 1 1 0 1 1 1 <-- Missing 0
	//1 1 1 1 2 1 1 1 <-- Has a 2 instead
	public static void KnnAttempt1(){
		for(int i=0;i<test.columnLength;i++){
			float[] row = test.getRow(i);
			int missingValLoc = -1;
			//Loop through each value in this row
	    	for(int o=0;o<row.length;o++){
	    		float value = row[o];
	    		int valInt = Math.round(value);
	    		if(missingValLoc!=-1 && valInt == 0){
	    			//test.set(i,o,5.55f);
	    			System.out.println("More than one missing value");
	    		}
	    		if(valInt == 0){
	    			missingValLoc=o;
	    		}
	    	}
	    	//Take this row and search through train for a similar row
	    	if(missingValLoc!=0){
	    		findRowInTrain(row,missingValLoc);
	    	}
		}
		//test.save("out", false);
		//test.save("out-intRound", true);
	}
	
	public static void findRowInTrain(float[] TestRow,int location){
		//Get row
		//For every row in train
		//Go through each value and tally up how simular the row is value by value
		int score = 0;
		int scoreOne = 0;
		int scoreTwo = 0;
		int hScore = 0;
		float[] hScoreArr = null;
		for(int i=0;i<train.columnLength;i++){
			score = 0;
			float[] trainRow = train.getRow(i);
			for(int o=0;o<trainRow.length;o++){
				//If the values match, then increase score
				if(trainRow[o]==TestRow[o]){
					score++;
				}
			}
			//If there are multiple highscores, we should see what is the highest prob of a value to appear. 
			if(score > hScore){
				hScore = score;
				hScoreArr = trainRow;
				if(Math.round(trainRow[location])==1){
					scoreOne++;
				}else{
					scoreTwo++;
				}
			}
		}
		//Edged further towards a 2 than a 1. Not sure what to do with a tie
		int intGuess = 0;
		float betterGuess = (scoreTwo/(scoreTwo+scoreOne))+1;
		if(scoreOne<scoreTwo){
			intGuess = 1;
		}else if(scoreOne>scoreTwo){
			intGuess = 2;
		}else{
			intGuess = ThreadLocalRandom.current().nextInt(0,2); 
		}
		System.out.println(hScore+" -"+scoreOne+"/"+scoreTwo+"- i("+intGuess+") f("+betterGuess+") "+Arrays.toString(TestRow)+" /// "+Arrays.toString(hScoreArr));
	}

}	