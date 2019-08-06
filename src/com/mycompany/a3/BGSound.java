package com.mycompany.a3;
import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
/**
 * handles background music
 * 
 * @author Tim
 *
 */

public class BGSound implements Runnable{
	private Media media;
	
	public BGSound(String fileName){
		try{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),"/"+fileName);
			media = MediaManager.createMedia(is, "audio/wav", this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//pauses the music
	public void pause() {
		media.pause();
	}
	//plays the music
	public void play() {
		media.play();
	}
	
	public void run() {
		media.setTime(0);
		media.play();
	}
}
