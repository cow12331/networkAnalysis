import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
//get tweets by twitter API
public class WriteMultiTimeline {
	public static void main(String[] args) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
		String[] str = { "AlphaWolfTradin", "NYDNBenChapman","dbiello","_natw","Penenberg", "danielptucker","manoushz","nntaleb","dustynrobots","jraitamaa" };
		int count = 0;
		for (String s : str) {
			FileWriter fw = new FileWriter("target/"+s+".txt");
			BufferedWriter bw1 = new BufferedWriter(fw);
			try {
				List<Status> statuses;
				String user;
				String out = "";
				Paging page = new Paging(1, 200);
				user = s;
				statuses = twitter.getUserTimeline(user, page);
				for (Status status : statuses) {
					String test = "@" + status.getUser().getScreenName()
							+ " - " + status.getText() + "\n";
					out += test;
				}
				bw1.write(out);
				bw1.flush();
				bw1.close();
				System.out.println(s+" finished "+count);
			} catch (TwitterException te) {
				te.printStackTrace();
				System.out
						.println("Failed to get timeline: " + te.getMessage());
				System.exit(-1);
			}
		}
		System.out.println("All finished");
	}
}
