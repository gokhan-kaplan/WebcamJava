/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JamU;
 import java.io.File;

    	import java.net.URL;
    	
   	import javax.swing.JFrame;
   
   	public class MediaTest extends JFrame
   	{
            public URL mediaURL=null;
         public void cl1(){
        
   
            	
               	
              	 
            
              	
   
            	 if ( mediaURL != null ) // only display if there is a valid URL
            	 {
              	 JFrame mediaTest = new JFrame( "Video" );
               	 mediaTest.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
                     
              	 MediaPanel mediaPanel = new MediaPanel( mediaURL );
              	 mediaTest.add( mediaPanel );
   
               	 mediaTest.setSize(640, 480 );
              	mediaTest.setVisible( true );
         
          }
                 
  	}
        }