import java.util.Arrays;

public class Tools {
	public static void count(Data d,int val){
		int count = 0;
		for(int i=0;i<d.columnLength;i++){
	    	for(int o=0;o<d.rowLength;o++){
	    		float value = d.get(i,o);
	    		if( Math.round(value) == val ){
	    			count++;
	    		}
	    	}
	    }
		System.out.println(val+": E="+count);
	}
	public static void overview(Data d){
		System.out.println("--"+d.name+"---");
		count(d,0);
		count(d,1);
		count(d,2);
		count(d,3);
	}
	
	public static boolean contains(final int[] arr, final int key) {
	    return Arrays.stream(arr).anyMatch(i -> i == key);
	}
}
