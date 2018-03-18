package loginbox;

/**
 * Klasa opisuj¹ca œrodowisko, do którego mo¿na siê zalogowaæ
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class Environment {
	private String environmentName;
	
	/**
	 * Kontruktor przyjmuje nazwê i tworzy œrodowsko o podanej nazwie
	 * @param name nazwa tworzonego œrodowiska
	 */
	public Environment(String name) {
		environmentName = name;
	}
	
	/**
	 * Metoda <b>getName</b> zwraca nazwê œrodowiska
	 * @return nazwa œrodowiska
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
