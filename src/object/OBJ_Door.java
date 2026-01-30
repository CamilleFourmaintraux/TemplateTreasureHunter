package object;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;



public class OBJ_Door extends SuperObject{
	public OBJ_Door(GamePanel gp) {
		this.gp=gp;
		this.name="Door";
		try {
			this.image=ImageIO.read(new FileInputStream("res/img/object/door.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.collision=true;
	}
}
