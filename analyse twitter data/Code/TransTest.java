import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class TransTest {

	/**
	 * @param args
	 */
	private static final long inf = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception {

		List<String> saveUnique = chooseUniqueString("E:/poly project/cssahw1/twitter.txt");
		List<TransSet> mapSet= transStringToInt(saveUnique);
		
		FileReader fr = new FileReader("E:/poly project/cssahw1/twitter.txt");
		BufferedReader br = new BufferedReader(fr);		
		String line = "";
		//List<String> saveUnique = new ArrayList<String>();
		
		while ((line = br.readLine()) != null) {
			String s = line;
			String[] sa = s.split(" ");
			String nodeA = fromIdtoNode(sa[0], mapSet)+"";
			String nodeB = fromIdtoNode(sa[1], mapSet)+"";
			System.out.println(nodeA+" "+nodeB+" 1");
		}
		br.close();
		
		//System.out.println(fromIdtoNode("138140005", mapSet));
	}
	
	public static int fromIdtoNode(String id, List<TransSet> savelist) {
		int out = 0;
		for(TransSet i : savelist) {
			if(i.id.equals(id)) {
				out = i.node;
				break;
			}
		}
		return out;
	}
		
	public static List<TransSet> transStringToInt(List<String> arrlist) {
		List<TransSet> map = new ArrayList<TransSet>();
		for(int i = 0; i < arrlist.size(); i++) {
			TransSet saveMap = new TransSet(i);
			saveMap.setId(arrlist.get(i));
			map.add(saveMap);
		}
		return map;
		//for(TransSet i : map) {
		//	System.out.println(i.node+"-->"+i.id);
		//}
	}

	public static List<String> chooseUniqueString(String path) throws Exception {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		int max = Integer.MIN_VALUE;
		String line = "";
		String line2 = "";
		List<String> saveUnique = new ArrayList<String>();
		
		while ((line = br.readLine()) != null) {
			String s = line;
			String[] sa = s.split(" ");
			
			if(!saveUnique.contains(sa[0])) {
				saveUnique.add(sa[0]);
			}
			if(!saveUnique.contains(sa[1])) {
				saveUnique.add(sa[1]);
			}						
		}
		br.close();
		return saveUnique;
		//for(String i : saveUnique) {
		//	System.out.println(i);
		//}
	}
	

}
