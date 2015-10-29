package SayWhat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUIllermo extends JFrame {//fõmenü frameje
	

	private static final long serialVersionUID = 1L;
	//3 gombból áll, "Új játék", "Toplista", "Kilépés"
	private JButton newGame = new JButton("Ember ember ellen");
	private JButton highScores = new JButton("Toplista");
	private JButton newGameHumanVSComputer = new JButton("Ember a gép ellen");
	private JButton newGameComputerVSComputer = new JButton("Gép a gép ellen");
	private JButton exit = new JButton("Kilépés");
	
	public  class BtnListener implements ActionListener{//buttonactionlistener, mindhárom gombra, stringet kap paraméterként, ezek alapján hívja a különbözõ frameket
		
		String hova;
		
		public BtnListener(String param){
			
			hova = param;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			hivo(hova);
			System.out.println("meghívtam: "+ hova);
			
		}
		
		
	}
	
	public GUIllermo(){
	//frame konstruktor. Az ablak mérete 1280x720, ablak neve "Othello", a frame háttere egy kép. A gombok átlátszóak, kék színûek.
		super("Othello");
		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("gandalfthepug.jpg")))));
		} catch (IOException e) {
			
			System.out.print("Image doesnt exist");
		}
		
		
		
		
		newGame.setBounds(450, 100, 400, 100);
		newGameHumanVSComputer.setBounds(450, 225, 400, 100);
		newGameComputerVSComputer.setBounds(450, 350, 400, 100);
		exit.setBounds(550, 475, 200, 100);
		
		newGame.setBorder(null);
		newGameHumanVSComputer.setBorder(null);
		newGameComputerVSComputer.setBorder(null);
		exit.setBorder(null);
		
		newGame.setContentAreaFilled(false);
		newGameHumanVSComputer.setContentAreaFilled(false);
		newGameComputerVSComputer.setContentAreaFilled(false);
		exit.setContentAreaFilled(false);
		
		newGame.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		newGameHumanVSComputer.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		newGameComputerVSComputer.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		exit.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		
		newGame.setForeground(Color.cyan);
		newGameHumanVSComputer.setForeground(Color.cyan);
		newGameComputerVSComputer.setForeground(Color.cyan);
		exit.setForeground(Color.cyan);
		
		this.add(newGame);
		this.add(newGameHumanVSComputer);
		this.add(newGameComputerVSComputer);
		this.add(exit);
		
		BtnListener newg = new BtnListener("ujjatek");
		newGame.addActionListener(newg);
		BtnListener egz = new BtnListener("exit");
		exit.addActionListener(egz);
		
		
		this.setResizable(false);
		this.pack();
	}
	
	public void hivo(String ide){
	
	//Ezt hívja meg a buttonlistener. ha az átadott string "toplista", a Toplista framet nyitja meg, magát bezárja, ha az átadott string "ujjatek", a Nev_megadas framet nyitja meg, magát bezárja.
	//Ha az átadott string "exit", System.exit(0)-val kilép.
	
		Toplista topl = null;
		Nev_megadas ujtk = null;
		
		if(ide == "toplista")	{
			topl = new Toplista();
			this.setVisible(false);
			topl.setVisible(true);
		}
		
		if(ide == "ujjatek")  {
			ujtk = new Nev_megadas();
			this.setVisible(false);
			ujtk.setVisible(true);
			
		}
		if(ide == "exit") System.exit(0);
	}
}
