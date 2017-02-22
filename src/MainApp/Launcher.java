package MainApp;

import controller.Controller;
import model.StockMarketModel;
import view.LoginPage;

public class Launcher {

	public static void main(String[] args) {
		
		LoginPage login = new LoginPage();
		StockMarketModel model = new StockMarketModel();
		Controller controller = new Controller(login,model);
		login.addController(controller);
		login.addModel(model);
		login.setVisible(true);

	}

}
