import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	//unit is one part of game terrain
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int AMOUNTF_UNITS = (SCREEN_WIDTH*SCREEN_HIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	final int x[] = new int[AMOUNTF_UNITS];
	final int y[] = new int[AMOUNTF_UNITS];
	int bodyParts = 6;
	public int applesEaten = 0;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	boolean table = false;
	Timer timer;
	Random random;
	
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new UniqueKeyAdapter());
		startGame();
	}
	
	public void startGame(){
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			if(table) {
				for(int i = 0; i < SCREEN_HIGHT/UNIT_SIZE; i++) {
					g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HIGHT);
					g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
				}
			}
			
			g.setColor(Color.green);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0; i < bodyParts; i++) {
				int remainder = i % 2;
				
				if(i == 0) {
					g.setColor(new Color(255, 215, 0));
					g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					continue;
				}
				else if(remainder == 0) {
					g.setColor(new Color(255, 140, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					continue;
				} else {
					g.setColor(new Color(255, 69, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
			}
		} else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
		}
	}
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollision() {
		//when tries eat the body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//when collides with left border
		if(x[0] < 0) {
			running = false;
		}
		
		//when collides with right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		//when collides with top border
		if(y[0] < 0) {
			running = false;
		}
		//when collides with bottom border
		if(y[0] > SCREEN_HIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		//score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		
		//gameover
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SCREEN_HIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}
	
	public class UniqueKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				//control by cursor
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
				
				//control by W/S/A/D
				case KeyEvent.VK_A:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_D:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_W:
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_S:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
			
			}
		}
	}

}
