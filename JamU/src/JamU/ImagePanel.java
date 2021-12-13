/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JamU;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author ogrenci
 */

    class ImagePanel extends Panel 

    {
public BufferedImage bfı=null;
    public Image myimg = null;
public Image cr=null;
public Image an=null;
public Image sn=null;
    public ImagePanel()

    {
     
      
     setLayout(null);
      setSize(1000,1000);

    }

   

    public void setImage(Image img)

    {

      this.myimg = img;
 
      repaint();

    }
 

    public void paint(Graphics g)   

    {

      if (myimg != null) 

      {
 
      g.drawImage(myimg, 0, 0, this);
       g.drawImage(an, 0, 0, this);
  g.drawImage(cr, 0, 0, this);

  
  
  
      }
      

    }
    public Image getImage(){
    
    return this.myimg;
    }
 
    }


    
