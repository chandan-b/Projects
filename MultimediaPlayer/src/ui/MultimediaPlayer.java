package ui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import control.VideoThread;
import data.VideoContainer;

import javax.swing.JButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

import javax.swing.JSlider;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MultimediaPlayer {

	private final static int width  = 352;
	private final static int height = 288;
	
	private JFrame frmMultimediaPlayer;
	public JLabel videobox;
	public JLabel queryvideobox;
	public ImageIcon videoframe;
	public ImageIcon queryvideoframe;
	private BufferedImage blankframe;
	
	VideoContainer currentVideo,queryCurrentVideo;
	VideoThread videoThread,queryVideoThread;
	//QueryVideoThread queryVideoThread;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnStop;
	private JButton btnPause;
	private JButton btnPlay;
	private JSlider videoslider,queryvideoslider;
	//private JLabel lblQueryvideobox;
	private JButton btnQueryplay;
	private JButton btnQuerypause;
	private JButton btnQuerystop;
	public String[] queryVideo = {"first","second"};
	String queryVideoStr1 = "/Users/charanshampur/Desktop/Multimedia - Homework 2/query/first/first";
	String queryVideoStr2 = "/Users/charanshampur/Desktop/Multimedia - Homework 2/query/second/second";
	int queryFrameCount = 150;
	JList qlist;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MultimediaPlayer window = new MultimediaPlayer();
					window.frmMultimediaPlayer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MultimediaPlayer() {
		initializeUI();
		loadDefaultVideo();
	}

	private void loadDefaultVideo(){
		queryCurrentVideo = new VideoContainer(queryVideoStr1, queryFrameCount);
		queryVideoThread = new VideoThread(queryCurrentVideo, queryvideoframe, queryvideobox, queryvideoslider);
		currentVideo = new VideoContainer("/Users/charanshampur/Desktop/Multimedia - Homework 2/CSCI576_Project_Database/musicvideo/musicvideo", 600);
		videoThread = new VideoThread(currentVideo, videoframe, videobox, videoslider);
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeUI() {
		frmMultimediaPlayer = new JFrame();
		frmMultimediaPlayer.setTitle("Multimedia Player");
		frmMultimediaPlayer.setBounds(100, 100, 858, 568);
		frmMultimediaPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		blankframe = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		videoframe = new ImageIcon(blankframe);
		queryvideoframe = new ImageIcon(blankframe);
		//frmMultimediaPlayer.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		frmMultimediaPlayer.getContentPane().setLayout(null);
		videobox = new JLabel(videoframe);
		queryvideobox = new JLabel(queryvideoframe);
		videobox.setBounds(457, 253, 395, 222);
		frmMultimediaPlayer.getContentPane().add(videobox);
		queryvideobox.setBounds(36, 253, 342, 222);
		frmMultimediaPlayer.getContentPane().add(queryvideobox);
		
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(477, 487, 117, 29);
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(videoThread.videoEnded)
				{
					videoThread = new VideoThread(currentVideo, videoframe, videobox, videoslider);
				}
				videoThread.play();
				btnPlay.setEnabled(false);
				btnStop.setEnabled(true);
				btnPause.setEnabled(true);
				}
		});
		
		videoslider = new JSlider();
		videoslider.setBounds(480, 212, 351, 29);
		videoslider.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				videoThread.pause();
				btnPause.setEnabled(false);
				btnPlay.setEnabled(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				videoThread.setCurrentFrame(videoslider.getValue());
				videoThread.displayCurrentFrame();
			}
		});
		videoslider.setValue(0);
		videoslider.setMaximum(600);
		frmMultimediaPlayer.getContentPane().add(videoslider);

		frmMultimediaPlayer.getContentPane().add(btnPlay);
		
		btnPause = new JButton("Pause");
		btnPause.setBounds(607, 487, 117, 29);
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				videoThread.pause();
				btnPause.setEnabled(false);
				btnPlay.setEnabled(true);

			}
		});
		frmMultimediaPlayer.getContentPane().add(btnPause);
		btnPause.setEnabled(false);
		
		btnStop = new JButton("Stop");
		btnStop.setBounds(735, 487, 117, 29);
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					videoThread.stopvideo();
					resetVideoBox();
					btnStop.setEnabled(false);
					btnPause.setEnabled(false);
					btnPlay.setEnabled(true);				
			}
		});
		frmMultimediaPlayer.getContentPane().add(btnStop);
		btnStop.setEnabled(false);
		
		//queryvideobox = new JLabel("queryvideobox");
		
		
		btnQueryplay = new JButton("QueryPlay");
		btnQueryplay.setBounds(33, 487, 117, 29);
		btnQueryplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(queryVideoThread.videoEnded)
				{
					if(qlist.getSelectedIndex()==0)
						queryCurrentVideo.initializeVideo(queryVideoStr1, queryFrameCount);
					else
						queryCurrentVideo.initializeVideo(queryVideoStr2, queryFrameCount);
					
					queryVideoThread = new VideoThread(queryCurrentVideo, queryvideoframe, queryvideobox, queryvideoslider);
				}
				queryVideoThread.play();
				btnQueryplay.setEnabled(false);
				btnQuerystop.setEnabled(true);
				btnQuerypause.setEnabled(true);
				}
		});
		frmMultimediaPlayer.getContentPane().add(btnQueryplay);
		
		btnQuerypause = new JButton("QueryPause");
		btnQuerypause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				queryVideoThread.pause();
				btnQuerypause.setEnabled(false);
				btnQueryplay.setEnabled(true);

			}
		});
		btnQuerypause.setEnabled(false);
		btnQuerypause.setBounds(161, 487, 117, 29);
		frmMultimediaPlayer.getContentPane().add(btnQuerypause);
		
		btnQuerystop = new JButton("QueryStop");
		btnQuerystop.setBounds(290, 487, 117, 29);
		btnQuerystop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					queryVideoThread.stopvideo();
					queryResetVideoBox();
					btnQuerystop.setEnabled(false);
					btnQuerypause.setEnabled(false);
					btnQueryplay.setEnabled(true);				
			}
		});
		btnQuerystop.setEnabled(false);
		frmMultimediaPlayer.getContentPane().add(btnQuerystop);
		
		queryvideoslider = new JSlider();
		queryvideoslider.setBounds(36, 212, 342, 29);
		queryvideoslider.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				queryVideoThread.pause();
				btnQuerypause.setEnabled(false);
				btnQueryplay.setEnabled(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				queryVideoThread.setCurrentFrame(queryvideoslider.getValue());
				queryVideoThread.displayCurrentFrame();
			}
		});
		queryvideoslider.setValue(0);
		queryvideoslider.setMaximum(queryFrameCount);
		frmMultimediaPlayer.getContentPane().add(queryvideoslider);
		
		qlist = new JList(queryVideo);
		qlist.setSelectedIndex(0);
		qlist.setVisibleRowCount(4);
		qlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		qlist.setBounds(36, 50, 168, 147);
		qlist.addListSelectionListener(new ListSelectionListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				queryVideoThread.stopvideo();
				queryResetVideoBox();
				btnQuerystop.setEnabled(false);
				btnQuerypause.setEnabled(false);
				btnQueryplay.setEnabled(true);
				queryVideoThread.stop();
				if(qlist.getSelectedIndex() == 0)
				{
					queryCurrentVideo.initializeVideo(queryVideoStr1, queryFrameCount);
					queryVideoThread = new VideoThread(queryCurrentVideo, queryvideoframe, queryvideobox, queryvideoslider);
				}
				else if (qlist.getSelectedIndex() == 1)
				{
					queryCurrentVideo.initializeVideo(queryVideoStr2, queryFrameCount);
					queryVideoThread = new VideoThread(queryCurrentVideo, queryvideoframe, queryvideobox, queryvideoslider);
				}
				
			}
			
		});
		frmMultimediaPlayer.getContentPane().add(qlist);
		
	}
	
	private void resetVideoBox(){
		videoframe.setImage(blankframe);
		videobox.updateUI();
		videoslider.setValue(0);
	}
	
	private void queryResetVideoBox(){
		queryvideoframe.setImage(blankframe);
		queryvideobox.updateUI();
		queryvideoslider.setValue(0);
	}
}
