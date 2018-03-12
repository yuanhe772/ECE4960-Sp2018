# ECE4960 - Programming Assignment 2
### Name: Yuan He(yh772)

#### 1. Overview:

This program is written in java, and compiled with Eclipse on Mac OS and Command-line in Linux.

#### 2. Program Breakdown:

The program breaks down into 5 classes, Main.java, Test.java, SparseMatrix.java, Vector.java, and Jacobi.java:

 - ***SparseMatrix.java:*** construct the sparse matrix's data structures into an instantiable class, and implemented the sparse matrix basic operations including addition(subtraction), matrix multiplication, element - retrieving, value-setting, and Jacobi-decomposition;
 
 - ***Vector. java:*** construct vector's data structures into an instantiable class, providing more flexibilty in development than simple Arrays when operated with sparse matrix. Also implemented basic vector operation of addition(subtraction);
 
 - ***Jacobi.java:*** implemented the iterative steps of Jacobi-Iterative solver;
 
 - ***Test.java:*** verify both the **inputs** and **outputs** of SparseMatrix's functions. Further discussions are in Algorithm&Design.txt;
 
 - ***Main.java:*** main entry for entire program, calls the modular testing funcions and the Jacobi-solver function;

 - ***report.txt:*** The **program-generated** report for entire project, consists of brief description and outcome, estimated number of operations, measured computation time and MEM usage for each function being tested;
 
 - ***Algorithm&Design.md:*** The algo and design for implementing the modular testing;
 
 - ***PlatformDifference.txt:*** The differences observed within different platforms;
 
 - ***PythonGeneratedTestingData:*** Batches of large testing data for sparse matrix functions' black-box testing.
