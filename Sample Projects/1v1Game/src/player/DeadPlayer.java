package player;

public class DeadPlayer extends Player {
	public DeadPlayer() {
		super();
	}
	public void update() {
		isDead = true;
	}
}
