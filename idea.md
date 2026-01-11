# 💡 Pulse — Project Idea & Design

This file explains how Pulse works internally and why it was built this way.

---

## 1️⃣ How Pulse Works (in 5 steps) ⚙️

1. The **server** starts and waits for a client using a `ServerSocket`.
2. The **client** connects to the server using a `Socket`.
3. Both sides open input/output streams to send and receive messages.
4. Messages are exchanged in real time using TCP.
5. If either side sends `exit` or disconnects, both ends close cleanly.

Pulse uses **real networking**, not simulated data.

---

## 2️⃣ Similar Methods in Client & Server 🔁

Both sides use similar ideas to communicate:

| Method | Used In | What It Does |
|------|--------|--------------|
| `startReading()` | Client & Server | Reads messages coming from the socket |
| `startWriting()` | Client & Server | Sends messages through the socket |
| `sendMessage()` | Client | Sends user input to the server |
| `readLine()` | Both | Reads one message at a time from the network |
| `close()` | Both | Closes the socket and ends communication |

These methods form the **communication pipeline** between the two programs.

---

## 3️⃣ Threading & Disconnection 🧵🔌

Pulse uses **two threads** on both client and server:

- **Reading thread** → Listens for incoming messages  
- **Writing thread** → Sends outgoing messages  

This allows:
- Sending and receiving at the same time  
- No UI freezing  
- Smooth real-time chat  

### Disconnection logic:
- If `readLine()` returns `null`, the other side disconnected
- If `"exit"` is sent, both sides close the socket
- The client GUI disables input when disconnected

This prevents:
- Ghost messages
- Crashes
- Broken UI states

---

## 4️⃣ GUI & Chat Bubble Logic 🎨💬

The GUI is built using **Java Swing**.

It uses:
- `JFrame` → main window  
- `JPanel` → layout & glass panels  
- `JTextField` → message input  
- `JButton` → send button  
- `JScrollPane` → scrolling chat  

### Chat bubbles:
Each message is shown as a **bubble panel**:
- Right side → user messages  
- Left side → server messages  
- Rounded corners using custom `RoundedPanel`  
- Dark and blue colors for contrast  

The GUI stays in sync with the network:
- New messages appear as bubbles  
- When disconnected, input is disabled  
- The UI reflects real connection state

---

Pulse is designed to show how **real-time systems** behave, not just how UIs look.








