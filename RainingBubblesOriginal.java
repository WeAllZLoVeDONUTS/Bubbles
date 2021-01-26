/*
 * Name: Kevin You
 * Date: 2 / 12 / 2020
 * Project: A program to make bubbles rain from the ceiling.  We will adjust it as we go.
 * 
 *  We have provided you with some working code. With a partner, look through
 *  the code to determine what is happening. Document your findings within the
 *  code. We will make MANY changes to this and it is important to know what is
 *  happening before we do.
 *  
 */
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.applet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RainingBubblesOriginal extends Applet  {
	
	//constants to control size, speed, number of circles...
	private final int DELAY = 30;
	private final int MAX_SIZE = 20;
	private final int MAX_CIRCLES = 20;
	private final int MAX_VELOCITY = 15;

	//these are called "parallel arrays." Is there a better way to handle all
	//of this data?  Hint... these could all be ATTRIBUTES of a certain class.  Make that class and 
	//create a single array of that object.

	/*private int[] x;
	private int[] y;
	private int[] yvelocity;
	private int[] size;*/

	private Bubble[] bubbles;
	private int score;
	private int lives;
	private int tInd;              // TARGET INDEX
	private boolean hasWon;        // IF WON OR NOT
	private boolean useEffect;     // DETERMINES WHETHER TO USE EFFECT(SEE FURTHER CODE)
	private boolean toDouble;      //WHETHER TO DOUBLE VALUE OF TARGET OR NOT
	private int doubVal;           // VALUE WHEN T VALUE IS DOUBLED

	public void init() 
	{
		//What is the purpose of this method?  State as a comment under this line.

		//Document this...what's going on in each line?...
		this.resize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2,
				(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);

		//again, change these parallel arrays to make them better.

		/*x = new int[MAX_CIRCLES];
		y = new int[MAX_CIRCLES];
		yvelocity = new int[MAX_CIRCLES];
		size = new int[MAX_CIRCLES];*/

		bubbles = new Bubble[MAX_CIRCLES]; // DECALRE SIZE OF THE ARRAY

		lives = 3;                         // AMOUNT OF LIVES

		tInd = bubbles.length-1;           // SETS TARGET INDEX TO LAST ELEMENT

		hasWon = false;

		useEffect = false;

		toDouble = false;
		doubVal = 1;

		for(int count = 0;count < MAX_CIRCLES; count++)
		{
			resetBubble(count);
		}


		KeyListener myKeyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(score >= 10) {
						score -= 10;
						lives++;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(score >= 50) {
						hasWon = true;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					if(score >= 5) {
						score -= 5;
						useEffect = true;
					}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}

		};
		addKeyListener(myKeyListener);

		//I needed this to use "repaint" the screen. It uses a timer which is "listenedTo" by an ActionListener

		ActionListener taskPerformer = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				repaint();
			}
		};

		new Timer(DELAY, taskPerformer).start();

	}

	//This method is to "double buffer".  If it wasn't here,
	//the animations would flicker.  No need to modify/comment anything
	//in this method.
	public void update(Graphics g)
	{
		Graphics offgc;
		Image offscreen = null;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();

		offgc.setColor(getBackground());
		offgc.fillRect(0,0,d.width,d.height);
		offgc.setColor(getForeground());

		paint(offgc);

		g.drawImage(offscreen, 0, 0, this);

		Font newFont = new Font("Serif", Font.BOLD, 24);
		g.setFont(newFont);
		g.drawString("Score: " + score, this.getWidth()-100, 20);
		g.drawString("Dodge Bubbles", this.getWidth()/2, 20);
		g.drawString("|_____________|", (this.getWidth()/2)-5, 20);
		g.drawString("Lives: " + lives, 10, 20);
		g.drawString("Press Spacebar to spend 10", 10, this.getHeight()-150);
		g.drawString("Points but gain a life", 10, this.getHeight()-130);
		g.drawString("Press Enter to spend", 10, this.getHeight()-90);
		g.drawString("50 points to Win", 10, this.getHeight()-70);
		g.drawString("Press Up Arrow to spend", 10, this.getHeight()-40);
		g.drawString("5 points to have a random change", 10, this.getHeight()-20);

		if (lives < 1) {
			g.drawString("No More Lives, Exiting Now", this.getWidth()/2, this.getHeight()/2);
			//new Scanner().
			try {Thread.sleep(2000);} catch (Exception e) {}
			System.exit(0);
		}
		if(hasWon == true) {
			g.drawString("CONGRATULATIONS", (this.getWidth()/2)-100, this.getHeight()/2);
			g.drawString("YOU WON", (this.getWidth()/2)-50, (this.getHeight()/2)+20);
			try {Thread.sleep(2000);} catch (Exception e) {}
			System.exit(0);
		}
		if(useEffect == true) {
			int ra = (int)(Math.random()*9);
			if(ra == 0) {
				for(int k = 0; k < bubbles.length-1; k++) {
					bubbles[k].setSize(bubbles[k].getSize()+7);                                                //RANDOM EFFECT 1: INCREASE SIZE BY 2
				}
				g.drawString("INCREASE SIZE >:)", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 1) {
				for(int k = 0; k < bubbles.length-1; k++) {
					bubbles[k].setxVelocity((bubbles[k].getxVelocity()*3)/2);
					bubbles[k].setyVelocity((bubbles[k].getyVelocity()*3)/2);                                  //RANDOM EFFECT 2: INCREASE VELO BY 2
				}
				g.drawString("INCREASE SPEED >:)", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 2) {
				for(int k = 0; k < bubbles.length-1; k++) {
					bubbles[k].setSize(bubbles[k].getSize()-7);                                            //RANDOM EFFECT 3: DECREASE SIZE BY 2
				}
				g.drawString("DECREASE SIZE :D", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 3) {
				for(int k = 0; k < bubbles.length-1; k++) {
					bubbles[k].setxVelocity(bubbles[k].getxVelocity()/2);  
					bubbles[k].setyVelocity(bubbles[k].getyVelocity()/2);                                  //RANDOM EFFECT 4: DECREASE VELOCITY BY 2
				}
				g.drawString("DECREASE SPEED", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 4) {
				score += 20;                                                                                   //RANDOM EFFECT 5: GAIN 20 POINTS
				g.drawString("GAINED 20 POINTS :D", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 5) {
				score-= 10;                                                                                 //RANDOM EFFECT 6: LOSE 10 POINTS
				if(score < 10) {
					score -= score;
				}
				g.drawString("LOST 10 POINTS :(", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 6) {
				lives += 2;                                                                            //RANDOM EFFECT 7: GAIN 2 LIVES
				g.drawString("GAINED 2 LIVES :)", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 7) {
				lives -= 1;                                                                           //RANDOM EFFECT 8: LOSE A LIFE
				g.drawString("LOST A LIFE :(", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			if(ra == 8) {
				toDouble = true;                                                                                     // RANDOM EFFECT 9: DOUBLES VALUE
				doubVal *= 2;
				g.drawString("DOUBLE TARGETS VALUE :D", this.getWidth()/2, this.getHeight()/2);
				try {Thread.sleep(500);} catch(Exception e) {};
			}
			useEffect = false;
		}
	}

	private void resetBubble(int index)
	{
		//What does this method do?  Comment under this line.  Also, again - fix the parallel array issue.
		//THIS METHOD INITIALIZES ALL OF THE VALUES TO A BUBBLE OBJECT

		bubbles[index] = new Bubble();
		int x = (int)(Math.random()*4);
		if(x == 0) {
			bubbles[index].setX(0);
			bubbles[index].setY(0);
		}
		if(x == 1) {
			bubbles[index].setX(this.getWidth()-1);
			bubbles[index].setY(0);
		}
		if(x == 2) {
			bubbles[index].setX(this.getWidth()-1);
			bubbles[index].setY(this.getHeight()-1);
		}
		if(x == 3) {
			bubbles[index].setX(0);
			bubbles[index].setY(this.getHeight()-1);
		}
		bubbles[index].setyVelocity((int)(Math.random() * MAX_VELOCITY)+2);
		bubbles[index].setSize((int)(Math.random() * MAX_SIZE + 30));
		bubbles[index].setxVelocity((int)(Math.random() * MAX_VELOCITY));

		if(index == tInd) {
			bubbles[index].setY((int)(Math.random()*this.getHeight()));
			bubbles[index].setX((int)(Math.random()*this.getWidth()));
			bubbles[index].setyVelocity(0);
			bubbles[index].setSize(15);
			bubbles[index].setxVelocity(0);

		}
	}

	public void paint(Graphics g) 
	{	
		Ellipse2D circle;
		Graphics2D g2 = (Graphics2D)g;

		Random rand = new Random();
		float r = rand.nextFloat();
		float gr = rand.nextFloat();                 //RANDOMIZES COLOR
		float b = rand.nextFloat();
		Color color = new Color(r, gr, b);
		g2.setPaint(color);	

		//Document this...what's going on in each line?... there should be a comment for each line.
		for(int i = 0;i < MAX_CIRCLES; i++) // GOES THROUGH EACH BUBBLE OBJECT IN THE BUBBLE ARRAY
		{ 
			if(i == tInd) {
				Color c = new Color(255, 0, 0);         //SETTING COLOR OF TARGET TO YELLOW
				g2.setPaint(c);
			}



			bubbles[i].setY(bubbles[i].getY() + bubbles[i].getyVelocity()); //GETS PROPER VELOCITY
			bubbles[i].setX(bubbles[i].getX()+bubbles[i].getxVelocity());           //MOVE BUBBLES TO THE RIGHT

			if(bubbles[i].getX() <= 1) {
				bubbles[i].setxVelocity(bubbles[i].getxVelocity()*-1);
			}
			if(bubbles[i].getY() <= 1) {                                            //BOUNCE OFF LEFT WALL AND TOP WALL
				bubbles[i].setyVelocity(bubbles[i].getyVelocity()*-1);
			}

			if(bubbles[i].getX() >= this.getWidth()) {
				bubbles[i].setxVelocity(bubbles[i].getxVelocity()*-1);
			}
			if(bubbles[i].getY() >= this.getHeight()) {
				bubbles[i].setyVelocity(bubbles[i].getyVelocity()*-1);               //BOUNCE OFF RIGHT WALL AND BOTTOM WALL
			}

			{
				/*	if(bubbles[i].getY()>this.getHeight())  // RESET BUBBLE IF IT'S ABOVE GETHEIGHT
			{

				//				resetBubble(i);
			}*/
				circle =new Ellipse2D.Double(bubbles[i].getX(), bubbles[i].getY(), bubbles[i].getSize(), bubbles[i].getSize());	//CREATES THE BUBBLE
				g2.fill(circle);

				int cx = bubbles[i].getX() + bubbles[i].getSize()/2; // x coordinate of the bubble
				int cy = bubbles[i].getY() + bubbles[i].getSize()/2; // y coordinate of the bubble
				int mx = MouseInfo.getPointerInfo().getLocation().x;
				int my = MouseInfo.getPointerInfo().getLocation().y-40; // dirty fix for now, with full screen
				double mouseToCenter = Math.sqrt((mx - cx) * (mx -cx) + (my - cy) * (my -cy));
				if(mouseToCenter < bubbles[i].getSize()/2) {
					if(i == tInd) {
						if(toDouble == true) {
							score += doubVal;
						}
						else {
							score++;
						}

						bubbles[tInd].setY((int)(Math.random()*(this.getHeight()-50))+25);
						bubbles[tInd].setX((int)(Math.random()*(this.getWidth()-50))+25);
						bubbles[tInd].setTouched(true);

					}
					else if(!bubbles[i].isTouched()) { // the bubble is touched the FIRST time

						lives--;
						bubbles[i].setTouched(true);
					}
				} else {
					bubbles[i].setTouched(false);
				}
			}
		}
	}
}

/*FINALLY - change this to make it your own.  Make different colors (randomize it, if you want). 
 *  Change the starting spot of the bubbles.  Change the size.  Do something to make it your own.  
 *  Try to make some kind of video game out of it (I'm thinking pong...).  */