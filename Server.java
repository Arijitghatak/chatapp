 import java.io.*; // importing network parts of java
 import java.net.*;
 class Server {

    ServerSocket Server; // using through java.net 
    Socket Socket;
    BufferedReader br;
    PrintWriter out;

    //constructor 
    public Server (){
       try {
           Server = new ServerSocket(7777);
           System.out.println("Server is ready to accept connection...");
           System.out.println("Waiting...");
           Socket=Server.accept();

       } catch (Exception e) {
        e.printStackTrace();
       } 
    }
    public static void main(String[] args) {
        System.out.println("Server is running...");
        new Server();
    }   
}
