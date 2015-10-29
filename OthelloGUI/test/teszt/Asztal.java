package teszt;

import SayWhat.Korong;

public class Asztal {
	
	public Korong[][] palya = new Korong[10][10]; //10*10-es korong 2d t�mb ==> lesz egy kerete a p�ly�nak �res korongokb�l
	
	
	public Asztal(){
		
		for(int i=0;i<10;i++){//p�lya felt�lt�se �res koronggokkal 
			
			for(int j=0;j<10;j++){
				Korong temp = new Korong();
				setUres(temp);
				
				palya[i][j] = temp;
			}
		}
		
		//k�z�ps� korongok alap�rtelmezett be�ll�t�sa
		setVilagos(palya[4][4]);
		setSotet(palya[4][5]);
		setVilagos(palya[5][5]);
		setSotet(palya[5][4]);
	}
	
	public void asztalKiir(){//asztal megjelen�t�se a konzolon
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
		System.out.println("S�t�tek sz�ma: "+ hanySotet() +" Vil�gosak sz�ma: " + hanyVilagos());
	}
	
	
	
	public void setUres(Korong a){//aktu�lis korong �resbe v�lt�sa
		
		a.setSzin('-');
	}
	
	public void setSotet(Korong a){//aktu�lis korong feket�be v�lt�sa
		
		a.setSzin('X');
	}
	
	public void setVilagos(Korong a){//aktu�lis korong vil�gosba v�lt�sa
		
		a.setSzin('O');
	}
	
	//forgat� f�ggv�nyek
	int sotetForgat(int x, int y){//s�t�t l�p�sekor ez vizsg�lja, hogy megfelel� e a l�p�s
		System.out.println("S�t�t j�n");
		
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
		
		System.out.println("L�p�scount: "+szamlalo);
		
		if(szamlalo>0) setSotet(palya[x][y]);
		return szamlalo;
		
	}
	
	
	int vilagosForgat(int x, int y){//vil�gos l�p�sekor ez vizsg�lja, hogy megfelel� e a l�p�s
		System.out.println("Vil�gos j�n");
		
		
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
		
		System.out.println("L�p�scount: "+szamlalo);
		
		if(szamlalo>0) setVilagos(palya[x][y]);
		return szamlalo;
	}
	
	
	//forgat� f�ggv�nyek
	int  sorBAL(int x, int y, char clr){//megvizsg�lja az aktu�lis sor bal oldal�t, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
		
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
	
	int  sorJOBB(int x, int y, char clr){//megvizsg�lja az aktu�lis sor jobb oldal�t, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
		
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
	
	
	int  oszlopFEL(int x, int y, char clr){//megvizsg�lja az aktu�lis oszlopot felfele, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
		
		
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
	
	int  oszlopLE(int x, int y, char clr){//megvizsg�lja az aktu�lis oszlopot lefele, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
		
		
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
	
	//�tl�
	
	
	
	
	
	int atloBALFEL(int x, int y, char clr){//megvizsg�lja az aktu�lis pont balfels� �tl�j�t, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
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
	
	int atloJOBBFEL(int x, int y, char clr){//megvizsg�lja az aktu�lis pont jobbfels� �tl�j�t, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
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
	
	int atloBALLE(int x, int y, char clr){//megvizsg�lja az aktu�lis pont balals� �tl�j�t, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
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
	
	int atloJOBBLE(int x, int y, char clr){	//megvizsg�lja az aktu�lis pont jobbals� �tl�j�t, hogy lehet-e ide rakni. Ha igen, �tforgatja a korongokat.
		
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
	
	//t�bla vizsg�lata
	int hanyUres(){//v�gigiter�l a "p�lya" t�mb�n, megn�zi h�ny �res van
		
		int szamlalo=0;
		
		for(int i = 1;i<9;i++){
			for(int j = 1; j<9; j++){
				if(palya[i][j].getSzin()=='-') szamlalo++;
				
			}
		}
		return szamlalo;
	}
	
	
	int hanySotet(){//v�gigiter�l a "p�lya" t�mb�n, megn�zi h�ny s�t�t van
		
		int szamlalo=0;
		
		for(int i = 1;i<9;i++){
			for(int j = 1; j<9; j++){
				if(palya[i][j].getSzin()=='X') szamlalo++;
				
			}
		}
		return szamlalo;
	}
	
	
	int hanyVilagos(){//v�gigiter�l a "p�lya" t�mb�n, megn�zi h�ny vil�gos van
		
		int szamlalo=0;
		
		for(int i = 1;i<9;i++){
			for(int j = 1; j<9; j++){
				if(palya[i][j].getSzin()=='O') szamlalo++;
				
			}
		}
		return szamlalo;
	}
	
	
}
