package Server;

import java.io.CharArrayWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import Util.NetworkUtil;
import javafx.beans.InvalidationListener;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;


public class Server {

	private ServerSocket ServSock;
	//public ArrayList<Admin> admin_Name=new ArrayList<Admin>();
	//ArrayList<String> bal=new ArrayList<String>();
	//public ArrayList<String> admin_Position=new ArrayList<>();



	public String[] Names={"A","B","c"};
	public String[] admin_Password={"1","2","3"};
	public String[] admin_Place={"A1","B1","C1"};
	public ObservableList<String> place= FXCollections.observableArrayList("Ajimpur Bus stop", "Aarong-Manik Mia Evenue Turning", "Buet", "Asad Gate", "Dhanmondi 32 bus stop", "Shukrabad bus stop", "Mirpur 10 Busstand", "Mirpur 14 Busstand", "New market-Nilkhet turning", "Panthopath-Mirpur turning", "Science Lab");









	CharArrayWriter f = new CharArrayWriter();
    /*String s = "Hello";
    char buf[] = new char[s.length()];
    //s.getChars(0, s.length(), buf, 0);
*/

	
	public Server() {
		try {
			ServSock = new ServerSocket(33343);
			while (true) {
				Socket clientSock = ServSock.accept();
				NetworkUtil nc=new NetworkUtil(clientSock);
                String s= (String) nc.read();
                char buf[]=new char[s.length()];
                s.getChars(0,s.length(),buf,0);
                try {
                    f.write(buf);
                }catch(Exception e){

                }
                try (FileWriter f2 = new FileWriter("Server.txt", true)) {
                    f.writeTo(f2);
                }catch(Exception e){

                }

                System.out.println(s);
			}
		}catch(Exception e) {
			System.out.println("Server starts:"+e);
		}
	}
	
	public static void main(String args[]) {
		Server objServer = new Server();
	}
}


