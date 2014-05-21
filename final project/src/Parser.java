import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * use for getting stock name
 * 
 */
public class Parser {
	public static ArrayList<String> getStockName(String tweet) {
		StringTokenizer st = new StringTokenizer(tweet);
		ArrayList<String> stockNames = new ArrayList<String>();

		while (st.hasMoreTokens()) {
			String currentToken = st.nextToken();

			if (currentToken.charAt(0) == '$' && currentToken.length() > 1
					&& Character.isAlphabetic(currentToken.charAt(1))) {
				String stockName = currentToken.substring(1).replaceAll("[^a-zA-Z']", "");
				int tempN = stockName.length();
				if(tempN > 2 && stockName.substring(tempN - 2, tempN).equals("'s")) {
					stockName = stockName.substring(0, tempN - 2);
				}
				if (!stockNames.contains(stockName)) {
					stockNames.add(stockName);
				}
			}
		}
		return stockNames;
	}
}
