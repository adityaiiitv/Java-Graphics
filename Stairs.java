// Beams.java: Generating input files for a spiral of beams. The
//    values of n, a and alpha (in degrees) as well as the output 
//    file name are to be supplied as program arguments.
//    Uses: Point3D (Section 3.9).
import java.io.*;
import java.awt.*;
import java.awt.event.*;
public class Stairs
{
	public static int height_C;
	public static void main(String[] args) throws IOException
	{
		if (args.length != 3)
		{
			System.out.println(
               "Supply n (> 0), a (>= 0.5), alpha (in degrees)\n" + 
               "and a filename as program arguments.\n");
			System.exit(1);
		}
		int n = 0;
		double a = 0, alphaDeg = 0;
		try 
		{
			n = Integer.valueOf(args[0]).intValue();
			a = 10;//Double.valueOf(args[1]).doubleValue();
			alphaDeg = Double.valueOf(args[1]).doubleValue();
			if (n <= 0 || a < 0.5)
				throw new NumberFormatException();
		} 
		catch (NumberFormatException e) 
		{
			System.out.println("n must be an integer > 0");
			System.out.println("a must be a real number >= 0.5");
			System.out.println("alpha must be a real number");
			System.exit(1);
		}
		new BeamsObj(n, a, alphaDeg * Math.PI / 180, "stairs_beg.dat");
		height_C=n;
		new Cylinder();
		try 
		{
			// create a writer for permFile
			BufferedWriter out = new BufferedWriter(new FileWriter("stairs_full.dat", true));
			// create a reader for tmpFile
			BufferedReader in = new BufferedReader(new FileReader("stairs_beg.dat"));
			String str;
			while ((str = in.readLine()) != null)
			{
				out.write("\n"+str);
            }
			in.close();
			out.close();
		}
		catch (IOException e) 
		{
        }
        
        try 
        {
			// create a writer for permFile
			BufferedWriter out = new BufferedWriter(new FileWriter("stairs_full.dat", true));
			// create a reader for tmpFile
			BufferedReader in = new BufferedReader(new FileReader("stairs_end.dat"));
			String str;
			while ((str = in.readLine()) != null) 
			{
				out.write("\n"+str);
			}
			in.close();
			out.close();
		} 
		catch (IOException e) 
		{
        }
        File file = new File("stairs_full.dat");
		File file2 = new File(args[2]);
		file.renameTo(file2);
   }
}

class BeamsObj 
{
	FileWriter fw;

	BeamsObj(int n, double a, double alpha, String fileName) throws IOException 
	{
		fw = new FileWriter(fileName);
		Point3D[] P = new Point3D[9];
		double b = a+5;
		P[1] = new Point3D(a, -2, 0);
		P[2] = new Point3D(a, 2, 0);
		P[3] = new Point3D(b, 2, 0);
		P[4] = new Point3D(b, -2, 0);
		P[5] = new Point3D(a, -2, 1);
		P[6] = new Point3D(a, 2, 1);
		P[7] = new Point3D(b, 2, 1);
		P[8] = new Point3D(b, -2, 1);
		
		int t=360;
		for (int k = t; k < n+t; k++) 
		{ // Beam k:
			double phi = (k-t) * alpha, cosPhi = Math.cos(phi), sinPhi = Math.sin(phi);
			int m = 8 * k;
			for (int i = 1; i <= 8; i++) 
			{
				double x = P[i].x, y = P[i].y;
				float x1 = (float) (x * cosPhi - y * sinPhi), 
                  y1 = (float) (x * sinPhi + y * cosPhi), 
                  z1 = (float) (P[i].z + k-t);
				fw.write(" "+(m + i) + " " + x1 + " " + y1 + " " + z1 + "\r\n");
			}
		}
		fw.write("Faces:\r\n");
		for (int k = t; k < n+t; k++) 
		{ // Beam k again:
			int m = 8 * k;
			face(m, 5, 6, 2, 1);
			face(m, 3, 7, 8, 4);
			face(m, 8, 7, 6, 5);
			face(m, 2, 3, 4, 1);
			face(m, 6, 7, 3, 2);
			face(m, 4, 8, 5, 1);
			/*face(m, 1, 2, 6, 5);
			face(m, 4, 8, 7, 3);
			face(m, 5, 6, 7, 8);
			face(m, 1, 4, 3, 2);
			face(m, 2, 3, 7, 6);
			face(m, 1, 5, 8, 4);*/
		}
		fw.close();
	}

	void face(int m, int a, int b, int c, int d) throws IOException 
	{
		a += m;
		b += m;
		c += m;
		d += m;
		fw.write(" "+a + " " + b + " " + c + " " + d + ".\r\n");
	}	
}
class Cylinder extends Frame 
{
	Cylinder() {new DlgCylinder(this);}
}

class DlgCylinder extends Dialog 
{
	FileWriter fw;

	DlgCylinder(Frame fr) 
	{
		super(fr, "Cylinder (possibly hollow); height = 1", true);
		int n = 20;
		float dOuter = 0, dInner = 0;
		dOuter = 20;
		dInner = 0;
		try 
		{
			genCylinder(n, dOuter / 2, dInner / 2);
		} 
		catch (IOException ioe) {}
	}

	void genCylinder(int n, float rOuter, float rInner) throws IOException 
	{
		int n2 = 2 * n, n3 = 3 * n, n4 = 4 * n;
		fw = new FileWriter("stairs_full.dat");
		double delta = 2 * Math.PI / n;
		int st=2000;
   
		for (int i = 1; i <= n; i++) 
		{
			double alpha = i * delta, cosa = Math.cos(alpha), sina = Math.sin(alpha);
			for (int inner = 0; inner < 2; inner++) 
			{
				double r = (inner == 0 ? rOuter : rInner);
				if (r > 0)
					for (int bottom = 0; bottom < 2; bottom++) 
					{
						int height=0;
						int k = (2 * inner + bottom) * n + i;
						if(bottom==1)
						{height=Stairs.height_C;}
						fw.write("\n "+k+" "+r*cosa+" "+r*sina+" "+height+"\r");
					}
			}
		}
		fw.close();
		fw = new FileWriter("stairs_end.dat"); 
		// Vertical, rectangular faces:
		for (int i = 1; i <= n; i++) 
		{
			int j = i%n + 1;//i % n + 1;
         // Outer rectangle:
			wi(j); wi(i); wi(i + n); wi(j + n); fw.write(".\r\n");
			wi(j + n); wi(i + n);wi(i);wi(j);  fw.write(".\r\n");
		}
		
		fw.close();
	}

	void wi(int x) throws IOException 
	{
		fw.write(" " + String.valueOf(x));
	}

	void wr(double x) throws IOException 
	{
		if (Math.abs(x) < 1e-9) x = 0;
		fw.write(" " + String.valueOf((float) x));
		// float instead of double to reduce the file size
	}
}
