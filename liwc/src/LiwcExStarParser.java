import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

//get rid of all star * from original LIWC catalogue
public class LiwcExStarParser {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("LIWC2001_English.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";

		while ((line = br.readLine()) != null) {
			String out = "";
			String s = line;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) != '*') {
					out += s.charAt(i);
				}
			}
			System.out.println(out);
		}
		br.close();
	}
}
