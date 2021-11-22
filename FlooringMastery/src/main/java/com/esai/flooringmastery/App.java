package com.esai.flooringmastery;

import com.esai.flooringmastery.controller.FlooringMasteryController;
import com.esai.flooringmastery.dao.FlooringMasteryDao;
import com.esai.flooringmastery.dao.FlooringMasteryDaoException;
import com.esai.flooringmastery.dao.FlooringMasteryDaoFileImpl;
import com.esai.flooringmastery.service.FlooringMasteryServiceLayer;
import com.esai.flooringmastery.service.FlooringMasteryServiceLayerFileImpl;
import com.esai.flooringmastery.ui.FlooringMasteryView;
import com.esai.flooringmastery.ui.UserIO;
import com.esai.flooringmastery.ui.UserIOConsoleImpl;

/**
 *
 * @author Esai
 */
public class App {
    
    public static void main(String args[]) throws FlooringMasteryDaoException{
        UserIO io = new UserIOConsoleImpl();
        FlooringMasteryView view = new FlooringMasteryView(io);
        FlooringMasteryDao dao = new FlooringMasteryDaoFileImpl();
        FlooringMasteryServiceLayer service = new FlooringMasteryServiceLayerFileImpl(dao);
        FlooringMasteryController controller = new FlooringMasteryController(view, service);
        controller.run();
    }
}
