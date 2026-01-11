 package Client;
 
 import java.io.*;
 import java.net.*;
 
 class Client {

    Socket socket ;       // connection to the server
    BufferedReader br;    // reads messages from server
    PrintWriter out;      // send message to server

    private Gui gui;      // reference to Gui class for updating UI


    // client starts and connects to the server (constructor)

public Client (Gui gui){ 
this.gui = gui;

    try {
        System.out.println("sending Request to server ");
        socket = new Socket("127.0.0.1",7778);
        System.out.println("Connection done");

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           out = new PrintWriter(socket.getOutputStream(),true);

           startReading (); // start listening for server messages
          // startWriting();


    } catch (Exception e) {
        e.printStackTrace();
    }
}


// runs in bg and listens for messages from server 

public void startReading(){
    Runnable r1 = ()->{
        System.out.println("reader started..");

        try {

        while ( true) { 
            String msg = br.readLine();
            if (msg == null || msg.equals("exit")){
                System.out.println("Server has terminated the chat");
                
                gui.onDisconnected();
                socket.close();
                break;
            }
            System.out.println("Server : " + msg);

            gui.addMessage(msg,false);
        
        }

        }catch(Exception e){
           // e.printStackTrace();
           gui.onDisconnected();
           System.out.println("connection closed");
    }
    };
        new Thread(r1).start();

    }

    // as we have gui for clinet we don not need writting method here

   /*  public void startWriting(){
    System.out.println("writer started ...");
    Runnable r2 = ()->{

        try {

        while (!socket.isClosed()) {
            
                
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);
                out.flush();

                if (content.equals("exit")){
                    socket.close();
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
        */
   


    // sends a message typed in the GUI to the server
    
 public void sendMessage(String msg){
    try{
    if (socket == null && socket.isClosed()) return;
        out.println(msg);
        out.flush();

        if (msg.equals("exit")){
            
                socket.close();
                gui.onDisconnected();
            } 
        }catch (Exception e) {
                e.printStackTrace();
                gui.onDisconnected();
            }
        }
    }

 
