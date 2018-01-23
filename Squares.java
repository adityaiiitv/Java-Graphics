/* The following code is a modified version of the code given at page 13 figure 1.5 in the textbook by Leen Ammeraal and Kang Zhang.
 * The textbook code was to create 50 triangles inside one another.
 * It was modified to create 30 squares inscribed in each other.
*/
import java.awt.*;
import java.awt.event.*;

public class Squares extends Frame
{ 
	public static void main(String[] args)
	{
		new Squares();	// Calling default constructor for Squares
	}
	Squares()
	{
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e){System.exit(0);}});
			setSize (1000, 1000);	// Setting window size
			add("Center", new CvSquares());
			show();
		}
	}
	
class CvSquares extends Canvas
{	
	int maxX, maxY, minfromboth, xCenter, yCenter;
	void initgr()
	{ 
		Dimension d = getSize();
		maxX = d.width - 1; maxY = d.height - 1;
		minfromboth = Math.min(maxX, maxY);	// to get the minimum from both maxX and maxY
		xCenter = maxX/2; yCenter = maxY/2;
	}
	

	
	public void paint(Graphics g)
	{ 
		initgr();
		float side = 0.95F * minfromboth, sideHalf = 0.5F * side, // to get exactly half from a side
		h = sideHalf * (float)Math.sqrt (3),
		xA, yA, xB, yB, xC, yC, xD, yD,
		xA1, yA1, xB1, yB1, xC1, yC1, xD1,yD1, p, q;
		q = 0.5F;	// set ratio
		p = 1 - q;
		xA = xCenter - sideHalf;
		yA = yCenter - 0.5F * h;
		xB = xCenter + sideHalf;
		yB = yA;
		xD = xCenter - sideHalf;	// In the notation I have changed the allignment of c and d.
		yD = yCenter + 0.5F * h;	// That means D comes before C.
		xC = xCenter + sideHalf;
		yC = yCenter + 0.5F * h;
		for (int i=0; i<30; i++)
		{ 
			g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));	// drawing all 4 lines
			g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
			g.drawLine(iX(xC), iY(yC), iX(xD), iY(yD));
			g.drawLine(iX(xD), iY(yD), iX(xA), iY(yA));
			xA1 = p * xA + q * xB;
			yA1 = p * yA + q * yB;
			xB1 = p * xB + q * xC;
			yB1 = p * yB + q * yC;
			xC1 = p * xC + q * xD;
			yC1 = p * yC + q * yD;
			xD1 = p * xD + q * xA;	// changing
			yD1 = p * yD + q * yA;
			xA = xA1; xB = xB1; xC = xC1; xD=xD1;
			yA = yA1; yB = yB1; yC = yC1; yD=yD1;
		}
	}
	int iX(float x){return Math.round(x);}	// conversion
	int iY(float y){return maxY - Math.round(y);}
}
