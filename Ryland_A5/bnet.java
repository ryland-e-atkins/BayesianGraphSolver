
import java.util.ArrayList;

public class bnet
{	
	
	public static Node[] nodes;
	
	public static double P(String[] goals, String[] givens)
	{
		/*	P(goals, given)
		 * 		Prob = 1.0
		 * 		for goal in goals
		 * 			Prob = Prob * getP(goal, given)
		 * 		return Prob
		 */
		double Prob = 1.0;
		for(String goal : goals)
		{
			Prob = Prob * getP(goal,givens);
		}
		return(Prob);
	}
	
	private static double getP(String goal, String[] givens)
	{
		/*	getP(goal, given)
		 * 		if all of goal.parents in given
		 * 			return goal.table(parents)
		 * 		else 
		 * 			prob = goal.table(parents)
		 * 			for parent in goal.parents
		 * 				prob = prob * getP(parent, given)
		 * 			return prob
		 */
		
		double prob = 1.0;
		
		//Find node that corresponds to goal
		Node goalNode = new Node();
		for(Node node : nodes)
		{
			//For goal
			if(goal.charAt(0)==node.getName().charAt(0) && goal.charAt(1)==node.getName().charAt(1))
			{
				goalNode = node;
			}
		}
		
		
		//If all its parents are in given
		int count = 0;
		for(Node parent : goalNode.getParents())
		{
			for(String given : givens)
			{
				if(given.charAt(0)==parent.getName().charAt(0) && given.charAt(1)==parent.getName().charAt(1))
				{
					count++;
				}
			}
		}
		//Final Check
		if(count == goalNode.getParents().length)
		{
			//return probability corresponding to parents
			
			//Test each row until correct one is found
			for(int i=0;i<goalNode.getTable().length;i++)
			{
				int correct = 0;
				//Get index of parent in goalNode, use that as column index
				for(int j = 0;j<goalNode.getParents().length;j++)
				{
					//get the correct given string
					for(String given : givens)
					{
						//Comparing to parents again
						if(given.charAt(0)==goalNode.getParents()[j].getName().charAt(0) && given.charAt(1)==goalNode.getParents()[j].getName().charAt(1))
						{
							//Tally it up
							if(goalNode.getTable()[i][j]==1.0 && given.charAt(2)=='t')
							{
								correct++;
							}
							if(goalNode.getTable()[i][j]==0.0 && given.charAt(2)=='f')
							{
								correct++;
							}
						}
					}
				}
				//Final check
				if(correct == goalNode.getParents().length)
				{
					if(goal.charAt(2)=='t')
					{
						prob = goalNode.getTable()[i][goalNode.getTable()[i].length-2];
						break;
					}
					else
					{
						prob = goalNode.getTable()[i][goalNode.getTable()[i].length-1];
						break;
					}
				}	
			}
			return(prob);
		}
		//If all its parents are NOT in given
		else
		{
			//Take goal node true probability
			prob = prob * goalNode.getTable()[0][goalNode.getTable()[0].length-2];
			//Find probability of each parent and multiply
			for(Node parent : goalNode.getParents())
			{
				prob = prob * getP(parent.getName()+"t",givens);
			}
			return(prob);
		}
	}
		
	
	
	public static void main(String[] args)
	{
		//Create nodes and tables of probabilities for each of the nodes.
		
		double[][] SaTab = {{0.9,0.1}};
		
		double[][] SbTab = {{0.3,0.7}};
		
		double[][] HaTab = {{1.0,1.0,0.2,0.8},
					 		{1.0,0.0,0.003,0.997},
					 		{0.0,1.0,0.85,0.15},
					 		{0.0,0.0,0.1,0.9}};
		
		double[][] HbTab = {{1.0,1.0,0.3,0.7},
							{1.0,0.0,0.002,0.998},
							{0.0,1.0,0.75,0.25},
							{0.0,0.0,0.2,0.8}};
		
		double[][] WaTab = {{1.0,0.35,0.65},
					 		{0.0,0.01,0.99}};
		
		double[][] WbTab = {{1.0,0.3,0.7},
					 		{0.0,0.02,0.98}};
		
		Node Sa = new Node("Sa",SaTab,new Node[] {});
		Node Sb = new Node("Sb",SbTab,new Node[] {});
		Node Ha = new Node("Ha",HaTab,new Node[] {});
		Node Hb = new Node("Hb",HbTab,new Node[] {});
		Node Wa = new Node("Wa",WaTab,new Node[] {});
		Node Wb = new Node("Wb",WbTab,new Node[] {});
		
		Ha.setParents(new Node[] {Sa,Hb});
		Hb.setParents(new Node[] {Sb,Ha});
		Wa.setParents(new Node[] {Ha});
		Wb.setParents(new Node[] {Hb});
		
		nodes = new Node[] {Sa,Sb,Ha,Hb,Wa,Wb};
		
		String[] goals = {};
		String[] givns = {};
		
		if(args.length > 0)
		{
			//Break down args[] into goals and givens of type String 
			String goal = "";
			int i = 0;
			for(i=0;i<args.length;i++)
			{
				if(args[i].compareTo("given")!=0)
				{
					goal = goal + args[i] + " ";
				}
				else
				{
					i++;
					break;
				}
			}
			String given = "";
			while(i<args.length)
			{
				given = given + args[i] + " ";
				i++;
			}
				
			goals = goal.split(" ");
			givns = given.split(" ");
		}
		
		double prob = P(goals,givns);	
		
		System.out.format("The conditional probability is %1.2f",(prob*100));
		System.out.println("%");
		
	}
}