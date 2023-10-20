import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.concurrent.*;
import java.awt.geom.*;
class randomPts extends JPanel
{
    ArrayList<Point> list;
    Point origin=new Point(0,0);
    int size=5;
    double scale=1;
    ExecutorService smoothAnim;
    public randomPts()
    {
        setBackground(Color.black);
        list=new ArrayList<>();
        smoothAnim=Executors.newFixedThreadPool(1);
        new javax.swing.Timer(4,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                list.add(new Point(    (int)(getWidth()*Math.pow(Math.random(),5)) ,  (int)((2000*Math.PI)*Math.pow(Math.random(),1))     ));
                repaint();
            }
        }).start();
        MouseAdapter works=new MouseAdapter()
        {
            Point lastOrigin=new Point(0,0);
            public void mousePressed(MouseEvent e)
            {
                lastOrigin.x=e.getX();
                lastOrigin.y=e.getY();                
            }
            public void mouseDragged(MouseEvent me)
            {
                origin.x+=me.getX()-lastOrigin.x;
                origin.y+=me.getY()-lastOrigin.y;
                lastOrigin.x=me.getX();
                lastOrigin.y=me.getY();
                
            }
        };
        addMouseMotionListener(works);
        addMouseListener(works);
        addMouseWheelListener(new MouseWheelListener()
        {
            public void mouseWheelMoved(MouseWheelEvent we)
            {
                animateOutTo(we.getWheelRotation());
            }
        });
    }
    int queue=0;
    public void animateOutTo(int zoomType)
    {
        //ignore scrolling if more than 15 are in the queue
        if(queue>15)
        return;
        queue++;
        smoothAnim.execute(new Runnable()
        {
            public void run()
            {
                double k=0;
                double init=scale;
                double finale=scale-0.03*zoomType;
                
                while(k<=1)
                {
                    scale=init*(1-k)+finale*(k);
                    try
                    {
                        Thread.sleep(1);
                    }
                    catch (Exception ie)
                    {
                        ie.printStackTrace();
                    }
                    k+=0.01;
                }
                queue--;
            }
        });
    }
    Font font=new Font("",Font.PLAIN,25);
    @Override
    public void paintComponent(Graphics gg)
    {
        Graphics2D g=(Graphics2D)gg;
        super.paintComponent(gg);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Scale: "+scale+"x",30,30);
        AffineTransform def=g.getTransform();
        float red=1f;
        g.translate(origin.x*red,origin.y*red);//dont do it after scale as then it drags in the proportion of the scale
            g.scale(scale,scale);g.translate(origin.x*red,origin.y*red);//dont do it after scale as then it drags in the proportion of the scale
            g.scale(scale,scale);
        size=(int)(5/scale);
        for(int i=0;i<list.size();i++)
        {
            Point p=list.get(i);
            
            red=(float)i/(float)list.size();
            
            g.setColor(new Color( (int)(255*red),255-(int)(255*red),255-(int)(255*red)));
            //g.fillOval(p.x,p.y,size,size);  //IF THE Point object represents the (x,y) cartesian coordinates
            g.fillOval(    (int)(getWidth()/(2*scale)+p.x*Math.cos(p.y)),(int)(getHeight()/(2*scale)+p.x*Math.sin(p.y)),size,size);   //if the Point object represents (r,theta)(polar coordinates)
        }
        g.setTransform(def);
    }
    public static void main(String[] aaa)
    {
        JFrame f=new JFrame("Random points");
        f.add(new randomPts());
        f.setSize(800,800);
        f.setVisible(true);
    }
}