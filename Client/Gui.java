package Client;

import java.awt.*; // buttons,text fields,frames
import javax.swing.*; // spacing withot boxes
import javax.swing.border.EmptyBorder;   // layout,colors,fonts



public class Gui {
    
    //screen parts 

    private JFrame frame;
    private JTextArea ChatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Client client ;

    private JPanel chatContainer;
    private JScrollPane scroll;


    // constructor for start build ui and networking the object
    public Gui(){
        createUI();
    }

    public void setClient(Client client ){
        this.client= client;
    }

    // shows the window when the app starts 
    public void start(){
        frame.setVisible(true);
    }


   

private void createUI(){

    // create the window 

    frame = new JFrame("PULSE"); //title
    frame.setSize(500,650); //size
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close behaviour
    frame.setLocationRelativeTo(null); // centers to screen


    // create dark layout

    JPanel root = new JPanel(new BorderLayout());
    root.setBackground(new Color(18,18,18));
    root.setBorder(new EmptyBorder(10,10,10,10));


    // add logo 
    ImageIcon icon = new ImageIcon(
        getClass().getResource("/Client/logo.png")
    );
    Image img = icon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH );
    icon = new ImageIcon(img);

        // Logo label
    JLabel logo = new JLabel(icon);
    logo.setPreferredSize(new Dimension(60, 60));
    logo.setHorizontalAlignment(SwingConstants.CENTER);
    logo.setVerticalAlignment(SwingConstants.CENTER);
    
    // App name
    JLabel titleText = new JLabel("Pulse");
    titleText.setFont(new Font("Segoe UI", Font.BOLD, 28));
    titleText.setForeground(Color.WHITE);
    titleText.setBorder(new EmptyBorder(0, 15, 0, 0));

    // Combine logo + text
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
    titlePanel.setOpaque(false);
    titlePanel.add(logo);
    titlePanel.add(titleText);
    titlePanel.setBorder(new EmptyBorder(10, 0, 15, 0));


    // chat area

    chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(chatContainer, BoxLayout.Y_AXIS));
        chatContainer.setBackground(new Color(18, 18, 18));

        scroll = new JScrollPane(chatContainer);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);

    //glass panel

        JPanel glassPanel = new JPanel(new BorderLayout());
    glassPanel.setBackground(new Color(255, 255, 255, 18)); // semi-transparent white
    glassPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    glassPanel.add(scroll, BorderLayout.CENTER);




    // input panel 

    JPanel inputPanel = new JPanel(new BorderLayout(10,10));
    inputPanel.setBackground(new Color(40,40,40));
    inputPanel.setBorder(new EmptyBorder(10,0,0,0));

    inputField = new JTextField();
    inputField.setBackground(new Color (40,40,40));
    inputField.setForeground(Color.WHITE);
    inputField.setCaretColor(Color.WHITE);
    inputField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
        new EmptyBorder(10, 10, 10, 10)));
    inputField.setFont(new Font ("Segoe UI",Font.PLAIN,14));


    // add send button 

    ImageIcon SendIcon = new ImageIcon(
        getClass().getResource("/Client/pulsesend.png")
    );

    Image sendImg = SendIcon.getImage().getScaledInstance(32,32 ,Image.SCALE_SMOOTH);
    SendIcon = new ImageIcon(sendImg);

    sendButton = new JButton(SendIcon);
    sendButton.setBackground(new Color(70,130,180));
    sendButton.setFocusPainted(false);
    sendButton.setBorderPainted(false);
    sendButton.setOpaque(true);
    sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // add howver in send button 
    sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent e) {
         allowHover(true);
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
        allowHover(false);
    }
    private void allowHover(boolean on) {
    if (on) {
        sendButton.setBackground(new Color(80, 200, 170)); // blue-green
      }  else {
        sendButton.setBackground(new Color(70, 130, 180)); // normal blue
    }
    
}

});



    // layout 

    inputPanel.add(inputField,BorderLayout.CENTER);
    inputPanel.add(sendButton,BorderLayout.EAST);

    root.add(titlePanel,BorderLayout.NORTH);
    root.add(glassPanel,BorderLayout.CENTER);
    root.add(inputPanel,BorderLayout.SOUTH);

    frame.setContentPane(root);

    // button + Enter key Listener 

    sendButton.addActionListener(e -> sendMessage());
    inputField.addActionListener(e -> sendMessage());

}
    // Bubble ui 

     private JPanel createBubble(String text, boolean isUser) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        wrapper.setMaximumSize(new Dimension(420, Integer.MAX_VALUE));
        


        RoundedPanel bubble = new RoundedPanel(20);
        bubble.setLayout(new BorderLayout());
        bubble.setBorder(new EmptyBorder(10, 15, 10, 15));
        bubble.setMaximumSize(new Dimension(320, Integer.MAX_VALUE));



        JLabel label = new JLabel("<html>" + text + "</html>");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(300, label.getPreferredSize().height));

        if (isUser) {
            bubble.setBackground(new Color(70, 130, 180));
           JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
                right.setOpaque(false);
                right.add(bubble);
                wrapper.add(right, BorderLayout.CENTER);

        } else {
            bubble.setBackground(new Color(40, 40, 40));
            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                left.setOpaque(false);
                left.add(bubble);
                wrapper.add(left, BorderLayout.CENTER);

        }

        bubble.add(label, BorderLayout.CENTER);
        wrapper.setBorder(new EmptyBorder(5, 10, 5, 10));
        return wrapper;
    }


    // send message logic 

 private void sendMessage(){
        String msg = inputField.getText().trim();
        if(!msg.isEmpty()){
           if (client != null) {
            client.sendMessage(msg);
        }

          addMessage( msg,true);
            inputField.setText("");
        }
    }

    public void addMessage(String msg,boolean isUser){
        JPanel bubble = createBubble(msg,isUser);
       chatContainer.add(bubble);
       chatContainer.revalidate();
       chatContainer.revalidate();
        chatContainer.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });

       // animation 
       new Timer(10, e -> scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum())).start();
    }

    public void onDisconnected() {
    inputField.setEnabled(false);
    sendButton.setEnabled(false);
    addMessage("Connection closed.",false);
}

// rounded panel 

class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }


}