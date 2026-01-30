package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	GamePanel gp;
	Font textFont;
	Font titleFont;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean isGameFinished=false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp=gp;
		OBJ_Key key = new OBJ_Key(gp);
		keyImage=key.image;
		this.textFont = new Font("Arial",Font.PLAIN, 40);
		this.titleFont = new Font("Arial",Font.BOLD, 80);
	}
	public void showMessage(String text) {
		message=text;
		messageOn=true;
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(textFont);
		g2.setColor(Color.white);
		if(isGameFinished) {
			
			String text;
			int textLength;
			int x,y;
			
			g2.setFont(titleFont);
			g2.setColor(Color.yellow);
			text="Congratulation !";
			textLength=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x=gp.SCREEN_WIDTH/2-textLength/2;
			y = gp.SCREEN_HEIGHT/2-3*gp.TILE_SIZE;
			g2.drawString(text,x,y);
			

			g2.setFont(textFont);
			g2.setColor(Color.white);
			text="You found the treasure!";
			textLength=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x=gp.SCREEN_WIDTH/2-textLength/2;
			y = gp.SCREEN_HEIGHT/2-1*gp.TILE_SIZE;
			g2.drawString(text,x,y);

			text="You played for "+dFormat.format(playTime)+"s !";
			textLength=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x=gp.SCREEN_WIDTH/2-textLength/2;
			y=gp.SCREEN_HEIGHT/2+4*gp.TILE_SIZE;
			g2.drawString(text,x,y);
			
			gp.gameThread=null;
			
		}else {
			g2.drawImage(keyImage, gp.TILE_SIZE/2, gp.TILE_SIZE/2, gp.TILE_SIZE, gp.TILE_SIZE, null);
			g2.drawString("x"+gp.player.nbKey, 75, 60);
			
			playTime+=(double)1/60;
			g2.drawString("Time:"+dFormat.format(playTime), gp.TILE_SIZE*11, 60);
			
			if(messageOn) {
				g2.setFont(this.textFont.deriveFont(30F));
				g2.drawString(message, gp.TILE_SIZE/2, gp.TILE_SIZE*5);
				messageCounter++;
				if(messageCounter>120) {//toute les frames, donc si > à 120 frames or on est à 60fps soit 2secondes
					messageCounter=0;
					messageOn=false;
				}
			}
		}
	}
}
