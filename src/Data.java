import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Data {

	final int rowLength = 14;
	int columnLength = 0;
	String name = null;
	ArrayList<float[]> data = new ArrayList<float[]>();

	public Data(String name) {
		if (name == "test" || name == "train") {
			this.name = name;
			read(name);
			
			System.out.println("<< "+name+".txt loaded");
		} else {
			System.out.println("Data must be test or train");
		}
	}

	private void read(String s) {
		try {
			for (String line : Files.readAllLines(Paths.get("data/" + s + ".txt"))) {
				float[] row = new float[rowLength];
				for (int n = 0; n < rowLength; n++) {
					row[n] = Float.valueOf(line.split(" ")[n]);
				}
				data.add(row);
			}
			this.columnLength = data.size();
		} catch (IOException e) {
		}
	}

	public void print() {
		for (float[] row : data) {
			System.out.println(Arrays.toString(row));
		}
	}
	
	public float get(int row,int column){
		return data.get(row)[column];
	}
	
	public void set(int row,int column, float value){
		data.get(row)[column] = value;
	}
	
	public void setRow(int row, float[] value){
		data.set(row, value);
	}
	
	public float[] getRow(int row){
		return data.get(row);
	}
	public void printRow(int row){
		System.out.println(Arrays.toString(getRow(row)));
	}
	
	public float[] getColumn(int column){
		float[] columnArr = new float[columnLength];
		int i = 0;
		for (float[] row : data) {
			columnArr[i++] = row[column];
		}
		return columnArr;
	}
	public void printColumn(int column){
		System.out.println(Arrays.toString(getRow(column)));
	}
	
	public void save(String filename,boolean integer){
		try{
			String FileName;
			if(!integer){
				FileName = "data/output/" + this.name + "-"+filename+".txt";
			}else{
				FileName = "data/output/" + this.name + "-"+filename+"-int.txt";
			}
			PrintWriter writer = new PrintWriter(FileName);
		    String outputLine = "";
		    for(int i=0;i<this.columnLength;i++){
		    	outputLine = "";
		    	for(int o=0;o<this.rowLength;o++){
		    		float value = get(i,o);
		    		//Ensure the DP is at most 6DP
		    		if(!integer){
		    			outputLine += new Float(value).toString().replaceAll("\\.?0*$", "");
		    			outputLine += " ";
		    		}else{
		    			//outputLine += Math.round(value)+" ";
		    			//Fuzzy Logic
		    			if(value>=1.4){
		    				outputLine += "2 ";
		    			}else{
		    				outputLine += "1 ";
		    			}
		    		}
		    	}
		    	writer.println(outputLine);
		    }
		    writer.close();
		    System.out.println(">> "+this.name+".txt saved to '"+FileName);
		} catch (IOException e) {
			System.out.println(">> Error saving "+this.name+".txt");
			System.out.println(e);
		}
	}

}
