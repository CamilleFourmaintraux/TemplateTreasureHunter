package object;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends SuperObject{
		public OBJ_Chest(GamePanel gp) {
			this.gp=gp;
			this.name="Chest";
			try {
				this.image=ImageIO.read(new FileInputStream("res/img/object/chest.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
