public class CleanText {	
	public static String cleanText(String s){
        String[] w=s.split(" ");
        String out = "";
        for(String str : w){
            if(str.length() > 6 && str.substring(0, 4).equals("http")) continue;
            out += str + " ";
        }       
        return out;
	}
}
