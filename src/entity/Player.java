package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int SCREEN_X;
	public final int SCREEN_Y;
	
	public int nbKey;
	
	private int standCounter=0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp=gp;
		this.keyH=keyH;
		
		this.SCREEN_X=gp.SCREEN_WIDTH/2-gp.TILE_SIZE/2;
		this.SCREEN_Y=gp.SCREEN_HEIGHT/2-gp.TILE_SIZE/2;
		
		this.nbKey=0;
		
		solidArea = new Rectangle(8,16,(int)(gp.TILE_SIZE/1.5),(int)(gp.TILE_SIZE/1.5));
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		this.setDefaultValues();
		this.initPlayerImages();
	}
	
	public void setDefaultValues() {
		this.worldX=gp.TILE_SIZE*23;
		this.worldY=gp.TILE_SIZE*21;
		this.speed=4;
		this.direction="down";
	}
	
	public void initPlayerImages() {
		up1=setup("boy_up_1");
		up2=setup("boy_up_2");
		down1=setup("boy_down_1");
		down2=setup("boy_down_2");
		left1=setup("boy_left_1");
		left2=setup("boy_left_2");
		right1=setup("boy_right_1");
		right2=setup("boy_right_2");
	}
	
	public BufferedImage setup(String imageName) {
		UtilityTool uTool=new UtilityTool();
		BufferedImage image=null;
		try {
			image=ImageIO.read(new FileInputStream("res/img/player/walking_sprites/"+imageName+".png"));
			image=uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void update() {
		if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed) {
			if(keyH.upPressed) {
				direction="up";
			}else if(keyH.downPressed) {
				this.direction="down";
			}else if(keyH.leftPressed) {
				this.direction="left";
			}else if(keyH.rightPressed) {
				this.direction="right";
			}
			
			collisionOn=false;
			gp.cChecker.checkTile(this);
			
			int objIndex=gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(!collisionOn) {
				switch(direction) {
				case("up"):
					worldY-=speed;
					break;
				case("down"):
					worldY+=speed;
					break;
				case("left"):
					worldX-=speed;
					break;
				case("right"):
					worldX+=speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter>12) {
				if(spriteNum==1) {
					spriteNum=2;
				}else if(spriteNum==2) {
					spriteNum=1;
				}
				spriteCounter=0;
			}
		}else {
			standCounter++;
			if(standCounter>20) {
				spriteNum=1;
				standCounter=0;
			}
		}
		
		
	}
	
	public void pickUpObject(int index) {
		if(index != -1) {
			String objName = gp.obj[index].name;
			switch(objName) {
			case "Key":
				gp.playSE(1);
				this.nbKey++;
				gp.obj[index]=null;
				gp.ui.showMessage("You got a key !");
				break;
			case "Door":
				if(nbKey>0) {
					gp.playSE(3);
					gp.obj[index]=null;
					nbKey--;
					gp.ui.showMessage("You opened the door.");
				}else {
					gp.ui.showMessage("You don't have a key.");
				}
				break;
			case "Chest":
				gp.ui.isGameFinished=true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			case "Boots":
				gp.playSE(2);
				gp.ui.showMessage("Speed up !");
				this.speed+=2;
				gp.obj[index]=null;
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction){
		case "up":
			if(spriteNum==1) {
				image=up1;
			}else if(spriteNum==2) {
				image=up2;
			}
			break;
		case "down":
			if(spriteNum==1) {
				image=down1;
			}else if(spriteNum==2) {
				image=down2;
			}
			break;
		case "left":
			if(spriteNum==1) {
				image=left1;
			}else if(spriteNum==2) {
				image=left2;
			}
			break;
		case "right":
			if(spriteNum==1) {
				image=right1;
			}else if(spriteNum==2) {
				image=right2;
			}
			break;
		}
		
		g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE,null);
		if(gp.keyH.debugOn) {
			g2.setColor(Color.red);
			g2.drawRect(SCREEN_X+solidArea.x, SCREEN_Y+solidArea.y, solidArea.width, solidArea.height);
		}
	}
}
