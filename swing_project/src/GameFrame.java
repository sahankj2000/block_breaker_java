import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePannel panel;
    GameFrame(JFrame menuFrame){
        panel = new GamePannel(menuFrame,this);
        this.add(panel);
        this.setTitle("Block Breaker");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
