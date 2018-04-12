# ECE4960 - Programming Assignment 3
## Name: Yuan He(yh772)

### 1. Overview:

This program is written in java, and compiled with Eclipse (java 8) on Mac OS.

### 2. Program Breakdown:

The program breaks down into 8 java classes, and 1 python script for plotting.

#### Source files:

 - ***iterativeSolver.java:*** the core script for project3, implements an iterative solver with line search that interfaces with any type of delta V vector, and Hessian matrix (Quasi-Newton, or Secant method), and any type of data (Normalized, or Unnormalized data).
 
 - ***gradientEsti. java:*** the script that implements different gradient etimation methods (Quasi-Newton, Secant, or hybrid Quasi-Newton for Power Law's parameter extraction) to render delta V vectors, and Hessian matrixes to the iterative solver.
 
 - ***dataIO.java:*** generate data for Power Law, and EKV models; generate initial guesses for iterative solver; implemented file operations for auto-generating report.
 
 - ***directMatrixSolver.java:*** implement a direct matrix solver that is robust against ill-conditioned matrix.
 
 - ***FullMatrix.java:*** implement operations for full matrixes.
 
 - ***Vector.java:*** implement operations for Vectors.
 
 - ***Test.java:*** test the helper functions in FullMatrix.java and Vector.java; Verify the output of direct matrix solver(Task1), and the output of iterative solver(Task7).
 
 - ***Main.java:*** main entry for entire program, calls the modular tests and execute each task sequentially.
 
 - ***Plot.ipynb:*** The Python script for plotting Task 3, 5, and 7.

#### Reports and ouputs:

 - ***Auto_Report.txt:*** The **program-generated** report for entire project's numerical testing results, meta data, and observations.
 
 - ***Visual_Report_of_Task_3_5_7.pdf:*** The report with visualization results, and observations.
 
 - ***Log.txt:*** The **program-generated** log file for iterative solver, reports the parameter(and their sensitivities), || V ||, || Delta || within each iteration.
