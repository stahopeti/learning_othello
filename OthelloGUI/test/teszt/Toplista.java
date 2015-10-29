package teszt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Toplista extends JFrame {
	
	private JLabel szoveg = new JLabel();
	private JTextField[] lista = new JTextField[10];
	private JTextField[] scores = new JTextField[10];
	private JButton vissza = new JButton("Vissza");
	Ujjatek atmenet = new Ujjatek();
	TOPLIST toptoplista = new TOPLIST();
	
	public  class BtnListener implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			vissza();
			
		}
		
		
	}
	
	public void vissza(){
		
		
		GUIllermo visszamegyek = new GUIllermo();
		
		this.setVisible(false);
		visszamegyek.setVisible(true);
		
	}
	
	public Toplista(){
	//frame konstruktor. Az ablak mérete 1280x720, ablak neve "Toplista", a frame háttere egy kép. A gomb átlátszó, kék színû.
		super("Toplista");
		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("gandalfthepug.jpg")))));
		} catch (IOException e) {
			
			System.out.print("Image doesnt exist");
		}
		
		int asd = 40;
		for(int i = 0; i<10; i++){
			
			lista[i] = new JTextField();
			
			lista[i].setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
			lista[i].setSize(260, 40);
			lista[i].setLocation(480, 100+ (i*asd));
			lista[i].setEnabled(false);
			lista[i].setDisabledTextColor(Color.black);
			
			scores[i] = new JTextField();
			scores[i].setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
			scores[i].setSize(40, 40);
			scores[i].setLocation(750, 100+ (i*asd));
			scores[i].setEnabled(false);
			scores[i].setDisabledTextColor(Color.black);
			
		}
		
		toptoplista = atmenet.deserializalas();
		Collections.sort(toptoplista.nevek_listaja, FajlbaTopLista.ScoreBased);
		//textfield feltöltése
		String temp = "";
		for(int i = 0;i<toptoplista.nevek_listaja.size() && i<10;i++){
			
			
			temp += toptoplista.nevek_listaja.get(i).name;
			
			
			scores[i].setText(Integer.toString(toptoplista.nevek_listaja.get(i).score));
			lista[i].setText(temp);
			
			this.add(lista[i]);
			this.add(scores[i]);
			temp = "";
		}
		
		
		
		//textfield feltöltése
		
		szoveg.setText("TOPLISTA");
		szoveg.setForeground(Color.DARK_GRAY);
		szoveg.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
		szoveg.setLocation(537, 30);
		szoveg.setBorder(null);
		szoveg.setSize(500, 50);
		
		
		
		vissza.setLocation(1130,670);
		vissza.setSize(150,50);
		BtnListener visszaGomb = new BtnListener();
		vissza.addActionListener(visszaGomb);
		vissza.setBorder(null);
		vissza.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		vissza.setForeground(Color.cyan);
		vissza.setContentAreaFilled(false);
		this.add(vissza);
		this.add(szoveg);
		this.setResizable(false);
		this.pack();
		
	}
	
}
