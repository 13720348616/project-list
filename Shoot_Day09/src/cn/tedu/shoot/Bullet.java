package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 子弹 */
public class Bullet extends FlyingObject  {
	private static BufferedImage image;
	static{
		image = loadImage("bullet.png");
	}
	private int step;
	
	/** 构造方法 */
	public Bullet(int x,int y){
		width = 8;
		height = 14;
		this.x = x;
		this.y = y;
		step = 3;
	}
	
	/** 子弹走步step() */
	public void step(){
		y-=step;
	}
	
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}
		if(isDead()){
			state = REMOVE;
		}
		return null;
	}
	
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
}







