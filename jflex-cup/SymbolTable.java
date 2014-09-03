import java.util.LinkedList;
import java.util.List;

public class SymbolTable{
	
	private class Descriptor{
		private String name;
		private String type;
		
		public String getName(){
			return name;
		}
		
	}
	
	private List<List<Descriptor>> levels;
	
	private int amountLevels;
	
	public SymbolTable(){
		// Constructor
		levels = new LinkedList<>();
		amountLevels = 0;
	}
	
	public void insertNewBlock(){
		List level = new LinkedList<Descriptor>();
		levels.add(level);
		amountLevels++;
	}
	
	public void closeBlock(){
		amountLevels--;
		levels.remove(amountLevels);
	}
	
	public void insertNewDescriptor(Descriptor descriptor){
		// Level must exists.
		// The descriptor's name must be unique.
		levels.get(amountLevels).add(descriptor);
	}
	
	/**
	 * Search a descriptor in the levels.
	 * @param id 
	 * @return The Descriptor found, or otherwise null.
	 */
	public Descriptor search(String id){
		for (int i = amountLevels; i >= 0; i++)
		{
			System.out.println("Level " + i + ": ");
			for (int j = 0; j < levels.get(i).size(); j++)
			{
				if (levels.get(i).get(j).getName() == id) {
					return levels.get(i).get(j);
				}
			}
		}
		return null;
	}
	
	public void showTable(){
		System.out.println();
		for (int i = 0; i < amountLevels; i++)
		{
			System.out.println("Level " + i + ": ");
			for (int j = 0; j < levels.get(i).size(); j++)
			{
				System.out.print(levels.get(i).get(j) + ", ");
			}
		}
		
	}
	
}