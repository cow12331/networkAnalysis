import java.net.*;
import java.io.*;
/**
 * 
 * get stock price by Yahoo API
 *
 */
public class StockCompare4{
	static double total=0;
	static double correct=0;
	
	public static double accuracy(){
		return correct/total;
	}
	
	public static void isCorrect(String stock, String time, String predict) throws IOException{
		double comp=compare(stock, time);
		if (predict.toLowerCase().equals("pos")&&comp>0) correct++;
		else if (predict.toLowerCase().equals("neg")&&comp<0) correct++;
		
	}
	
	public static double compare(String stock, String date){
		int diff=0;
		try {
			URL oracle;
			oracle = new URL(urlEncode(stock, date));
			BufferedReader in=new BufferedReader(
			new InputStreamReader(oracle.openStream()));

			total++;
			String inputLine;
			int count=0;
			double[] price=new double[2];
			inputLine = in.readLine();
			while ((inputLine = in.readLine()) != null){
			    String[] split=inputLine.split(",");
			    price[count]=Double.parseDouble(split[4]);
			    count++;
			}
			in.close();
			
			diff=(int) ((price[0]-price[1])*10000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		return diff/10000.0;
        
	}
	
	public static String urlEncode(String stock, String dateStr){
		stock=stock.toUpperCase();
		String[] splitDate=dateStr.split(" ");
		int[] start_date={monthConv(splitDate[1])-1,Integer.parseInt(splitDate[2]),Integer.parseInt(splitDate[5])};
		
		int[] end_date=new int[3];
		// leap year Feb 29 Fri
		if(isLeap(start_date[2])&&start_date[0]==1&&start_date[1]==29&&splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=start_date[0]+1;
			end_date[1]=3;
			end_date[2]=start_date[2];
		}
		// leap year Feb 29
		else if(isLeap(start_date[2])&&start_date[0]==1&&start_date[1]==29){
			end_date[0]=start_date[0]+1;
			end_date[1]=1;
			end_date[2]=start_date[2];
		}
		// leap year Feb 28 Fri
		else if(isLeap(start_date[2])&&start_date[0]==1&&start_date[1]==28&&splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=start_date[0]+1;
			end_date[1]=2;
			end_date[2]=start_date[2];
		}
		// leap year Feb 28
		else if(isLeap(start_date[2])&&start_date[0]==1&&start_date[1]==28){
			end_date[0]=start_date[0];
			end_date[1]=29;
			end_date[2]=start_date[2];
		}
		// not leap year Feb 28 Fri
		else if(!(isLeap(start_date[2]))&&start_date[0]==1&&start_date[1]==28&&splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=start_date[0]+1;
			end_date[1]=3;
			end_date[2]=start_date[2];
		}
		// not leap year Feb 28
		else if(!(isLeap(start_date[2]))&&start_date[0]==1&&start_date[1]==28){
			end_date[0]=start_date[0]+1;
			end_date[1]=1;
			end_date[2]=start_date[2];
		}
		// Dec 31 Fri
		else if(start_date[0]==11&&start_date[1]==31&&splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=0;
			end_date[1]=3;
			end_date[2]=start_date[2]+1;
		}
		// Dec 31
		else if(start_date[0]==11&&start_date[1]==31){
			end_date[0]=0;
			end_date[1]=2;
			end_date[2]=start_date[2]+1;
		}
		// large month day 31 Fri
		else if((start_date[0]==0||start_date[0]==2||start_date[0]==4||start_date[0]==6||start_date[0]==7||start_date[0]==9)&&
				start_date[1]==31&&splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=start_date[0]+1;
			end_date[1]=3;
			end_date[2]=start_date[2];
		}
		// large month day 31
		else if((start_date[0]==0||start_date[0]==2||start_date[0]==4||start_date[0]==6||start_date[0]==7||start_date[0]==9)&&
				start_date[1]==31){
			end_date[0]=start_date[0]+1;
			end_date[1]=1;
			end_date[2]=start_date[2];
		}
		// small month day 30 Fri
		else if((start_date[0]==3||start_date[0]==5||start_date[0]==8||start_date[0]==10)&&
				start_date[1]==30&&splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=start_date[0]+1;
			end_date[1]=3;
			end_date[2]=start_date[2];
		}
		// small month day 30
		else if((start_date[0]==3||start_date[0]==5||start_date[0]==8||start_date[0]==10)&&
				start_date[1]==30){
			end_date[0]=start_date[0]+1;
			end_date[1]=1;
			end_date[2]=start_date[2];
		}
		//general Fri
		else if(splitDate[0].toLowerCase().equals("fri")){
			end_date[0]=start_date[0];
			end_date[1]=start_date[1]+3;
			end_date[2]=start_date[2];
		}
		// general case
		else{
			end_date[0]=start_date[0];
			end_date[1]=start_date[1]+1;
			end_date[2]=start_date[2];
		}
		return "http://ichart.yahoo.com/table.csv?s="+stock+"&d="+end_date[0]+"&e="+end_date[1]+"&f="+end_date[2]+
				"&g=d&a="+start_date[0]+"&b="+start_date[1]+"&c="+start_date[2]+"&ignore=.csv";
	}
	
	public static boolean isLeap(int year){
		if(year%4!=0) return false;
		else if(year%100!=0) return true;
		else if(year%400==0) return true;
		else return false;
	}
	
	public static int monthConv(String monthStr){
		int month=0;
		monthStr=monthStr.toLowerCase();
        switch (monthStr){
            case "jan": month=1;
                     break;
            case "feb": month=2;
                     break;
            case "mar": month=3;
                     break;
            case "apr": month=4;
                     break;
            case "may": month=5;
                     break;
            case "jun": month=6;
                     break;
            case "jul": month=7;
                     break;
            case "aug": month=8;
                     break;
            case "sep": month=9;
                     break;
            case "oct": month=10;
                     break;
            case "nov": month=11;
                     break;
            case "dec": month=12;
                     break;
            default: month=-1;
                     break;
        }
        return month;
	}

}
