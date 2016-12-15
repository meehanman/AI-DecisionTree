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
		
		//K Nearest Neighbour
		//KNN.KnnAttempt1();
		
		//Decision Tree
		//DecisionTree.Run();
		//save("Decision Tree");
		
		//ZeroR
		//ZeroR.Run();
		//save("ZeroR");
		
		//RunZeroRFloat
		//ZeroR.RunZeroRFloat();
		//save("ZeroRFloat");
		
		//RunOneR
		OneR.Run();
		save("RunOneR");
		
	}
	
	public static void save(String name){
		FileName = name;
		//Save Root Mean Square Error (RMSE)
		test.save(FileName, false);
		//Save Criterion: Zero-one error
		test.save(FileName, true);
	}

}