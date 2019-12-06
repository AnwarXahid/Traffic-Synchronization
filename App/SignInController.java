package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Server.Server;

public class SignInController{

    private GoogleApp googleApp;
    public Server server=new Server();

    @FXML
    private Label label1;
    @FXML
    private PasswordField  passwordfield ;
    @FXML
    private Hyperlink hyperlink ;
    @FXML
    private TextField textfield;
    @FXML
    private Label password;
    @FXML
    private Button signInButton;


    @FXML
    private void signInButtonAction(ActionEvent event){
        setGoogleApp(googleApp);

        String userName = textfield.getText();
        String password = passwordfield.getText();
        System.out.println(userName);
        System.out.println(password);

        try {
            if(userName.equals("Prince")&& password.equals("1234")){
                googleApp.SuperAdminPage();
            }



            ArrayList<String> adminName= new ArrayList<String>();
            ArrayList<String> adminPassword= new ArrayList<String>();
            ArrayList<String> adminPlace= new ArrayList<String>();
            try ( FileReader fr = new FileReader("AdminList.txt") )
            {
                int c;
                String  temp = "";

                while((c = fr.read()) != -1) {
                    if(c=='+'){
                        adminName.add(temp);
                        temp = "";
                        continue;
                    }

                    if(c=='*'){
                        adminPassword.add(temp);
                        temp="";
                        continue;
                    }

                    if(c=='/'){
                        adminPlace.add(temp);
                        temp="";
                        continue;
                    }

                    temp+=(char)c;
                }
            } catch(IOException e) {
                System.out.println(e);
            }





            for(int i=0;i<adminName.size();i++) {
                System.out.print(adminName.get(i)+"  ");
            }
            System.out.println (" ");
            System.out.println (adminName.size());

            for(int i=0;i<adminPassword.size();i++) {
                System.out.print(adminPassword.get(i)+"  ");
            }
            System.out.println (" ");
            System.out.println (adminPassword.size());

            for(int i=0;i<adminPlace.size();i++) {
                System.out.print(adminPlace.get(i)+"  ");
            }
            System.out.println (" ");
            System.out.println (adminPlace.size());





            for(int i=0;i<adminName.size();i++){
                System.out.println(adminName.get(i));
                if (userName.equals(adminName.get(i))){
                    if(password.equals(adminPassword.get(i))){
                        googleApp.AdminPage(adminPlace.get(i));
                        break;
                    }
                }
            }

        }catch (Exception e){

        }
    }

    @FXML
    private void hyperlinkActionEvent(ActionEvent event)throws IOException{
        setGoogleApp(googleApp);
        try{
            googleApp.showSignUpPage();
        }catch (Exception e){

        }
    }



    void setGoogleApp(GoogleApp googleApp) {
        this.googleApp = googleApp;
    }


}
