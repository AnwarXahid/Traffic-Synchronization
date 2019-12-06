package App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class SignUpController {
    private GoogleApp googleApp;

    @FXML
    private Label label1,label2,label3label4,label5,label6,label7,label8,label9,label10,label11,label12;
    @FXML
    private PasswordField  passwordfield1,passwordfield2 ;
    @FXML
    private TextField textfield1,textfield2,textfield3,textfield4,textfield5;
    @FXML
    private Button button;

    @FXML
    private void signUpAction(ActionEvent event){

    }


    void setGoogleApp(GoogleApp googleApp){
        this.googleApp=googleApp ;
    }

}
