import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class LiwcToCatalogue {
	//generate map of catalogue, key is word, value is catalogue
	public static HashMap<String, ArrayList> getMap(String fileName) throws Exception {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = "";		
		HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();		
		
		while ((line = br.readLine()) != null) {
			ArrayList<String> values = new ArrayList<String>();			
			String[] items = line.split("	");			
			for(int i = 1; i < items.length; i++) {
				values.add(items[i]);
			}
			map.put(items[0], values);
		}
		br.close();
		return map;
		
	}
	
	//generate map of bigfive, key is catalogue, value is weight of bigfive
	public static HashMap<String, ArrayList> getWeightMap(String fileName) throws Exception {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = "";		
		HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();
		
		while ((line = br.readLine()) != null) {
			ArrayList<String> weight = new ArrayList<String>();
			ArrayList<String> tempValues = new ArrayList<String>();
			StringTokenizer tokens = new StringTokenizer(line);					
			while (tokens.hasMoreTokens()) {
				String word = tokens.nextToken();
				tempValues.add(word);
			}			
			for(int i = 1; i < tempValues.size(); i++) {
				weight.add(tempValues.get(i));
			}			
			map.put(tempValues.get(0), weight);
		}
		br.close();
		return map;
	}

}
