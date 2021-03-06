package SayWhat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class UjjatekGepVsGep extends JFrame{

	private List<String> lepesSorozat=new ArrayList<String>();
	private TreeNode Root;
	private TreeNode elozoElement;
	int howManyGames;
	boolean vege = false;
	int skip=0;
	static int j;
	boolean tukorvizsgalat=true;
	int transzform=0;
	
	private static final long serialVersionUID = 1L;
	

	private JTextField textFieldbe= new JTextField(5);
	private JTextField textFieldki= new JTextField(5);

	
	//Asztal.
	Asztal gameh = new Asztal();
		
	//Glob�lis v�ltoz�k.
	static int korszamlalo = 0; //Ez�ltal d�ntj�k el ki k�vetkezik. 
	private int feherSc =0, feketeSc =0; //J�t�kosok pontsz�mai.
	//TOPLIST lista = new TOPLIST(); //n�v_pontsz�m form�tumban
	
	//2dimenzi�s gombt�mb.
	//	JButton[][] tabla = new JButton[10][10];
	 
	//J�t�kosok nevei.
	//private JLabel fkt = new JLabel("Fekete J�t�kos: "+ gameh.hanySotet());
	//private JLabel fhr = new JLabel("Feh�r J�t�kos: "+ gameh.hanyVilagos());
	//JTextField fekete = new JTextField("Fekete");
	//JTextField feher = new JTextField("Feh�r");
	
	
	
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Befejez");
	private JButton vissza = new JButton("Vissza");
	
	private JLabel jatekszam = new JLabel("J�t�kok sz�ma:");
	private JLabel jatekle = new JLabel("Lej�tszott j�t�kok sz�ma:");
	private JLabel allapot = new JLabel("");
	
	
	
	
	public  class BtnListener implements ActionListener{//"Vissza" gomb listenerje, gombra kattintva visszal�p�nk a f�men�be.
		@Override
		public void actionPerformed(ActionEvent arg0) {
			vissza();	
		}
	}
	
	public  class BtnStopListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			j=howManyGames-1;	
		}
	}

	
	public List<String> lehetsegesLepesLista(){
		List<String> lepesek = new ArrayList<String>();
		
		if(korszamlalo%2==0){       						       //k�l�n feh�r, k�l�n fekete j�t�kos eset�n
			for(int i=1;i<9;i++){                   		       //bev�gigmegyek a t�bla �sszes elem�n (ezen lehet tal�n optimaliz�lni k�s�bb)
				for(int j=1;j<9;j++){
						if (0<gameh.sotetForgat(i,j,true)){             //megvizsg�lom, hogy a l�p�s lehets�ges-e
							String part1=Integer.toString(i);
							String part2=Integer.toString(j);
							lepesek.add(part1+part2);          //belerakom a list�ba
						}
				}
			}
		}
		if(korszamlalo%2==1){              							//ugyan az, mint fekete j�t�kos eset�n
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
		lepesSorozat.add(part1+part2);                       //Poz�ci� Stringg� alak�t�sa
		
		TreeNode aktualis=new TreeNode(part1+part2);         //l�trehozom a besz�rand� elemet
	    
		TreeNode[] gyerekek=elozoElement.getChildren();      //az el�z� elemnek megn�zem a gyerekeit
		
		//Lehet h az az elem m�r kor�bban be volt sz�rva, teh�t lehetnek gyerekei, az is lehet h friss ez az �g �s nincs is m�g gyereke.
		for (TreeNode i : gyerekek){							//megn�zem, hogy a sz�l�nek van-e m�r ilyen gyereke
			if (i.getPosition().equals(aktualis.getPosition())){
	    		elozoElement=i;                          //ha van m�r ilyen gyereke, akkor nem kell besz�rnom
	    		beszur=false;
	    	}	    	
	    }
		if (beszur){                                            //ha m�g nem volt ilyen gyerek, akkor besz�r�s gyereknek
			elozoElement.add(aktualis);
			elozoElement=aktualis;	
		}	
	}

	public int transzformal(int x){
		if (transzform==1){
			return 8-x+1;	
		}
		return x;
	}	

	
	public void feketeComputer(){				//fekete g�pi j�t�kos l�p�se 
		List<String> lehetsegesLepesek = new ArrayList<String>();
		lehetsegesLepesek=lehetsegesLepesLista();             //kigy�jt�m egy list�ba a lehets�ges l�p�seket
		
		int x=0;
		int y=0;
		int lepett_e=0;
		
		//System.out.println("Fekete Lehets�ges l�p�sek:");
		//for (int i=0;i<lehetsegesLepesek.size();i++){
		//	System.out.println(lehetsegesLepesek.get(i));
		//}
		
		if (lehetsegesLepesek.size()==0){korszamlalo++;skip++;}         //ha nincs hova l�pni, akkor passzolni kell
		else{
			skip=0;
			String Lepes=feketeComputerLep(lehetsegesLepesek);    //kisz�molja, hogy hova kell l�pnie
			x=Character.getNumericValue(Lepes.charAt(0));
			y=Character.getNumericValue(Lepes.charAt(1));
			if (korszamlalo==0){x=3;y=4;}
			
			/*
			if (tukorvizsgalat){
				if (x==6 && y==5){
					transzform=1;
					tukorvizsgalat=false;
					x=transzformal(x);
					y=transzformal(y);
				}
				else{tukorvizsgalat=false;}
			}
			*/
															
			lepett_e = gameh.sotetForgat(x,y,false);//A f�ggv�ny visszat�r�si �rt�ke egy int, ha ez nagyobb mint 0, volt megfelel� l�p�s.
			if (lepett_e>0){
				fabaSzur(x,y);
				korszamlalo++;
				try {
					update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("updateolva");//tesztkimenet konzolra
				//gameh.asztalKiir();
			}
			//System.out.println("lepes: " + korszamlalo%2 + " lepett_e: " + lepett_e);//teszt kimenet a konzolra
			if(lepett_e==0) {
				//rossz_lepes.setText("Rossz L�p�s!");    //ha nem volt �rja ki, hogy "Rossz l�p�s!". 
			}				
			
			//System.out.println("L�p�sek sorozata:");
			//for (int i=0;i<lepesSorozat.size();i++){
			//	System.out.println(lepesSorozat.get(i));
			//}
		
			//System.out.println("\n�resek sz�ma: " +gameh.hanyUres()+ "\n");//teszt kimenet a konzolra
		}
	}

	public void feherComputer(){ 				//feh�r g�pi j�t�kos l�p�se 
		List<String> lehetsegesLepesek = new ArrayList<String>();
		lehetsegesLepesek=lehetsegesLepesLista();             //kigy�jt�m egy list�ba a lehets�ges l�p�seket
		
		int x=0;
		int y=0;
		int lepett_e=0;
		
		//System.out.println("Feh�r Lehets�ges l�p�sek:");
		//for (int i=0;i<lehetsegesLepesek.size();i++){
		//	System.out.println(lehetsegesLepesek.get(i));
		//}
		
		if (lehetsegesLepesek.size()==0){korszamlalo++;skip++;}         //ha nincs hova l�pni, akkor passzolni kell
		else{
			skip=0;
			String Lepes=feherComputerLep(lehetsegesLepesek);    //kisz�molja, hogy hova kell l�pnie
			x=Character.getNumericValue(Lepes.charAt(0));
			y=Character.getNumericValue(Lepes.charAt(1));													
			
			lepett_e = gameh.vilagosForgat(x,y,false);//A f�ggv�ny visszat�r�si �rt�ke egy int, ha ez nagyobb mint 0, volt megfelel� l�p�s.
			if (lepett_e>0){
				fabaSzur(x,y);
				korszamlalo++;
				try {
					update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("updateolva");//tesztkimenet konzolra
				//gameh.asztalKiir();
			}
	
			//System.out.println("lepes: " + korszamlalo%2 + " lepett_e: " + lepett_e);//teszt kimenet a konzolra
			if(lepett_e==0) {
				//rossz_lepes.setText("Rossz L�p�s!");    //ha nem volt �rja ki, hogy "Rossz l�p�s!". 							
						
				//System.out.println("\n�resek sz�ma: " +gameh.hanyUres()+ "\n");//teszt kimenet a konzolra
			}
		}	
	}
	
	public String feherComputerLep(List<String> lehetsegesLepesek){
		TreeNode[] Gyerekek=elozoElement.getChildren();        //FONTOS: mivel ez a feh�r j�t�kos; a WinRate a kezd�, azaz fekete j�t�kosra vonatkozik!!!
		int x;
		int y;
		
		if (Gyerekek.length==0){                               //ha nincs gyereke akkor mindegyik WinRate 0.5 teh�t mehet a random
			//System.out.println("Nincs m�g gyereke");
			Random rand = new Random(); 
			int randomValue = rand.nextInt(lehetsegesLepesek.size());                //veszek egy random sz�mot
			//System.out.println("RANDOM: "+randomValue);
			
			x=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(0)));
			y=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(1)));
		}
		else{
			double TempMin=1.0;
			int TempPosIndex=0;
			List<String> tmp = new ArrayList<String>(lehetsegesLepesek);
			
			//System.out.println("FEH�R_COMPUTER_L�P");
			
			for (TreeNode gyerek : Gyerekek){                   		//v�gigmegyek a gyerekeken            		
				for (int i=0;i<lehetsegesLepesek.size();i++){    		//v�gigmegyek a lehets�ges l�p�sek list�n, megn�zem, hogy benne van-e (lehet h f�l�sleges egy�bk�nt)
					if (gyerek.getPosition().equals(lehetsegesLepesek.get(i))){    
						//TESZT
						//System.out.println("Index: "+ i + "Current Gyerek: " + gyerek.getPosition() + " WinRateje: " + gyerek.getWinRate() + " Kor�bbiMIN: " + TempMin);
						if (gyerek.getWinRate()>0.5){
							for (int j=0; j<tmp.size(); j++){
								if (gyerek.getPosition().equals(tmp.get(j))){
									tmp.remove(j);				//kiveszem a list�b�l
								}
							}
						}
						else if (gyerek.getWinRate()<=TempMin){				//ha benne van, akkor maxkeres�s a WinRate-re
							TempMin=gyerek.getWinRate();
							TempPosIndex=i;
						}

					}
				}	
			}
			if (TempMin<0.5){
				x=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(0)));
				y=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(1)));	
				
			}
			else{  // ha a legnagyobb gyerek WinRate-je kisebb mint 0.5, akkor jobb a marad�k l�p�sek k�z�l random v�lasztani, hiszen a ki nem pr�b�lt �gak
				if (tmp.size()>0){
					Random rand = new Random(); 
					int randomValue = rand.nextInt(tmp.size()); 						
					x=Character.getNumericValue((tmp.get(randomValue).charAt(0)));
					y=Character.getNumericValue((tmp.get(randomValue).charAt(1)));
				}
				else{  //ha az �sszes gyerek WinRate-je 0.5 alatt volt, �s benne volt a lehets�ges l�p�sek list�j�ban ez az eset �ll el�
					x=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(0)));
					y=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(1)));
				}
			}
		}
		String part1=Integer.toString(x);
		String part2=Integer.toString(y);
		//System.out.println("V�lasztott l�p�s: " + part1+part2);
		return part1+part2;
	}

	public String feketeComputerLep(List<String> lehetsegesLepesek){
		TreeNode[] Gyerekek=elozoElement.getChildren();       
		int x;
		int y;
		
		if (Gyerekek.length==0){                               //ha nincs gyereke akkor mindegyik WinRate 0.5 teh�t mehet a random
			//System.out.println("Nincs m�g gyereke");
			Random rand = new Random(); 
			int randomValue = rand.nextInt(lehetsegesLepesek.size());                //veszek egy random sz�mot
			//System.out.println("RANDOM: "+randomValue);
			
			x=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(0)));
			y=Character.getNumericValue((lehetsegesLepesek.get(randomValue).charAt(1)));
		}
		else{
			double TempMax=0.0;
			int TempPosIndex=0;
			List<String> tmp = new ArrayList<String>(lehetsegesLepesek);
			
			//System.out.println("FEKETE_COMPUTER_L�P");
			
			for (TreeNode gyerek : Gyerekek){                   		//v�gigmegyek a gyerekeken            		
				for (int i=0;i<lehetsegesLepesek.size();i++){    		//v�gigmegyek a lehets�ges l�p�sek list�n, megn�zem, hogy benne van-e (lehet h f�l�sleges egy�bk�nt)
					if (gyerek.getPosition().equals(lehetsegesLepesek.get(i))){    
						//TESZT
						//System.out.println("Current Gyerek: " + gyerek.getPosition() + " WinRateje: " + gyerek.getWinRate() + " Kor�bbiMAX: " + TempMax);
						if (gyerek.getWinRate()<0.5){
							for (int j=0; j<tmp.size(); j++){
								if (gyerek.getPosition().equals(tmp.get(j))){
									tmp.remove(j);				//kiveszem a list�b�l
								}
							}
						}
						else if (gyerek.getWinRate()>TempMax){				//ha benne van, akkor maxkeres�s a WinRate-re
							TempMax=gyerek.getWinRate();
							TempPosIndex=i;
						}

					}
				}	
			}
			if (TempMax>0.5){
				x=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(0)));
				y=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(1)));	
				
			}
			else{  // ha a legnagyobb gyerek WinRate-je kisebb mint 0.5, akkor jobb a marad�k l�p�sek k�z�l random v�lasztani, hiszen a ki nem pr�b�lt �gak
				if (tmp.size()>0){
					Random rand = new Random(); 
					int randomValue = rand.nextInt(tmp.size()); 						
					x=Character.getNumericValue((tmp.get(randomValue).charAt(0)));
					y=Character.getNumericValue((tmp.get(randomValue).charAt(1)));
				}
				else{  //ha az �sszes gyerek WinRate-je 0.5 alatt volt, �s benne volt a lehets�ges l�p�sek list�j�ban ez az eset �ll el�
					x=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(0)));
					y=Character.getNumericValue((lehetsegesLepesek.get(TempPosIndex).charAt(1)));
				}
			}
		}
		String part1=Integer.toString(x);
		String part2=Integer.toString(y);
		//System.out.println("V�lasztott l�p�s: " + part1+part2);
		return part1+part2;
	}
	
	public  class BtnStartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {			
			start();
			
		}
	}
	
	public void start(){
		String param = textFieldbe.getText();	
		howManyGames = Integer.parseInt(param);
				
		System.out.println("Ennyi menetet kell lej�tszanom: "+howManyGames);
		
		
		//Adatb�zis bet�lt�se
		TreeNode serTreeNode = null;
		try{

			FileInputStream fileIn = new FileInputStream("Tree.dat");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			serTreeNode = (TreeNode) in.readObject();
			in.close();
			fileIn.close();
			Root=serTreeNode;
			elozoElement=Root;
			allapot.setText("Siker�lt az adatb�zis bet�lt�se");
			System.out.println("Siker�lt az adatb�zis bet�lt�se");		   		
		}catch(IOException e){
			//e.printStackTrace();
			System.out.println("Nem siker�lt az adatb�zis bet�lt�se");
			allapot.setText("Nem siker�lt az adatb�zis bet�lt�se");
			Root=new TreeNode("00");
			elozoElement=Root;
				   
		}catch(ClassNotFoundException c){
			System.out.println("Tree class not found");
			c.printStackTrace();
		}
		
		new Thread(new Runnable() {

			  public void run() {
				  	allapot.setText("J�t�kok fut�sa folyamatban");
			    	for (j = 0; j < howManyGames; j++) {
			    		try {
							jatek();
							//System.out.println((j+1) + ". j�t�k v�ge");
						} catch (NumberFormatException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		// Runs inside of the Swing UI thread
			    		SwingUtilities.invokeLater(new Runnable() {
			    			public void run() {
			    				textFieldki.setText(Integer.toString(j));
			    			}
			    		});
			    		if (j%1000==0){
				    		try {
				    			java.lang.Thread.sleep(5);
				    		}
				    		catch(Exception e) { }
				    	}
			    	}
			    	if (j==howManyGames){
			    		textFieldki.setText(Integer.toString(howManyGames));
			    		Treekiir();
			    	}
			    }
		}).start();
			    	
	
	}
	
	public void Treekiir(){
		try{
			System.out.printf("Megkezdtem az adatb�zis f�jlba �r�s�t\n");
			allapot.setText("Megkezdtem az adatb�zis f�jlba �r�s�t");
			//szerializ�lom a Tree-t
			FileOutputStream fileOut = new FileOutputStream("Tree.dat");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(Root);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in Tree.dat\n");
			allapot.setText("Adatb�zis f�jlba �r�sa sikeresen megt�rt�nt!");
		}catch(IOException i){
			i.printStackTrace();
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public UjjatekGepVsGep(){
		super("Uj_jatek");					
		
		//Frame konstruktor. A frame m�rete 1280x720, neve "Uj_jatek".
		korszamlalo=0;
		

		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new JLabel(new ImageIcon((getClass().getResource("/gandalfthepug.jpg")))));
		
		textFieldbe.setBounds(850,295,100,25);
		this.add(textFieldbe);
		
		textFieldki.setBounds(850,365,100,25);
		textFieldki.setEditable(false);
		this.add(textFieldki);
		
		jatekszam.setBounds(500,275,300,50);
		jatekszam.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		jatekszam.setForeground(Color.cyan);
		this.add(jatekszam);
		
		jatekle.setBounds(500,350,300,50);
		jatekle.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		jatekle.setForeground(Color.cyan);
		this.add(jatekle);
		
		allapot.setBounds(300,450,900,50);
		allapot.setFont(new Font("Serif", Font.TRUETYPE_FONT, 35));
		allapot.setForeground(Color.cyan);
		this.add(allapot);
		
		startButton.setLocation(500,200);
		startButton.setSize(150,50);
		BtnStartListener startGomb = new BtnStartListener();
		startButton.addActionListener(startGomb);
		//startButton.setBorder(null);
		startButton.setFont(new Font("Serif", Font.TRUETYPE_FONT, 50));
		startButton.setForeground(Color.cyan);
		startButton.setContentAreaFilled(false);
		this.add(startButton);
		
		stopButton.setLocation(825,200);
		stopButton.setSize(250,65);
		BtnStopListener stopGomb = new BtnStopListener();
		stopButton.addActionListener(stopGomb);
		//stopButton.setBorder(null);
		stopButton.setFont(new Font("Serif", Font.TRUETYPE_FONT, 50));
		stopButton.setForeground(Color.cyan);
		stopButton.setContentAreaFilled(false);
		this.add(stopButton);
		
		vissza.setLocation(1130,580);
		vissza.setSize(150,50);
		BtnListener visszaGomb = new BtnListener();
		vissza.addActionListener(visszaGomb);
		vissza.setBorder(null);
		vissza.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));
		vissza.setForeground(Color.cyan);
		vissza.setContentAreaFilled(false);
		this.add(vissza);
		
		/*
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
			
		//p�lya fel�p�t�se
		Border border = new LineBorder(Color.black, 1);
		int sorban=425;
		int oszlopban=127;
		for(int i = 1; i<9;i++){
			for(int j=1;j<9;j++){
				if(gameh.palya[i][j].getSzin()=='-'){
					tabla[i][j] = new JButton();
					tabla[i][j].setIcon(new ImageIcon((getClass().getResource("/nincs_korong.jpg"))));
				}
				if(gameh.palya[i][j].getSzin()=='X'){
					tabla[i][j] = new JButton(new ImageIcon((getClass().getResource("/fekete_korong.jpg"))));
				}
				if(gameh.palya[i][j].getSzin()=='O'){
					tabla[i][j] = new JButton(new ImageIcon((getClass().getResource("/feher_korong.jpg"))));
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
		//p�lya fel�p�t�se
		
		
		this.add(rossz_lepes);
		this.add(fekete);
		this.add(feher);
		this.add(fkt);
		this.add(fhr);
		this.setResizable(false);
		this.pack();
		System.out.println("J�t�kh�v�s el�tt �llunk");
		*/
	
	}
	

		
	
	public int getFeherSc(){
		
		return feherSc;//visszaadja a vil�gos pontsz�m�t
		
	}
	
	public int getFeketeSc(){
		
		return feketeSc;//visszaadja a vil�gos pontsz�m�t
		
	}
	
	
	
	public static void serializalas(final TOPLIST param){
		/*
		
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
		
		*/
	}
	

	public static TOPLIST deserializalas() {
		/*
		TOPLIST temp = new TOPLIST();
		
		try {
			FileInputStream f =	new FileInputStream("SERIALIZALT.dat");
			ObjectInputStream in =	new ObjectInputStream(f);
			temp = (TOPLIST)in.readObject();
			in.close();
		} catch(IOException ex) { 
		} catch(ClassNotFoundException ex) { 
		}
		//System.out.println("\n\nSerializ�lt visszat�ltve\n\n");
		return temp;
		*/
		return null;
	}
	
	
	
	public void update() throws IOException{
		
		
		/*
		
		for(int i = 1; i<9;i++){//V�gig iter�l a p�ly�n, friss�ti a megjelen�t�st.
			for(int j = 1;j<9;j++){
				if(gameh.palya[i][j].getSzin()=='-'){
					tabla[i][j].setIcon(new ImageIcon((getClass().getResource("/nincs_korong.jpg"))));
				}
				if(gameh.palya[i][j].getSzin()=='X'){
					tabla[i][j].setIcon(new ImageIcon((getClass().getResource("/fekete_korong.jpg"))));
				}
				if(gameh.palya[i][j].getSzin()=='O'){
					tabla[i][j].setIcon(new ImageIcon((getClass().getResource("/feher_korong.jpg"))));
				}
				
			}
			
			
		}
		
		//"N�v : pontsz�m" labelre
		fkt.setText("Fekete J�t�kos: "+ gameh.hanySotet());
		fhr.setText("Feh�r J�t�kos: "+ gameh.hanyVilagos());
		rossz_lepes.setText("");	
		
		*/
		
		if(gameh.hanyUres()==0 || gameh.hanySotet()==0 || gameh.hanyVilagos()==0){            //ha m�r nincs �res, vagy elfogyott az egyik j�t�kos korongja
											
			vege = true;
			//int allas = 0; // ha fekete nyert 0 ha feh�r 1 ha d�ntetlen 2
				
			//pontsz�m be�ll�t�sa
			feherSc = gameh.hanyVilagos();
			feketeSc = gameh.hanySotet();
				
			//String nyertes = "D�ntetlen";
				
			//System.out.println("A j�t�knak v�ge!");
			//System.out.println("A j�t�k v�g�n: L�p�sek sorozata:");
			//for (int i=0;i<lepesSorozat.size();i++){
			//	System.out.println(lepesSorozat.get(i));
			//}
														
			
			if(feketeSc > feherSc) {//Ha s�t�tnek t�bb pontja van.
				
				//rossz_lepes.setText(fekete.getText()+ " nyert!"); 
				//allas = 0;
				//nyertes = fekete.getText();
				//System.out.println("FEKETE NYERT: ");
				
				for (int i=0;i<lepesSorozat.size();i++){
					//System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement.incWinCount();
					elozoElement.setWinRate();
					//System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement=elozoElement.getParent();
					
				}
				
				
			}
			if(feketeSc < feherSc) {//Ha vil�gosnak t�bb pontja van.
				//rossz_lepes.setText(feher.getText()+ " nyert!");
				//allas = 1;
				//nyertes = feher.getText();
				//System.out.println("FEH�R NYERT: ");
				
				for (int i=0;i<lepesSorozat.size();i++){
					//System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement.incLoseCount();
					elozoElement.setWinRate();
					//System.out.println(elozoElement.getPosition() + " Winrate: " + elozoElement.getWinRate());
					elozoElement=elozoElement.getParent();
				}
				
			}
			if(feherSc==feketeSc) {
				//rossz_lepes.setText("D�ntetlen!");
				//allas = 2; 
				//System.out.println("D�NTETLEN");
			}//Ha egyenl� az �ll�s
			
			//System.out.println("fekete: "  + feketeSc + "feher: "+ feherSc);//teszt kimenet konzolra
			    
			/*
						
			
			for(int i = 0;i<lista.nevek_listaja.size(); i++){//tesztkimenet konzolra
				
				//System.out.println(lista.nevek_listaja.get(i).name + " : " + lista.nevek_listaja.get(i).score);
				
			}
			
			int szamolo = 0;//ha a sz�mol� 0, olyan j�t�kos nyert, aki m�g eddig nem.
			for(int i = 0;i<lista.nevek_listaja.size(); i++){
				
				if(allas == 0){
					if(lista.nevek_listaja.get(i).name.equals(fekete.getText())){ //ha m�r az "i" index� elem benne volt a toplist�ban, hozz�ad egyet a pontj�hoz
						
						lista.nevek_listaja.get(i).score = lista.nevek_listaja.get(i).score+1;
						szamolo++;
					}
					
					
				}
				if(allas == 1){
					if(lista.nevek_listaja.get(i).name == feher.getText()){	//ha m�r az "i" index� elem benne volt a toplist�ban, hozz�ad egyet a pontj�hoz
						
						lista.nevek_listaja.get(i).score = lista.nevek_listaja.get(i).score+1;
						szamolo++;
					}


					
				}
				
				if(allas == 2) szamolo++;
			}
			
			
			//System.out.println("volt hasonl�: " + szamolo);
			if(szamolo == 0){//�j bejegyz�s a toplist�ba
				
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
			
			BufferedWriter kiir = new BufferedWriter(new FileWriter("toplista.txt"));//�t�rjuk a toplist�t
			for(int i = 0;i<lista.nevek_listaja.size(); i++){
				
				String temp = lista.nevek_listaja.get(i).name + "_" + lista.nevek_listaja.get(i).score;
				kiir.write(temp);
				kiir.newLine();
				//System.out.println(lista.nevek_listaja.get(i).name + "_" + lista.nevek_listaja.get(i).score);
				
			}
			
			kiir.close();
			serializalas(lista);//serializ�ljuk a list�t.
			
			TOPLIST lol = deserializalas();//
			
			
			
			
			for(int i = 0;i<lol.nevek_listaja.size(); i++){
				
				//System.out.println(" " + lol.nevek_listaja.get(i).name + "_" + lol.nevek_listaja.get(i).score);//teszt kimenet konzolra
				
			}
			*/
		}
		
		
	}

	
	public void vissza(){
		//Visszavisz a f�men�be, ezt a framet bez�rja.
		
		GUIllermo visszamegyek = new GUIllermo();
		visszamegyek.setVisible(true);
		this.setVisible(false);
		this.dispose();
		j=howManyGames-1;
		
	}
	
	public void jatek() throws NumberFormatException, IOException{
		
		//System.out.println("Elkezd�d�tt az �j game");
		while(!vege){
			
			if(korszamlalo%2==0 && !vege){ feketeComputer();}
			else if(korszamlalo%2==1 && !vege){ feherComputer(); }
			if (skip>=2){vege=true;}
		}
		korszamlalo = 0;
		gameh = new Asztal();
		vege = false;
		feherSc=0;
		feketeSc=0;

		lepesSorozat=new ArrayList<String>();
		elozoElement=Root;	
		//System.out.println("egy jatek vege");
	}
	
}
	




