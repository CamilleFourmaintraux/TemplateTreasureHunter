package object;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{
	
	public OBJ_Boots(GamePanel gp) {
		this.gp=gp;
		this.name="Boots";
		try {
			this.image=ImageIO.read(new FileInputStream("res/img/object/boots.png"));
			this.image=uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
