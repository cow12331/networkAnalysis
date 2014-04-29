import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

public class RDFTwitterGrph extends Object{
	// some definitions
	static String tutorialURI = "http://hostname/rdf/homework2/";
	static String briansName = "hongjian niu";
	static String title = "twitter graph to RDF";
	static String date = "12/03/2014";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileReader fr = new FileReader("twitter.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";

		// create an empty model
		Model model = ModelFactory.createDefaultModel();
		
		while ((line = br.readLine()) != null) {
			String s = line;
			String[] sa = s.split(" ");
			
			String node1 = "http://somewhere/"+sa[0];
			String node2 = "http://somewhere/"+sa[1];
			
			String node1_id = sa[0];
			String node2_id = sa[1];
			
			// create the resource
			Resource rNode1 = model.createResource(node1);
			Resource rNode2 = model.createResource(node2);
			
			// and add the properties
			/*
			 * F represent ID, NF represent Follower
			 */
			rNode1.addProperty(VCARD.N, node1_id);
			rNode2.addProperty(VCARD.N, node2_id);
			
			rNode1.addProperty(VCARD.FN, rNode2);
			
		}
		model.write(System.out);
	}

}
