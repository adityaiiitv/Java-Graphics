/* The given class is modified from the class having the same name in the code given in the textbook for Defining Polygon
 */
import java.awt.*;
import java.awt.event.*;
public class GameWindow extends Frame
{ 
	public static void main(String[] args){new GameWindow();}

GameWindow()
{ 
	super("Define polygon vertices by clicking");
	addWindowListener(new WindowAdapter()
	{
		public void windowClosing(WindowEvent e){System.exit(0);}});
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setSize(500,500);//d.width-1,d.height-1);
		add("Center", new Game());
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		show();
	}
}
