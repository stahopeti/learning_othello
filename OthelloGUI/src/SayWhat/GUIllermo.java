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

public class GUIllermo extends JFrame {//f�men� frameje
	

	private static final long serialVersionUID = 1L;
	//3 gombb�l �ll, "�j j�t�k", "Toplista", "Kil�p�s"
	private JButton newGame = new JButton("�j J�t�k");
	private JButton highScores = new JButton("Toplista");
	private JButton exit = new JButton("Kil�p�s");
	
	public  class BtnListener implements ActionListener{//buttonactionlistener, mindh�rom gombra, stringet kap param�terk�nt, ezek alapj�n h�vja a k�l�nb�z� frameket
		
		String hova;
		
		public BtnListener(String param){
			
			hova = param;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			hivo(hova);
			System.out.println("megh�vtam: "+ hova);
			
		}
		
		
	}
	
	public GUIllermo(){
	//frame konstruktor. Az ablak m�rete 1280x720, ablak neve "Othello", a frame h�ttere egy k�p. A gombok �tl�tsz�ak, k�k sz�n�ek.
		super("Othello");
		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("gandalfthepug.jpg")))));
		} catch (IOException e) {
			
			System.out.print("Image doesnt exist");
		}
		
		
		
		
		newGame.setBounds(550, 100, 200, 100);
		highScores.setBounds(550, 250, 200, 100);
		exit.setBounds(550,400, 200, 100);
		
		newGame.setBorder(null);
		highScores.setBorder(null);
		exit.setBorder(null);
		
		newGame.setContentAreaFilled(false);
		highScores.setContentAreaFilled(false);
		exit.setContentAreaFilled(false);
		
		newGame.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		highScores.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		exit.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		
		newGame.setForeground(Color.cyan);
		highScores.setForeground(Color.cyan);
		exit.setForeground(Color.cyan);
		
		this.add(newGame);
		this.add(highScores);
		this.add(exit);
		
		BtnListener newg = new BtnListener("ujjatek");
		newGame.addActionListener(newg);
		BtnListener topl = new BtnListener("toplista");
		highScores.addActionListener(topl);
		BtnListener egz = new BtnListener("exit");
		exit.addActionListener(egz);
		
		
		this.setResizable(false);
		this.pack();
	}
	
	public void hivo(String ide){
	
	//Ezt h�vja meg a buttonlistener. ha az �tadott string "toplista", a Toplista framet nyitja meg, mag�t bez�rja, ha az �tadott string "ujjatek", a Nev_megadas framet nyitja meg, mag�t bez�rja.
	//Ha az �tadott string "exit", System.exit(0)-val kil�p.
	
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
