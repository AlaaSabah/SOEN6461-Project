package MainApp;

import controller.Controller;
import model.StockMarketModelSubject;
import view.LoginPage;

public class Launcher {

	public static void main(String[] args) {
		
		LoginPage login = new LoginPage();
		StockMarketModelSubject model = new StockMarketModelSubject();
		Controller controller = new Controller(model);
		login.addController(controller);
		login.addModel(model);
		login.setVisible(true);

	}

}
