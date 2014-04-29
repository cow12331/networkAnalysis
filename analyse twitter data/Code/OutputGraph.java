import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputGraph {
    public static void main(String[] args) throws Exception {
        Spring sp = new Spring();
        List<Node> lNodes = new ArrayList<Node>();
        List<Edge> lEdges = new ArrayList<Edge>();
        List<String>checkNode = new ArrayList<String>();
        
        FileReader fr = new FileReader("E:/poly project/cssahw1/graphAfterTransIdtoInt.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		//String output = "";
		
		while ((line = br.readLine()) != null) {
			String s = line;
			String[] sa = s.split(" ");
			if(!checkNode.contains(sa[0])) {
				checkNode.add(sa[0]);
			}
			if(!checkNode.contains(sa[1])) {
				checkNode.add(sa[1]);
			}
			Edge saveEdge = new Edge(sa[0], sa[1]);
			Edge saveEdge2 = new Edge(sa[1], sa[0]);
			lEdges.add(saveEdge);
			lEdges.add(saveEdge2);
		}
		
		for(String i : checkNode) {
			Node saveNode = new Node(i);
			lNodes.add(saveNode);
		}
		
		/**		
		System.out.print(output);
        Node v1 = new Node("0");
        Node v2 = new Node("1");
        Node v3 = new Node("2");
        Node v4 = new Node("3");
        Edge e1 = new Edge("0", "1");
        Edge e2 = new Edge("1", "2");
        Edge e3 = new Edge("2", "3");
        Edge e4 = new Edge("3", "0");
        lNodes.add(v1);
        lNodes.add(v2);
        lNodes.add(v3);
        lNodes.add(v4);
        lEdges.add(e1);
        lEdges.add(e2);
        lEdges.add(e3);
        lEdges.add(e4);
        */
        br.close();
        
        //set output scale
        double start_x, start_y, initSize = 40.0;
        for (Node node:lNodes) {
            start_x = 0 + 1100 * .5;
            start_y = 0 + 1000 * .5;
            node.setX(start_x + initSize * (Math.random() - .5));
            node.setY(start_y + initSize * (Math.random() - .5));
        }
        System.out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" xmlns:xllink=\"http://www.w3.org/1999/xlink\" width=\"1000\" height=\"1000\">");
        for(int i = 0 ; i < lNodes.size(); i++) {
        	System.out.println("<ellipse cx=\""+lNodes.get(i).getX()+"\" cy=\""+lNodes.get(i).getY()+"\" rx=\"5.60\" ry=\"5.60\" "+"style=\"fill:rgb(255,255,0);stroke:rgb(0,0,0);stroke-width:0.50;\"/>");
        }
        
        for(int i = 0 ; i < lEdges.size(); i++) {
        	
        	String id1 = lEdges.get(i).getId1();
        	String id2 = lEdges.get(i).getId2();
        	Node node1 = sp.getNodeById(lNodes,id1);
        	Node node2 = sp.getNodeById(lNodes,id2);
        	System.out.println("<line x1=\""+node1.getX()+"\" y1=\""+node1.getY()+"\" x2=\""+node2.getX()+"\" y2=\""+node2.getY()+"\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
        }
        System.out.println("</svg>");
        System.out.println("============================================================================");
        List<Node> reSetNodes = sp.springLayout(lNodes,lEdges);
        
        //iteration
        for(int j = 1; j <= 20; j++) {
	        for(int i=1; i<j*10; i++){
	            reSetNodes = sp.springLayout(reSetNodes,lEdges);
	        }
	        String output = "";
	        for(Node node:reSetNodes){
	          // System.out.println(node.getId()+" "+node.getX()+" "+node.getY());
	        }
	        //output nodes
	        output += "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" xmlns:xllink=\"http://www.w3.org/1999/xlink\" width=\"1000\" height=\"1000\">\n";
	        //System.out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" xmlns:xllink=\"http://www.w3.org/1999/xlink\" width=\"1000\" height=\"1000\">");
	        for(int i = 0 ; i < reSetNodes.size(); i++) {
	        	//System.out.println("<ellipse cx=\""+lNodes.get(i).getX()+"\" cy=\""+lNodes.get(i).getY()+"\" rx=\"5.60\" ry=\"5.60\" "+"style=\"fill:rgb(255,255,0);stroke:rgb(0,0,0);stroke-width:0.50;\"/>");
	        	output += "<ellipse cx=\""+lNodes.get(i).getX()+"\" cy=\""+lNodes.get(i).getY()+"\" rx=\"5.60\" ry=\"5.60\" "+"style=\"fill:rgb(255,255,0);stroke:rgb(0,0,0);stroke-width:0.50;\"/>\n";
	        }
	        //output edges
	        for(int i = 0 ; i < lEdges.size(); i++) {
	        	
	        	String id1 = lEdges.get(i).getId1();
	        	String id2 = lEdges.get(i).getId2();
	        	Node node1 = sp.getNodeById(lNodes,id1);
	        	Node node2 = sp.getNodeById(lNodes,id2);
	        	//System.out.println("<line x1=\""+node1.getX()+"\" y1=\""+node1.getY()+"\" x2=\""+node2.getX()+"\" y2=\""+node2.getY()+"\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
	        	output += "<line x1=\""+node1.getX()+"\" y1=\""+node1.getY()+"\" x2=\""+node2.getX()+"\" y2=\""+node2.getY()+"\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>\n";
	        }
	        //System.out.println("</svg>");
	       output += "</svg>\n";
	       System.out.print(output);
	       byte[] buff = new byte[] {};
	       buff = output.getBytes();
	       FileOutputStream out = new FileOutputStream("target/mysvg_"+j+".svg");
	       out.write(buff, 0, buff.length);
	    }
    }
}