# ECE4960 - Programming Assignment 5 (A Generic 1D/2D Parabolic PDE Solver for Heat Equation)
## Name: Yuan He(yh772)

### 1. Overview:
This project is written in java, and compiled with Eclipse (java 8) on Mac OS.

### 2. Program Breakdown:

The program is broken down into: PDESolver.java, DiscretizationRule.java, Main.java, TestHelper.java, and other inherited helper scripts including FilIO.java, SparseMatrix.java, Vector.java, Jacobi.java.

#### Source files:

 - ***Solver.java:*** Implements ODE solevrs, including Forward Euler, RK4, and RK34 with time adaption.
 
 - ***Simulation.java:*** Simulates RC circuit and Amplifier circuit by iteratively calling Solver.java, and outputs simulating log files for plotting.
 
 - ***Test.java:*** Validates ODE solvers with the example in previous Hacker Practice, and output ||error%|| log files for plotting; Tests helper functions.
 
 - ***Main.java:*** Main entry for entire project, tests helper functions, validate ODE solvers, and executes circuit simulations.
 
 - ***Plot.ipynb:*** Plots the auto-generated output data.
 
#### Inherited source files:

- ***Vector.java:*** Imported from previous-written java project "MatrixAndVector", constructs Vector type, and implements vector operations.

- ***FileIO.java:*** Imported from previous-written java project "MatrixAndVector", implements file operations.

#### Plotting Scripts:
- ***Plot1D.mat & Plot2D.mat:*** Create the 1D plot and 2D animations for Heat Equations' simulation.

#### Reports and ouputs:
 
 - ***Report.pdf:*** Implementation details, testing strategies, program results(plots) and observations for this project.
 
 - ***TestHelperReport.txt:*** Auto-generated helper functions' test results.

 - ***HeatEquationSimulationLogFiles:*** Simulation results of heat equations solved by this generic parabolic PDE solver.
 
 - ***.jpg & .gif files***: Heat equations' simulation visualizations in 1D and 2D.
