package object;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
	public OBJ_Key(GamePanel gp) {
		this.gp=gp;
		this.name="Key";
		try {
			this.image=ImageIO.read(new FileInputStream("res/img/object/key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
