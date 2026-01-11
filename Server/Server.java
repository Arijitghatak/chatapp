 import java.io.*; // importing network parts of java
 import java.net.*;
 class Server {

    ServerSocket Server; // listens for client connection
    Socket Socket;  // connected client

    BufferedReader br;      // reads data from client
    PrintWriter out;        // send data to client

    //constructor 
    public Server (){
       try {
           Server = new ServerSocket(7778);
           System.out.println("Server is ready to accept connection...");
           System.out.println("Waiting...");

           Socket=Server.accept();      // blocks until a client connects

           br = new BufferedReader(new InputStreamReader(Socket.getInputStream()));

           out = new PrintWriter(Socket.getOutputStream());

           startReading ();     // thread for receiving message
           startWriting();      // thread for sending message  

       } catch (Exception e) {
        e.printStackTrace();
       } 
    }

    // contineously reads messages sent by the client

public void startReading(){
    Runnable r1 = ()->{
        System.out.println("reader started..");

        try {

        while (true) { 
            String msg = br.readLine();    // null means client disconnected

            if (msg==null || msg.equals("exit")){
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

    // contineously sends messages typed in server console

public void startWriting(){
    System.out.println("writer started ...");
    Runnable r2 = ()->{

         try {

        while ( !Socket.isClosed()) {
           
            // when server is not closed 
                
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);
                out.flush();
            
             // server is closed 
                
                if (content==null || content.equals("exit")){
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

