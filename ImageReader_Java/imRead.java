import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


public class imageReader {

  
   public static void main(String[] args) 
   {
   	

	String fileName = args[0];
	int Ys = Integer.parseInt(args[1]);     	/* Sampling value for Y */
	int Us = Integer.parseInt(args[2]);			/* Sampling value for U */
	int Vs = Integer.parseInt(args[3]);			/* Sampling value for V */
	int	Qu = Integer.parseInt(args[4]);			/* Quantisation	level */
   	int width = 352;
	int height = 288;
	
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	BufferedImage img_out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
	int hw = (height*width)+1;
	
	int[] R = new int[(int)hw];
	int[] G = new int[(int)hw];
	int[] B = new int[(int)hw];
	
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
				byte g = bytes[ind+height*width];
				byte b = bytes[ind+height*width*2]; 
				
				/*Values of RBG is brought into the range 0 to 255*/
				if(r<0)
				{
					R[ind] = (int)(r+256);
				}else{ 	
					R[ind] = r;
				}
				if(g<0)
				{
					G[ind] = (int)(g+256);
				}else{ 	
					G[ind] = g;
				}
				if(b<0)
				{
					B[ind] = (int)(b+256);
				}else{ 	
					B[ind] = b;
				}
				
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				img.setRGB(x,y,pix);
				ind++;
			}
		}
		
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	
	/* Gaussian blur */
	
	int i = 0, End = (width-1);
	
	for( i = width ; i<(hw-width-1) ; i++)
	{
		if(((i%width)==0) || (i == End))
		{
			if(i%width == 0)
				End = i+width-1;
			i++;
		}else
		{
			R[i] = (int)((R[i-1]+R[i+1]+R[i+width-1]+R[i+width]+R[i+width+1]+R[i-width-1]+R[i-width]+R[i-width+1])/8);
			G[i] = (int)((G[i-1]+G[i+1]+G[i+width-1]+G[i+width]+G[i+width+1]+G[i-width-1]+G[i-width]+G[i-width+1])/8);
			B[i] = (int)((B[i-1]+B[i+1]+B[i+width-1]+B[i+width]+B[i+width+1]+B[i-width-1]+B[i-width]+B[i-width+1])/8);
		}
	}
	
	
	/*-----------------------------------------------------------------------*/
	
	//RGB to YUV Conversion
	double[] Y = new double[(int)hw];
	double[] U = new double[(int)hw];
	double[] V = new double[(int)hw];
	
	for (int i = 0; i<hw; i++)
	{	
		Y[i] =  0.299*R[i] + 0.587*G[i] + 0.114*B[i];
		U[i] = -0.147*R[i] - 0.289*G[i] + 0.436*B[i];
		V[i] =  0.615*R[i] - 0.515*G[i] - 0.100*B[i];	
    }
	/*---------------------------------------------------------------------*/
	
	//Down sampling of YUV
		
	double[] Y_DS = new double[(int)hw];
	double[] U_DS = new double[(int)hw];
	double[] V_DS = new double[(int)hw];
	
	/* Initialise array values to zero */
	for(int i = 0; i< hw; i++)
	{
		Y_DS[i] = 0;
		U_DS[i] = 0;
		V_DS[i] = 0;
	}
	
	if(Ys!=1)
	{	
		int k =0, i = 0, c=0;
		while(k < hw)
		{
			Y_DS[k] = Y[k];
			k = k+Ys;				/* To Down-sample skip the number of values as provided in the input  */
		}
	}else
	{
		for (int i = 0; i<hw; i++)
		{
			Y_DS[i] = Y[i];
		}
	}
	
	
	if(Us!=1)
	{	
		int k =0, i = 0;
		while(k < hw)
		{
			U_DS[k] = U[k];
			k = k+Us;
		}
	}else
	{
		for (int i = 0; i<hw; i++)
		{
			U_DS[i] = U[i];
		}
	}
	
	if(Vs!=1)
	{	
		int k =0, i = 0;
		while(k < hw)
		{
			V_DS[k] = V[k];
			k = k+Vs;
		}
	}else
	{
		for (int i = 0; i<hw; i++)
		{
			V_DS[i] = V[i];
		}
	}
	
	/*---------------------------------------------------------------*/
	//Up sampling of YUV
	double[] Y_US = new double[(int)hw];
	double[] U_US = new double[(int)hw];
	double[] V_US = new double[(int)hw];
	
	for(int i = 0; i< hw; i++)
	{
		Y_US[i] = Y_DS[i];
		U_US[i] = U_DS[i];
		V_US[i] = V_DS[i];
	}
	
	if(Ys > 1)
	{	
		int i = 0; 
		while(i < (hw-1))
			{
				int j =i+1;
				double count=1;
				while (count<Ys)
				{
				Y_US[j] = (Y_DS[i]*((double)(Ys-count)/(double)Ys))+(Y_DS[i+Ys]*((double)count/(double)Ys));
				j++;
				count++;
				}
				i=i+Ys;
				
				if(i+Ys>=hw) 
				break;
			}
	}
	
	if(Us > 1)
	{
		int i = 0;
		while(i < (hw-1))
			{
				int j =i+1;
				double count=1;
				while (count<Us)
				{
				U_US[j] = (U_DS[i]*((double)(Us-count)/(double)Us))+(U_DS[i+Us]*((double)count/(double)Us));
				j++;
				count++;
				}
				i=i+Us;
				
				if(i+Us>=hw) break;
			}
	}
	
	if(Vs > 1)
	{
		int i = 0; 
		while(i < (hw-1))
		{
			int j =i+1;
			double count=1;
				while (count<Vs)
				{
				V_US[j] = (V_DS[i]*((double)(Vs-count)/(double)Vs))+(V_DS[i+Vs]*((double)count/(double)Vs));
				j++;
				count++;
				}
				i=i+Vs;
				
				if(i+Vs>=hw) break;
		}
	}
	
	/* ------------------------------------------------------------------- */
	/* Gaussian blur */
	int End = (width-1);
	
	for(int i = width ; i<(hw-width-1) ; i++)
	{
		if(((i%width)==0) || (i == End))
		{
			if(i%width == 0)
				End = i+width-1;
			i++;
		}else
		{
			Y[i] = (int)((Y[i-1]+Y[i+1]+Y[i+width-1]+Y[i+width]+Y[i+width+1]+Y[i-width-1]+Y[i-width]+Y[i-width+1])/8);
			U[i] = (int)((U[i-1]+U[i+1]+U[i+width-1]+U[i+width]+U[i+width+1]+U[i-width-1]+U[i-width]+U[i-width+1])/8);
			V[i] = (int)((V[i-1]+V[i+1]+V[i+width-1]+V[i+width]+V[i+width+1]+V[i-width-1]+V[i-width]+V[i-width+1])/8);
		}
	}
	/*---------------------------------------------------------------*/
	// YUV to RGB conversion
	double[] R_itm = new double[(int)hw];
	double[] G_itm = new double[(int)hw];
	double[] B_itm = new double[(int)hw];
	
	/* byte[] R1 = new byte[(int)hw];
	byte[] G1 = new byte[(int)hw];
	byte[] B1 = new byte[(int)hw]; */
	
	/* for (int i = 0; i<hw; i++)
	{	
		R_itm[i] =  0.999*Y_DS[i] + 0.000*U_DS[i] + 1.140*V_DS[i];
		G_itm[i] = 	1.000*Y_DS[i] - 0.395*U_DS[i] - 0.581*V_DS[i];
		B_itm[i] =  1.000*Y_DS[i] + 2.032*U_DS[i] - 0.000*V_DS[i];	
    } */
	
	for (int i = 0; i<hw; i++)
	{	
		R_itm[i] =  0.999*Y_US[i] + 0.000*U_US[i] + 1.140*V_US[i];
		G_itm[i] = 	1.000*Y_US[i] - 0.395*U_US[i] - 0.581*V_US[i];
		B_itm[i] =  1.000*Y_US[i] + 2.032*U_US[i] - 0.000*V_US[i];	
    }
	
	for (int i = 0; i<hw; i++)
	{	
		R[i] = (int) R_itm[i];
		if(R[i]<0){
		R[i] = 0;
		}else
		if(R[i]>=255){
		R[i] = 255;
		}
		
		
		G[i] = (int) G_itm[i];
		if(G[i]<0){
		G[i] = 0;
		}else
		if(G[i]>=255){
		G[i] = 255;
		}
		
		
		B[i] = (int) B_itm[i];
		if(B[i]<0){
		B[i] = 0;
		}else
		if(B[i]>=255){
		B[i] = 255;
		}
		
    }
	
	/*---------------------------------------------------------------*/
	//Quantisation
	
	int Q_lvl = Math.round((256/Qu));
	int[] lookup = new int [hw];
	
	lookup[0] = 0;
	lookup[1] = Q_lvl-1;
	int k = 2;
	int value = 0;
	while(value <= (256-Q_lvl))
	{
		lookup[k] = lookup[k-1]+Q_lvl;
		value = lookup[k];
		k++;
	}
	
	for(int i = 0; i<hw; i++)
	{	
		int j = 1;
		while(lookup[j]<R[i])
		{	
			if(j<hw-2)
				j++;
			else
				break;
		}
		R[i] = (byte)lookup[j-1];
		
	}
	
	for(int i = 0; i<hw; i++)
	{	
		int j = 1;
		while(lookup[j]<G[i])
		{
			if(j<hw-2)
				j++;
			else
				break;
		}
		G[i] = (byte)lookup[j-1];
		
	}
	
	for(int i = 0; i<hw; i++)
	{	
		int j = 1;
		while(lookup[j]<B[i])
		{
			if(j<hw-2)
				j++;
			else
				break;
		}
		B[i] = (byte)lookup[j-1];
		
	}
	
	/*---------------------------------------------------------------*/
	
	// Dispaly image
	int ind = 0;
	for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
		 
				//byte a = 0;
				byte r = (byte) R[ind];
				byte g = (byte) G[ind];
				byte b = (byte) B[ind]; 
				
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				img_out.setRGB(x,y,pix);
				ind++;
			}
		}
	
	
	/*---------------------------------------------------------------*/
   
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