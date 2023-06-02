package GUI;

import Graph.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Navigate2 {
    private static final int WIDTH = 673; // 窗口宽度
    private static final int HEIGHT = 723; // 窗口高度

    private ArrayList<Node> path  = new ArrayList<>();
    private JFrame frame;
    private JLayeredPane layeredPane  = new JLayeredPane();
    private JPanel imagePanel ;
    private JPanel transparentPanel ;

    int[] x;
    int[] y;


    public Navigate2(ArrayList<Node> path) {
        this.path = path;


        x = new int[path.size()];
        y = new int[path.size()];
        for(int i = 0;i<path.size();i++){
            x[i] = path.get(i).getX();
            y[i] = path.get(i).getY();
        }






        frame = new JFrame("导航" );
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/image/SchoolMap.jpg")); // 替换为你的图片路径
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage finalImage = image;

        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setBounds(0, 0, 673, 723);

        JPanel transparentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // 设置线条粗细
                Stroke oldStroke = g2d.getStroke();
                float lineWidth = 5f * 1; // 设置线条粗细为原始线条粗细的2.5倍
                g2d.setStroke(new BasicStroke(lineWidth));

                // 绘制红线
                g2d.setColor(Color.RED);
                g2d.drawPolyline(x,y,x.length);

                // 恢复线条粗细
                g2d.setStroke(oldStroke);
            }
        };
        transparentPanel.setOpaque(false); // 设置面板透明
        transparentPanel.setBounds(0, 0, 673, 723);

        layeredPane.add(transparentPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(imagePanel, JLayeredPane.DEFAULT_LAYER);


        frame.add(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
