import java.io.BufferedReader;
import java.io.FileReader;

public class ReadZachary {
	private static final int inf = Integer.MAX_VALUE;

	public static void main(String args[]) throws Exception {
		int[][] a = readZachary("E:/poly project/cssahw1/graphAfterTransIdtoInt.txt");
		/*
		 * cluster coefficient
		 */
		/**
		for (int i = 0; i < a.length; i++) {
			double output = ClusterCoefficient.countTriangle(i, a);
			System.out.println("node"+i+"'s clustering coefficient is"+output);
		}
		*/
		
		/*
		 * betweenness
		 */
		/**
		int[] countAll = new int[a.length];
		double allpath = a.length * (a.length) - 1;
		for (int i : countAll) {
			i = 0;
		}
		for (int i = 0; i < a.length; i++) {
			int[] countIndiv = new int[a.length];
			countIndiv = Betweenness.getBetweenness(a, i);
			for (int j = 0; j < countIndiv.length; j++) {
				if (countIndiv[j] != 0) {
					countAll[j] += countIndiv[j];
				}
			}
		}

		for (int i = 0; i < countAll.length; i++) {
			System.out.println("the times of shortest path though node"
					+ (i) + " equal " + countAll[i]);
			System.out.println("betweenness of note" + (i ) + " is "
					+ countAll[i] / (2 * allpath));
		}
		*/ 
		/*
		 * closeness
		 */
		/**
		for (int i = 0; i < a.length; i++) {
			double output = CloseDijkstraPath.getCloseness(a, i);
			System.out.println((i) + "'s closeness is " + output);
		}
		*/
		/*
		 * degree
		 */
		
		int[] outputFordegree = Degree.getDegree(a);
		for (int i = 0; i < outputFordegree.length; i++) {
			System.out.println("Node " + (i) + "'s degree is "
					+ outputFordegree[i]);
		}
		
	}

	public static int[][] readZachary(String path) throws Exception {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		int max = Integer.MIN_VALUE;
		String line = "";
		String line2 = "";

		while ((line = br.readLine()) != null) {
			String s = line;

			String[] sa = s.split(" ");

			int[] in = new int[3];

			in[0] = Integer.parseInt(sa[0]) ;
			in[1] = Integer.parseInt(sa[1]) ;
			in[2] = Integer.parseInt(sa[2]);
			for (int i = 0; i < 3; i++) {
				// System.out.print(in[i] + " ");
			}
			if (in[0] > max) {
				max = in[0];
			}
			if (in[1] > max) {
				max = in[1];
			}

			// System.out.print("\n");
		}
		br.close();

		// System.out.print("dimension is " + max + "\n");
		// initial graph array
		int[][] ga = new int[max + 1][max + 1];
		for (int i = 0; i < max + 1; i++)
			for (int j = 0; j < max + 1; j++) {
				if (i == j)
					ga[i][j] = 0;
				else
					ga[i][j] = inf;
			}

		FileReader fr2 = new FileReader(path);
		BufferedReader br2 = new BufferedReader(fr2);

		while ((line2 = br2.readLine()) != null) {
			String s = line2;
			String[] sa = s.split(" ");
			int[] in = new int[3];

			in[0] = Integer.parseInt(sa[0]) ;
			in[1] = Integer.parseInt(sa[1]) ;
			in[2] = Integer.parseInt(sa[2]);
			// System.out.print(in[i] + " ");
			ga[in[0]][in[1]] = in[2];
			ga[in[1]][in[0]] = in[2];
			// System.out.print("\n");
		}

		for (int i = 0; i < max + 1; i++) {
			for (int j = 0; j < max + 1; j++) {
				// System.out.print(ga[i][j] + " ");
			}
			// System.out.print("\n");
		}
		br2.close();
		return ga;
	}
}
