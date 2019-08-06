package com.mycompany.a3;
/**
 * handles sound effects
 * 
 * @author Tim
 *
 */
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media media;
	public Sound(String fileName) {
		try{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),"/"+fileName);
			media = MediaManager.createMedia(is, "audio/wav");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//plays the sound
	public void play() {
		media.setTime(0);
		media.play();
	}
}
