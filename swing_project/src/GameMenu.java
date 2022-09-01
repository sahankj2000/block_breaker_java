import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu {
    GameMenu(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame();
        float refactor = 0.15F;
        frame.setSize(size.width-(int)(size.width*refactor),size.height-(int)(size.height*refactor));
        refactor = 0.25F;
        JButton start = new JButton("New Game");
        start.setFont(new Font("Arial",Font.PLAIN,(int)(frame.getHeight()*0.05)));
        start.setBounds((int)(frame.getWidth()*refactor),(int)(frame.getHeight()*refactor),(int)(frame.getWidth()*refactor*2),(int)(frame.getHeight()*0.075));
        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial",Font.PLAIN,(int)(frame.getHeight()*0.05)));
        exit.setBounds((int)(frame.getWidth()*refactor),((int)(frame.getHeight()*refactor)+((int)(frame.getHeight()*0.075)+10)),(int)(frame.getWidth()*refactor*2),(int)(frame.getHeight()*0.075));
        frame.add(start);
        frame.add(exit);
        frame.setTitle("Block Breaker Java");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                GameFrame gameFrame = new GameFrame(frame);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }
}
