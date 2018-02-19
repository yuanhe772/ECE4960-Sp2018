/** Last Update: 02/18/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

/** Implement the compressed matrix and verify that it's consistent with the
 * full matrix, by retrieving its elements*/

import java.util.ArrayList;
import java.util.List;

public class HP2 {

	static ArrayList<Integer> value = new ArrayList<Integer>();
	static ArrayList<Integer> rowPtr = new ArrayList<Integer>();
	static ArrayList<Integer> colInd = new ArrayList<Integer>();
	static int[][] full_m = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};

	public HP2() {
		value.add(1);
		value.add(2);
		value.add(3);
		value.add(4);
		value.add(5);
		value.add(6);
		value.add(7);
		value.add(8);
		value.add(9);
		value.add(10);
		value.add(11);
		value.add(12);

		rowPtr.add(0);
		rowPtr.add(3);
		rowPtr.add(6);
		rowPtr.add(9);
		rowPtr.add(10);
		rowPtr.add(12);

		colInd.add(0);
		colInd.add(1);
		colInd.add(4);
		colInd.add(0);
		colInd.add(1);
		colInd.add(2);
		colInd.add(1);
		colInd.add(2);
		colInd.add(4);
		colInd.add(3);
		colInd.add(0);
		colInd.add(4);
	}

	public static Integer retrieveElement(int row, int col) {

		List<Integer> sub = colInd.subList(rowPtr.get(row), rowPtr.get(row+1));
		List<Integer> sub_value = value.subList(rowPtr.get(row), rowPtr.get(row+1));

		int col_index = 0;
		int value = 0;

		if(sub.contains(col)) {
			col_index = sub.indexOf(col);
			value = sub_value.get(col_index);
		}

		return value;
	}

	public static void main(String args[]) {
		HP2 m = new HP2();
		int flag = 0; // 0 means the compressed matrix == desired full matrix, 1 means inconsistent

		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.println("compressed matrix" +"[" + i + "]" + "[" + j +"] = " + retrieveElement(i,j));
				if(retrieveElement(i,j) != full_m[i][j]){
					flag = 1;
					break;
				}
			}
		}

		System.out.println("\nAfter comparing with the full matrix, "
				+ "the compressed matrix is " + (flag == 0? "consistent" : "inconsistent") +
				" with the full matrix.");
	}
}
