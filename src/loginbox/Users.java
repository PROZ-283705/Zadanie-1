package loginbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Klasa odpowiadaj¹ca za przechowywanie i walidacjê u¿ytkowników w systemie
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class Users {
	
	private ObservableList<String> userListDev = FXCollections.observableArrayList();
	private ObservableList<String> userListTest = FXCollections.observableArrayList();
	private ObservableList<String> userListProd = FXCollections.observableArrayList();
	
	/**
	 * Konstruktor wype³nia listy u¿ytkowników
	 */
	public Users() {
		userListDev.addAll("jan.nowak","jakub.malinowski","jacek.telkowski");
		userListTest.addAll("edyta.nowak","ewa.gruszka","el¿bieta.rankowska");
		userListProd.addAll("micha³.nitowski","marcin.nowak","maurycy.lampa");
	}
	
	/**
	 * Metoda <b>getUsersForEnv</b> zwraca listê u¿ytkowników danego œrodowiska w postaci ObservableList
	 * @param envName nazwa œrodowiska, dla którego chcemy otrzymaæ listê u¿ytkowników
	 * @return lista u¿ytkowników œrodowiska
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
	 * Metoda <b>isPassCorrect</b> odpowiada za walidacjê hase³ u¿ytkowników.
	 * @param envName nazwa œrodowiska
	 * @param userName nazwa u¿ytkownika
	 * @param userPass has³o u¿ytkownika
	 * @return <b>true</b> jeœli znaleziono podanego u¿ytkownika w podanym œrodowisku i jego has³o jest poprawne lub <b>false</b> w przeciwnym przypadku
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
