package MainApp;

import controller.Controller;
import model.StockMarketModel;
import view.LoginPage;

public class Launcher {

	public static void main(String[] args) {
		
		LoginPage login = new LoginPage();
		Controller controller = new Controller();
		StockMarketModel model = new StockMarketModel();
		login.setVisible(true);

	}

}
