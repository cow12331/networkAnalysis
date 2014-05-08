import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Parser {

	public static void main(String[] args) throws IOException, Exception {
		String tweet = "Gladstone Land Corp announces earnings. $0.05 EPS. Beats estimates. $1.50m revenue. http://ift.tt/1iVRis9 $LAND3 $LAND2 $LAND";
		String twitterTime = "Fri Apr 25 18:14:57 EDT 2014";

		// get stockname from tweet
		ArrayList<String> stockNames = getStockName(tweet);
		// get attitude from tweet
		SentiAnalyse sentiAnalyse = new SentiAnalyse();
		String attitude = sentiAnalyse.myEvaluate(tweet);
		// get stock actual price from tweet
		StockCompare4 stockCompare = new StockCompare4();
		for(String stockName : stockNames) {
		StockCompare4.isCorrect(stockName, twitterTime, attitude);

		System.out.println("r=" + StockCompare4.accuracy());
		// show outcome
		StockCompare4.isCorrect(stockName, twitterTime, attitude);
		System.out.println("Tweet-->" + tweet);
		System.out.println("stockname-->" + stockName);
		System.out.println("Attitude-->" + attitude);
		}
	}

	public static ArrayList<String> getStockName(String tweet) {
		StringTokenizer st = new StringTokenizer(tweet);
		ArrayList<String> stockNames = new ArrayList<String>();

		while (st.hasMoreTokens()) {
			String currentToken = st.nextToken();

			if (currentToken.charAt(0) == '$' && currentToken.length() > 1
					&& Character.isAlphabetic(currentToken.charAt(1))) {
				// System.out.println(currentToken.substring(1));
				String stockName = currentToken.substring(1);
				if (!stockNames.contains(stockName)) {
					stockNames.add(currentToken.substring(1));
				}
			}
		}
		return stockNames;
	}
}
