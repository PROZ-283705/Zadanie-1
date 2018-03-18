package loginbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Klasa odpowiadaj�ca za przechowywanie i walidacj� u�ytkownik�w w systemie
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class Users {
	
	private ObservableList<String> userListDev = FXCollections.observableArrayList();
	private ObservableList<String> userListTest = FXCollections.observableArrayList();
	private ObservableList<String> userListProd = FXCollections.observableArrayList();
	
	/**
	 * Konstruktor wype�nia listy u�ytkownik�w
	 */
	public Users() {
		userListDev.addAll("jan.nowak","jakub.malinowski","jacek.telkowski");
		userListTest.addAll("edyta.nowak","ewa.gruszka","el�bieta.rankowska");
		userListProd.addAll("micha�.nitowski","marcin.nowak","maurycy.lampa");
	}
	
	/**
	 * Metoda <b>getUsersForEnv</b> zwraca list� u�ytkownik�w danego �rodowiska w postaci ObservableList
	 * @param envName nazwa �rodowiska, dla kt�rego chcemy otrzyma� list� u�ytkownik�w
	 * @return lista u�ytkownik�w �rodowiska
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
	 * Metoda <b>isPassCorrect</b> odpowiada za walidacj� hase� u�ytkownik�w.
	 * @param envName nazwa �rodowiska
	 * @param userName nazwa u�ytkownika
	 * @param userPass has�o u�ytkownika
	 * @return <b>true</b> je�li znaleziono podanego u�ytkownika w podanym �rodowisku i jego has�o jest poprawne lub <b>false</b> w przeciwnym przypadku
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
