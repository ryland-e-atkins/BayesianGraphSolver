import java.util.Arrays;



public class Node
{
	private String name;
	private double[][] table;
	private Node[] parents;
	private String[] children;
	
	
	Node()
	{
		
	}
	
	
	Node(String name, double[][] table)
	{
		this.name = name;
		this.table = table;
	}
	
	
	Node(String name, double[][] table, Node[] parents)
	{
		this.name = name;
		this.table = table;
		this.parents = parents;
		this.children = children;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double[][] getTable() {
		return table;
	}


	public void setTable(double[][] table) {
		this.table = table;
	}


	public Node[] getParents() {
		return parents;
	}


	public void setParents(Node[] parents) {
		this.parents = parents;
	}


	public String[] getChildren() {
		return children;
	}


	public void setChildren(String[] children) {
		this.children = children;
	}


	public String toString()
	{
		return(this.name + " " + Arrays.deepToString(this.table));
	}
}