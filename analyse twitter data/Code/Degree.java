public class Degree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] testArr = {  
                { 0, 1, 4, -1, -1, -1 },  
                { 1, 0, 2, 7, 5, -1 },  
                { 4, 2, 0, -1, 1, -1 },   
                { -1, 7, -1, 0, 3, 2 },  
                { -1, 5, 1, 3, 0, 6 },   
                { -1, -1, -1, 2, 6, 0 } };  
		int[] output = getDegree(testArr);
		for(int i: output) {
			System.out.print(i+" ");
		}
	}

	public static int[] getDegree(int[][] arr) {
		int length = arr[0].length;
		int[] degreeArr = new int[length];
		// int count = 0;
		for (int i = 0; i < length; i++) {
			int count = 0;
			for (int j = 0; j < length; j++) {
				if (arr[i][j] > 0 && arr[i][j] < Integer.MAX_VALUE) {
					count++;
				}
			}
			degreeArr[i] = count;
		}
		return degreeArr;
	}

}
