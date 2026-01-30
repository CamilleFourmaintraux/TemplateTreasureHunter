package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//SCREEN SETTINGS
	private final int ORIGINAL_TILE_SIZE = 16; //16x16
	private final int SCALE = 3;
	public final int TILE_SIZE = SCALE*ORIGINAL_TILE_SIZE; //48x48

	public final int MAX_SCREEN_ROW = 12;
	public final int MAX_SCREEN_COL = 16;
	
	public final int SCREEN_HEIGHT = TILE_SIZE*MAX_SCREEN_ROW; //576 pixels
	public final int SCREEN_WIDTH = TILE_SIZE*MAX_SCREEN_COL; //768 pixels
	
	//WORLD SETTINGS
	public final int MAX_WORLD_COL=50;
	public final int MAX_WORLD_ROW=50;
	
	private final int FPS=60;
	long drawStart;
	long drawEnd;
	long timePassed;
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	TileManager tileM=new TileManager(this);
	public KeyHandler keyH = new KeyHandler();
	
	Sound music = new Sound();
	Sound se = new Sound();
	
	Thread gameThread;
	public UI ui = new UI(this);
	
	public Player player = new Player(this,this.keyH);
	public SuperObject obj[] = new SuperObject[10];
	public AssetSetter aSetter = new AssetSetter(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	/*@Override
	public void run() {
		double drawInterval = 1000000000/FPS; //On update l'écran tous les 0.01666 secondes.
		double nextDrawTime = System.nanoTime()+drawInterval;
		
		while(gameThread!=null) {
			
			this.update();
			this.repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if(remainingTime<0) {
					remainingTime=0;
				}
				Thread.sleep((long)remainingTime);
				
				nextDrawTime+=drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}*/
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta=0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount=0;
		
		while(gameThread!=null) {
			
			currentTime=System.nanoTime();
			
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=(currentTime-lastTime);
			lastTime = currentTime;
			
			if(delta >=1) {
				this.update();
				this.repaint();
				delta--;
				drawCount++;
			}
			
			if(timer>=1000000000) {
				System.out.println("FPS:"+drawCount);
				drawCount=0;
				timer=0;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(keyH.debugOn) {
			drawStart=0;
			drawStart=System.nanoTime();
		}
		
		
		tileM.draw(g2);
		
		for(int i=0; i<obj.length; i++) {
			if(obj[i]!=null) {
				obj[i].draw(g2, this);
			}
		}
		
		player.draw(g2);
		
		ui.draw(g2);
		
		if(keyH.debugOn) {
			drawEnd=0;
			drawEnd=System.nanoTime();
			
			timePassed = drawEnd-drawStart;
			System.out.println(timePassed);
			g2.drawString("Draw Time :"+timePassed+"s", 10, 500);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
