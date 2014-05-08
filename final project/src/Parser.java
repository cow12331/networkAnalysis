import java.io.IOException;
import java.util.StringTokenizer;


public class Parser {

	public static void main(String[] args) throws IOException, Exception {
		String tweet = "Gladstone Land Corp announces earnings. $0.05 EPS. Beats estimates. $1.50m revenue. http://ift.tt/1iVRis9  $ #LAND";
		String twitterTime = "Fri Apr 25 18:14:57 EDT 2014";
		
		//get stockname from tweet
		String stockName = getStockName(tweet);
		//get attitude from  tweet
		SentiAnalyse sentiAnalyse = new SentiAnalyse();
		String attitude = sentiAnalyse.myEvaluate(tweet);
		//get stock actual price from tweet
		StockCompare4 stockCompare = new StockCompare4();
		StockCompare4.isCorrect(stockName, twitterTime, attitude);
		
		System.out.println("r="+StockCompare4.accuracy());
		//show outcome
		if(!stockName.equals("WrongStock")) {
			StockCompare4.isCorrect(stockName, twitterTime, attitude);
		}		
		System.out.println("Tweet-->" + tweet);
		System.out.println("stockname-->" + stockName);
		System.out.println("Attitude-->" + attitude);		
	}

	public static String getStockName(String tweet) {
	     StringTokenizer st = new StringTokenizer(tweet);
	     while (st.hasMoreTokens()) {
	         String currentToken = st.nextToken();
	         
	         if(currentToken.charAt(0) == '$' && currentToken.length() > 1 && Character.isAlphabetic(currentToken.charAt(1))) {
	        	 //System.out.println(currentToken.substring(1));
	        	 return currentToken.substring(1);
	         }
	     }
	     return "WrongStock";
	}
}
