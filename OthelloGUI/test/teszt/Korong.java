package teszt;

public class Korong {
	
	private char szin; //- "üres" X "fekete" O "fehér"
	
	public Korong(){szin = '-';}//korong alapértelmezésben '-' üres 
	public char getSzin(){		//vissszaadja a korong "színét"
		
		return szin;
	}
	
	public void setSzin(char param){//megváltoztatja a korong "színét" paraméter színûre
		
		szin=param;
	}
	
	
	
}
