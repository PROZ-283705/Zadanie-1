package loginbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa odpowiadająca za przechowywanie i walidację użytkowników w systemie
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class Users {
	
	private ObservableList<String> userListDev = FXCollections.observableArrayList();
	private ObservableList<String> userListTest = FXCollections.observableArrayList();
	private ObservableList<String> userListProd = FXCollections.observableArrayList();
	
	/**
	 * Konstruktor wypełnia listy użytkowników
	 */
	public Users() {
		userListDev.addAll("jan.nowak","jakub.malinowski","jacek.telkowski");
		userListTest.addAll("edyta.nowak","ewa.gruszka","elzbieta.rankowska");
		userListProd.addAll("michal.nitowski","marcin.nowak","maurycy.lampa");
	}
	
	/**
	 * Metoda <b>getUsersForEnv</b> zwraca listę użytkowników danego środowiska w postaci ObservableList
	 * @param envName nazwa środowiska, dla którego chcemy otrzymać listę użytkowników
	 * @return lista użytkowników środowiska
	 */
	public ObservableList<String> getUsersForEnv(String envName)
	{
		switch(envName)
		{
		case "Produkcyjne": return userListProd;
		case "Testowe": return userListTest;
		case "Deweloperskie": return userListDev;
		}
		return null;
	}
	
	/**
	 * Metoda <b>isPassCorrect</b> odpowiada za walidację haseł użytkowników.
	 * @param envName nazwa środowiska
	 * @param userName nazwa użytkownika
	 * @param userPass hasło użytkownika
	 * @return <b>true</b> jeżli znaleziono podanego użytkownika w podanym środowisku i jego hasło jest poprawne lub <b>false</b> w przeciwnym przypadku
	 */
	public Boolean isPassCorrect(String envName, String userName, String userPass)
	{
		switch(envName) {
		case "Produkcyjne": 
			if(userListProd.contains(userName))
			{
				if(userPass.equals("prod")) return true;
			}
			break;
		case "Testowe":
			if(userListTest.contains(userName))
			{
				if(userPass.equals("test")) return true;
			}
			break;
		case "Deweloperskie": 
			if(userListDev.contains(userName))
			{
				if(userPass.equals("dev")) return true;
			}
			break;
		}
		return false;
	}
}
