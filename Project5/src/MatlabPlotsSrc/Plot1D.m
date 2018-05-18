% Plotting 1D

X=0:1:14;
t=0:1:100;
[xx,tt]=meshgrid(X,t);
% Plot the surface
surf(xx,tt,U)
xlabel('space')
ylabel('time')
zlabel('Temperature')