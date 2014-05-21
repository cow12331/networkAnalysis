/**
 * main program, use this program to get information and run analyse method
 */

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteTimeline {
	public static void main(String[] args) throws Exception {

		/*
		 * String[] ids = { "iHugoAlbert", "LaMonicaBuzz", "Stockaholics",
		 * "SamanthaLaDuc", "KeeneOnMarket", "AlertTrade", "portefeuillefun",
		 * "MargieTBBJ", "LouisvilleWhale", "RealFanboy101"};
		
		
		String[] ids = { "herbgreenberg", "macd_man", "Akos_Fintor",
				"ACInvestorBlog", "OMillionaires", "nicoleurken",
				"SamanthaLaDuc", "jimcramer", "DeidreZune", "TheDestinyShow" };*/
		String[] ids = {"herbgreenberg"};
		
		for (String id : ids) {			
			writeFile("target/" + id + ".txt", getMoreContent(id));
		}
	}

	// catch all stocks each tweet
	public static String getMoreContent(String id) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();

		Parser parser = new Parser();
		StockCompare4 stockCompare = new StockCompare4();
		SentiAnalyse sentiAnalyse = new SentiAnalyse();
		CleanText cleanText = new CleanText();

		List<Status> statuses;
		String user;
		String out = "";
		int count = 0;

		for (int i = 1; i < 2; i++) {
			Paging page = new Paging(i, 200);
			user = id;
			statuses = twitter.getUserTimeline(user, page);
			for (Status status : statuses) {
				// get tweet text
				String oriTweet = status.getText();
				String tweet = cleanText.cleanText(oriTweet);
				// get time
				String time = status.getCreatedAt() + " ";
				ArrayList<String> stockNames = parser.getStockName(tweet);
				for (String stockName : stockNames) {
					count++;
					String attitude = sentiAnalyse.myEvaluate(tweet);
					System.out.println(tweet);
					System.out.println(stockName + " " + time + " " + attitude);
					System.out.println("---------" + count + "-----------");
					out += (tweet + "\n");
					out += (stockName + "," + time + "," + attitude + "\n");
					StockCompare4.isCorrect(stockName, time, attitude);
					// count++;
				}
			}
		}
		System.out.println(id + "'s accuracy is " + StockCompare4.accuracy());
		System.out.println(id + " has completed!!!");
		out += ("\n" + "number of stock is " + count + "\n");
		out += ("r=" + StockCompare4.accuracy());
		return out;
	}

	//output file
	public static void writeFile(String fileName, String inputStr)
			throws Exception {
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(inputStr);
		bw.flush();
		bw.close();
	}
}
