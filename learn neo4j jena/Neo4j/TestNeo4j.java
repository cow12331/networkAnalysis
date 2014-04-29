

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TraversalPosition;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.UniqueFactory;

import static org.neo4j.kernel.impl.util.FileUtils.deleteRecursively;

public class TestNeo4j {
	public enum RelTypes implements RelationshipType {
		HASFOLLOW
	}

	private static final String MATRIX_DB = "target/twitter-db";
	private GraphDatabaseService graphDb;
	private long matrixNodeId;

	public static void main(String[] args) throws IOException {
		TestNeo4j matrix = new TestNeo4j();
		matrix.setUp();
		matrix.shutdown();
	}

	public void setUp() throws IOException {
		deleteRecursively(new File(MATRIX_DB));
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(MATRIX_DB);
		registerShutdownHook();
		createNodespace();
	}

	public void shutdown() {
		graphDb.shutdown();
	}

	public void createNodespace() throws IOException {

		try (Transaction tx = graphDb.beginTx()) {

			FileReader fr = new FileReader("twitter.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";

			Map<String, Node> check = new HashMap<String, Node>();
			while ((line = br.readLine()) != null) {

				String s = line;
				String[] sa = s.split(" ");
		
				if (!check.containsKey(sa[0])) {
					Node node1 = graphDb.createNode();
					node1.setProperty("id", sa[0]);
					check.put(sa[0], node1);
				}
				if (!check.containsKey(sa[1])) {
					Node node2 = graphDb.createNode();
					node2.setProperty("id", sa[1]);
					check.put(sa[1], node2);
				}
				
				check.get(sa[0]).createRelationshipTo(check.get(sa[1]), RelTypes.HASFOLLOW);
				
				
			}

			tx.success();
		}

	}

	
	private void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
}
