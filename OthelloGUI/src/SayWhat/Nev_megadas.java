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
import javax.swing.JTextField;

public class Nev_megadas extends JFrame {
	//Név megadása frame. Itt adhatják meg a játékosok a neveiket, az "OK!" gombra kattintva elindul a játék, a "Vissza" gombra kattintva visszajutnak a fõmenübe.
	

	private static final long serialVersionUID = 1L;
	private JTextField blck = new JTextField("Player 1");
	private JTextField wht = new JTextField("Player 2");
	private JButton vissza = new JButton("Vissza");
	private JButton ok = new JButton("OK!");
	public Ujjatek mehet = new Ujjatek();//létrehoz egy "Ujjatek" framet.
	
	public  class BtnListener implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			vissza();
			
		}
		
		
	}
	
	public class OkBtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
	
			fight();
		}
		
		
		
	}
	
	public void vissza(){
		//"Vissza" gombra kattintva ez a függvény hívódik meg, a fõmenübe juttat.
		GUIllermo visszamegyek = new GUIllermo();
		
		this.setVisible(false);
		visszamegyek.setVisible(true);
		
	}
	
	public void fight(){
		//"OK!" gombra kattintva ez a függvény hívódik meg, beállítja a neveket az Ujjatek framen, meg is nyitja, ezt pedig bezárja.
		mehet.fekete.setText(blck.getText());
		mehet.feher.setText(wht.getText());
		this.setVisible(false);
		mehet.setVisible(true);
		this.dispose();
		
	}
	
	public Nev_megadas(){
	//Frame konstruktor. Az ablak mérete 1280x720, ablak neve "Új játék", a frame háttere egy kép. A gombok átlátszóak, kék színûek.
		super("Uj_játék");
		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("gandalfthepug.jpg")))));
		} catch (IOException e) {
			
			System.out.print("Image doesnt exist");
		}
		
		
		
		vissza.setLocation(1130,670);
		vissza.setSize(150,50);
		BtnListener visszaGomb = new BtnListener();
		vissza.addActionListener(visszaGomb);
		vissza.setBorder(null);
		vissza.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		vissza.setForeground(Color.cyan);
		vissza.setContentAreaFilled(false);
		
		blck.setBackground(Color.darkGray);
		wht.setBackground(Color.darkGray);
		
		blck.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		wht.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		
		blck.setSize(150,50);
		wht.setSize(150,50);
		
		
		blck.setForeground(Color.cyan);
		wht.setForeground(Color.cyan);
		blck.setLocation(200, 50);
		wht.setLocation(980,50);
		
		ok.setBorder(null);
		ok.setContentAreaFilled(false);
		ok.setFont(new Font("Serif", Font.TRUETYPE_FONT, 60));
		ok.setSize(150,50);
		ok.setForeground(Color.cyan);
		ok.setLocation(570, 300);
		
		OkBtnListener okee = new OkBtnListener();
		ok.addActionListener(okee);
		
		this.add(blck);
		this.add(wht);
		this.add(ok);
		this.add(vissza);
		
		this.setResizable(false);
		this.pack();
		
	}
	
}
