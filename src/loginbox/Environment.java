package loginbox;

/**
 * Klasa opisująca środowisko, do którego można się zalogować
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class Environment {
	private String environmentName;
	
	/**
	 * Kontruktor przyjmuje nazwę i tworzy środowsko o podanej nazwie
	 * @param name nazwa tworzonego środowiska
	 */
	public Environment(String name) {
		environmentName = name;
	}
	
	/**
	 * Metoda <b>getName</b> zwraca nazwę środowiska
	 * @return nazwa środowiska
	 */
	public String getName()	{
		return environmentName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return environmentName;
	}
}
