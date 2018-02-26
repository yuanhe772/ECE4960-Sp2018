/** Last Update: 02/25/2018; Author: Yuan He (yh772); Platform: MacOS, Eclipse, Java8 */


import java.util.ArrayList;
import java.util.List;

public class SparseMatrix {
	ArrayList<Double> value = new ArrayList<Double>();
	ArrayList<Integer> rowPtr = new ArrayList<Integer>();
	ArrayList<Integer> colInd = new ArrayList<Integer>();
	
	/**Constructor*/
	public SparseMatrix(ArrayList<Double> value2, ArrayList<Integer> rowPtr2, ArrayList<Integer> colInd2) {
		value = value2;
		rowPtr = rowPtr2;
		colInd = colInd2;
	}

	/**Retrieve element (r,c) from sparse matrix*/
	public double retrieveElement(int r, int c) {
		List<Integer> sub = colInd.subList(rowPtr.get(r), rowPtr.get(r+1));
		List<Double> sub_value = value.subList(rowPtr.get(r), rowPtr.get(r+1));
		int col_index = 0;
		double value = 0;
		if(sub.contains(c)) {
			col_index = sub.indexOf(c);
			value = sub_value.get(col_index);
		}
		return value;
	}

	/**Switch row[i] and row[j] for matrix A and Vector x*/
	public int rowPermute(int i, int j) {
		try {
			ArrayList<Double> value_new = new ArrayList<Double>();
			ArrayList<Integer> colInd_new = new ArrayList<Integer>();
			ArrayList<Integer> rowPtr_new = new ArrayList<Integer>();
			if(i>j) {
				int temp = i;
				i = j;
				j = temp;
			}
			value_new.addAll(value.subList(rowPtr.get(0), rowPtr.get(i)));
			value_new.addAll(value.subList(rowPtr.get(j), rowPtr.get(j+1)));
			value_new.addAll(value.subList(rowPtr.get(i+1), rowPtr.get(j)));
			value_new.addAll(value.subList(rowPtr.get(i), rowPtr.get(i+1)));
			value_new.addAll(value.subList(rowPtr.get(j+1), value.size()));
			colInd_new.addAll(colInd.subList(rowPtr.get(0), rowPtr.get(i)));
			colInd_new.addAll(colInd.subList(rowPtr.get(j), rowPtr.get(j+1)));
			colInd_new.addAll(colInd.subList(rowPtr.get(i+1), rowPtr.get(j)));
			colInd_new.addAll(colInd.subList(rowPtr.get(i), rowPtr.get(i+1)));
			colInd_new.addAll(colInd.subList(rowPtr.get(j+1), colInd.size()));
			int r1_l = rowPtr.get(i+1) - rowPtr.get(i);
			int r2_l = rowPtr.get(j+1) - rowPtr.get(j);

			if(r1_l == r2_l) {
				rowPtr_new = rowPtr;
			}
			else {
				int gap = r2_l-r1_l;
				rowPtr_new.addAll(rowPtr.subList(0, i+1));
				// Increment every element in rowPtr by (r2_l - r1_l)
				for(int it=i+1; it<j+1; it++) {
					rowPtr_new.add(rowPtr.get(it) + gap);
				}
				rowPtr_new.addAll(rowPtr.subList(j+1, rowPtr.size()));
			}
			value = value_new;
			rowPtr = rowPtr_new;
			colInd = colInd_new;
			return 0;
		}catch(Exception e){
			return 1;
		}
	}

	/**Add a*row[i] to row[j] for Matrix A and Vector x*/
	public int rowScale(int i, int j, double a) {
		try {
		int rank = rowPtr.size()-1;
		for(int it=0; it<rank; it++) {
			matrix_setter(j, it, retrieveElement(i, it)*a + retrieveElement(j, it));
		}
		return 0;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}

	/**Return the product of Ax = b*/
	public Vector product(Vector vec) {
		double[] r = new double[vec.len];
		int rank = rowPtr.size()-1;
		for(int i=0; i < rank; i++) {
			for(int j=0; j < rank; j++) {
				r[i] += retrieveElement(i, j) * vec.v[j]; 
			}
		}
		return new Vector(r);
	}

	// Helper functions:
	/**Set the [r,c] element in matrix to v*/
	public void matrix_setter(int r, int c, double v) {
		List<Integer> sub = colInd.subList(rowPtr.get(r), rowPtr.get(r+1));
		// If position [r,c] != 0：
		if(sub.contains(c)) {
			int col_index = sub.indexOf(c);
			if(v!=0) {
				value.set(rowPtr.get(r)+col_index, v);
			}
			else {
				//delete this element from value[]
				value.remove(rowPtr.get(r)+col_index);
				//decrement rowPtr[r+1] and the elements after it
				for(int i=r+1; i<rowPtr.size();i++) {
					rowPtr.set(i, rowPtr.get(i)-1);
				}
				//delete this element from colInd[]
				colInd.remove(rowPtr.get(r)+col_index);
			}
		}
		// If position [r,c] == 0：
		else {
			//add this element into value[]
			value.add(rowPtr.get(r), v);
			//increment rowPtr[r+1] and the elements after it
			for(int i=r+1; i<rowPtr.size(); i++) {
				rowPtr.set(i, rowPtr.get(i)+1);
			}
			//add this element into colInd[]
			colInd.add(rowPtr.get(r), c);
		}
		return;
	}
}
