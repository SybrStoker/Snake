import javax.swing.JFrame;

public class GameFrame extends JFrame{
	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Just A Snake(not python!)");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
