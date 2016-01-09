package diGraph;

	/*
	 * Tim Appelt u. Kai Hofmann
	 * Gruppe 04
	 * Luft und Raufahrtinformatik
	 * 5. ADS Blatt
	 */

public class List {
	
	private ListItem head;
	private ListItem tail;
	public int size = 0; 
	
	//Constructor
	public List(){
		head = tail = null;
	}
	
	//Insert at Head
	public ListItem insert(DiGraphNode key){
		//New Listelement 
		size++;
		ListItem element = new ListItem();
		element.key = key;
		
		//The previous head becomes the "follower" of the new one; 
		element.next = head;
		element.prev = null;
		
		if(head != null){
			head.prev = element;
		}else{
			//If it is an empty List the new Element is also the tail
			tail = element;
		}
		
		//New Elements become always the head in our implementation
		head = element;
		return element;
	}
	
	//Return Head
	public ListItem getHead(){
		return head;
	}
	
	//Return Tail
	public ListItem getTail(){
		return tail;
	}
	
	//Delete Tail
	public void deleteLast(){
		if(tail.prev==null){
			size = 0;
			//If we delete the last Element then we have a new List
			head = tail = null;
		}else{
			size--;
			//Otherwise we delete the tail and previous is the new tail
			ListItem element = tail.prev;
			tail = element;
		}
		
	}
	/**
	 * creates an empty List , e.g. deletes evrything
	 */
	public void empty() {
		head = tail = null;
		
	}

	
}
