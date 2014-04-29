import java.util.Arrays;

public class CloseDijkstraPath {
	//distance between two node can't be connected directly
	private static final int inf = Integer.MAX_VALUE;
	public static double getCloseness(int[][] graph,int u) {
		int n = graph.length;
		//save shortest path to other node
		int[] path = new int[n];
		//save shortest distance to other node
		int dist[] = new int[n];
		//using for checking the node whether has been used
		boolean s[] = new boolean[n];
		Arrays.fill(s, false);
		Arrays.fill(dist, inf);
		s[u] = true;
		int min, v;

		// initial path array and distance array
		for (int i = 0; i < n; i++) {
			dist[i] = graph[u][i];
			if (i != u && dist[i] < inf)
				path[i] = u;
			else
				path[i] = -1;
		}

		while (true) {
			min = inf;
			v = -1;
			//find shortest distance
			for (int i = 0; i < n; i++) {
				if (!s[i]) {
					if (dist[i] < min) {
						min = dist[i];
						v = i;
					}
				}
			}
			if (v == -1)
				break;
			//update shortest distance
			s[v] = true;
			for (int i = 0; i < n; i++) {
				if (!s[i] && graph[v][i] != inf
						&& dist[v] + graph[v][i] < dist[i]) {
					dist[i] = dist[v] + graph[v][i];
					path[i] = v;
				}
			}
		}
		
		//using for tracking back path
		int[] shortest = new int[n];
		double closenessPrim = 0.0;
		double closeness = 0.0;
		// output distance
		for (int i = 0; i < n; i++) {
			// save path for output
			Arrays.fill(shortest, -1);
			int count = 0;
			int preNode = i;
			
			//output shortest distance
			//System.out.println("From " + (u) + " to " + (i) + " distance is "
			//		+ dist[i]);
			closenessPrim += dist[i];
			/*
			//from u to u
			if (path[i] == -1) {
				System.out.print(u + "->");
			} else {
				while (preNode != u) {
					shortest[count] = path[preNode];
					count++;
					preNode = path[preNode];
				}
			}
			//from u to other nodes
			for (int j = count-1; j >= 0; j--) {
				System.out.printf("%d->", shortest[j]);
			}
			System.out.println(i);
			*/
		}
		closeness = 1/closenessPrim;
		//System.out.println((u)+"'s closeness is "+closeness);
		return closeness;
	}

	public static void main(String[] args) {
		int[][] W = { { 0, 1, 4, inf, inf, inf }, { 1, 0, 2, 7, 5, inf },
				{ 4, 2, 0, inf, 1, inf }, { inf, 7, inf, 0, 3, 2 },
				{ inf, 5, 1, 3, 0, 6 }, { inf, inf, inf, 2, 6, 0 } };

		getCloseness(W, 1);

	}
}