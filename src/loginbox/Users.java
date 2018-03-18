package loginbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class Users {
	
	ObservableList<String> userListDev = FXCollections.observableArrayList("jan.nowak","jakub.malinowski","jacek.telkowski");
	ObservableList<String> userListTest = FXCollections.observableArrayList("edyta.nowak","ewa.gruszka","el¿bieta.rankowska");
	ObservableList<String> userListProd = FXCollections.observableArrayList("micha³.nitowski","marcin.nowak","maurycy.lampa");
	
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
