//import Package_Name

public class Main implements Runnable{

		Minesweeper Game= new Minesweeper();

		public static void main(String[] args) {
			new Thread(new Main()).start();
			

		}

		@Override
		public void run()
		{
			while(true) {
				Game.repaint();
				if(Game.resetter==false)
				  Game.CheckVictory();
			}
			
		}

	}
