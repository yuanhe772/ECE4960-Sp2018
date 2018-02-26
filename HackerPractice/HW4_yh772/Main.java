/** Last Update: 02/25/2018; Author: Yuan He (yh772); Platform: MacOS, Eclipse, Java8 */


import java.util.ArrayList;
import java.io.*;

public class Main {
	public static void main(String args[]) {

		//PART ONE:
		ArrayList<Double> value = new ArrayList<Double>();
		ArrayList<Integer> rowPtr = new ArrayList<Integer>();
		ArrayList<Integer> colInd = new ArrayList<Integer>();

		/** Instantiate a Sparse Matrix:*/
		value.add(1.0);
		value.add(2.0);
		value.add(3.0);
		value.add(4.0);
		value.add(5.0);
		value.add(6.0);
		value.add(7.0);
		value.add(8.0);
		value.add(9.0);
		value.add(10.0);
		value.add(11.0);
		value.add(12.0);
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
		SparseMatrix sp = new SparseMatrix(value,rowPtr,colInd);

		/** Instantiate a Full Matrix:*/
		double[][] full_matrix = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		FullMatrix fm = new FullMatrix(full_matrix);

		/** Instantiate a Vector:*/
		double[] v = {5,4,3,2,1};
		Vector vec = new Vector(v);

		/** Testing of Part 1:*/
		System.out.println("Outputs for testing part 1:\n");
		permutation1(sp,fm);
		scaling1(sp,fm);
		product1(sp,fm,vec);


		//PART TWO:
		/**Instantiate the SparseMatrix 
		 * from memplus.mtx, pre-process it by decrement each column and row index by 1*/
		SparseMatrix sp2 = readData("memplus.mtx", 17758 , 126150);

		/** Testing of Part 2:*/
		System.out.println("\n\n\nOutputs for testing part 2:");
		permutation2(sp2);
		Scaling2(sp2);

	}


	//PART ONE:
	/** Part 1's permutation testing:*/
	public static void permutation1(SparseMatrix sp, FullMatrix fm) {
		if(sp.rowPermute(0, 2)==0 && fm.rowPermute(0, 2)==0) {
			System.out.println("  Part 1: "+ (norm_matrix(sp, fm)==0?"Passed":"Failed")+
					" second-norm checking for permuting row 1&3 !");
		}
		else System.out.println("Permuting method's Error");
		if(sp.rowPermute(0, 4)==0 && fm.rowPermute(0, 4)==0) {
			System.out.println("  Part 1: "+ (norm_matrix(sp, fm)==0?"Passed":"Failed")+
					" second-norm checking for permuting row 1&5 !");
		}
		else System.out.println("Permuting method's Error");
	}


	/** Part 1's scaling testing:*/
	public static void scaling1(SparseMatrix sp, FullMatrix fm) {
		if(sp.rowScale(0, 3, 3.0)==0 && fm.rowScale(0, 3, 3.0)==0) {
			System.out.println("  Part 1: "+ (norm_matrix(sp, fm)==0?"Passed":"Failed")+
					" second-norm checking for 1st time rowScaling!");
		}
		else System.out.println("RowScaling method's Error");
		if(sp.rowScale(4, 1, -4.4)==0 && fm.rowScale(4, 1, -4.4)==0) {
			System.out.println("  Part 1: "+ (norm_matrix(sp, fm)==0?"Passed":"Failed")+
					" second-norm checking for 2nd time rowScaling!");
		}
		else System.out.println("RowScaling method's Error");
	}


	/** Part 1's multiplying testing:*/
	public static void product1(SparseMatrix sp, FullMatrix fm, Vector v) {
		System.out.println("  Part 1: "+ (norm_matrix(sp, fm)==0?"Passed":"Failed")+
				" second-norm checking of multipying a sparse matrix!");
	}


	/** Part 1's second norm test function for matrix:*/
	public static double norm_matrix(SparseMatrix sp, FullMatrix fm){
		double norm = 0;
		for(int i=0; i<fm.rank; i++) {

			for(int j=0; j<fm.rank; j++) {
				norm += Math.pow((sp.retrieveElement(i, j) - fm.full_m[i][j]), 2);
			}
		}
		return norm;
	}


	/** Part 1's second norm test function for vector:*/
	public static double norm_vector(Vector sp, Vector fm){
		double norm = 0;
		for(int i=0; i<sp.len; i++) {
			norm += Math.pow((sp.v[i]-fm.v[i]),2);
		}
		return norm;
	}


	//PART TWO:
	/** Part 2's data input, and parsing into sparse matrix:*/
	public static SparseMatrix readData(String filePath, int rank, int num) {

		ArrayList<Double> value = new ArrayList<Double>();
		ArrayList<Integer> rowPtr = new ArrayList<Integer>();
		ArrayList<Integer> colInd = new ArrayList<Integer>();

		int[] row_array = new int[rank+1];
		for(int i=0; i<row_array.length;i++) {
			rowPtr.add(row_array[i]);
		}

		int[] col_array = new int[num];
		double[] val_array = new double[num];
		for(int i=0; i<val_array.length;i++) {
			value.add(val_array[i]);
			colInd.add(col_array[i]);
		}

		ArrayList<Integer> rowPtr_helper = new ArrayList<Integer>(); 
		rowPtr.set(0,0);

		int col;
		int row;
		double val;

		//Parse into rowPtr[]
		try {
			File file = new File(filePath);
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String lineTxt = null;
				int iterator = 0;
				String[] line = new String[4];
				while ((lineTxt = br.readLine()) != null) {
					line = lineTxt.split(" ");

					if(iterator > 1) {
						if(line.length == 4) {
							line[2] = line[3];
						}

						row = Integer.valueOf(line[0])-1;

						if(rowPtr_helper.contains(row)) {
							for(int i=row+1; i<rowPtr.size(); i++) {
								if(rowPtr.get(i)!=0) {
									rowPtr.set(i, rowPtr.get(i)+1);
								}
							}
						}
						else {
							rowPtr_helper.add(row);
							int index = 0;
							for(int i=1; i<=row;i++) {
								if(rowPtr.get(i)!=0)
									index = rowPtr.get(i);
							}
							rowPtr.set(row+1, index+1);

							for(int i=row+2;i<rowPtr.size();i++) {
								if(rowPtr.get(i)!=0)
									rowPtr.set(i, rowPtr.get(i)+1);
							}
						}
					}
					iterator+=1;
				}
				br.close();
			} else {
				System.out.println("File doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Parse into colInd[] and value[]
		try {
			File file = new File(filePath);
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String lineTxt = null;
				int iterator = 0;
				String[] line = new String[4];

				while ((lineTxt = br.readLine()) != null) {
					line = lineTxt.split(" ");

					if(iterator > 1) {
						if(line.length == 4) {
							line[2] = line[3];
						}
						row = Integer.valueOf(line[0])-1;
						col = Integer.valueOf(line[1])-1;
						val = Double.valueOf(line[2]);
						int i=0;
						for(i=rowPtr.get(row); i<rowPtr.get(row+1); i++) {
							if(value.get(i)==0) {
								break;
							}
						}
						value.set(i, val);
						colInd.set(i, col);
					}
					iterator+=1;
				}
				br.close();

			} else {
				System.out.println("File doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new SparseMatrix(value,rowPtr,colInd);
	}


	/** Part 2's permutation testing:*/
	public static void permutation2(SparseMatrix sm) {
		//sm: original matrix, sm2: permuted matrix
		SparseMatrix sm2= new SparseMatrix(new ArrayList<Double>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		sm2.value = (ArrayList<Double>) sm.value.clone();
		sm2.colInd = (ArrayList<Integer>) sm.colInd.clone();
		sm2.rowPtr = (ArrayList<Integer>) sm.rowPtr.clone();

		//record time and memory usage
		long startTime= System.nanoTime();
		sm2.rowPermute(1-1, 3-1);
		sm2.rowPermute(1-1, 5-1);	
		sm2.rowPermute(10-1, 3000-1);
		sm2.rowPermute(5000-1, 10000-1);
		sm2.rowPermute(6-1, 15000-1);
		long endTime= System.nanoTime();
		Runtime runtime = Runtime.getRuntime();
		System.out.println("\n  Part 2: permutations spent time: "+(endTime-startTime)+" ns");
		System.out.println("  Part 2: permutations cost memory: "+(runtime.totalMemory()-runtime.freeMemory())+" bytes");

		//Correctness testing
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(1-1, i)) != (sm2.retrieveElement(3-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(3-1, i)) != (sm2.retrieveElement(5-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(5-1, i)) != (sm2.retrieveElement(1-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(10-1, i)) != (sm2.retrieveElement(3000-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(3000-1, i)) != (sm2.retrieveElement(10-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(5000-1, i)) != (sm2.retrieveElement(10000-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(10000-1, i)) != (sm2.retrieveElement(5000-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(6-1, i)) != (sm2.retrieveElement(15000-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		for(int i=0; i<sm.rowPtr.size(); i++) {
			if((sm.retrieveElement(15000-1, i)) != (sm2.retrieveElement(6-1, i))) {
				System.out.println("  Part 2: permutation error!");
				return;
			}
		}
		System.out.println("  Part 2: Passed permutation check!");
		return;
	}


	/** Part 2's scaling testing:*/
	public static void Scaling2(SparseMatrix sm) {
		SparseMatrix sm2= new SparseMatrix(new ArrayList<Double>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		sm2.value = (ArrayList<Double>) sm.value.clone();
		sm2.colInd = (ArrayList<Integer>) sm.colInd.clone();
		sm2.rowPtr = (ArrayList<Integer>) sm.rowPtr.clone();

		long startTime= System.nanoTime();
		sm2.rowScale(2-1, 4-1, 3.0);
		sm2.rowPermute(2-1, 5-1);
		sm2.rowScale(5-1, 4-1, -3.0);
		long endTime= System.nanoTime();
		Runtime runtime = Runtime.getRuntime();

		System.out.println("\n  Part 2: row-scaling spent time: "+(endTime-startTime)+" ns");
		System.out.println("  Part 2: row-scaling cost memory: "+(runtime.totalMemory()-runtime.freeMemory())+" bytes"); 
		double sum=0.0;
		for(int i=0; i<sm.value.size(); i++) {
			sum+=sm.value.get(i);
		}
		checkSums(sm2, sum);
	}


	/** Part 2's scaling testing's helper function:*/
	public static void checkSums(SparseMatrix sm, double check2) {
		double[] v= new double[sm.colInd.size()];
		for(int i=0; i<v.length; i++) {
			v[i]=1.0;
		}
		Vector x= new Vector(v);
		Vector result= sm.product(x);
		double check1=0;
		for(int i=0; i<result.len; i++) {
			check1+=result.v[i];
		}
		if(Math.abs(check1-check2)<Math.pow(10, -7)) {
			System.out.println("  Part 2: Passed CheckSum testing!");
		}else {
			System.out.println("  Part 2: CheckSum testing fail!");
		}
	}

}
