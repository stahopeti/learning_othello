package teszt;

import SayWhat.Korong;

public class Asztal {
	
	public Korong[][] palya = new Korong[10][10]; //10*10-es korong 2d tömb ==> lesz egy kerete a pályának üres korongokból
	
	
	public Asztal(){
		
		for(int i=0;i<10;i++){//pálya feltöltése üres koronggokkal 
			
			for(int j=0;j<10;j++){
				Korong temp = new Korong();
				setUres(temp);
				
				palya[i][j] = temp;
			}
		}
		
		//középsõ korongok alapértelmezett beállítása
		setVilagos(palya[4][4]);
		setSotet(palya[4][5]);
		setVilagos(palya[5][5]);
		setSotet(palya[5][4]);
	}
	
	public void asztalKiir(){//asztal megjelenítése a konzolon
		System.out.print("  ");
		for(int i = 0; i<10; i++){
			System.out.print(" " + i);
		}
		
		for(int i=0; i<10;i++){
			System.out.print("\n");
			System.out.print(i + " ");
			for(int j=0;j<10;j++){
				System.out.print(" " + palya[i][j].getSzin());
				
			}
			
		}
		System.out.println();
		System.out.println("Sötétek száma: "+ hanySotet() +" Világosak száma: " + hanyVilagos());
	}
	
	
	
	public void setUres(Korong a){//aktuális korong üresbe váltása
		
		a.setSzin('-');
	}
	
	public void setSotet(Korong a){//aktuális korong feketébe váltása
		
		a.setSzin('X');
	}
	
	public void setVilagos(Korong a){//aktuális korong világosba váltása
		
		a.setSzin('O');
	}
	
	//forgató függvények
	int sotetForgat(int x, int y){//sötét lépésekor ez vizsgálja, hogy megfelelõ e a lépés
		System.out.println("Sötét jön");
		
		int szamlalo = 0;
		if(palya[x][y].getSzin()=='X' || palya[x][y].getSzin()=='O' ) return 0;
		
		
		szamlalo+=sorBAL(x,y,'X');
		szamlalo+=sorJOBB(x,y,'X');
		szamlalo+=oszlopFEL(x,y,'X');
		szamlalo+=oszlopLE(x,y,'X');
		
		
		szamlalo+=atloBALFEL(x,y,'X');
		szamlalo+=atloBALLE(x,y,'X');
		szamlalo+=atloJOBBFEL(x,y,'X');
		szamlalo+=atloJOBBLE(x,y,'X');
		
		System.out.println("Lépéscount: "+szamlalo);
		
		if(szamlalo>0) setSotet(palya[x][y]);
		return szamlalo;
		
	}
	
	
	int vilagosForgat(int x, int y){//világos lépésekor ez vizsgálja, hogy megfelelõ e a lépés
		System.out.println("Világos jön");
		
		
		int szamlalo = 0;
		
		if(palya[x][y].getSzin()=='X' || palya[x][y].getSzin()=='O' ) return 0;
		
		
		szamlalo+=sorBAL(x,y,'O');
		szamlalo+=sorJOBB(x,y,'O');
		szamlalo+=oszlopFEL(x,y,'O');
		szamlalo+=oszlopLE(x,y,'O');
		
		
		szamlalo+=atloBALFEL(x,y,'O'); 
		szamlalo+=atloBALLE(x,y,'O');
		szamlalo+=atloJOBBFEL(x,y,'O');
		szamlalo+=atloJOBBLE(x,y,'O');
		
		System.out.println("Lépéscount: "+szamlalo);
		
		if(szamlalo>0) setVilagos(palya[x][y]);
		return szamlalo;
	}
	
	
	//forgató függvények
	int  sorBAL(int x, int y, char clr){//megvizsgálja az aktuális sor bal oldalát, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		
		int szamlalo=0;
		
		for(int i = y; i>1; i--){
			
			
			if(palya[x][i-1].getSzin() == clr ){
				
				for(int j = i; j<y; j++) {
					switch(clr){
					case 'X' : { setSotet(palya[x][j]); break;}
					case 'O' : { setVilagos(palya[x][j]); break;}
					}
					
					szamlalo++;
					
				}
				break;
			}
			if(palya[x][i-1].getSzin() == '-') break;
		}
		
		
		return szamlalo;
		
	}
	
	int  sorJOBB(int x, int y, char clr){//megvizsgálja az aktuális sor jobb oldalát, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		
		int szamlalo=0;
		
		
		
		for(int i = y; i<8; i++){
			
			if(palya[x][i+1].getSzin() == clr ){
				
				for(int j = i; j>y; j--) {
					switch(clr){
					case 'X' : { setSotet(palya[x][j]); break;}
					case 'O' : { setVilagos(palya[x][j]); break;}
					}
					
					szamlalo++;
					
				}
				break;
			}
			if(palya[x][i+1].getSzin() == '-') break;
		}
		
		
		return szamlalo;
		
	}
	
	
	int  oszlopFEL(int x, int y, char clr){//megvizsgálja az aktuális oszlopot felfele, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		
		
		int szamlalo=0;
		
		for(int i = x; i>1; i--){
			
			if(palya[i-1][y].getSzin()==clr){
				for(int j = i; j<x; j++){
					switch(clr){
					case 'X' : { setSotet(palya[j][y]); break;}
					case 'O' : { setVilagos(palya[j][y]); break;}
					
					}
					
					szamlalo++;
				}
				
				break;
				
				
				
				
				
			}if(palya[i-1][y].getSzin()=='-') break;
		}
		
		return szamlalo;
	}
	
	int  oszlopLE(int x, int y, char clr){//megvizsgálja az aktuális oszlopot lefele, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		
		
		int szamlalo=0;
		
		for(int i = x; i<8; i++){
			
			if(palya[i+1][y].getSzin()==clr){
				for(int j = i; j>x; j--){
					switch(clr){
					case 'X' : { setSotet(palya[j][y]); break;}
					case 'O' : { setVilagos(palya[j][y]); break;}
					
					}
					
					szamlalo++;
				}
				break;}
			
			if(palya[i+1][y].getSzin()=='-') break;
			
			
		}
		
		
		return szamlalo;
	}
	
	//átló
	
	
	
	
	
	int atloBALFEL(int x, int y, char clr){//megvizsgálja az aktuális pont balfelsõ átlóját, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		int szamlalo = 0;
		
		for(int i = x, j = y; i>1 || j>1; i--, j--){
			
			if(palya[i-1][j-1].getSzin()==clr){
				for(int k = i, l=j; k<x || l<y; k++, l++){
					switch(clr){
					case 'X' : { setSotet(palya[k][l]); break;}
					case 'O' : { setVilagos(palya[k][l]); break;}
					
					}
					
					szamlalo++;
				}
				break;
				
			}
			if(palya[i-1][j-1].getSzin()=='-') break;
		}
		return szamlalo;
	}
	
	int atloJOBBFEL(int x, int y, char clr){//megvizsgálja az aktuális pont jobbfelsõ átlóját, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		int szamlalo = 0;
		
		for(int i = x, j = y; i>1 || j<8; i--, j++){
			
			if(palya[i-1][j+1].getSzin()==clr){
				for(int k = i, l=j; k<x || l>y; k++, l--){
					switch(clr){
					case 'X' : { setSotet(palya[k][l]); break;}
					case 'O' : { setVilagos(palya[k][l]); break;}
					
					}
					
					szamlalo++;
				}
				break;
				
			}
			if(palya[i-1][j+1].getSzin()=='-') break;
		}
		return szamlalo;
		
	}
	
	int atloBALLE(int x, int y, char clr){//megvizsgálja az aktuális pont balalsó átlóját, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		int szamlalo = 0;
		
		for(int i = x, j = y; i<8 || j>1; i++, j--){
			
			if(palya[i+1][j-1].getSzin()==clr){
				for(int k = i, l=j; k>x || l<y; k--, l++){
					switch(clr){
					case 'X' : { setSotet(palya[k][l]); break;}
					case 'O' : { setVilagos(palya[k][l]); break;}
					
					}
					
					szamlalo++;
				}
				break;
				
			}
			if(palya[i+1][j-1].getSzin()=='-') break;
		}
		return szamlalo;
	}
	
	int atloJOBBLE(int x, int y, char clr){	//megvizsgálja az aktuális pont jobbalsó átlóját, hogy lehet-e ide rakni. Ha igen, átforgatja a korongokat.
		
		int szamlalo = 0;
		
		for(int i = x, j = y; i<8|| j<8; i++, j++){
			
			if(palya[i+1][j+1].getSzin()==clr){
				for(int k = i, l=j; k>x|| l>y; k--, l--){
					switch(clr){
					case 'X' : { setSotet(palya[k][l]); break;}
					case 'O' : { setVilagos(palya[k][l]); break;}
					
					}
					
					szamlalo++;
				}
				break;
				
			}
			if(palya[i+1][j+1].getSzin()=='-') break;
		}
		
		
		return szamlalo;
		
	}
	
	//tábla vizsgálata
	int hanyUres(){//végigiterál a "pálya" tömbön, megnézi hány üres van
		
		int szamlalo=0;
		
		for(int i = 1;i<9;i++){
			for(int j = 1; j<9; j++){
				if(palya[i][j].getSzin()=='-') szamlalo++;
				
			}
		}
		return szamlalo;
	}
	
	
	int hanySotet(){//végigiterál a "pálya" tömbön, megnézi hány sötét van
		
		int szamlalo=0;
		
		for(int i = 1;i<9;i++){
			for(int j = 1; j<9; j++){
				if(palya[i][j].getSzin()=='X') szamlalo++;
				
			}
		}
		return szamlalo;
	}
	
	
	int hanyVilagos(){//végigiterál a "pálya" tömbön, megnézi hány világos van
		
		int szamlalo=0;
		
		for(int i = 1;i<9;i++){
			for(int j = 1; j<9; j++){
				if(palya[i][j].getSzin()=='O') szamlalo++;
				
			}
		}
		return szamlalo;
	}
	
	
}
