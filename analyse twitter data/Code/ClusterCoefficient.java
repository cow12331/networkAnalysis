public class ClusterCoefficient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int inf = Integer.MAX_VALUE;
		int[][] W = { { 0, 1, 4, inf, inf, inf }, { 1, 0, 2, 7, 5, inf },
				{ 4, 2, 0, inf, 1, inf }, { inf, 7, inf, 0, 3, 2 },
				{ inf, 5, 1, 3, 0, 6 }, { inf, inf, inf, 2, 6, 0 } };
		System.out.println(countTriangle(1,W));
		//System.out.print(2147483647<inf);
	}

	public static double countTriangle(int node, int[][] ga) {
		int countTri = 0;
		int countTriangle = 0;
		int length = ga.length;
		int infi = Integer.MAX_VALUE;

		for (int i = 0; i < length - 1; i++) {
			for (int j = i + 1; j < length; j++) {
				if (ga[node][i] != 0 && ga[node][j] != 0 && ga[node][i] < infi
						&& ga[node][j] < infi) {
					countTri++;
					//System.out.println(node +" "+ i + " " + j);
					if (ga[i][j] < infi) {
						countTriangle++;
						//System.out.println("isTriangle");
					}
				}
			}
		}
		return  (double)countTriangle/countTri;

	}
}
