/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JamU;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

class GetGraphics extends FotografDuzenle
{
    public FotografDuzenle ft=null;
    public ImagePanel ipa=null; 
 
      
      public void c1(){
      
        JFrame frame=new JFrame("Kaydet");
  frame.setLayout(new FlowLayout());
  BufferedImage image=new BufferedImage(640,480,BufferedImage.TYPE_INT_ARGB);
   Graphics2D g=(Graphics2D)image.getGraphics();
  
  g.setColor(Color.red);
  g.fillRect(0, 0, 100, 100);
    g.setColor(Color.black);
  g.fillRect(20, 20, 100, 100);
    g.setColor(Color.blue);
  g.fillRect(50, 50, 100, 100);
  
  g.drawImage(ft.a, 0, 0, rootPane);
 //g.drawImage(ft.scaleImage, 0, 0, rootPane);
 //g.drawImage(ft.scaleImage2, 0, 0, rootPane);
  g.dispose();
  JLabel label=new JLabel(new ImageIcon(image));
  frame.add(label);
  
  frame.setSize(640,480);
      frame.setVisible(true);
      
      
      }
  public static void main(String[] args)
  {
     
     
     GetGraphics a=new GetGraphics();
     a.c1();
 
  
  }
}