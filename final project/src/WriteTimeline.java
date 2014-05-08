/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		
		String[] ids = { "iHugoAlbert", "LaMonicaBuzz", "Stockaholics",
				"SamanthaLaDuc", "KeeneOnMarket", "AlertTrade", "portefeuillefun",
				"MargieTBBJ", "LouisvilleWhale", "RealFanboy101"}; 
		//String[] ids = { "iHugoAlbert"};
		for(String id: ids) {
			writeFile("target/"+id+".txt", getContent(id));
		}
		/**
		 * Twitter twitter = new TwitterFactory().getInstance(); FileWriter fw =
		 * new FileWriter("test.txt"); BufferedWriter bw1 = new
		 * BufferedWriter(fw); Parser parser = new Parser(); StockCompare4
		 * stockCompare = new StockCompare4(); SentiAnalyse sentiAnalyse = new
		 * SentiAnalyse(); try { List<Status> statuses; String user; String out
		 * = ""; Paging page = new Paging(10, 200); int count = 0; // user =
		 * "AmericanBanking"; user = "iHugoAlbert"; statuses =
		 * twitter.getUserTimeline(user, page);
		 * 
		 * for (Status status : statuses) { // get tweet text String tweet =
		 * status.getText(); // get time String time = status.getCreatedAt() +
		 * " ";
		 * 
		 * ArrayList<String> stockNames = parser.getStockName(tweet); for
		 * (String stockName : stockNames) { String attitude =
		 * sentiAnalyse.myEvaluate(tweet); System.out.println(tweet);
		 * System.out.println(stockName + " " + time + " " + attitude);
		 * System.out.println("========" + (++count) + "======"); out +=
		 * stockName + " " + time + " " + attitude + "\n";
		 * StockCompare4.isCorrect(stockName, time, attitude); } }
		 * System.out.print("r=" + StockCompare4.accuracy()); out += "\n" + "r="
		 * + StockCompare4.accuracy(); bw1.write(out); bw1.flush(); bw1.close();
		 * 
		 * } catch (TwitterException te) { te.printStackTrace();
		 * System.out.println("Failed to get timeline: " + te.getMessage());
		 * System.exit(-1); }
		 */
	}

	public static String getContent(String id) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();

		Parser parser = new Parser();
		StockCompare4 stockCompare = new StockCompare4();
		SentiAnalyse sentiAnalyse = new SentiAnalyse();

		List<Status> statuses;
		String user;
		String out = "";
		Paging page = new Paging(10, 200);
		int count = 0;
		user = id;
		statuses = twitter.getUserTimeline(user, page);

		for (Status status : statuses) {
			// get tweet text
			String tweet = status.getText();
			// get time
			String time = status.getCreatedAt() + " ";

			ArrayList<String> stockNames = parser.getStockName(tweet);
			for (String stockName : stockNames) {
				String attitude = sentiAnalyse.myEvaluate(tweet);
				System.out.println(tweet);
				System.out.println(stockName + " " + time + " " + attitude);
				System.out.println("---------" + (++count) + "-----------");
				out += (tweet + "\n");
				out += (stockName + " " + time + " " + attitude + "\n");
				StockCompare4.isCorrect(stockName, time, attitude);
				count++;
			}
		}
		System.out.println(id + "'s accuracy is " + StockCompare4.accuracy());
		System.out.println(id + " has completed!!!");
		out += ("\n" + "number of stock is " + count+"\n");
		out += ("r=" + StockCompare4.accuracy());
		return out;
	}

	public static void writeFile(String fileName, String inputStr)
			throws Exception {
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(inputStr);
		bw.flush();
		bw.close();
	}
}
