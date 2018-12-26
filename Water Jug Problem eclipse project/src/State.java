import java.util.Objects;

//creates a class states can be stored
public class State {
	//the state holds three attributes a,b and c which hold the current value of the water in each jug
	int a;
	int b;
	int c;

	
	public State(int a,int b,int c) {
	    super();
	    this.a = a;
	    this.b = b;
	    this.c = c;
	}
	
	public int getA() { //returns the amount of water in jug A
		return a;
	}
	
	public int getB() { //returns the amount of water in jug B
		return b;
	}
	
	public int getC() { //returns the amount of water in jug C
		return c;
	}
	
	public void fillA(int aLimit) { //fills jug A to the limit
		a = aLimit;
	}
	
	public void fillB(int bLimit) { //fills jug B to the limit
		b = bLimit;
	}
	
	public void fillC(int cLimit) { //fills jug C to the limit
		c = cLimit;
	}
	
	@Override
	public String toString() { //alters the toString() method because it was returning the memory address of the state rather than the state itself
		return ("("+this.getA()+", "+this.getB()+", "+this.getC()+")");
	}
	
	@Override
	public boolean equals(Object o) { //alters the equals method in java because when the .contains() method is used, it used this method, and this alteration stops the method from always returning false 
	    if (this == o) return true;
	    if (!(o instanceof State)) return false;
	    State state = (State) o;
	    return a == state.a &&
	            b == state.b &&
	            c == state.c;
	}

	@Override
	public int hashCode() { //also overrides the method so that the .contains() method does not always return false
	    return Objects.hash(a, b, c);
	}
	  

	
	

}

