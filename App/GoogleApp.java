package App;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Util.NetworkUtil;

public class GoogleApp extends Application {

    private Scene scene;
    MyBrowser myBrowser;
    MyAdminBrowser myAdminBrowser;
    MySuperAdminBrowser mySuperAdminBrowser;
    double lat;
    double lon;
    double t;
    private Stage stage;
    private String serverAddress = "127.0.0.1";
    private int serverPort = 33343;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    //for purely loading the client page
        this.stage = stage;
        ClientPage();
        //SuperAdminPage();

    }
    public void ClientPage(){
        myBrowser= new MyBrowser();
        Scene scene = new Scene(myBrowser);

        stage.setScene(scene);
        stage.setWidth(820);
        stage.setHeight(660);
        stage.show();

    }


    class MyBrowser extends Region {
    //adding html file
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();




        public MyBrowser() {
            final URL urlGoogleMaps = getClass().getResource("Client.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });

            getChildren().add(webView);



    //adding buttons
            HBox toolbar  = new HBox(80);


            Button signIn = new Button("SignIn");
            toolbar.setMargin(signIn, new Insets(0, 0, 0, 600));
            signIn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        showSignInPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            Button refresh = new Button("Refresh");
            toolbar.setMargin(refresh, new Insets(5,0,0,0));
            refresh.setOnAction(new EventHandler<ActionEvent>() {
                                   // NetworkUtil nc = new NetworkUtil(serverAddress, serverPort);


                                    ArrayList<String> longitudes = new ArrayList<String>();
                                    ArrayList<String> latitudes = new ArrayList<String>();
                                    ArrayList<String> time = new ArrayList<String>();

                                    @Override
                                    public void handle(ActionEvent arg0) {

                                        try (FileReader fr = new FileReader("Server.txt")) {
                                            int c;
                                            String temp = "";

                                            while ((c = fr.read()) != -1) {
                                                if (c == '+') {
                                                    latitudes.add(temp);
                                                    temp = "";
                                                    continue;
                                                }

                                                if (c == '-') {
                                                    longitudes.add(temp);
                                                    temp = "";
                                                    continue;
                                                }

                                                if (c == '*') {
                                                    time.add(temp);
                                                    temp = "";
                                                    continue;
                                                }

                                                temp += (char) c;
                                            }
                                        } catch (IOException e) {
                                            System.out.println(e);
                                        }





                                        String[] latitude = new String[latitudes.size()];
                                        String[] longitude = new String[latitudes.size()];
                                       // String [] times=new String[latitudes.size()];



                                        for (int i = 0; i < latitudes.size(); i++) {
                                            latitude[i] = latitudes.get(i);
                                            longitude[i] = longitudes.get(i);
                                           // times[i]=time.get(i);
                                        }

                                        for (int i = 0; i < time.size(); i++) {
                                            System.out.print(latitude[i] + "  ");
                                        }

                                        //networking should be here

                                        for (int i = 0; i < latitude.length; i++) {
                                            lat = Double.parseDouble(latitude[i]);
                                            lon = Double.parseDouble(longitude[i]);
                                           // t=Double.parseDouble(times[i]);

                                            System.out.printf("%.2f %.2f%n", lat, lon);

                                            webEngine.executeScript("" +
                                                            "window.lat = " + lat + ";" +
                                                            "window.lon = " + lon + ";" +
                                                            "document.goToLocation(window.lat,window. lon);"
                                            );
                                        }
                                    }
                                }
            );

                    toolbar.getChildren().addAll(signIn, refresh);

            getChildren().addAll(toolbar);


        }
    }
    public void AdminPage(String pos){

        myAdminBrowser= new MyAdminBrowser(pos);
        Scene scene = new Scene(myAdminBrowser);

        stage.setScene(scene);
        stage.setWidth(810);
        stage.setHeight(660);
        stage.show();


    }

    class MyAdminBrowser extends Region {
        //adding html file
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        public ComboBox comboBox;

        ObservableList<String>options= FXCollections.observableArrayList("Ajimpur Bus stop", "Aarong-Manik Mia Evenue Turning","Bangla Motor Bus Stop","Basnhundhara City South",  "Asad Gate", "Dhanmondi 32 bus stop","Fakirapul","Farmgate","Kamalapur","Kawran Bazar Bus Stop", "Shukrabad bus stop", "Mirpur 10 Busstand", "Mirpur 14 Busstand", "New market-Nilkhet turning","Paltan Bus Stop", "Panthopath-Mirpur turning","Press Club Bus Stop","Science Lab","ShahBag");
        ObservableList<String>latitude= FXCollections.observableArrayList("23.727065", "23.758108", "23.7457912","23.749933", "23.760268","23.751922 ","23.7348605", "23.7572733","23.7320191", "23.7519608","23.752452" ,"123", "345", "23.732358", "23.7300823", "23.751313", "23.7298288", "23.738842","23.7392481");
        ObservableList<String>longitude= FXCollections.observableArrayList("90.386131","90.374275","90.3947461","90.3922516","90.372902","90.377709","90.4172444","90.3899958","90.4259218","90.3926378","90.377714","123","345","90.385090","90.4100561","90.378288","90.4073561","90.383446","90.3958189");
//latitude and longitude textfield


//latitude and longitude textfield
        final TextField latitude_admin=new TextField();
        final TextField longitude_admin=new TextField();





        public MyAdminBrowser(String pos) {
            final URL urlGoogleMaps = getClass().getResource("Admin_Map.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });

            getChildren().add(webView);



            //adding buttons
            HBox h=new HBox();




            Button signOut = new Button("SignOut");
            h.setMargin(signOut, new Insets(0, 0, 0, 0));
            signOut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        ClientPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });







            final Button button=new Button("Update");
            final Label time_taken=new Label("Estimated Time");
            h.setMargin(time_taken, new Insets(05,10, 0, 10));
            final TextField time=new TextField();


            setComboBox(pos);


            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        NetworkUtil nc = new NetworkUtil(serverAddress, serverPort);
                        if (latitude_admin.getText() != null && longitude_admin.getText() != null && time.getText() != null)
                            nc.write(latitude_admin.getText() + "+" + longitude_admin.getText() + "-" + time.getText() + "*");

                        lat = Double.parseDouble(latitude_admin.getText());
                        lon = Double.parseDouble(longitude_admin.getText());

                        System.out.printf("%.2f %.2f%n", lat, lon);

                        webEngine.executeScript("" +
                                        "window.lat = " + lat + ";" +
                                        "window.lon = " + lon + ";" +
                                        "document.goToLocation(window.lat,window. lon);"
                        );

                        System.out.println();
                        latitude_admin.setText(null);
                        longitude_admin.setText(null);
                        time.setText(null);

                    } catch (Exception e1) {

                    }

                }
            });







            h.getChildren().addAll(latitude_admin,longitude_admin,time_taken,time, comboBox, button,signOut);
           // h1.getChildren().addAll(latitude_admin, longitude_admin);
            //toolbar.getChildren().addAll(signOut);





            getChildren().add(h);
            //getChildren().add(h1);
            //getChildren().add(toolbar);
        }

        void setComboBox(String pos){
            comboBox=new ComboBox();
            comboBox.getItems().addAll(pos);
            comboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    System.out.println(ov);
                    System.out.println(t);
                    System.out.println(t1);
                    int b = options.indexOf(t1);
                    latitude_admin.setText(latitude.get(b));
                    longitude_admin.setText(longitude.get(b));
                }
            });
        }

    }


    public void SuperAdminPage(){
        mySuperAdminBrowser= new MySuperAdminBrowser();
        Scene scene = new Scene(mySuperAdminBrowser);

        stage.setScene(scene);
        stage.setWidth(810);
        stage.setHeight(660);
        stage.show();
    }

    class MySuperAdminBrowser extends Region {
        //adding html file
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();




        public MySuperAdminBrowser() {
            final URL urlGoogleMaps = getClass().getResource("Admin_Map.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });

            getChildren().add(webView);



            //adding buttons
            HBox h=new HBox();






            Button signOut = new Button("SignOut");
            h.setMargin(signOut, new Insets(0, 0, 0, 0));
            signOut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        ClientPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });



            final TextField latitude_admin=new TextField();
            latitude_admin.setPrefWidth(30);
            final TextField longitude_admin=new TextField();
            longitude_admin.setPrefWidth(30);



            final Button button=new Button("Update");
            final Label time_taken=new Label("Estimated Time");
            h.setMargin(time_taken, new Insets(05,10, 0, 10));
            final TextField time=new TextField();
            final Button create=new Button("Create");




            ObservableList<String>options= FXCollections.observableArrayList("Ajimpur Bus stop", "Aarong-Manik Mia Evenue Turning","Bangla Motor Bus Stop","Basnhundhara City South", "Buet", "Asad Gate", "Dhanmondi 32 bus stop","Fakirapul","Farmgate","Kamalapur","Kawran Bazar Bus Stop", "Shukrabad bus stop", "Mirpur 10 Busstand", "Mirpur 14 Busstand", "New market-Nilkhet turning","Paltan Bus Stop", "Panthopath-Mirpur turning","Press Club Bus Stop","Science Lab","ShahBag");
            ObservableList<String>latitude= FXCollections.observableArrayList("23.727065", "23.758108", "23.7457912","23.749933", "23.749933","23.726073","23.760268","23.7348605", "23.7572733","23.7320191", "23.7519608","23.752452" ,"123", "345", "23.732358", "23.7300823", "23.751313", "23.7298288", "23.738842","23.7392481");
            ObservableList<String>longitude= FXCollections.observableArrayList("90.386131","90.374275","90.3947461","90.3922516","90.3922516","90.391990","90.372902","90.4172444","90.3899958","90.4259218","90.3926378","90.377714","123","345","90.385090","90.4100561","90.378288","90.4073561","90.383446","90.3958189");
//latitude and longitude textfield


            final ComboBox comboBox=new ComboBox(options);
            h.setMargin(comboBox, new Insets(0, 0, 0, 10));
            comboBox.setPromptText("Choose your position");


            comboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    System.out.println(ov);
                    System.out.println(t);
                    System.out.println(t1);
                    int b = options.indexOf(t1);
                    latitude_admin.setText(latitude.get(b));
                    longitude_admin.setText(longitude.get(b));
                }
            });



            create.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        showCreateAdminPage();
                    } catch (Exception e1) {

                    }

                }
            });







            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        NetworkUtil nc = new NetworkUtil(serverAddress, serverPort);
                        if (latitude_admin.getText() != null && longitude_admin.getText() != null && time.getText() != null)
                            nc.write(latitude_admin.getText() + "+" + longitude_admin.getText() + "-" + time.getText() + "*");

                        lat = Double.parseDouble(latitude_admin.getText());
                        lon = Double.parseDouble(longitude_admin.getText());

                        System.out.printf("%.2f %.2f%n", lat, lon);

                        webEngine.executeScript("" +
                                        "window.lat = " + lat + ";" +
                                        "window.lon = " + lon + ";" +
                                        "document.goToLocation(window.lat,window. lon);"
                        );

                        System.out.println();
                        latitude_admin.setText(null);
                        longitude_admin.setText(null);
                        time.setText(null);

                    } catch (Exception e1) {

                    }

                }
            });







            h.getChildren().addAll(latitude_admin,longitude_admin,time_taken,time, comboBox, button,signOut,create);
            // h1.getChildren().addAll(latitude_admin, longitude_admin);
            //toolbar.getChildren().addAll(signOut);





            getChildren().add(h);
            //getChildren().add(h1);
            //getChildren().add(toolbar);
        }

    }






    public void showSignInPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Sign In.fxml"));
        Parent root = loader.load();

        SignInController controller = loader.getController();
        controller.setGoogleApp(this);
        //Set the Primary stage

        stage.setTitle("Sign_In");
        stage.setScene(new Scene(root, 810, 660));
        stage.show();


    }


    public void showSignUpPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Sign Up.fxml"));
        Parent root = loader.load();

        SignUpController controller = loader.getController();
        controller.setGoogleApp(this);
        //Set the Primary stage

        stage.setTitle("Gui_3");
        stage.setScene(new Scene(root, 810, 660));
        stage.show();


    }

    public void showCreateAdminPage()throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Create_Admin.fxml"));
        Parent root = loader.load();

        CreateAdminController controller = loader.getController();
        controller.setGoogleApp(this);
        //Set the Primary stage

        stage.setTitle("Gui_3");
        stage.setScene(new Scene(root, 810, 660));
        stage.show();
    }



}
