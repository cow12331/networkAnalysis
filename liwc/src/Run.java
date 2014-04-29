import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Run {

	public static void main(String[] args) throws Exception {
		//get map of catalogue and bigfive
		HashMap<String, ArrayList> cataLogueMap = LiwcToCatalogue.getMap("LIWC2001_English_noStar.txt");
		HashMap<String, ArrayList> weightMap = LiwcToCatalogue.getWeightMap("mapFromLiwcToFive.txt");				
		//get tweets data
		String[] twitterName = { "AlphaWolfTradin", "NYDNBenChapman","dbiello","_natw","Penenberg", "danielptucker","manoushz","nntaleb","dustynrobots","jraitamaa" };

		for(String t : twitterName) {
			String report = "";
			//count number of words in catalogue
			int[] test = count("tweets/"+t+".txt", cataLogueMap);
			//count bigfive grade
			double[] testFive = getGigFive(test, weightMap);
			//output and write outcome
			for (int i = 1; i < 69; i++) {
				String catalogue = "number of word in catalogue " + i + " is"+ test[i]+"\n";
				report += catalogue;
			}			
			for(double i : testFive) {
				String bigfive = i+"\n";
				report += bigfive;
			}
			FileWriter fw = new FileWriter("target/"+t+"Report.txt");
			BufferedWriter bw1 = new BufferedWriter(fw);
			bw1.write(report);
			bw1.flush();
			bw1.close();
		}
	}
	
	//count bigfive grade
	static double[] getGigFive(int[] intArr, HashMap<String, ArrayList> map) {
		double[] bigFive = new double[5];
		for(int i = 1; i < intArr.length; i++ ) {
			ArrayList<String> weight = map.get(i+"");
			for(int j = 0; j < 5; j++) {
				double dw = Double.parseDouble(weight.get(j));
				bigFive[j] += (double)intArr[i] * dw;
			}
			
		}
		return bigFive;
	}
	
	//count the number of words in every categories
	static int[] count(String fileName, HashMap<String, ArrayList> map) throws Exception {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		int[] statCatalogue = new int[69];
		
		while ((line = br.readLine()) != null) {
			// exlude , and .
			String afterMyTokenize = lowerLatter(tokenize(line));
			// tokenize
			StringTokenizer tokens = new StringTokenizer(afterMyTokenize);
			while (tokens.hasMoreTokens()) {
				String word = tokens.nextToken();
				if (map.containsKey(word)) {
					ArrayList<String> catalogue = map.get(word);
					for (String i : catalogue) {
						int number = Integer.parseInt(i);
						statCatalogue[number]++;
					}
				}
			}
		}
		br.close();
		return statCatalogue;
		
	}

	//Tokenize function
	// exclude , and .
	static String tokenize(String string) {
		StringTokenizer st = new StringTokenizer(string);
		String out = "";
		
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			int n = temp.length();
			if (temp.substring(n - 1).equals(",")
					| temp.substring(n - 1).equals(".")) {
				out += temp.substring(0, n - 1) + " ";
			} else {
				out += temp + " ";
			}
		}
		return out;
	}
	
	//transform all letter to lower case
	static String lowerLatter(String b) {
		char letters[] = new char[b.length()];
		for (int i = 0; i < b.length(); i++) {
			char letter = b.charAt(i);
			if (letter >= 'A' && letter <= 'Z')
				letter = (char) (letter + 32);
			letters[i] = letter;
		}
		return new String(letters);
	}
}
