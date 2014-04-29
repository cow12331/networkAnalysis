import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import com.hp.hpl.jena.rdf.model.Model;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class TestQueryJena {
	public static void main(String args[]) {
		sparqlTest();
	}

	static void sparqlTest() {
		String queryString = "prefix vCard:<http://www.w3.org/2001/vcard-rdf/3.0#>"
				+ "select ?id  "
				//+ "where {<http://somewhere/1> vCard:FN ?follower }";
				+ "where {?resource vCard:N ?id }";
				
		long startTime=System.currentTimeMillis();
		Query query = QueryFactory.create(queryString);
		long endTime=System.currentTimeMillis();
		
		FileManager.get().addLocatorClassLoader(
				TestQueryJena.class.getClassLoader());
		Model model = FileManager.get().loadModel("twitter.rdf");
		
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		

		ResultSet results = qexec.execSelect();

		
		ResultSetFormatter.out(System.out, results, query);
		System.out.println("select time "+(endTime-startTime)+"ms");
		
		qexec.close();

	}
}