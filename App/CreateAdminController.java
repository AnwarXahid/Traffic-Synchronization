package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Server.Server;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class CreateAdminController {
    private GoogleApp googleApp;
    private Server server=new Server();



    @FXML
    private Label label1,label2,label3;
    @FXML
    private TextField textfield1,textfield2,textfield3;
    @FXML
    private Button button;
    @FXML
    private Button back;
    @FXML
    private void createAdminAction(ActionEvent event){



        try ( FileWriter fr = new FileWriter("AdminList.txt",true) )
        {
            String str1 = "";
            String str2 = "";
            String str3 = "";

            str1+=textfield1.getText()+"+";
            str2+=textfield2.getText()+"*";
            str3+=textfield3.getText()+"/";

            fr.write(str1);
            fr.write(str2);
            fr.write(str3);


            textfield1.setText(null);
            textfield2.setText(null);
            textfield3.setText(null);


        } catch(IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void createBackAction(ActionEvent event) {
        googleApp.SuperAdminPage();

    }

    void setGoogleApp(GoogleApp googleApp) {
        this.googleApp = googleApp;
    }



}
