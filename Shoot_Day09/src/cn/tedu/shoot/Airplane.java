package cn.tedu.shoot;
import java.awt.image.BufferedImage;

/** 小敌机 */
public class Airplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("airplane"+i+".png");
		}
	}
	private int step;
	
	/** 构造方法 */
	public Airplane(){
		width = 49;
		height = 36;
		x = (int)(Math.random()*(World.WIDTH-this.width));
		y = -this.height;
		step = 2;
	}
	
	/** 小敌机走步step() */
	public void step(){
		y+=step;
	}
	
	int deadIndex = 1;
	public BufferedImage getImage(){
		if(isLife()){
			return images[0];
		}else if(isDead()){
			BufferedImage img = images[deadIndex++];
			if(deadIndex==images.length){
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public int getScore(){
		return 1;
	}
	
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT;
	}
	
}





