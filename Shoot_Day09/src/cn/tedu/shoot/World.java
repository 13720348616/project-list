package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** 世界 */
public class World extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enemies = {}; //敌人数组
	private Bullet[] bullets = {}; //子弹数组
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	static{
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
	}
	
	
	public FlyingObject nextOne(){
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type < 5){
			return new Bee();
		}else if(type < 12){
			return new Airplane();
		}else{
			return new BigAirplane();
		}
	}
	
	int flyEnterIndex = 0;
	public void enterAction(){
		flyEnterIndex++;
		if(flyEnterIndex%40==0){
			FlyingObject obj = nextOne();
			enemies = Arrays.copyOf(enemies,enemies.length+1);
			enemies[enemies.length-1] = obj;
		}
	}
	
	public void stepAction(){
		sky.step();
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
	}
	
	int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		if(shootIndex%30==0){
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length);
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
		}
	}
	
	private int score = 0;
	public void bangAction(){
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			for(int j=0;j<enemies.length;j++){
				FlyingObject f = enemies[j];
				if(b.isLife() && f.isLife() && b.hit(f)){
					b.goDead();
					f.goDead();
					if(f instanceof Enemy){
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					if(f instanceof Award){
						Award a = (Award)f;
						int type = a.getType();
						switch(type){
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						}
					}
					
				}
			}
		}
	}
	
	public void outOfBoundsAction(){
		int index = 0;
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++){
			FlyingObject e = enemies[i];
			if(!e.outOfBounds() && !e.isRemove()){
				enemyLives[index++] = e;
			}
		}
		enemies = Arrays.copyOf(enemyLives,index);
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			if(!b.outOfBounds() && !b.isRemove()){
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives,index);
	}
	
	public void hitAction(){
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.hit(f)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
				break;
			}
		}
	}
	
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			state = GAME_OVER;
		}
	}
	
	int num = 1;
	public void action(){ //启动执行
		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				}
			}
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run(){
				if(state==RUNNING){
					enterAction();
					stepAction();
					shootAction();
					outOfBoundsAction();
					bangAction();
					hitAction();
					checkGameOverAction();
				}
				repaint();
			}
		},intervel,intervel);
	}
	
	public void paint(Graphics g){
		sky.paint(g);
		hero.paint(g);
		for(int i=0;i<enemies.length;i++){
			enemies[i].paint(g);
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].paint(g);
		}
		
		g.drawString("SCORE: "+score,10,25);
		g.drawString("LIFE: "+hero.getLife(),10,45);
		
		switch(state){
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		
		world.action();
		
	}
}














