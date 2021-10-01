package entities;

public class Player extends Entity{
	
	public Player(float InitX, float InitY) {
		super(InitX, InitY);
	}
	
	// Key Press Mappings
	public void moveRight() {
		this.xSpeed = 5f;	
	}
	
	public void moveLeft() {
		this.xSpeed = -5f;
	}
	
	public void jump() {
		if(jumpsLeft > 0) {
			this.onPlatform = false;
			this.ySpeed = 10f;
			
			jumpsLeft--;
		}

	}
	public void fall() {
		this.onPlatform = false;
		
		this.ySpeed -= 1.5f;
	}
}