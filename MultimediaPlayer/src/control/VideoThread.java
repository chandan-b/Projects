package control;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;

import data.VideoContainer;

public class VideoThread extends Thread {
	
	VideoContainer video;
	ImageIcon frameimage;
	JLabel videobox;
	Clip audioClip;
	JSlider videoslider;
	
	long framedelay;
	int curframe = 0;
	boolean videopaused = false;
	public boolean videostarted = false,videoEnded = false;
	
	public VideoThread(VideoContainer videocontainer, ImageIcon videoframe, JLabel videobox, JSlider videoslider){
		
		this.video = videocontainer;
		this.frameimage = videoframe;
		this.videobox = videobox; 
		this.videoslider = videoslider;
		
		try {
			audioClip = AudioSystem.getClip();
			audioClip.open(video.audiotrack.audiostream);
			framedelay = audioClip.getMicrosecondLength()/(video.getFrameCount() * 1000);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {

		videostarted = true;
		
		for(; curframe < video.getFrameCount(); curframe++){

				displayCurrentFrame();
			
			try {
				Thread.sleep(framedelay);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		videoEnded = true;
	}
	
	public void displayCurrentFrame(){
		frameimage.setImage(video.videoframes[curframe].image);
		videoslider.setValue(curframe);
		videobox.updateUI();
	}

	@SuppressWarnings("deprecation")
	public void play(){
		if(videopaused){
			audioClip.start();
			this.resume();
			videopaused = false;
		}
		else 
		if(!videostarted){
			audioClip.start();
			this.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void pause(){
		if(!videopaused){
			audioClip.stop();
			this.suspend();
			videopaused = true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stopvideo(){
		if(videostarted){
			audioClip.stop();
			this.suspend();
			curframe = 0;
			audioClip.setFramePosition(0);
			videopaused = true;
		}
	}
	
	public void setCurrentFrame(int framenumber){
		curframe = framenumber;
		audioClip.setMicrosecondPosition(framenumber*framedelay*1000);
	}
	/*
	public void initializeThread(VideoContainer videocontainer)
	{
		this.video = videocontainer;
		try {
			audioClip = AudioSystem.getClip();
			audioClip.open(video.audiotrack.audiostream);
			framedelay = audioClip.getMicrosecondLength()/(video.getFrameCount() * 1000);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
	