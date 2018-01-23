/* The given class is modified from the class having the same name in the code given in the textbook for Defining Polygon.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Game extends Canvas
{ 
	float x0, y0, rWidth = 20F, rHeight = 30F, pixelSize;	// pixelSize to set the size
	boolean ready = false;	// boolean for pausing the Game
	int centerX, centerY;
	public Graphics g;
	public int left = iX(-rWidth/2), right = iX(rWidth/2),bottom = iY(-rHeight/2), top = iY(rHeight/2);

Game()
{ 
	addMouseListener(new MouseAdapter()
	{ 
		public void mousePressed(MouseEvent evt)
		{	
			float xA = fx(evt.getX()), yA = fy(evt.getY());
			boolean quit = false;	// boolean for quit button
			quit = checkQuit(xA,yA);
			if(quit)
			{
				System.exit(0);
			}
		}
	});
	addMouseMotionListener(new MouseAdapter()
	{
		public void mouseMoved(MouseEvent evt)
		{
			float xA = fx(evt.getX()), yA = fy(evt.getY());
			ready = checkPause(xA,yA);		// check for pause
			repaint();
		}
	}
	);
}
	void initgr()
	{
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
		centerX = maxX/2; centerY = maxY/2;	// set center
	}
	
	int iX(float x){return Math.round(centerX + x/pixelSize);}	// conversion
	int iY(float y){return Math.round(centerY - y/pixelSize);}
	float fx(int x){return (x - centerX) * pixelSize;}
	float fy(int y){return (centerY - y) * pixelSize;}
	
	boolean checkQuit(float xa,float ya)	// checking to quit
	{
		int flag=0;
		int xA=iX(xa),yA=iY(ya);	// making suitable changes for the box
		int left = iX(-rWidth/2), right = iX(rWidth/2), bottom = iY(-rHeight/2), top = iY(rHeight/2);
		if(xA>(left+(2*(right-left)/3)+(right-left)/15) && xA<(left+(2*(right-left)/3)+(right-left)/15+(right - left)/5) && 
		yA>(3*bottom/4) && yA< (3*bottom/4 + (bottom - top)/8))
		{
			flag=1;
		}
		if(flag==0)
		{return false;}
		if(flag==1)
		{return true;}
		return false;	
	}
	boolean checkPause(float xa,float ya)
	{
		int flag=0;	// flag to check for pause
		int xA=iX(xa),yA=iY(ya);
		int left = iX(-rWidth/2), right = iX(rWidth/2), bottom = iY(-rHeight/2), top = iY(rHeight/2);
		if((xA>left) && xA<(left+2*(right - left)/3) && yA<(top/10 + 4*(right-left)/3)  && yA>top)
		{
			flag=1;
		}
		if(flag==0)
		{return false;}
		if(flag==1)
		{return true;}
		return false;
	}
	public void paint(Graphics g)
	{ 
		initgr();
		int left = iX(-rWidth/2), right = iX(rWidth/2),
		bottom = iY(-rHeight/2), top = iY(rHeight/2);	// suitable changes for boxes
		g.drawRect(left,top/10, 2*(right - left)/3, 4*(right-left)/3);
		// Size of main are to be 10 x 20 blocks
		// side of one block = (right-left)/15
		g.drawRect(left+(2*(right-left)/3), top/10, (right - left)/3, 4*(right-left)/3);
		g.drawRect(left+(2*(right-left)/3)+(right-left)/15,top/10, (right - left)/5, (bottom - top)/8);
		g.drawRect(left+(2*(right-left)/3)+(right-left)/15,3*bottom/4, (right - left)/5, (bottom - top)/8);
		g.drawString("Quit",left+(2*(right-left)/3)+(right-left)/12,(3*bottom/4)+(bottom - top)/16);
		g.drawString("Level:",left+(2*(right-left)/3)+(right-left)/15,bottom/4);
		g.drawString("Lines:",left+(2*(right-left)/3)+(right-left)/15,bottom/3);
		g.drawString("Score:",left+(2*(right-left)/3)+(right-left)/15,bottom/2);	// adding text
		
		if(ready)
		{
			Font myFont = new Font ("Courier New", 1, 17);
			g.setFont(myFont);
			g.drawString("Pause",left + (right-left)/4,(3*bottom/4+(bottom - top)/8)/2);// Pause text
		}
		
		// Draw a figure in the main box
		Point2D p2D =new Point2D(left+left/2,top);
		Shapes s=new Shapes(p2D, (right - left)/15, 1);
		int n=s.v.size();
		Point2D a= (Point2D)(s.v.elementAt(0));
		Point2D b= (Point2D)(s.v.elementAt(n-1));
		g.drawLine((int)a.x,(int)a.y,(int)b.x,(int)b.y);
		for(int i=1;i<n;i++)
		{
			b= (Point2D)(s.v.elementAt(i));
			g.drawLine((int)a.x,(int)a.y,(int)b.x,(int)b.y);
			a.x=b.x;a.y=b.y;
		}
		// Draw a figure in the next box
		Point2D p2D1 =new Point2D(left+(2*(right-left)/3)+(right-left)/10,top);
		Shapes s1=new Shapes(p2D1, (right - left)/15, 1);
		n=s1.v.size();
		Point2D c= (Point2D)(s1.v.elementAt(0));
		Point2D d= (Point2D)(s1.v.elementAt(n-1));
		g.drawLine((int)c.x,(int)c.y,(int)d.x,(int)d.y);
		for(int i=1;i<n;i++)
		{
			d= (Point2D)(s1.v.elementAt(i));
			g.drawLine((int)c.x,(int)c.y,(int)d.x,(int)d.y);
			c.x=d.x;c.y=d.y;
		}
	}
}
