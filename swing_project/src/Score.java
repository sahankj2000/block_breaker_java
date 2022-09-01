import java.awt.*;

public class Score extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int score;
    Score(int width,int height){
        GAME_WIDTH = width;
        GAME_HEIGHT = height;
        score = 0;
    }
    public void draw(Graphics g,int life){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas",Font.PLAIN,(int)(GAME_HEIGHT*0.06)));
        g.drawString(String.valueOf("SCORE: "+score),(int)(GAME_WIDTH*0.02),(int)(GAME_HEIGHT*0.075));
        g.drawString(String.valueOf("LIFE: "+life),(int)(GAME_WIDTH*0.875),(int)(GAME_HEIGHT*0.075));
    }
}
