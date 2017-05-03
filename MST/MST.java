package apps;

import structures.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import apps.PartialTreeList.Node;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	    //make PartialTree List
		//make new partial tree for every vertex in the graph
		//make a heap for the tree
		PartialTreeList result=new PartialTreeList();
		Vertex[] ver=graph.vertices;
		for(int i=0; i<ver.length; i++)
		{
			PartialTree T=new PartialTree(ver[i]);
			//make new ARC and put it in the heap
			//Each Arc is the connection from node to neighbor with weight
			for(Vertex.Neighbor next=ver[i].neighbors; next!=null; next=next.next)
			{
				int w=next.weight;
				Vertex vert=next.vertex;
				PartialTree.Arc A=new PartialTree.Arc(ver[i], vert, w);
				T.getArcs().insert(A);
			}
			result.append(T);
			
		}
		  
		return result;
	}
	 
	    

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1){
			//Remove the first partial tree PTX from L. Let PQX be PTX's priority queue.
			PartialTree PTX = ptlist.remove();
			System.out.println(PTX.toString());
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs();
			//Remove the highest-priority arc from PQX. Say this arc is a. 
			//Let v1 and v2 be the two vertices connected by a, where v1 belongs to PTX.
			PartialTree.Arc a=PQX.getMin();
			Vertex v1=PTX.getRoot();
			Vertex v2=a.v2;
			//If v2 also belongs to PTX, go back to Step 4 and pick the next highest priority arc, 
			//otherwise continue to the next step
			while(!PQX.isEmpty() && v2.getRoot().equals(v1))
			{   //System.out.println("loooping");
				a=PQX.deleteMin();
				v2=a.v2;
			}
			//result.add(a);
			//Find the partial tree PTY to which v2 belongs.
			//Remove PTY from the partial tree list L.
			//Let PQY be PTY's priority queue.
			
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			MinHeap<PartialTree.Arc> PQY = PTY.getArcs();
			
			//Combine PTX and PTY. 
			//This includes merging the priority queues PQX and PQY into a single priority queue.
			//Append the resulting tree to the end of L 
			
			PTY.getRoot().parent=PTX.getRoot();
			PQY.merge(PQX);
			try
			{
				PQX.getMin();
			}catch(Exception e)
			{       //System.out.println("caught Exception");
		     		break;
			}
			PTX.merge(PTY);
			ptlist.append(PTX);
			result.add(a);
			
			
		}
		
		//System.out.println("returning/////");
		return result;
	}
	
  	
	public static void main(String[] args) throws IOException{
		Graph g = new Graph("graph3.txt");
		PartialTreeList t=MST.initialize(g);
		Iterator<PartialTree> i=t.iterator();
		/*while(i.hasNext())
		{
			System.out.println(i.next().toString());
		}*/
		ArrayList<PartialTree.Arc> a = MST.execute(t);
		for(PartialTree.Arc b : a)
		{
			System.out.println(b.toString());
		}
		//ArrayList<PartialTree.Arc> a = MST.execute(t);
		
	}  
	
}
