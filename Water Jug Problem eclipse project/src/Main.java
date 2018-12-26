import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

//keep splitting until there are no more unique states then keep filling by popping the stack to old values

public class Main {
	
	//creates global variables
	static ArrayList<State> nodes = new ArrayList<State>(); //creates an array of states that is going to be used as memory
	static Stack<State> stack = new Stack<State>(); //creates a stack to store the states that are going to be searched
	static State currentState; //the current state that the algorithm is traversing 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Enter an integer limit for jug A: "); //gets 3 inputs from the user for each jug
		int aLimit = reader.nextInt();
		
		System.out.println("Enter an integer limit for jug B: ");
		int bLimit = reader.nextInt();
		
		System.out.println("Enter an integer limit for jug C: ");
		int cLimit = reader.nextInt();
		
		reader.close();
				
		State startState = new State(0,0,0); // creates the starting node/state 
		
		nodes.add(startState); //adds the start node to the stack and the "nodes" list
		stack.push(startState); //adds the start node to the stack
		
		State stateOne = new State(aLimit,0,0); //the algorithm starts by filling jug A
		
		nodes.add(stateOne); //the state is added to the "nodes" list and the stack
		stack.push(stateOne);
		
		while(!stack.isEmpty()) { //a while loop that searches for unique neighbours 
			currentState = (State) stack.peek(); //gets the most recent state from the stack by popping 
			
			//pour water from jug A into B if possible and if it leads to a unique state
			if(pour("a","b",aLimit,bLimit,cLimit)) continue;
			
			//pour water from jug A into C if it is possible and if it leads to a unique state
			if(pour("a","c",aLimit,bLimit,cLimit)) continue; 
				
			
			//pour water from jug B into A if it is possible and if it leads to a unique state
			if(pour("b","a",aLimit,bLimit,cLimit)) continue;
			
			//pour water from jug B into C if it is possible and if it leads to a unique state
			if(pour("b","c",aLimit,bLimit,cLimit)) continue;
			
			//pour water from jug C into A if it is possible and if it leads to a unique state
			if(pour("c","a",aLimit,bLimit,cLimit)) continue;
			
			//pour water from jug C into B if it is possible and if it leads to a unique state
			if(pour("c","b",aLimit,bLimit,cLimit)) continue;
			
			//searches which jug to fill if it leads to a unique state. The search starts from jug A
			if(fill(aLimit, bLimit, cLimit)) continue;
			
			//searches which jug to empty if it leads to a unique state. The search starts from jug A
			if(empty(aLimit, bLimit, cLimit)) continue;
			
			stack.pop(); //popped once no neighbours are available
			
		}
		
		System.out.println(nodes.toString());
		
	}
	
	//the "empty" function
	public static boolean empty(int aLimit, int bLimit, int cLimit) { //takes an input of the jug limits, then checks if emptying a jug would create a unique state.
		int a = currentState.getA();
		int b = currentState.getB();
		int c = currentState.getC();
		
		if(addNewState(0,b,c)) {
			return true;
		}
		if(addNewState(a,0,c)) {
			return true;
		}
		if(addNewState(a,b,0)) {
			return true;
		}
		
		return false; //if no jug can be emptied to create a new state, the function returns false and the while loop goes to the next state in the stack
	}
	
	//the function checks if a jug can be filled to create a new state. If it can then the new state is created and added to the stack where that state is traversed 
	public static boolean fill(int aLimit, int bLimit, int cLimit) {
		int a = currentState.getA();
		int b = currentState.getB();
		int c = currentState.getC();
		
		if(addNewState(aLimit,b,c)) {
			return true;
		}
		
		if(addNewState(a,bLimit,c)) {
			return true;
		}
		if(addNewState(a,b,cLimit)) {
			return true;
		}
		
		return false; //if no new state can be created by filling the jugs, the program returns false so the algorithm can look if emptying jugs will lead to a unique state
		
	}
	
	//the pour function takes the inputs of the two jugs water is flowing from, and the jug limits. "jugOne" is where water is leaving and "jugTwo" is where the water is received 
	public static boolean pour(String jugOne, String jugTwo, int aLimit, int bLimit, int cLimit) {
		int a = currentState.getA(); //gets the current jug values
		int b = currentState.getB();
		int c = currentState.getC();
		
		
		//creates a switch statement to see where the water should be poured
		switch(jugOne) {
			case "a": //the case shows where the water is being poured out from
				if(a > 0 && jugTwo == "b") { //if there is water to be poured, and if the receiver jug is "B"
					if(a+b <= bLimit) { //if the total value of the jugs is less than or equal to the limit of the receiver jug
						return addNewState(0,a+b,c); //if pouring water from A to B leads to a unique state, then it is added to the stack and to the memory be using the "addNewState" function
							
					}else if(b < bLimit) { //if jug B is not at its maximum limit
						int newA, newB;
						int freeSpace = bLimit - b; //calculates the free space in jug B
						if(a >= freeSpace) { //if jug A can fill the free space in jug B
							newA = a - freeSpace;
							newB = bLimit;
							return addNewState(newA, newB, c);
							
							
						}else { //if not then all the remaining water from A is given to B
							newA = 0;
							newB = a + b;
							return addNewState(newA, newB, c);
							
						}					
					}
				
					return false;
				}
				
				if(a>0 && jugTwo == "c") { //if there is water to pour from jug A and if the receiver is jug B
					if(a+c <= cLimit) { //if the water in A and the water in C is in total less than the jug C limit
						return addNewState(0,b,a+c); //then a new state is created and added to the stack and memory if the state is unique
						
						
					}else if(c < cLimit) { //if there is free space is jug C
						int newA, newC;
						int freeSpace = cLimit - c; //free space in jug C is calculated
						if(a >= freeSpace) { //if jug A can fill in the free space
							newA = a-freeSpace;
							newC = cLimit;
							return addNewState(newA,b,newC);		
						}else { //if the water in A can't fill the free space then the remaining water in A is poured into C
							newA = 0;
							newC = a+c;
							return addNewState(newA,b,newC); //creates a new state if it is unique					
						}
					}
					
					return false;
					
				}
				break;
				
			case "b": //if "jugOne" is "b" then water is being poured from B into another jug
				if(b > 0 && jugTwo == "a") { //pour water from B into A if there is water in B and if "jugTwo" is A
					if(b+a <= aLimit) { //if the total water in both jugs is less than the limit of jug A
						return addNewState(a+b,0,c); //then look for a unique state where all the water from B has been poured to A
						
					}else if(a < aLimit) { //if there is free space in jug A
						int newA, newB;
						int freeSpace = aLimit - a; //find the free space in jug A
						if(b >= freeSpace) { //if the water in jug B can fill the space in A
							newA = aLimit;
							newB = b - freeSpace;
							return addNewState(newA,newB,c); //looks for a unique state after the free space in A has been filled
							
						}else { //program comes here if the water in B is less than the free space so all remaining water from B is poured into A
							newA = a+b;
							newB = 0;
							return addNewState(newA,newB,c); //looks for a new state after the water in B has been poured into A
						}
										
					}
					return false;
					
				}
				if(b>0 && jugTwo == "c") { //pour water from B into C
					if(b+c <= cLimit) { //if the total water from both jugs is less than the limit
						return addNewState(a,0,b+c); //looks for a new state where all the water has been poured into jug C
						
					}else if(c < cLimit) { //if all the water from B would exceed the limit of C if added then fill up the free space in jug C
						int newB, newC;
						int freeSpace = cLimit - c;
						if(b >= freeSpace) {
							newB = b-freeSpace;
							newC = cLimit;
							return addNewState(a, newB, newC);
						}else {
							newB = 0;
							newC = b+c;
							return addNewState(a, newB, newC);
						}
						
					}
					return false;
					
				}
				break;
			case "c":
				if(c > 0 && jugTwo == "a") { //jug c into a
					if(c+a <= aLimit) {
						return addNewState(a+c,b,0);
						
					}else if(a < aLimit) {
						int newA, newC;
						int freeSpace = aLimit - a;
						if(c >= freeSpace) {
							newA = aLimit;
							newC = c - freeSpace;
							return addNewState(newA, b, newC);
						}else {
							newA = a+c;
							newC = 0;
							return addNewState(newA, b, newC);
						}
						
						
					}
					
					return false;
					
				}
				if(c > 0 && jugTwo == "b") { //jug c into b
					if(c+b <= bLimit) {
						return addNewState(a,b+c,0);
						
					}else if(b < bLimit) {
						int newB, newC;
						int freeSpace = bLimit - b;
						if(c >= freeSpace) {
							newB = bLimit;
							newC = c - freeSpace;
							return addNewState(a, newB, newC);
						}else {
							newB = b+c;
							newC = 0;
							return addNewState(a, newB, newC);
						}
						
						
					}
				}
				
				return false;
				
				
				
		}
		return false;
		
		
	}
	
	//checks if the state given is unique
	public static boolean addNewState(int a, int b, int c) {
		State newState = new State(a, b, c);
		
		if(nodes.contains(newState) == false) { //if the state is not in the "nodes" list it means that it is uniuqe therefore it is added to the "nodes" list and added to the stack
			nodes.add(newState);
			stack.push(newState);
			return true; //returns true so that the algorithm knows that a new state has been added and to traverse the new state
			
		}else { //if the state is not new, then this function returns false so the program keeps searching for unique neighbours with the current state
			return false;
		}
		
		
	}
	
	
}
