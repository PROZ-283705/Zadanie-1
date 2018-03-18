package loginbox;

/**
 * Klasa opisuj�ca �rodowisko, do kt�rego mo�na si� zalogowa�
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class Environment {
	private String environmentName;
	
	/**
	 * Kontruktor przyjmuje nazw� i tworzy �rodowsko o podanej nazwie
	 * @param name nazwa tworzonego �rodowiska
	 */
	public Environment(String name) {
		environmentName = name;
	}
	
	/**
	 * Metoda <b>getName</b> zwraca nazw� �rodowiska
	 * @return nazwa �rodowiska
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
