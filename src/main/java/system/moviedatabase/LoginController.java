package system.moviedatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.LoginDTO;

import java.io.IOException;


public class LoginController {
    private Main main;

    @FXML
    private TextField userText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    void loginAction(ActionEvent event) {
        String productionCompanyName=userText.getText();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setProductionCompanyName(productionCompanyName);
        try {
            main.getNetworkUtil().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void shutDownAction(ActionEvent event){
        main.exit();
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }
}
