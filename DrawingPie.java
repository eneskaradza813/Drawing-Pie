package drawingpie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.*;
import javax.swing.*;
import javax.swing.JPanel;

public class DrawingPie extends JFrame{

    GraphicsPanel drawPanel = new GraphicsPanel();
    JButton drawButton  = new JButton();
    JButton clearButton = new JButton();
    static Ellipse2D.Double myEllipse;
    static int numberSlicies;
    static double[] extent = new double[6];
    static Color[] myColors = new Color[6];
    static boolean isDrawn = false;
    Random myRandom = new Random();
    
    public static void main(String[] args) {

        new DrawingPie().show();
    }

    public DrawingPie() {
        setTitle("Drawing Pie Segments");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
            
});
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        drawPanel.setPreferredSize(new Dimension(250, 250));
        drawPanel.setBackground(Color.WHITE);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(drawPanel, gridConstraints);
        drawButton.setText("Draw/Fill Pie");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        getContentPane().add(drawButton, gridConstraints);
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawButtonActionPerformed(e);
            }
        });
        clearButton.setText("Clear Pie");
        clearButton.setEnabled(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.insets = new Insets(5, 0, 5, 0);
        getContentPane().add(clearButton, gridConstraints);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtonActionPerformed(e);
            }
        });
        pack();
        myEllipse = new Ellipse2D.Double(20, 20, drawPanel.getWidth() - 40, drawPanel.getHeight()-40);
        myColors[0] = Color.RED;
        myColors[1] = Color.GREEN;
        myColors[2] = Color.YELLOW;
        myColors[3] = Color.BLUE;
        myColors[4] = Color.MAGENTA;
        myColors[5] = Color.CYAN;
        
    }
    void drawButtonActionPerformed(ActionEvent e){
        double degresRemaining = 360;
        
        numberSlicies = myRandom.nextInt(5)+2;
        for(int n = 0; n < numberSlicies; n++){
            if(n < numberSlicies - 1){
                extent[n] = myRandom.nextInt((int)(degresRemaining - 1))+1;
            }else{
                extent[n] = degresRemaining;
            }
            degresRemaining -= extent[n];
        }
        isDrawn = true;
        drawButton.setEnabled(false);
        clearButton.setEnabled(true);
        drawPanel.repaint();
    }
    void clearButtonActionPerformed(ActionEvent e){
        isDrawn = false;
        drawButton.setEnabled(true);
        clearButton.setEnabled(false);
        drawPanel.repaint();
    }
    void exitForm(WindowEvent e){
        System.exit(0);
    }
class GraphicsPanel extends JPanel{

        public GraphicsPanel() {
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D)g;
            super.paintComponent(g2D);
            if(DrawingPie.isDrawn){
                double startAngle = 0;
                for(int n = 0; n < DrawingPie.numberSlicies; n++){
                    Arc2D.Double myArc = new Arc2D.Double(DrawingPie.myEllipse.x, DrawingPie.myEllipse.y, DrawingPie.myEllipse.width, DrawingPie.myEllipse.height, startAngle, DrawingPie.extent[n], Arc2D.PIE);
                    g2D.setPaint(DrawingPie.myColors[n]);
                    g2D.fill(myArc);
                    g2D.setPaint(Color.BLACK);
                    g2D.draw(myArc);
                    startAngle += DrawingPie.extent[n];
                }
                g2D.draw(DrawingPie.myEllipse);
            }
            g2D.dispose();
        }
    
}
}
