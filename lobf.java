import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
class lobf extends JPanel
{
    double xMean,yMean,slope;
    Point p1,p2;
    ArrayList<Point> data;
    public lobf(JFrame f)
    {
        data=new ArrayList<>();
        p1=new Point(0,0);
        p2=new Point(0,0);
        f.setSize(800,600);
        f.add(this);
        setBackground(Color.black);
        f.setVisible(true);
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent me)
            {
                if(SwingUtilities.isRightMouseButton(me))
                {
                    data.clear();
                }
                else
                data.add(new Point(me.getX(),me.getY()));
                updateValues();
            }
        });
        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent me)
            {
                data.add(new Point(me.getX(),me.getY()));
                updateValues();
            }
        });
    }
    public void updateValues()
    {
        double sumxy=0,sumx=0,sumy=0,sumSqx=0;
        for(Point p:data)
        {
            sumxy+=p.x*p.y;
            sumx+=p.x;
            sumy+=p.y;
            sumSqx+=p.x*p.x;
        }
        xMean=sumx/data.size();
        yMean=sumy/data.size();
        slope=( (sumxy-(sumx*sumy)/data.size()) )/(sumSqx-(sumx*sumx)/data.size());
        p1=new Point(0,(int)f(0));
        p2=new Point(getWidth(),(int)f(getWidth()));
        repaint();
    }
    public double f(int x)
    {
        return (yMean+slope*(x-xMean));
    }
    public void paintComponent(Graphics gg)
    {
        super.paintComponent(gg);
        Graphics2D g=(Graphics2D)gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.white);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        g.setStroke(new BasicStroke(8));
        
        for(Point p:data)
        {
            g.drawLine(p.x,p.y,p.x,p.y);
            g.setColor(new Color((p.x*255)/getWidth(),156,(p.y*255)/getHeight()));
            g.drawString("("+p.x+","+p.y+")",p.x+10,p.y+4);
        }
        
        g.setColor(Color.white);
        g.drawString("y="+slope+"x+"+f(0),20,30);
    }
    public static void main(String[] ar)
    {
        new lobf(new JFrame("Line of best fit"));
    }
}