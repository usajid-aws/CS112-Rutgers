package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }
  
    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    
   
    public PartialTree remove() 
    throws NoSuchElementException {
   		//CLL of Nodes
        //Each Node has a tree as data
    	//if root is null, throw exception
    	//if only one element in the tree, return roots data, tree will be null
    	//else, same as removing first element in CLL
    	PartialTree temp;
    	if(rear==null)
    	{
    		throw new NoSuchElementException("tree is null");
    	}
    	else if(this.size==1)
    	{
    		temp=rear.tree;
    		rear=null;
    		size=0;
       	}
    	//remove front of CLL
    	else
    	{
    		Node nextTemp=rear.next;
    		rear.next=rear.next.next;
    		size--;
    		return nextTemp.tree;
    	}
      	return temp;
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    	//CLL of PartialTree
    	//go through CLL
    	//if the root of the next nodes tree is the vertex, remove the tree, and returns it 
    	//if gotten back to rear, throw exception
    	if(size==0)
    	{
    		throw new NoSuchElementException("tree not found");
    	}
    	if(size==1)  //only one element in the CLL
    	{
    		if(rear.tree.getRoot().equals(vertex.getRoot()))
    		{
    			PartialTree temp=rear.tree;
    			rear=null;
    			return temp;
    		}
    		else
    		{
    			throw new NoSuchElementException("tree not found");
    		}
    	}
    	Node ptr=rear;
    	while(ptr.next!=rear)
    	{   
    		if(ptr.next.tree.getRoot().equals(vertex.getRoot()))
    		{  
    			PartialTree temp=ptr.next.tree;
    			ptr.next=ptr.next.next;
    			size--;
    			return temp;
    		}
    		else
    			ptr=ptr.next;
    	}
    	//after breaking out, ptr next will be rear
    	//check rear, if same vertex, remove rear and update rear
    	// if not same vertex, throw Exception
       	if(rear.tree.getRoot().equals(vertex.getRoot()))
    	{
    		PartialTree temp=ptr.next.tree;
    		ptr.next=ptr.next.next;
    		rear=ptr;
    		size--;
    		return temp;
    	}
    	else
    	{
    		throw new NoSuchElementException("Tree not found");
    	}
    		//return null;
     }
   
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}


