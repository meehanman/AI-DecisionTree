import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	public String train;
	public String test;
	public Data() {
		this.test = read("test");
	}
	
	public static String read(String s){
		try (BufferedReader br = new BufferedReader(new FileReader("data/"+s+".txt")))
		{
			String save = null;
			String sCurrentLine;
			int lines = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				lines++;
				save+=sCurrentLine;
			}
			System.out.println(lines+" lines loaded for "+s+".");
			return save;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return s+" failed";

	}

}
