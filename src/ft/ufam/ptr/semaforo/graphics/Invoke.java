package ft.ufam.ptr.semaforo.graphics;

import java.awt.Container;
import javax.swing.*;

public class Invoke extends JFrame {

	private static final long serialVersionUID = 1L;
	private int i=0;
	private JProgressBar progress;
	
	public static void main(String[] args) {
		new Invoke();
	}

	public Invoke() {
		
		Container container = getContentPane();
		
		progress = new JProgressBar();
		progress.setValue(80);
		container.add(progress);
		
		setSize(600, 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		new Updater().start();
		SwingUtilities.invokeLater(new Update());
	}
	
	private class Updater extends Thread {
		
		@Override
		public void run() {
			while (i < 100) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SwingUtilities.invokeLater(new Update());
			}
			System.out.println("Morri!");
		}
		
	}
	
	private class Update implements Runnable {

		@Override
		public void run() {
			progress.setValue(++i);
		}
		
	}

}
