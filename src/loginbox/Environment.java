package loginbox;

public class Environment {
	String environmentName;
	
	public Environment(String name) {
		environmentName = name;
	}
	
	public String getName()	{
		return environmentName;
	}
	
	public String toString() {
		return environmentName;
	}
}
