import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePannel extends JPanel implements Runnable{
    static int GAME_WIDTH = 1800;
    static int GAME_HEIGHT = (int) (GAME_WIDTH*0.5);
    static Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static int PADDLE_WIDTH = GAME_WIDTH/5;
    static int PADDLE_HEIGHT = GAME_HEIGHT/30;
    static int BALL_DIAMETER = PADDLE_HEIGHT;
    static int BOX_HEIGHT_FACTOR = 10;
    static int BOX_WIDTH_FACTOR = 10;
    static int BOX_HEIGHT = (int)(GAME_HEIGHT/(BOX_HEIGHT_FACTOR*1.5));
    static int BOX_WIDTH = GAME_WIDTH/BOX_WIDTH_FACTOR;
    static int row = 5;
    static int column = 8;
    static int boxCount = row*column;
    static int remainingBoxes = boxCount;

    static int xSpeedLimit = 20;
    static int ySpeedLimit = 15;
    static int yFactor = boxCount/(ySpeedLimit-5);
    static int life;
    static JFrame menuFrame;
    static JFrame gameFrame;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle;
    Ball ball;
    Score score;
    Box[] boxes;

    GamePannel(JFrame menuFrame, GameFrame gameFrame){

        GAME_WIDTH = menuFrame.getWidth();
        GAME_HEIGHT = (int) (GAME_WIDTH*0.5);
        SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        PADDLE_WIDTH = GAME_WIDTH/5;
        PADDLE_HEIGHT = GAME_HEIGHT/30;
        BALL_DIAMETER = PADDLE_HEIGHT;
        BOX_HEIGHT_FACTOR = 10;
        BOX_WIDTH_FACTOR = 10;
        BOX_HEIGHT = (int)(GAME_HEIGHT/(BOX_HEIGHT_FACTOR*1.5));
        BOX_WIDTH = GAME_WIDTH/BOX_WIDTH_FACTOR;

        this.menuFrame = menuFrame;
        this.gameFrame = gameFrame;
        life = 3;
        newPaddle();
        newBall();
        initBoxes();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.addMouseMotionListener(new MML());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void initBoxes(){
        boxes = new Box[boxCount];
        int yExtra = 1*BOX_HEIGHT;
        int xExtra = 1*BOX_WIDTH;
        int gap = 5;
        int index = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                boxes[index] = new Box(((j+1)*BOX_WIDTH)+(gap/2),((i+1)*BOX_HEIGHT)+yExtra+(gap/2),BOX_WIDTH-gap,BOX_HEIGHT-gap);
                index++;
            }
        }
    }
    public void newPaddle(){
        paddle = new Paddle((GAME_WIDTH/2)-(PADDLE_WIDTH/2),(GAME_HEIGHT-PADDLE_HEIGHT),PADDLE_WIDTH,PADDLE_HEIGHT);
    }
    public void newBall(){
        //random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),paddle.y-20-BALL_DIAMETER,BALL_DIAMETER,BALL_DIAMETER);

    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        paddle.draw(g);
        ball.draw(g);
        for(int i=0;i<boxCount;i++){
            boxes[i].draw(g);
        }
        score.draw(g,life);
    }
    public void move(){
        paddle.move();
        ball.move();
    }
    public void checkCollision(){
        // stop paddle at window edges
        if(paddle.x <= 0){
            paddle.x = 0;
        }
        if(paddle.x >= GAME_WIDTH-PADDLE_WIDTH){
            paddle.x = GAME_WIDTH-PADDLE_WIDTH;
        }

        // bounce the ball from window edges
        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER){
            if(life != 0){
                ball.setYDirection(-ball.yVelocity);
                life--;
            }else{
                backToMenu();
            }
        }
        if(ball.x <= 0){
            ball.setXDirection(-ball.xVelocity);
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER){
            ball.setXDirection(-ball.xVelocity);
        }

        // paddle ball collision
        if(ball.intersects(paddle)){
            ball.yVelocity = -ball.yVelocity;
            ball.setPosition(ball.x,paddle.y-BALL_DIAMETER-1);
            int midBall = ball.x+(BALL_DIAMETER/2);
            int midPaddle = paddle.x+(PADDLE_WIDTH/2);
            int diff = midBall - midPaddle;
            int xFactor = (10*Math.abs(diff))/(PADDLE_WIDTH/2);

            if(diff<0){
                if(ball.xVelocity - xFactor < -xSpeedLimit){
                    ball.xVelocity = -xSpeedLimit;
                }else {
                    ball.xVelocity -= xFactor;
                }
            }else{
                if(ball.xVelocity + xFactor > xSpeedLimit){
                    ball.xVelocity = xSpeedLimit;
                }else {
                    ball.xVelocity += xFactor;
                }
            }
        }

        // ball box collision
        for(int i=0;i<boxCount;i++){
            if(boxes[i].weight >= 1 && ball.intersects(boxes[i])){
                int point1 = Math.abs(boxes[i].x - (ball.x + BALL_DIAMETER));
                int point2 = Math.abs(boxes[i].y - (ball.y + BALL_DIAMETER));
                int point3 = Math.abs(ball.x - (boxes[i].x + BOX_WIDTH));
                int point4 = Math.abs(ball.y - (boxes[i].y + BOX_HEIGHT));
                int min = Math.min(Math.min(Math.min(point1,point2),point3),point4);
                if(min == point1 || min == point3){
                    ball.setXDirection(-ball.xVelocity);
                } else if (min == point2 || min == point4) {
                    ball.setYDirection(-ball.yVelocity);
                }
                boxes[i].weight--;
                if(boxes[i].weight == 0){
                    remainingBoxes--;
                    int speed = ball.yVelocity;
                    if(remainingBoxes%yFactor == 0 && Math.abs(speed)<ySpeedLimit){
                        if(speed<0){
                            ball.yVelocity--;
                        }else{
                            ball.yVelocity++;
                        }
                    }
                }
                score.score++;
                break;
            }
        }
    }
    public void backToMenu(){
        gameFrame.setVisible(false);
        menuFrame.setVisible(true);
        gameThread.stop();
    }
    public void clearedBoxes(){
        if(remainingBoxes == 0){
            System.out.println("Level Cleared");
            backToMenu();
        }
    }
    public void edgeStuck(){
        if(ball.xVelocity == 0){
            if(ball.x >= GAME_WIDTH-BALL_DIAMETER-2){
                ball.xVelocity--;
            }else if(ball.x <= 1){
                ball.xVelocity++;
            }
        }
    }
    public void run(){ // game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1){
                checkCollision();
                move();
                edgeStuck();
                clearedBoxes();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                backToMenu();
            }
        }

    }
    public class MML implements MouseMotionListener {
        @Override
        public void mouseMoved(MouseEvent e) {
            paddle.setXPosition(e.getX()-(PADDLE_WIDTH/2));
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            //System.out.println("drag");
        }
    }
}
