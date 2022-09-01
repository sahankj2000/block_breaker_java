import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle{
    int xVelocity;
    int speed = 10;

    Paddle(int xPosition,int yPosition,int width,int height){
        super(xPosition,yPosition,width,height);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setXDirection(-speed);
            move();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            setXDirection(speed);
            move();
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setXDirection(0);
            move();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            setXDirection(0);
            move();
        }
    }
    public void setXDirection(int xDirection){
         xVelocity = xDirection;

    }
    public void setXPosition(int xPosition){
        x = xPosition;
    }
    public void move(){
        x = x + xVelocity;
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);
    }
}
