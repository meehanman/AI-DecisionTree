import java.io.IOException;
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
			System.out.println("Failed");
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
	
	public float[] getRow(int row){
		return data.get(row);
	}
	
	public float[] getColumn(int column){
		float[] columnArr = new float[columnLength];
		int i = 0;
		for (float[] row : data) {
			columnArr[i++] = row[column];
		}
		return columnArr;
	}

}
