import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Shapes
{
	Vector<Point2D> v = new Vector<Point2D>();	// A vector of points for a given shape
	Shapes(Point2D p, float side,int type)		// Parameterized constructor
	{
		if(type==1)								// Type 1 means a square
		{
			v.removeAllElements();				// Empty the vector
			v.addElement(new Point2D(p.x,p.y));						// Start adding points
			v.addElement(new Point2D(p.x + 2*side,p.y));
			v.addElement(new Point2D(p.x + 2*side,p.y + 2*side));
			v.addElement(new Point2D(p.x,p.y + 2*side));
		}
		if(type==2)
		{				// For more shapes
		}
		if(type==3)
		{
		}
		if(type==4)
		{
		}
		if(type==5)
		{
		}
		if(type==6)
		{
		}
		if(type==7)
		{
		}
	}	
}
