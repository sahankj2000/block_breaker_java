import java.awt.*;
import java.util.Random;

public class Box extends Rectangle {
    int weight;
    Random random;
    Box(int xPosition,int yPosition,int width,int height){
        super(xPosition,yPosition,width,height);
        random = new Random();
        weight = random.nextInt(5)+1;
    }
    public void draw(Graphics g){
        int off = 10;
        if(weight == 1){
            g.setColor(new Color(56, 34, 88));
            g.fillRect(x,y,width,height);
            g.setColor(new Color(196, 151, 242));
            g.fillRect(x+(off/2),y+(off/2),width-off,height-off);
        }
        if(weight == 2){
            g.setColor(new Color(62, 100, 214));
            g.fillRect(x,y,width,height);
            g.setColor(new Color(19, 39, 160));
            g.fillRect(x+(off/2),y+(off/2),width-off,height-off);
        }
        if(weight == 3){
            g.setColor(new Color(19, 67, 49));
            g.fillRect(x,y,width,height);
            g.setColor(new Color(138, 242, 113));
            g.fillRect(x+(off/2),y+(off/2),width-off,height-off);
        }
        if(weight == 4){
            g.setColor(new Color(240, 239, 2));
            g.fillRect(x,y,width,height);
            g.setColor(new Color(234, 179, 1));
            g.fillRect(x+(off/2),y+(off/2),width-off,height-off);
        }
        if(weight == 5){
            g.setColor(new Color(132, 0, 0));
            g.fillRect(x,y,width,height);
            g.setColor(new Color(242, 0, 48));
            g.fillRect(x+(off/2),y+(off/2),width-off,height-off);
        }
    }
}
