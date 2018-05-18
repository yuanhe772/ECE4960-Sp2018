% Plotting 2D

h = figure;
axis tight % this ensures that getframe() returns a consistent size
filename = 'animation.gif';
for n = 1:1:31
    % Draw plot for y = x.^n
    a = [1 1 1 ; 2 2 2 ; 3 3 3];
    b = [1 2 3 ; 1 2 3 ; 1 2 3];
    
    surf(a,b,reshape(trapezoid2D(n,:),[3,3]));
    xlabel('x space')
    ylabel('y space')
    zlabel('Temperature')
    drawnow
    zlim([0, 10]);
    
    % Capture the plot as an image
    frame = getframe(h);
    im = frame2im(frame);
    [imind,cm] = rgb2ind(im,256);
    % Write to the GIF File
    if n == 1
        imwrite(imind,cm,filename,'gif', 'Loopcount',inf);
    else
        imwrite(imind,cm,filename,'gif','WriteMode','append');
    end
end
