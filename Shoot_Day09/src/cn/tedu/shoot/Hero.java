package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 英雄机 */
public class Hero extends FlyingObject  {
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[2];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("hero"+i+".png");
		}
	}
	
	private int life; //命
	private int doubleFire; //火力值
	
	/** 构造方法 */
	public Hero(){
		width = 97;
		height = 124;
		x = 140;
		y = 400;
		life = 3;
		doubleFire = 10000;
	}
	
	private int index = 0;
	public void step(){
	}
	
	/** 英雄机随着鼠标动   x:鼠标的x坐标  y:鼠标的y坐标 */
	public void moveTo(int x,int y){
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	int deadIndex = 2;
	public BufferedImage getImage(){
		if(isLife()){
			return images[index++%2];
		}else if(isDead()){
			BufferedImage img = images[deadIndex++];
			if(deadIndex == images.length){
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire-=2;
			return bs;
		}else{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep);
			return bs;
		}
	}
	
	public void addLife(){
		life++;
	}
	
	public void addDoubleFire(){
		doubleFire+=40;
	}
	
	public int getLife(){
		return life;
	}
	
	public void subtractLife(){
		life--;
	}
	
	public int getDoubleFire(){
		return doubleFire;
	}
	
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	public boolean outOfBounds(){
		return false;
	}
	
}








