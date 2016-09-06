/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipclasses;

import javax.sound.sampled.*;
import java.io.File;

/**
 *
 * @author jeffreyyee
 */
class Sound implements Runnable {
    Thread myThread;
    File soundFile;
    public boolean donePlaying = false;
    public boolean stopPlaying = false;
    Sound(String _name)
    {
        soundFile = new File(_name);
        myThread = new Thread(this);
        myThread.start();
    }
    public void run()
    {
        try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat format = ais.getFormat();
    //    System.out.println("Format: " + format);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
        source.open(format);
        source.start();
        int read = 0;
        byte[] audioData = new byte[16384];
        
        while (read > -1){
            if (stopPlaying)
            {
                read = -1;
            }
            else
            {
                read = ais.read(audioData,0,audioData.length);
                if (read >= 0) {
                    source.write(audioData,0,read);
                }
            }
        }
        donePlaying = true;

        source.drain();
        source.close();
        }
        catch (Exception exc) {
            System.out.println("error: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

}