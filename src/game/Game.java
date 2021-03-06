package game;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import audio.Music;
import audio.MusicPlayer;
import audio.SFX;
import physics.InputManager;
import physics.Physics;
import rendering.Camera;
import rendering.Screen;
import rendering.Sprite;
import rendering.Tick;
import rendering.Weapon;
import rendering.World;

public class Game {
	private Screen currentScreen;
	private Timer gameLoop;
	private World world;
	private JFrame mainFrame; //hacking the mainframe with a visual basic GUI
	private Camera playerCam;
	private Player player;
	private Weapon gun;
	private MusicPlayer musPlayer;
	private boolean musicPlaying = false;
	private boolean over = false;
	private int ticksSinceBossActive = 0, bossMusicLag = 50;
	
	public static void main(String[] args){
		new Game();
	}
	
	public Game(){
		currentScreen = new Screen(this);
		player = new Player(this);
		playerCam = new Camera(player);
		//setting up the JFrame
		mainFrame = new JFrame();
		mainFrame.setSize(800, 450);
		mainFrame.getContentPane().add(currentScreen);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setTitle("JRay");
		world = new World(this, "res/sprites/world.png");
		gun = new Weapon("gun");
		musPlayer = new MusicPlayer();
		System.out.println("Started game!");
		currentScreen.rayCast();
		gameLoop = new Timer(1, new Tick(this, mainFrame));
		gameLoop.start();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Camera getCamera(){
		return playerCam;
	}
	public Screen getScreen(){
		return currentScreen;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void AITick(){
		for(int i = 0; i < getWorld().getSpriteList().size(); ++i){
			getWorld().getSpriteList().get(i).makeDecision();
		}
		if(getWorld().getBoss().isActive()){
			getWorld().getBoss().makeDecision();
			for(int i = 0; i < getWorld().getProjectiles().size(); ++i){
				if(getWorld().getProjectiles().get(i).isAlive())
					getWorld().getProjectiles().get(i).makeDecision();
			}
		}
	}
	
	public void gunFX(){
		musPlayer.play(new SFX("res/sfx/laserfire02.wav"),0);
	}

	public void playSFX(SFX sfx) {
		musPlayer.play(sfx, 0);
	}
	
	public void playBattleMusic(){
		if(!musicPlaying){
			musicPlaying = true;
			musPlayer.play(new Music("res/music/battle.wav"), -10);
		}
	}
	
	public void playBossMusic(){
		ticksSinceBossActive++;
		if(!musicPlaying && ticksSinceBossActive > bossMusicLag){	
			musicPlaying = true;
			musPlayer.play(new Music("res/music/noescape.wav"), 0);
		}
	}

	public void stopBattleMusic() {
		musPlayer.stopMusic();
		musicPlaying = false;
	}
	
	public void render(long frames){
		currentScreen.rayCast();
		currentScreen.setFrameRate(frames);
		mainFrame.repaint();
	}

	public Weapon getGun() {
		return gun;
	}

	public void over() {
		over = true;
	}
	
	public boolean isOver(){
		return over;
	}
	

}
