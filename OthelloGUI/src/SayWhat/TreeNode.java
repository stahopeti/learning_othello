package SayWhat;

import java.io.Serializable;
public class TreeNode implements Serializable
{
//private static final long serialVersionUID = 1L;
  private TreeNode parent;
  
  /**
   * An array of all this node's child nodes.  The array will always
   * exist (i.e. never <code>null</code>) and be of length zero if this is
   * a leaf node.
   * <p>
   * This is an array instead of a <code>Vector</code> to favor speed of
   * accessing the children.  The array takes longer on adds because
   * of array copying and management.  However to get the array it
   * can just be returned instead of creating a new array from the
   * <code>Vector</code> each time.  The tree is used frequently enough for
   * reading course elements that this difference makes a small impact
   * on rendering.
   */
  	private TreeNode[] children = new TreeNode[0];
  
  	//private String Position;
  	
  	private char Posx;
  	private char Posy;
	private short WinCount;
	private short LoseCount;
	
	public void incWinCount(){
		if (WinCount>30000){
			WinCount/=2;
			LoseCount/=2;
		}
		WinCount++;
	}
	public void incLoseCount(){
		if (LoseCount>30000){
			WinCount/=2;
			LoseCount/=2;
		}
		LoseCount++;
	}
	public String getPosition(){
		return Character.toString(Posx)+Character.toString(Posy);
	}
	public double getWinRate(){
		return (double)WinCount/(WinCount+LoseCount);
	}
	/*
	public void setWinRate(){
		double d=(double)WinCount/(WinCount+LoseCount);
		WinRate=(float)d;
		if (WinCount>30000 || LoseCount>30000){
			WinCount/=2;
			LoseCount/=2;
		}
	} 
	*/
	
	public String toString(){
		return this.getPosition();
	}
  
 
  public TreeNode (String Pos)
  {
		Posx=Pos.charAt(0);
		Posy=Pos.charAt(1);
		WinCount=5;
		LoseCount=5;
		//setWinRate();
  }


  /**
   * Adds the <code>child</code> node to this container making this its parent.
   * 
   * @param child is the node to add to the tree as a child of <code>this</code>
   * 
   * @param index is the position within the children list to add the
   *  child.  It must be between 0 (the first child) and the
   *  total number of current children (the last child).  If it is
   *  negative the child will become the last child.
   */
  public void add (TreeNode child, int index)
  {
    // Add the child to the list of children.
    if ( index < 0 || index == children.length )  // then append
    {
      TreeNode[] newChildren = new TreeNode[ children.length + 1 ];
      System.arraycopy( children, 0, newChildren, 0, children.length );
      newChildren[children.length] = child;
      children = newChildren;
    }
    else if ( index > children.length )
    {
      throw new IllegalArgumentException("Cannot add child to index " + index + ".  There are only " + children.length + " children.");
    }
    else  // insert
    {
      TreeNode[] newChildren = new TreeNode[ children.length + 1 ];
      if ( index > 0 )
      {
        System.arraycopy( children, 0, newChildren, 0, index );
      }
      newChildren[index] = child;
      System.arraycopy( children, index, newChildren, index + 1, children.length - index );
      children = newChildren;
    }
    
    // Set the parent of the child.
    child.parent = this;
  }
  
  /**
   * Adds the <code>child</code> node to this container making this its parent.
   * The child is appended to the list of children as the last child.
   */
  public void add (TreeNode child)
  {
    add( child, -1 );
  }
  
  
  public TreeNode getParent ()
  {
    return parent;
  }

  public TreeNode[] getChildren ()
  {
    return children;
  }




}
