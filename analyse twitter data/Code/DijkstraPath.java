import java.util.Arrays;

public class DijkstraPath {
	private static final int inf = Integer.MAX_VALUE;
	public static int[] dijkstra(int[][] graph, int n, int u) {
		int[] path = new int[n];
		int dist[] = new int[n];
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
			//find distance of shortest path
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
			// update
			s[v] = true;
			for (int i = 0; i < n; i++) {
				if (!s[i] && graph[v][i] != inf
						&& dist[v] + graph[v][i] < dist[i]) {
					dist[i] = dist[v] + graph[v][i];
					path[i] = v;
				}
			}
		}
		// output
		int[] shortest = new int[n];

		// output every path on note
		for (int i = 0; i < n; i++) {
			// save path for output
			Arrays.fill(shortest, -1);
			// shortest distance
			System.out.println("From " + u + " to " + i + " distance is "
					+ dist[i]);

			int count = 0;
			int preNode = i;
			// from u to u
			if (path[i] == -1) {
				System.out.print(u + "->");
			} else {
				while (preNode != u) {
					shortest[count] = path[preNode];
					count++;
					preNode = path[preNode];
				}
			}
			//other nodes
			for (int j = count-1; j >= 0; j--) {
				System.out.printf("%d->", shortest[j]);
			}
			System.out.println(i);
		}
		return dist;
	}

	public static void main(String[] args) {
		int[][] W = { { 0, 1, 4, inf, inf, inf }, { 1, 0, 2, 7, 5, inf },
				{ 4, 2, 0, inf, 1, inf }, { inf, 7, inf, 0, 3, 2 },
				{ inf, 5, 1, 3, 0, 6 }, { inf, inf, inf, 2, 6, 0 } };

		dijkstra(W, 6, 1);

	}
}