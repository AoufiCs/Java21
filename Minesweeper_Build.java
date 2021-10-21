// import Package_name

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Demineur extends JFrame{
	//Attributes
	Date StartDate=new Date();
	Date EndDate;
	MouseHandler mHandler= new MouseHandler();
	ClickHandler cHandler = new ClickHandler();
	int[][]mines=new int[16][9];
	int[][]voisins=new int[16][9];
	boolean [][] revealed=new boolean [16][9];
	boolean [][] flagged=new boolean [16][9];
	public int sec=0;
	int spacing=2;
	int MinesVoisins=0;
	String Message="Clear";
	//Coordinates
	public int mx=-100;
	public int my=-100;
	public int smileyX=605;
	public int smileyY=5;
	public int smileyCenterX=smileyX+35;
	public int smileyCenterY=smileyY+35;
	public int remainder =0;
	public int remainderX=5;
	public int remainderY=5;
	public int flagX=445;
	public int flagY=5;
	public int flagCenterX=flagX+35;
	public int flagCenterY=flagY+35;
	public int timerX=1200;
	public int timerY=5;
	public int MessX=720;
	public int MessY=-50;
	
	public boolean happy=true;
	public boolean win=false;
	public boolean lose=false;
	public boolean resetter=false;
	public boolean flagger=false;
	
	Random rand = new Random();
	
	public Demineur() {	
		//Game setup
		this.setTitle("Jeu du Demineur!");
		this.setSize(1300,840);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addMouseMotionListener(mHandler);
		this.addMouseListener(cHandler);
		this.setVisible(true);
		this.setResizable(false);
		for(int i=0; i<16;i++)
			for(int j=0; j<9;j++)
			{
				//Mines placement(20% can be changed below)
				if (rand.nextInt(100)<20) //here
					mines[i][j]=1;
				else
					mines[i][j]=0;
				revealed[i][j]=false;
			}
		//Find how many mines are near the spot selected
		for(int i=0; i<16;i++) {
			for(int j=0; j<9;j++) {
				if(mines[i][j]==1)
					remainder++;
				MinesVoisins=0;
				for(int m=0; m<16;m++) {
					for(int n=0; n<9;n++) { 
						if (!(m==i && n==j)) {
							if(isN(i,j,m,n)==true)
								MinesVoisins++;
						}
					}			
				}
			voisins[i][j]=MinesVoisins;
			}
		}		
		// Matrix/Board setup
		Board board=new Board();
		this.setContentPane(board);
	}
	
	public class Board extends JPanel {	
		public void paintComponent(Graphics g)
		{
			//Define what click does
			g.setColor(new Color(182,182,182));
			g.fillRect(0, 0, 1280, 800);
			for(int i=0; i<16;i++)
				for(int j=0; j<9;j++) {
					g.setColor(Color.gray);
					if (revealed[i][j]==true) {
						g.setColor(new Color(229,229,229));
						if (mines[i][j]==1)
							g.setColor(Color.red);
					}
					if(mx>=spacing+i*80+10 && mx<spacing+i*80+95-2*spacing && my>=spacing+j*80+110 && my<spacing+j*80+80+115-2*spacing) {
						g.setColor(new Color(121,121,255));
					}
					g.fillRect(spacing+i*80, spacing+j*80+80, 80-2*spacing, 80-2*spacing);
					if (revealed[i][j]==true) {
						g.setColor(Color.black);
						if(mines[i][j]==0 && voisins[i][j]!=0) {
							if(voisins[i][j]==1) {
								g.setColor(Color.blue);
							}else if(voisins[i][j]==2) {
								g.setColor(Color.green);
							}else if(voisins[i][j]==3) {
								g.setColor(Color.red);
							}else if(voisins[i][j]==4) {
								g.setColor(new Color(0,0,100));
							}else if(voisins[i][j]==5) {
								g.setColor(new Color(178,34,34));
							}else if(voisins[i][j]==6) {
								g.setColor(new Color(72,209,204));
							}else if(voisins[i][j]==7) {
								g.setColor(new Color(0,0,80));
							}else if(voisins[i][j]==8) {
								g.setColor(Color.darkGray);
							}
						g.setFont(new Font("Tahoma",Font.BOLD,40));
						g.drawString(Integer.toString(voisins[i][j]), i*80+27, j*80+80+55);
					}
						else if(mines[i][j]==1){
							g.fillRect(i*80+30, j*80+100, 20, 40);
							g.fillRect(i*80+20, j*80+110, 40, 20);
							g.fillRect(i*80+25, j*80+105, 30, 30);
							g.fillRect(i*80+38, j*80+95, 4, 50);
							g.fillRect(i*80+15, j*80+80+38, 50, 4);
						}
					}
			//Paint the flag
			if(flagged[i][j]==true) {
				g.setColor(Color.black);
				g.fillRect(i*80+38, j*80+80+15,5, 40);
				g.fillRect(i*80+26, j*80+80+50,30, 10);
				g.setColor(Color.red);
				g.fillRect(i*80+22, j*80+80+15, 20, 15);
				g.setColor(Color.black);

				g.drawRect(i*80+22, j*80+80+15, 20, 15);
				g.drawRect(i*80+23, j*80+80+16, 18, 13);
				g.drawRect(i*80+24, j*80+80+17, 16, 12);
				}
		}
			g.setColor(Color.black);
			g.fillRect(flagX+32, flagY+15,5, 40);
			g.fillRect(flagX+20, flagY+50,30, 10);
			g.setColor(Color.red);
			g.fillRect(flagX+16, flagY+15, 20, 15);
			g.setColor(Color.black);
			
			g.drawRect(flagX+16, flagY+15, 20, 15);
			g.drawRect(flagX+17, flagY+16, 18, 13);
			g.drawRect(flagX+18, flagY+17, 16, 12);
			if(flagger==true) {
				g.setColor(Color.red);
				g.drawRect(flagX-5, flagY+4,70,70);
				g.drawRect(flagX-4, flagY+5,68,68);
			}
			else {
				g.setColor(Color.black);
				g.drawRect(flagX-5, flagY+4,70,70);
				g.drawRect(flagX-4, flagY+5,68,68);
			}
			//Paint the emoji
			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(smileyX+15, smileyY+20, 10, 10);
			g.fillOval(smileyX+45, smileyY+20, 10, 10);
			if (happy==true) {
				g.fillRect(smileyX+20, smileyY+50, 30, 5);
				g.fillRect(smileyX+17, smileyY+45, 5, 5);
				g.fillRect(smileyX+48, smileyY+45, 5, 5);
			}else {
				g.fillRect(smileyX+20, smileyY+45, 30, 5);
				g.fillRect(smileyX+17, smileyY+50, 5, 5);
				g.fillRect(smileyX+48, smileyY+50, 5, 5);
			}
			//Stopwatch
			g.setColor(Color.darkGray);
			g.fillRect(timerX-50, timerY, 130, 70);
			if(lose==false && win==false) {
				sec = (int) ((new Date().getTime()-StartDate.getTime())/1000);
			}
			 
			 if(sec>999) {
				 sec=999;
			 }
			 if(win==true) {
				 g.setColor(Color.green);			 	
			 }
			 else if(lose==true) {
				 g.setColor(Color.red);
				 MinesOn();
			 }
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma",Font.PLAIN,60));
			if(sec<10)
				g.drawString("00"+Integer.toString(sec),timerX-35,timerY+60);
			else if(sec<100)
				g.drawString("0"+Integer.toString(sec),timerX-35,timerY+60);
			else
				g.drawString(Integer.toString(sec),timerX-35,timerY+60);
			
			//Display the number of remaining mines
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma",Font.PLAIN,35));
			g.drawString("Bombes restantes: "+Integer.toString(remainder),remainderX,remainderY+50);
			
			
			//Display if the player has won or lost
			if(win==true) {
				g.setColor(Color.green);
				Message="Vous avez gagn�!!";
				
			}
				else if (lose==true) {
				g.setColor(Color.red);
				Message="Vous avez perdu!";
			}
			if(win==true || lose==true) {
				MessY=-50+(int)(new Date().getTime() - EndDate.getTime())/10;	
				if(MessY>70)
					MessY=70;
				g.setFont(new Font("Tahoma",Font.PLAIN,50));
				g.drawString(Message, MessX, MessY-15);
			}	
		}
	}
	public void CheckVictory() {
		if(lose==false)
			for(int i=0; i<16;i++) {
				for(int j=0; j<9;j++) {
					if(mines[i][j]==1 && revealed[i][j]==true) {
						lose=true;
						happy=false;
						EndDate= new Date();
					
				}	
			}
		}
		if(TotalRevealed()>=144-nbMines() && win==false) {
			win=true;
		}
	}
	//afficher tous les mines si une est d�couverte
	public void MinesOn() {
		for(int i=0; i<16;i++) {
			for(int j=0; j<9;j++) {
				if (mines[i][j]==1) {
					revealed[i][j]=true;
				}
			}
		}
	}
	public int  nbMines() {
		int total=0;
		for(int i=0; i<16;i++)
			for(int j=0; j<9;j++) {
				if(mines[i][j]==1) {
					total++;
				}	
			}
		return total;
		
	}
	public int TotalRevealed() {
		int total=0;
		for(int i=0; i<16;i++)
			for(int j=0; j<9;j++) {
				if(revealed[i][j]==true) {
					total++;
				}	
			}
		return total;
	}
	public void reset() {
		remainder=0;
		flagger=false;
		resetter=true;
		StartDate= new Date();
		Message="V";
		MessY=-50;
		happy=true;
		win=false;
		lose=false;
		
		for(int i=0; i<16;i++)
			for(int j=0; j<9;j++)
			{
				if (rand.nextInt(100)<20) {
					mines[i][j]=1;
				}
				else {
					mines[i][j]=0;
				}
				revealed[i][j]=false;
				flagged[i][j]=false;
				
			}
		for(int i=0; i<16;i++) {
			for(int j=0; j<9;j++) {
				MinesVoisins=0;
				if(mines[i][j]==1)
					remainder++;
				for(int m=0; m<16;m++) {
					for(int n=0; n<9;n++) { 
						if (!(m==i && n==j)) {
							if(isN(i,j,m,n)==true)
								MinesVoisins++;
						}
					}			
				}
			voisins[i][j]=MinesVoisins;
			}
		}
		resetter=false;
		
		
	}
	public boolean inSmile() {
		int diff=(int)Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
		if(diff<35) {
			return true;
		}
		else
			return false;
		
	}
	public boolean inFlag() {
		int diff=(int)Math.sqrt(Math.abs(mx-flagCenterX)*Math.abs(mx-flagCenterX)+Math.abs(my-flagCenterY)*Math.abs(my-flagCenterY));
		if(diff<35) {
			return true;
		}
		else
			return false;
		
	}
	public int inboxX()
	{
		for(int i=0; i<16;i++)
			for(int j=0; j<9;j++)
			{
				if(mx>=spacing+i*80+10 && mx<spacing+i*80+95-2*spacing && my>=spacing+j*80+110 && my<spacing+j*80+80+115-2*spacing)
					return i;
			} 
		return -1;
	}
	public int inboxY()
	{
		for(int i=0; i<16;i++)
			for(int j=0; j<9;j++)
			{
				if(mx>=spacing+i*80+10 && mx<spacing+i*80+95-2*spacing && my>=spacing+j*80+110 && my<spacing+j*80+80+115-2*spacing)
					return j;
			}
		return -1;
		
	}
	//Verify if the spot/box near is a mine or not 
	public boolean isN(int mX,int mY,int cX,int cY)
	{
		if(mX-cX <2 && mX-cX >-2 && mY -cY <2 && mY -cY>-2 && mines[cX][cY]==1 )
		{
			return true;
		}
			return false;
	}
	//Manage the mouse click
	public class ClickHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
//			mx=e.getX();
//			my=e.getY();
			if(inboxX()!=-1 && inboxY()!=-1) {
				if(flagger==true && revealed[inboxX()][inboxY()]==false) {
					if(flagged[inboxX()][inboxY()]==false) {
						flagged[inboxX()][inboxY()]=true;
						remainder--;
					}
					else {
						flagged[inboxX()][inboxY()]=false;
						remainder++;
					}
				}
			
			else {
				if(flagged[inboxX()][inboxY()]==false) {
					revealed[inboxX()][inboxY()]=true; 
				}
				
			}
		}
			if(inSmile()==true)
				reset();
			if(inFlag()==true)
				if(flagger==false) {
					flagger=true;
			
					
				}
				else {
					flagger=false;
				}
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		}
	public class MouseHandler implements MouseMotionListener{
		@Override
		public void mouseMoved(MouseEvent e) {
			mx=e.getX();
			my=e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
			
		}
		
	}



