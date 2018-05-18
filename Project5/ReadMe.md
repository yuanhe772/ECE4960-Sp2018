# ECE4960 - Programming Assignment 5 (A Generic 1D/2D Parabolic PDE Solver for Heat Equation)
## Name: Yuan He(yh772)

### 1. Overview:
This project is written in java, and compiled with Eclipse (java 8) on Mac OS.

### 2. Program Breakdown:

The program is broken down into: PDESolver.java, DiscretizationRule.java, Main.java, TestHelper.java, and other inherited helper scripts including FilIO.java, SparseMatrix.java, Vector.java, Jacobi.java.

#### Source files:

 - ***PDESolver.java:*** Java class, a generic 1D/2D parabolic PDE solver that could take in any arbitrary length of input, constant D, grid size h, delta_t, start and end time. It also interfaces with DiscretizationRule.java, which provides different finite difference discretization rules for solving parabolic PDEs.
 
 - ***DiscretizationRule.java:*** Java interface, provides method handlers for various finite difference discretization rules including forward, backward, trapezoid Euler in both 1D and 2D.
 
 - ***TestHelper.java:*** Implements white box testing for helper functions used in this project.
 
 - ***Main.java:*** The main entrance for this project, invokes tests for helper functions, and validates heat equations’ simulation.
  
#### Inherited source files:

- ***Vector.java, SparseMatrix.java:*** Imported from previous-written java project "MatrixAndVector", the data structures for solving PDE equations. Instead of using FullMatrix, SparseMatrix is used because it would be more space efficient when the input vector space is tremendously large.

- ***FileIO.java:*** Imported from previous-written java project "MatrixAndVector", auto-generate output files and test reports.

- ***Jacobi.java:*** Imported from previous-written java project "MatrixAndVector", A generic Jacobi-iterative matrix solver, solves PDE equations.

#### Plotting Scripts:
- ***Plot1D.mat & Plot2D.mat:*** Create the 1D plot and 2D animations for Heat Equations' simulation.

#### Reports and ouputs:
 
 - ***Report.pdf:*** Implementation details, testing strategies, program results(plots) and observations for this project.
 
 - ***TestHelperReport.txt:*** Auto-generated helper functions' test results.

 - ***HeatEquationSimulationLogFiles:*** Simulation results of heat equations solved by this generic parabolic PDE solver.
 
 - ***.jpg & .gif files***: Heat equations' simulation visualizations in 1D and 2D.
