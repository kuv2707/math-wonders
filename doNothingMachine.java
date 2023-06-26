import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class doNothingMachine extends JPanel 
{
    double RADIUS=300d;
    double OMEGA=.5d;
    double t=0;
    public static void main(String aRgs[])
    {
        new doNothingMachine(new JFrame("no hacer nada"));
    }
    public static void main()
    {
        main(null);
    }
    public doNothingMachine(JFrame f)
    {
        f.setSize(500,500);
        f.add(this);
        f.setVisible(true);
        new javax.swing.Timer(35,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                t+=0.05;
                repaint();
            }
        }).start();
    }
    Color darkWhite=new Color(255,255,255,130);
    public void paintComponent(Graphics gg)
    {
        super.paintComponent(gg);
        Graphics2D g=(Graphics2D)gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        g.translate(getWidth()/2,getHeight()/2);
        g.setStroke(new BasicStroke(5));
        double prevangle=0;
        g.setColor(Color.yellow);
        g.drawOval(-(int)(RADIUS),-(int)(RADIUS),(int)(2*RADIUS),(int)(2*RADIUS));
        for(double angle=0;angle<=2*Math.PI;angle+=Math.PI/18)
        {
            g.rotate(angle-prevangle);
            double pointX=RADIUS*Math.sin(OMEGA*t+angle);
            //g.setColor(Color.green);
            //g.drawLine((int)(-RADIUS),0,(int)(RADIUS),0);
            g.setColor(Color.white);
            //g.drawLine((int)pointX,0,(int)pointX,0);
            g.fillOval((int)pointX-10,-10,20,20);
            prevangle=angle;
        }
        
        
        // for(double angle=0;angle<=2*Math.PI;angle+=Math.PI/18)
        // {
            // g.rotate(angle-prevangle);
            // double pointX=RADIUS*Math.sin(OMEGA*(t-0.3)+angle);
            // //g.setColor(Color.green);
            // //g.drawLine((int)(-RADIUS),0,(int)(RADIUS),0);
            // g.setColor(darkWhite);
            // //g.drawLine((int)pointX,0,(int)pointX,0);
            // g.fillOval((int)pointX-10,-10,20,20);
            // prevangle=angle;
        // }
        
    }
}
