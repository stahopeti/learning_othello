package teszt;

public class Korong {
	
	private char szin; //- "�res" X "fekete" O "feh�r"
	
	public Korong(){szin = '-';}//korong alap�rtelmez�sben '-' �res 
	public char getSzin(){		//vissszaadja a korong "sz�n�t"
		
		return szin;
	}
	
	public void setSzin(char param){//megv�ltoztatja a korong "sz�n�t" param�ter sz�n�re
		
		szin=param;
	}
	
	
	
}
