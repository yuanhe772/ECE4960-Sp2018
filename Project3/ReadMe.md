# ECE4960 - Programming Assignment 3
### Name: Yuan He(yh772)

#### 1. Overview:

This program is written in java, and compiled with Eclipse (java 8) on Mac OS.

#### 2. Program Breakdown:

The program breaks down into 5 classes, Main.java, iterativeSolver.java, gradientEsti.java, directMatrixSolver.java, dataIO.java, FullMatrix.java, Vector.java, and Test.java.

 - ***iterativeSolver.java:*** The core script for this project, implements an iterative solver with line search that interfaces with any type of delta V vector, and Hessian matrix (Quasi-Newton, or Secant method), and any type of data (Normalized, or Unnormalized data).
 
 - ***gradientEsti. java:*** The script that implements different gradient etimation methods (Quasi-Newton, Secant, or hybrid Quasi-Newton with real gradient for Power Law's parameter extraction) to render delta V vectors, and Hessian matrixes to the iterative solver.
 
 - ***Jacobi.java:*** implemented the iterative steps of Jacobi-Iterative solver;
 
 - ***Test.java:*** verify both the **inputs** and **outputs** of SparseMatrix's functions. Further discussions are in Algorithm&Design.pdf;
 
 - ***Main.java:*** main entry for entire program, calls the modular testing funcions and the Jacobi-solver function;

 - ***report.txt:*** The **program-generated** report for entire project's testing, consists of brief description and outcome for each test case, and their computational cost, along with a brief discussion on the testing outcomes;
 
 - ***Algorithm&Design.pdf:*** The algo and design for implementing the modular testing;
 
 - ***PlatformDifference.txt:*** The differences observed within different platforms;
 
 - ***PythonGeneratedTestingData:*** Batches of large testing data for sparse matrix functions' black-box testing.
