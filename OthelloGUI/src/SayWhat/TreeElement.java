package SayWhat;

public class TreeElement implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String Position;
	private int WinCount;
	private int LoseCount;
	private double WinRate;

	public TreeElement(String Pos){
		Position=Pos;
		WinCount=5;
		LoseCount=5;
	}
	
	public void incWinCount(){
		WinCount++;
	}
	public void incLoseCount(){
		LoseCount++;
	}
	public String getPosition(){
		return Position;
	}
	public double getWinRate(){
		return WinRate;
	}
	public void setWinRate(){
		if (WinCount>0 || LoseCount>0){
			WinRate=WinCount/(WinCount+LoseCount);
		}
	} 
	
	public String toString(){
		return this.getPosition();
	}
}
