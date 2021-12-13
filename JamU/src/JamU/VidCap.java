/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JamU;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.datasink.*;
import javax.swing.JOptionPane;

/**
 *
 * @author TW Burger from code by pawan_sin99
 */


    /**
     * @param args the command line arguments
     */
    


class VidCap {

    CaptureDeviceInfo device = null;
    MediaLocator ml = null;
    Player player = null;
    Component videoScreen = null;
    Processor processor = null;
    DataSink dataSink = null;
    TheDataSinkListener dataSinkListener = null;

    VidCap() {
        try { //gets a list of devices how support the given video format
            Vector deviceList = CaptureDeviceManager.getDeviceList(new YUVFormat());

            // get video device - the first one is almost always the only available camera
            device = (CaptureDeviceInfo) deviceList.firstElement();
            ml = device.getLocator();

            //create a source from the device
            DataSource ods = null;
            ods = Manager.createDataSource(ml);

            // Clone the video source so it can be displayed and used to capture
            // the video at the same time. Trying to use the same source for two
            // purposes would cause a "source is in use" error
            DataSource cloneableDS = Manager.createCloneableDataSource(ods);
            DataSource PlayerVidDS = cloneableDS;

            // The video capture code will use the clone which is controlled by the player
            DataSource CaptureVidDS = 
              ((javax.media.protocol.SourceCloneable) cloneableDS).createClone();

            // -----------------------------------------------------------------
            // Display video
            // by starting the player on the source
            // clonable data source the clones are fed data
            // stopping the player will stop the video flow to the clone data source
            // -----------------------------------------------------------------

            player = Manager.createRealizedPlayer(PlayerVidDS);
            player.start();

            // get an audio device and create an audio data source
            deviceList = CaptureDeviceManager.getDeviceList(
                            new javax.media.format.AudioFormat(null));
            device = (CaptureDeviceInfo) deviceList.firstElement();
            ml = device.getLocator();
            DataSource audioDataSource = Manager.createDataSource(ml);

            // merge audio and video data sources
            // --------------------------
            DataSource mixedDataSource = null;
            DataSource dsArray[] = new DataSource[2];
            dsArray[0] = CaptureVidDS; // this is a cloned datasource
                                 // and is controlled by the master clonable data source
            dsArray[1] = audioDataSource;
            try {
                mixedDataSource = javax.media.Manager.createMergingDataSource(dsArray);
            } catch (Exception e) {
                // your exception handling here
            }

            // setup output file format to msvideo
            FileTypeDescriptor outputType = 
                new FileTypeDescriptor(FileTypeDescriptor.MSVIDEO);

            // setup output video and audio data format
            Format outputFormat[] = new Format[2];
            //outputFormat[0] = new VideoFormat(VideoFormat.RGB);
            outputFormat[0] = new VideoFormat(VideoFormat.YUV);
            outputFormat[1] = new AudioFormat(AudioFormat.LINEAR);

            // ----------------------
            // create a new processor
            // ----------------------
            ProcessorModel processorModel = 
              new ProcessorModel(mixedDataSource, outputFormat, outputType);
            try {
                processor = Manager.createRealizedProcessor(processorModel);
            } catch (Exception e) {
                // your exception handling here
            }

            try {
                // get the output of the processor to be used as the datasink input 
                DataSource source = processor.getDataOutput();

                // create a File protocol MediaLocator with the location
                // of the file to which bits are to be written
                MediaLocator mediadestination = new MediaLocator("file:.\\vidcap.mpeg");

                // create a datasink to create the video file
                dataSink = Manager.createDataSink(source, mediadestination);
                
                // create a listener to control the datasink
                dataSinkListener = new TheDataSinkListener();
                dataSink.addDataSinkListener(dataSinkListener);
                dataSink.open();

                // now start the datasink and processor
                dataSink.start();

                processor.start();
            } catch (Exception e) {
                // your exception handling here
            }

            videoScreen = player.getVisualComponent();

          videoScreen.setSize(518, 408);
           

              

            

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

/**
 * Control the ending of the program prior to closing the data sink
 *
 */
class TheDataSinkListener implements DataSinkListener {

    boolean endOfStream = false;

    // Flag the ending of the data stream
    public void dataSinkUpdate(DataSinkEvent event) {
        if (event instanceof javax.media.datasink.EndOfStreamEvent) {
            endOfStream = true;
        }
    }

    /*Cause the current thread to sleep if the data stream is still available.
     * This makes certain that JMF threads are done prior
     * to closing the data sink and finalizing the output file
     */
    public void waitEndOfStream(long checkTimeMs) {
        while (!endOfStream) {
            try {
                //Thread.currentThread().sleep(checkTimeMs);
                Thread.sleep(checkTimeMs);
            } catch (InterruptedException ie) {
                // your exception handling here
            }
        }
    }
}