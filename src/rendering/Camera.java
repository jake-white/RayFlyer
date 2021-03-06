package rendering;

import game.Player;

public class Camera {
	double FOV = Math.PI/2, x, y;
	Player player;
	double viewingAngle = 1;
	
	public Camera(Player p){
		this.player = p;
	}
	
	public double getStartingAngle(){
		return RayPoint.validateAngle(player.getAngle()+FOV/2);
	}
	
	public double getEndingAngle(){
		return RayPoint.validateAngle(player.getAngle()-FOV/2);		
	}

	public double getAngle(){
		return player.getAngle();
	}
	
	public double getView(){
		return viewingAngle;
	}
	public void changeView(double inc){
		viewingAngle+=inc;
	}
	
	public double getFOV(){
		return FOV;
	}
	public double getHeight(){
		return player.getZ() + 0.8;
	}
	
	public double getX(){
		return player.getPosition().getX();
	}
	
	public double getY(){
		return player.getPosition().getY();
	}

}
