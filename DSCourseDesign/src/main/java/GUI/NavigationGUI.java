package GUI;

import Graph.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class NavigationGUI  {
    String imagePath ="src/main/resources/image/SchoolMap.jpg";

    private static final int WIDTH = 673; // 窗口宽度
    private static final int HEIGHT = 723; // 窗口高度

    private int[] X;
    private int[] Y;

    private ArrayList<Integer> XA = new ArrayList<>();
    private ArrayList<Integer> YA = new ArrayList<>();

    private ArrayList<Integer> XB = new ArrayList<>();
    private ArrayList<Integer> YB = new ArrayList<>();

    private int deltaX;
    private int deltaY;


    private int startX;
    private int startY;

    private int endX;
    private int endY;

    private int currentX;
    private int currentY;

    ArrayList<Node> path  = new ArrayList<>();

    JFrame frame;
    JLayeredPane layeredPane  = new JLayeredPane();
    JPanel imagePanel ;
    JPanel transparentPanel ;

    Timer timer;

    private void initializeUI() {
        for(Node node:path){
            XA.add(node.getX());
            YA.add(node.getY());
        }

        XB.add(XA.get(0));
        YB.add(YA.get(0));

        Integer[] XB1 = XB.toArray(new Integer[0]);
        X = new int[XB1.length];
        for (int j = 0;j<XB1.length;j++){
            X[j] = XB1[j];
        }

        Integer[] YB1 = YB.toArray(new Integer[0]);
        Y = new int[YB1.length];
        for (int j = 0;j<YB1.length;j++){
            Y[j] = YB1[j];
        }



        frame = new JFrame("Line Animation Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                g2d.drawPolyline(X,Y,X.length);

                // 恢复线条粗细
                g2d.setStroke(oldStroke);
            }
        };
        transparentPanel.setOpaque(false); // 设置面板透明
        transparentPanel.setBounds(0, 0, 673, 723);

        layeredPane.add(transparentPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(imagePanel, JLayeredPane.DEFAULT_LAYER);


      frame.add(layeredPane);
    }

    private void startAnimation() {
        final int i = 0;//标识当前位置
        int tail = path.size();//最大长度

        int animationDuration = 1500; // 动画持续时间（毫秒）
        int framesPerSecond = 60; // 每秒帧数

        int totalFrames = animationDuration / (1000 / framesPerSecond); // 总帧数

        startX = XA.get(i);
        startY = YA.get(i);

        currentX = startX;
        currentY = startY;

        endX = XA.get(i+1);
        endY = YA.get(i+1);



         deltaX = (endX - startX) / totalFrames; // X轴每帧的增量
         deltaY = (endY - startY) / totalFrames; // Y轴每帧的增量

        timer = new Timer(1000 / framesPerSecond, new ActionListener() {
            private int currentFrame = 1;
            int t = i;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame <= totalFrames) {
                    currentX += deltaX; // 更新当前坐标X
                    currentY += deltaY; // 更新当前坐标Y

                    XB.add(currentX);
                    YB.add(currentY);

                    Integer[] XB1 = XB.toArray(new Integer[0]);
                    X = new int[XB1.length];
                    for (int j = 0;j<XB1.length;j++){
                        X[j] = XB1[j];
                    }

                    Integer[] YB1 = YB.toArray(new Integer[0]);
                    Y = new int[YB1.length];
                    for (int j = 0;j<YB1.length;j++){
                        Y[j] = YB1[j];
                    }

                    transparentPanel.repaint(); // 触发重绘
                    currentFrame++;
                } else {
                    if(t!=tail-1){
                        currentFrame=1;
                        t++;

                        startX = XA.get(t);
                        startY = YA.get(t);

                        currentX = startX;
                        currentY = startY;

                        endX = XA.get(t+1);
                        endY = YA.get(t+1);

                        XB.add(startX);
                        YB.add(startY);

                        Integer[] XB1 = XB.toArray(new Integer[0]);
                        X = new int[XB1.length];
                        for (int j = 0;j<XB1.length;j++){
                            X[j] = XB1[j];
                        }

                        Integer[] YB1 = YB.toArray(new Integer[0]);
                        Y = new int[YB1.length];
                        for (int j = 0;j<YB1.length;j++){
                            Y[j] = YB1[j];
                        }
                        transparentPanel.repaint(); // 触发重绘

                        changeDeltaX((endX - startX) / totalFrames);
                        changeDeltaY((endY - startY) / totalFrames);
                    }else {
                        System.out.println(new Date());
                    }




                }
            }
        });

        timer.start(); // 启动定时器
    }

    private void changeDeltaX(int newvalue){
        deltaX = newvalue;
    }

    private void changeDeltaY(int newvalue){
        deltaY = newvalue;
    }

    public void show() {
        frame.setVisible(true);
    }

    public NavigationGUI(ArrayList<Node> path) {
        this.path = path;
        initializeUI();
        startAnimation();
        this.show();
    }
}
