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

           br = new BufferedReader(new InputStreamReader(Socket.getInputStream()));

           out = new PrintWriter(Socket.getOutputStream());

           startReading ();
           startWriting();

       } catch (Exception e) {
        e.printStackTrace();
       } 
    }

public void startReading(){
    Runnable r1 = ()->{
        System.out.println("reader started..");

        try {

        while (true) { 
            String msg = br.readLine();
            if (msg.equals("exit")){
                System.out.println("client has terminated the chat");
                Socket.close();
                break;
            }
            System.out.println("client : " + msg);
       
    }

     }
     catch(Exception e){
           // e.printStackTrace();

            System.out.println("connection closed");
        }
    };
        new Thread(r1).start();

    }

public void startWriting(){
    System.out.println("writer started ...");
    Runnable r2 = ()->{

         try {

        while ( !Socket.isClosed()) {
           
                
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);
                out.flush();

                if (content.equals("exit")){
                    Socket.close();
                    break;
                }
            
            } 

             System.out.println("connection closed");

            } catch (Exception e) {
                e.printStackTrace();
        }
    };
        new Thread(r2).start();
    }
    


     public static void main(String[] args) {
        System.out.println("Server is running...");
       Server Server = new Server();
} 
    }   

