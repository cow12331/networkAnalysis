import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			long cursor = -1;
			IDs ids;
			System.out.println("Listing following ids.");
			int count = 0;

			ids = twitter.getFollowersIDs(18290951L, cursor);
			for (long id : ids.getIDs()) {
				count++;
				System.out.println("18290951"+" "+ id);
			}
			System.out
					.println(count + "**************************************");


		} catch (TwitterException te) {
			te.printStackTrace();
			System.out
					.println("Failed to get friends' ids: " + te.getMessage());
			System.exit(-1);
		}
	}

}
