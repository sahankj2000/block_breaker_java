import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 5;

    Ball(int xPosition,int yPosition,int width,int height){
        super(xPosition,yPosition,width,height);
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0){
            randomXDirection--;
        }
        setXDirection(randomXDirection*initialSpeed);
        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0){
            randomYDirection--;
        }
        setYDirection(randomYDirection*initialSpeed);
    }
    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }
    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }
    public void setPosition(int xPosition,int yPosition){
        x = xPosition;
        y = yPosition;
    }
    public void setY(int yPosition){
        y = yPosition;
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x,y,height,width);
    }
}