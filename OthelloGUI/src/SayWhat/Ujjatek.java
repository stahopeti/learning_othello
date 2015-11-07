package SayWhat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Ujjatek extends JFrame{

	List<String> lepesSorozat=new ArrayList<String>();
	TreeNode Root=new TreeNode("root");
	TreeNode elozoElement=Root;

	
	private static final long serialVersionUID = 1L;

	//Asztal.
	Asztal gameh = new Asztal();
		
	//Globális változók.
	static int korszamlalo = 0; //Ezáltal döntjük el ki következik. 
	private int feherSc =0, feketeSc =0; //Játékosok pontszámai.
	TOPLIST lista = new TOPLIST(); //név_pontszám formátumban
	
	//2dimenziós gombtömb.
		JButton[][] tabla = new JButton[10][10];
	
	//Játékosok nevei.
	private JLabel fkt = new JLabel("Fekete Játékos: "+ gameh.hanySotet());
	private JLabel fhr = new JLabel("Fehér Játékos: "+ gameh.hanyVilagos());
	JTextField fekete = new JTextField();
	JTextField feher = new JTextField();
	
	
	
	
	private JButton vissza = new JButton("Vissza");
	
	private JLabel rossz_lepes = new JLabel("");
	
	
	
	
	
	public  class BtnListener implements ActionListener{//"Vissza" gomb listenerje, gombra kattintva visszalépünk a fõmenübe.
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			vissza();
			
		}
		
		
	}
	
	public class KorongListener implements ActionListener{//A játék lényegi része itt történik.
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int x=0,y=0;//ezeket adja tovább koordinátaként a sotetForgat vagy vilagosForgat függvénynek
			int lepett_e=0;
			System.out.println("Koronglistener\n");						
			
			for(int i=1;i<9;i++){
				for(int j=1;j<9;j++){				
					if(e.getSource()==tabla[i][j]){ //Megvizsgálja, a 2d gombtömb melyikére kattintottunk.						
						System.out.println("A lenyomott gomb: x:"+ (i)+" y:"+(j));//teszt kimenet konzolra
						x=i; y=j;
					}
				}
				
			}
			
			if(korszamlalo%2==0)//Globális változó, ha páros, sotetLep, ha páratlan vilagosLep.
				try {
					List<String> lehetsegesLepesek = new ArrayList<String>();     
					lehetsegesLepesek=lehetsegesLepesLista();            //kigyûjtöm egy listába a lehetséges lépéseket
					
					if (lehetsegesLepesek.size()==0){korszamlalo++;}	 //ha nincs hova lépni, akkor passzolni kell
					else{
						lepett_e = gameh.sotetForgat(x,y,false);//A függvény visszatérési értéke egy int, ha ez nagyobb mint 0, volt megfelelõ lépés.
						if (lepett_e>0){
							fabaSzur(x,y);
							korszamlalo++;
							update();//Update függvény frissíti a táblát.
							System.out.println("updateolva");//tesztkimenet konzolra
							gameh.asztalKiir();
						}
					}																						
				} catch (IOException e1) {		
					e1.printStackTrace();
				}
			if(korszamlalo%2==1){
				List<String> lehetsegesLepesek = new ArrayList<String>();
				lehetsegesLepesek=lehetsegesLepesLista();
				
				System.out.println("Lehetséges lépések:");
				for (int i=0;i<lehetsegesLepesek.size();i++){
					System.out.println(lehetsegesLepesek.get(i));
				}
				
				if (lehetsegesLepesek.size()==0){korszamlalo++;}
				else{
					TreeNode[] Gyerekek=elozoElement.getChildren();        //FONTOS: mivel ez a fehér játékos és a WinRate a kezdõ azaz fekete játékosra vonatkozik!!!
					
					if (Gyerekek.length==0){                               //ha nincs gyereke akkor mindegyik WinRate 0.5 tehát mehet a random
						System.out.println("Nincs még gyereke");
						Random rand = new Random(); 
						int randomValue = rand.nextInt(lehetsegesLepesek.size());                //veszek egy random számot
						System.out.println("RANDOM: "+randomValue);
						
						x=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(0)));
						y=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(1)));
					}
					else{
						double TempMax=0.0;
						int TempPosIndex=0;
						
						for (TreeNode gyerek : Gyerekek){                   		//végigmegyek a gyerekeken            		
							for (int i=0;i<lehetsegesLepesek.size();i++){    		//végigmegyek a lehetséges lépések listán, megnézem, hogy benne van-e (lehet h fölösleges egyébként)
								if (gyerek.getPosition().equals(lehetsegesLepesek.get(i))){    
									//TESZT
									System.out.println("Current Gyerek: " + gyerek.getPosition() + " WinRateje: " + gyerek.getWinRate() + " KorábbiMAX: " + TempMax);
									
									if (gyerek.getWinRate()>TempMax){				//ha benne van, akkor maxkeresés a WinRate-re
										TempMax=gyerek.getWinRate();
										TempPosIndex=i;
										lehetsegesLepesek.remove(i);				//kiveszem a listából
									}
								}
							}	
						}
						if (TempMax>0.5){
							x=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(0)));
							y=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(1)));	
							
						}
						else{  // ha a legnagyobb gyerek WinRate-je kisebb mint 0.5, akkor jobb a maradék lépések közül random választani, hiszen a ki nem próbált ágak
							if (lehetsegesLepesek.size()>0){
								Random rand = new Random(); 
								int randomValue = rand.nextInt(lehetsegesLepesek.size()); 						
								x=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(0)));
								y=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(1)));
							}
							else{  //ha az összes gyerek WinRate-je 0.5 alatt volt, és benne volt a lehetséges lépések listájában ez az eset áll elõ
								x=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(0)));
								y=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(1)));
							}
						}
						
					}													
					try {
						lepett_e = gameh.vilagosForgat(x,y,false);//A függvény visszatérési értéke egy int, ha ez nagyobb mint 0, volt megfelelõ lépés.
						if (lepett_e>0){
							fabaSzur(x,y);
							korszamlalo++;
							update();//Update függvény frissíti a táblát.
							System.out.println("updateolva");//tesztkimenet konzolra
							gameh.asztalKiir();
						}
					}catch (IOException e1) {
					
						e1.printStackTrace();
					}
				}
			}
			System.out.println("lepes: " + korszamlalo%2 + " lepett_e: " + lepett_e);//teszt kimenet a konzolra
			if(lepett_e==0) {
				rossz_lepes.setText("Rossz Lépés!");    //ha nem volt írja ki, hogy "Rossz lépés!". 
			}
								
			
			System.out.println("Lépések sorozata:");
			for (int i=0;i<lepesSorozat.size();i++){
				System.out.println(lepesSorozat.get(i));
			}
			
					
			System.out.println("\nüresek száma: " +gameh.hanyUres()+ "\n");//teszt kimenet a konzolra
			
			}
		
	} 
	
	public List<String> lehetsegesLepesLista(){
		List<String> lepesek = new ArrayList<String>();
		
		if(korszamlalo%2==0){       						       //külön fehér, külön fekete játékos esetén
			for(int i=1;i<9;i++){                   		       //bevégigmegyek a tábla összes elemén (ezen lehet talán optimalizálni késõbb)
				for(int j=1;j<9;j++){
						if (0<gameh.sotetForgat(i,j,true)){             //megvizsgálom, hogy a lépés lehetséges-e
							String part1=Integer.toString(i);
							String part2=Integer.toString(j);
							lepesek.add(part1+part2);          //belerakom a listába
						}
				}
			}
		}
		if(korszamlalo%2==1){              							//ugyan az, mint fekete játékos esetén
			for(int i=1;i<9;i++){
				for(int j=1;j<9;j++){
					if (0<gameh.vilagosForgat(i,j,true)){
						String part1=Integer.toString(i);
						String part2=Integer.toString(j);
						lepesek.add(part1+part2);
					}
				}
			}

		}
		return lepesek;
	}

	public void fabaSzur(int x, int y){
		boolean beszur=true;
		String part1=Integer.toString(x);
		String part2=Integer.toString(y);
		lepesSorozat.add(part1+part2);                       //Pozíció Stringgé alakítása
		
		TreeNode aktualis=new TreeNode(part1+part2);         //létrehozom a beszúrandó elemet
	    
		TreeNode[] gyerekek=elozoElement.getChildren();      //az elõzõ elemnek megnézem a gyerekeit
		
		//Lehet h az az elem már korábban be volt szúrva, tehát lehetnek gyerekei, az is lehet h friss ez az ág és nincs is még gyereke.
		for (TreeNode i : gyerekek){							//megnézem, hogy a szülõnek van-e már ilyen gyereke
			if (i.getPosition().equals(aktualis.getPosition())){
	    		elozoElement=i;                          //ha van már ilyen gyereke, akkor nem kell beszúrnom
	    		beszur=false;
	    	}	    	
	    }
		if (beszur){                                            //ha még nem volt ilyen gyerek, akkor beszúrás gyereknek
			elozoElement.add(aktualis);
			elozoElement=aktualis;	
		}	
	}

	public Ujjatek(){
	//Frame konstruktor. A frame mérete 1280x720, neve "Uj_jatek".
		super("Uj_jatek");
		korszamlalo=0;
		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("gandalfthepug.jpg")))));
		} catch (IOException e) {
			
			System.out.print("Image doesnt exist");
		}
		
		//palya
		vissza.setLocation(1130,670);
		vissza.setSize(150,50);
		BtnListener visszaGomb = new BtnListener();
		vissza.addActionListener(visszaGomb);
		vissza.setBorder(null);
		vissza.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		vissza.setForeground(Color.cyan);
		vissza.setContentAreaFilled(false);
		this.add(vissza);
		
		fekete.setEnabled(false);
		feher.setEnabled(false);
		
		fekete.setBackground(Color.darkGray);
		feher.setBackground(Color.darkGray);
		
		fkt.setSize(150,50);
		fhr.setSize(150,50);
		fekete.setSize(150,50);
		feher.setSize(150,50);
		
		fkt.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
		fhr.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
		fekete.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		feher.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		
		
		fekete.setOpaque(false);
		feher.setOpaque(false);
		
		fekete.setDisabledTextColor(Color.black);
		feher.setDisabledTextColor(Color.black);
		
		fekete.setBorder(null);
		feher.setBorder(null);
		
		rossz_lepes.setLocation(527, 550);
		rossz_lepes.setFont(new Font("Serif", Font.TRUETYPE_FONT, 50));
		rossz_lepes.setForeground(Color.cyan);
		rossz_lepes.setSize(350,150);
		
		fkt.setLocation(200, 0);
		fhr.setLocation(980, 0);
		fekete.setLocation(200, 50);
		feher.setLocation(980,50);
			
		//pálya felépítése
		Border border = new LineBorder(Color.black, 1);
		int sorban=425;
		int oszlopban=127;
		for(int i = 1; i<9;i++){
			for(int j=1;j<9;j++){
				if(gameh.palya[i][j].getSzin()=='-'){
					tabla[i][j] = new JButton();
					tabla[i][j].setIcon(new ImageIcon("nincs_korong.jpg"));
				}
				if(gameh.palya[i][j].getSzin()=='X'){
					tabla[i][j] = new JButton(new ImageIcon("fekete_korong.jpg"));
				}
				if(gameh.palya[i][j].getSzin()=='O'){
					tabla[i][j] = new JButton(new ImageIcon("feher_korong.jpg"));
				}
								
				tabla[i][j].setLocation(sorban,oszlopban);
				tabla[i][j].setSize(58,58);
				tabla[i][j].setBorder(border);
				this.add(tabla[i][j]);
				sorban+=58;
			}
			oszlopban+=58;
			sorban=425;
			
		}
		//pálya felépítése
		
		
		this.add(rossz_lepes);
		this.add(fekete);
		this.add(feher);
		this.add(fkt);
		this.add(fhr);
		this.setResizable(false);
		this.pack();
		System.out.println("jatekhivas elõtt állunk");
		try {
			jatek();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Adatbázis betöltése
		TreeNode serTreeNode = null;
	    try
	    {
	       FileInputStream fileIn = new FileInputStream("Tree.dat");
	       ObjectInputStream in = new ObjectInputStream(fileIn);
	       serTreeNode = (TreeNode) in.readObject();
	       in.close();
	       fileIn.close();
		   Root=serTreeNode;
		   elozoElement=Root;
		   System.out.println("Sikerült az adatbázis betöltése");		   
		   
		   //Tesztelgetéshez..  
//			TreeNode[] TestRoot=Root.getChildren();
//			TreeNode[] TestElsoGyerekGyereke=Root.getChildren()[1].getChildren();
//			TreeNode asd=TestElsoGyerekGyereke[0];
//			TreeNode[] wtf=asd.getChildren();
//			
//			System.out.println("Tesztelési célokra:\nRoot Gyerekei:");
//			for (int i=0;i<TestRoot.length;i++){
//				System.out.println(TestRoot[i].getPosition() + " Winrate: " + TestRoot[i].getWinRate());
//			}
//			System.out.println("Most épp a Root 2 gyerekének gyerekei:");
//			for (int i=0;i<TestElsoGyerekGyereke.length;i++){
//				System.out.println(TestElsoGyerekGyereke[i].getPosition());
//			}
//			System.out.println("Most épp a Root 2 gyerekének az elsõ gyerekének a gyerekei:");
//			for (int i=0;i<wtf.length;i++){
//				System.out.println(wtf[i].getPosition());
//			}
			
	    }catch(IOException i)
	    {
	       i.printStackTrace();
		   System.out.println("Nem sikerült az adatbázis betöltése");
	    }catch(ClassNotFoundException c)
	    {
	       System.out.println("Tree class not found");
	       c.printStackTrace();
	    }
		

		
	}
	

	

	
	
	
		
	
	public int getFeherSc(){
		
		return feherSc;//visszaadja a világos pontszámát
		
	}
	
	public int getFeketeSc(){
		
		return feketeSc;//visszaadja a világos pontszámát
		
	}
	
	
	
	public static void serializalas(final TOPLIST param){
		
		
		try {
			FileOutputStream f = new FileOutputStream("SERIALIZALT.dat");
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(param);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException");
			e.printStackTrace();
		}
		
		
	}
	

	public static TOPLIST deserializalas() {
		TOPLIST temp = new TOPLIST();
		
		try {
			FileInputStream f =	new FileInputStream("SERIALIZALT.dat");
			ObjectInputStream in =	new ObjectInputStream(f);
			temp = (TOPLIST)in.readObject();
			in.close();
		} catch(IOException ex) { 
		} catch(ClassNotFoundException ex) { 
		}
		System.out.println("\n\nSerializált visszatöltve\n\n");
		return temp;
	}
	
	
	
	public void update() throws IOException{
		
		
		
		for(int i = 1; i<9;i++){//Végig iterál a pályán, frissíti a megjelenítést.
			for(int j = 1;j<9;j++){
				if(gameh.palya[i][j].getSzin()=='-'){
					tabla[i][j].setIcon(new ImageIcon("nincs_korong.jpg"));
				}
				if(gameh.palya[i][j].getSzin()=='X'){
					tabla[i][j].setIcon(new ImageIcon("fekete_korong.jpg"));
				}
				if(gameh.palya[i][j].getSzin()=='O'){
					tabla[i][j].setIcon(new ImageIcon("feher_korong.jpg"));
				}
				
			}
			
			
		}
		//"Név : pontszám" labelre
		fkt.setText("Fekete Játékos: "+ gameh.hanySotet());
		fhr.setText("Fehér Játékos: "+ gameh.hanyVilagos());
		rossz_lepes.setText("");		
		
		if(gameh.hanyUres()==0){            //ha már nincs üres.
											
			int allas = 0; // ha fekete nyert 0 ha fehér 1 ha döntetlen 2
				
			//pontszám beállítása
			feherSc = gameh.hanyVilagos();
			feketeSc = gameh.hanySotet();
				
			String nyertes = "Döntetlen";
				
			System.out.println("A játéknak vége!");
			System.out.println("A játék végén: Lépések sorozata:");
			for (int i=0;i<lepesSorozat.size();i++){
				System.out.println(lepesSorozat.get(i));
			}
														
			
			if(feketeSc > feherSc) {//Ha sötétnek több pontja van.
				
				rossz_lepes.setText(fekete.getText()+ " nyert!"); 
				allas = 0;
				nyertes = fekete.getText();
				
				for (int i=0;i<lepesSorozat.size();i++){
					System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement.incWinCount();
					elozoElement.setWinRate();
					System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement=elozoElement.getParent();
					
				}
				
				
			}
			if(feketeSc < feherSc) {//Ha világosnak több pontja van.
				rossz_lepes.setText(feher.getText()+ " nyert!");
				allas = 1;
				nyertes = feher.getText();
				
				for (int i=0;i<lepesSorozat.size();i++){
					System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement.incLoseCount();
					elozoElement.setWinRate();
					System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement=elozoElement.getParent();
				}
				
			}
			if(feherSc==feketeSc) {rossz_lepes.setText("Döntetlen!"); allas = 2;}//Ha egyenlõ az állás
			
			System.out.println("fekete: "  + feketeSc + "feher: "+ feherSc);//teszt kimenet konzolra
			    
			
			
						
			try{																		//szerializálom a Tree-t
				FileOutputStream fileOut = new FileOutputStream("Tree.dat");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(Root);
				out.close();
				fileOut.close();
				System.out.printf("Serialized data is saved in Tree.dat\n");
			}catch(IOException i){
			    i.printStackTrace();
			}
						
			
			for(int i = 0;i<lista.nevek_listaja.size(); i++){//tesztkimenet konzolra
				
				System.out.println(lista.nevek_listaja.get(i).name + " : " + lista.nevek_listaja.get(i).score);
				
			}
			
			int szamolo = 0;//ha a számoló 0, olyan játékos nyert, aki még eddig nem.
			for(int i = 0;i<lista.nevek_listaja.size(); i++){
				
				if(allas == 0){
					if(lista.nevek_listaja.get(i).name.equals(fekete.getText())){ //ha már az "i" indexû elem benne volt a toplistában, hozzáad egyet a pontjához
						
						lista.nevek_listaja.get(i).score = lista.nevek_listaja.get(i).score+1;
						szamolo++;
					}
					
					
				}
				if(allas == 1){
					if(lista.nevek_listaja.get(i).name == feher.getText()){	//ha már az "i" indexû elem benne volt a toplistában, hozzáad egyet a pontjához
						
						lista.nevek_listaja.get(i).score = lista.nevek_listaja.get(i).score+1;
						szamolo++;
					}

					System.out.println("FEHÉR NYERT: " + nyertes);
					
				}
				
				if(allas == 2) szamolo++;
			}
			
			
			System.out.println("volt hasonló: " + szamolo);
			if(szamolo == 0){//új bejegyzés a toplistába
				
				if(allas == 0){
				FajlbaTopLista temp = new FajlbaTopLista();
				temp.name = fekete.getText();
				temp.score = 1;
				lista.nevek_listaja.add(temp);
				}
				
				if(allas == 1){
					FajlbaTopLista temp = new FajlbaTopLista();
					temp.name = feher.getText();
					temp.score = 1;
					lista.nevek_listaja.add(temp);
					
					}
				
			}
			
			BufferedWriter kiir = new BufferedWriter(new FileWriter("toplista.txt"));//átírjuk a toplistát
			for(int i = 0;i<lista.nevek_listaja.size(); i++){
				
				String temp = lista.nevek_listaja.get(i).name + "_" + lista.nevek_listaja.get(i).score;
				kiir.write(temp);
				kiir.newLine();
				System.out.println(lista.nevek_listaja.get(i).name + "_" + lista.nevek_listaja.get(i).score);
				
			}
			
			kiir.close();
			serializalas(lista);//serializáljuk a listát.
			
			TOPLIST lol = deserializalas();//
			
			
			
			
			for(int i = 0;i<lol.nevek_listaja.size(); i++){
				
				System.out.println(" " + lol.nevek_listaja.get(i).name + "_" + lol.nevek_listaja.get(i).score);//teszt kimenet konzolra
				
			}
			
		}
		
		
	}
	
	public void vissza(){
		//Visszavisz a fõmenübe, ezt a framet bezárja.
		
		GUIllermo visszamegyek = new GUIllermo();
		visszamegyek.setVisible(true);
		this.setVisible(false);
		this.dispose();
		
	}
	
	public void jatek() throws NumberFormatException, IOException{
		
		
		BufferedReader beolvas = new BufferedReader(new FileReader("toplista.txt"));
		String line = "";
		
		while((line = beolvas.readLine())!= null){//Beolvassa a toplistát, betölti a listába.
			FajlbaTopLista temp = new FajlbaTopLista();
			
			String[] parts = line.split("_");
			temp.name=parts[0];
			temp.score=Integer.parseInt(parts[1]);
			
			lista.nevek_listaja.add(temp);
			
			
		}
		
		beolvas.close();
		
		
		for(int i=1;i<9;i++){
			for(int j=1;j<9;j++){
				KorongListener kl = new KorongListener();
				tabla[i][j].addActionListener(kl);
				
			}
			
		}
	}
	

	
	
}



