package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;

	public boolean debugOn;
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_Z) {
			this.upPressed=true;
		}else if(code==KeyEvent.VK_S) {
			this.downPressed=true;
		}else if(code==KeyEvent.VK_Q) {
			this.leftPressed=true;
		}else if(code==KeyEvent.VK_D) {
			this.rightPressed=true;
		}else if(code==KeyEvent.VK_T) {
			if(debugOn) {
				this.debugOn=false;
			}else {
				this.debugOn=true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_Z) {
			this.upPressed=false;
		}else if(code==KeyEvent.VK_S) {
			this.downPressed=false;
		}else if(code==KeyEvent.VK_Q) {
			this.leftPressed=false;
		}else if(code==KeyEvent.VK_D) {
			this.rightPressed=false;
		}
	}
	

}
