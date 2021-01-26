public class Bubble {
	
	private int x;
	private int y;
	private int yVelocity;
	private int size;
	private int xVelocity;
	
	private boolean touched = false;
	


	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public Bubble(){
		
		x = (int)(Math.random()*100);
		y = (int)(Math.random()*100);
		yVelocity = (int)(Math.random()*100);
		xVelocity = (int)(Math.random()*100);
		size = (int)(Math.random()*100);
	
	}
	
	public int getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public void makeBig(int randSize) {
		size += randSize;
	}
	public void makeSmall(int randSize) {
		size -=randSize;
	}

	public void addyVel(int Velocity) {
		y += Velocity;
	}
	public void addxVel(int Velocity) {
		x += Velocity;
	}

}