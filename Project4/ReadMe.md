# ECE4960 - Programming Assignment 4
## Name: Yuan He(yh772)

### 1. Overview:

This project is written in java, and compiled with Eclipse (java 8) on Mac OS.

### 2. Program Breakdown:

The program breaks down into 4 (and 2 inherited previous-written helper classes) java classes, and 1 python script for plotting.

#### Source files:

 - ***Solver.java:*** Implements ODE solevrs, including Forward Euler, RK4, and RK34 with time adaption.
 
 - ***Simulation.java:*** Simulates RC circuit and Amplifier circuit by iteratively calling Solver.java, and outputs simulating log files for plotting.
 
 - ***Test.java:*** Validates ODE solvers with the example in previous Hacker Practice, and output ||error%|| log files for plotting; Tests helper functions.
 
 - ***Main.java:*** Main entry for entire project, tests helper functions, validate ODE solvers, and executes circuit simulations.
 
 - ***Plot.ipynb:*** Plots the auto-generated output data.
 
#### Inherited source files:

- ***Vector.java:*** Imported from previous-written java project "MatrixAndVector", constructs Vector type, and implements vector operations.

- ***FileIO.java:*** Imported from previous-written java project "MatrixAndVector", implements file operations.

#### Reports and ouputs:
 
 - ***P4Report.pdf:*** Reports results(plots) and observations for Task1-Task5.
 
 - ***TestHelper.txt:*** Auto-generated helper functions' test results.

 - ***OutputLogFiles:*** Output of ODE solvers' validation, and simulation results of RC and Amplifier circuits
