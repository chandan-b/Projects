import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


public class MyCompression{

  
   public static void main(String[] args) 
   {
   
	String fileName = args[0];
	int width = 352;
	int height = 288;
   	int N = Integer.parseInt(args[1]);
	double[] im = new double[width*height];
	int[] im_out = new int[width*height];
	
	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    BufferedImage img_out = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	
	double eoo = (int) (Math.log(N) / Math.log(2));

	int[] R = new int[(int)(width*height)];
	int[] G = new int[(int)(width*height)];
	int[] B = new int[(int)(width*height)];
	
    try {
	    File file = new File(args[0]);
	    InputStream is = new FileInputStream(file);

	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	
	    
	    int offset = 0;
        int numRead = 0;
		
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
        }		
		
    	int ind = 0;
		for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
		 
				byte r = bytes[ind];
				
				//Values of RBG is brought into the range 0 to 255
				if(r<0)
				{
					R[ind] = (int)(r+256);
				}else{ 	
					R[ind] = r;
				}
				
				im[ind] = R[ind];
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((r & 0xff) << 8) | (r & 0xff);
				img.setRGB(x,y,pix);
				ind++;
			}
		}
		
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	
	/* Initial x and y co-ordinate generation */
	double[] x = new double[(int)N];
	double[] y = new double[(int)N];
	for(int i = 0; i<N; i++)
	{
		x[i] = 0;
		y[i] = 0;	
	}
	if(N==2)
	{
		x[0] = 256/4;
		y[0] = 256/2;
		
		x[1] = 256*0.75;
		y[1] = y[0];
	}else
	{
	/* For perfect squares */
	if(eoo%2 == 0)
	{
		eoo = Math.sqrt(N);
		int x_count = 0;
		int y_count = 0;
		int x_test = 1;
		double inc = 0;
		
		while(x_count<N)
		{
			if(x_test<=(N/eoo))
			{	
				if(x_count==0)
				{	
					double A = 256/eoo;
					x[x_count] = A/2;
					y[y_count] = A/2;
					y_count = y_count+(int)eoo;
					inc = x[x_count];
				}else
				{
					double A = ((256*(x_count)))/eoo;
					x[x_count] = A+inc;
					y[y_count] = A+inc;
					y_count = y_count+(int)eoo;
				}
				x_test++;
			}
			else
			{
				x[x_count] = x[(x_count-(int)(N/eoo))];
			}
			x_count++;
		}
		y_count=0;
		while(y_count<N)
		{	
			if(y[y_count]==0)
			{
				y[y_count] = y[y_count-1];		
			}
			y_count++;
		}
	}else
	if(eoo%2 != 0)
	{	
		/* For non-perfect squares */
		eoo = Math.pow(2,(eoo+1));
		eoo = Math.sqrt(eoo);
		int x_count = 0;
		int y_count = 0;
		int x_test = 1;
		int y_test = 1;
		double yeoo = eoo/2;
		double inc = 0;
		
		while(x_count<N)
		{
			if(x_test<=(N/yeoo))
			{	
				if(x_count==0)
				{	
					double A = 256/eoo;
					x[x_count] = A/2;
					inc = x[x_count];
				}else
				{
					double A = ((256*(x_count)))/eoo;
					x[x_count] = A+inc;
				}
				x_test++;
			}
			else
			{
				x[x_count] = x[(x_count-(int)(N/yeoo))];
			}
			x_count++;
		}
		x_count = 0;
		x_test = 1;
		while(x_count<N)
		{
			if(x_test<=(N/eoo))
			{	
				if(x_count==0)
				{	
					double A = 256/eoo;
					y[y_count] = A/2;
					y_count = y_count+(int)eoo;
				
				}else
				{
					double A = ((256*(x_count)))/eoo;
					y[y_count] = A;
					y_count = y_count+(int)eoo;
				}
				x_test++;
			}
			x_count++;
		}
		y_count=0;
		while(y_count<N)
		{
			if(y[y_count] != 0)
			{
				if(y_count == 0)
				{
					y[y_count] = 256/eoo;
					inc = y[y_count];
				}
				else
				{
					y[y_count] = ((256*y_test)/yeoo)+inc;
					y_test++;
				}
			}else
			{
				y[y_count] = y[y_count-1];
			}
			y_count++;
		}
	}
	}
	/* ------------------------------------------------------------------------------------------------------ */
	double wh = width*height;
	/*Array of original pixel values*/
	double[] im_x = new double[(width*height)/2];
	double[] im_y = new double[(width*height)/2];
	/*Array of image containing nearest centroid values*/
	double[] im_xnew = new double[(width*height)/2];
	double[] im_ynew = new double[(width*height)/2];
	/*Array to hold the sum*/
	double[] im_xsum = new double[(int)N];
	double[] im_ysum = new double[(int)N];
	/*Array which has previous centroid values*/
	int[] xold = new int[(int)N];
	int[] yold = new int[(int)N];
	/*Array to hold count used for calculation*/
	double[] x_avg = new double[(int)N];
	double[] y_avg = new double[(int)N];
	/*Euclidean distance holder array*/
	double[] ED = new double[(int)N];

	for(int i = 0; i<N; i++)
	{ /*Euclidean co-ordinates to be used for testing the break condition*/
		xold[i] = 0;
		im_xsum[i] = 0;
		x_avg[i] = 0;
		yold[i] = 0;
		im_ysum[i] = 0;
		y_avg[i] = 0;
		ED[i] = 0;
	}
	
	/* Initial pixel mapping to x and y co-ordinates*/
	int m = 0;
	int n = 0;
	while(m<(wh/2))
	{
		im_x[m] = im[n];  		//Initial pixels arranged as x and y
		n = n+1;
		im_y[m] = im[n];
		im_xnew[m] = 0;
		im_ynew[m] = 0;
		m++;
		n=n+1;
		
	}
	
	/*Clustering Method*/
	int flag = 1;
	int flagcount = 0; 
	int count = 0;
	int index = 0;
	double min_val = 0;
	while(flag == 1)
	{
	/* Euclidean distance calculation and assigning New array the value of the centroid which has minimum distance.*/
	for(int i =0; i<(wh/2);i++)
	{
		int k = 0;
		while(k<N)
		{	/*Calculate the euclidean distance for a pixel with all the centroid points*/
			double X1 = Math.pow((im_x[i]-x[k]),2);
			double Y1 = Math.pow((im_y[i]-y[k]),2);
			ED[k] = Math.sqrt((X1+Y1));
			k++;
		}
		min_val = ED[0];
		index = 0;
		for(int j = 1; j<N; j++)
		{	/*Find the minimum of ED calculated for each pixel*/
			if(min_val>ED[j])
			{
				min_val = ED[j];
				index = j;
			}else
			{
			/* do nothing */
			}
		}
		/*Create a new x and y array and assign the centroid value which is at minimum distance from the pixel*/
		im_xnew[i] = x[index];
		im_ynew[i] = y[index];
	}
	/* Loop exit  */
	flagcount = 0;
	for(int i=0; i<N ;i++)
	{	
		//Check whether the old and new centroid points are the same
		if(xold[i]==(int)x[i] && yold[i]==(int)y[i])
		{
			flagcount++;
		}else
		{
			/*Do nothing*/
		}

	}
	if(flagcount==N)
	break; /*Break and exit from the loop to quantisation stage*/
	else
	{	
		/*Initialize the sum and average count to zero*/
		for(int i = 0; i<N; i++)
		{ /*Euclidean co-ordinates to be used for testing the break condition*/
			im_xsum[i] = 0;
			x_avg[i] = 0;
			im_ysum[i] = 0;
			y_avg[i] = 0;
		}
		/*Compute the average of the original pixel values which have same centroid value*/
		for(int i=0; i<(wh/2);i++)
		{
			int k = 0;
			while(k<N)
			{
				if(im_xnew[i]==x[k] && im_ynew[i]==y[k])
				{
					im_xsum[k] = im_xsum[k]+im_x[i];
					x_avg[k] = x_avg[k]+1;
					im_ysum[k] = im_ysum[k]+im_y[i];
					y_avg[k] = y_avg[k]+1;
				}else
				{
				/*Do Nothing*/
				}
				k++;
			}
		}
		/*Assign the existing centroid values to old and update the new centroid values with the average computed*/
		for(int i=0;i<N;i++)
		{
			xold[i] = (int)x[i];
			if(x_avg[i]==0)
			{
				x[i] = 0;
			}else
			{
				x[i] = im_xsum[i]/x_avg[i];
			}
			yold[i] = (int)y[i];
			if(y_avg[i]==0)
			{
				y[i] = 0;
			}else
			{
				y[i] = im_ysum[i]/y_avg[i];
			}
		}
	}
	/* if(flagcount==N)
	break; */
	}
	
	/*Quantization step*/
	int k =0;
	for(int i=0; i<wh ;i++)
	{
		im_out[i] = (int)im_xnew[k];
		im_out[i+1] = (int) im_ynew[k];
		i++;
		k++;
	}
 	
	/*------------Display output Image-----------------------*/
	int ind = 0;
	for(int i = 0; i < height; i++){
	
			for(int j = 0; j < width; j++){
		 
				int pix = 0xff000000 | ((im_out[ind] & 0xff) << 16) | ((im_out[ind] & 0xff) << 8) | (im_out[ind] & 0xff);
				img_out.setRGB(j,i,pix);
				ind++;
			}
		}
	/*---------------------------------------------------------*/
	
    // Use a panel and label to display the image
    JPanel  panel = new JPanel ();
    panel.add (new JLabel (new ImageIcon (img)));
    panel.add (new JLabel (new ImageIcon (img_out)));
    
    JFrame frame = new JFrame("Display images");
    
    frame.getContentPane().add (panel);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

   }
  
}