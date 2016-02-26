package data;

public class VideoContainer {
	
	public int numframes;
	public ImageContainer videoframes[];
	public AudioContainer audiotrack;
	

	public VideoContainer(String videokey, int framecount){
		
		videoframes = new ImageContainer[framecount];
		numframes 	= framecount;
		LoadVideo(videokey);

	}


	public void LoadVideo(String videokey) {
		
		int i;
		for(i = 0; i < numframes; i++){
			String suffix = String.format("%03d.rgb", i + 1);
			videoframes[i] = new ImageContainer(videokey + suffix);
		}
		
		LoadAudio(videokey);
	}
	
	public void LoadAudio(String videokey){
		audiotrack = new AudioContainer(videokey + ".wav");
	}
	
	public int getFrameCount(){
		return numframes;
	}
	
	public void initializeVideo(String videokey, int framecount)
	{
		videoframes = new ImageContainer[framecount];
		numframes 	= framecount;
		LoadVideo(videokey);
	}
}
