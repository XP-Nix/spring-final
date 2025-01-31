export default function MainPanel({ selectedFriend, message, setMessage }) {
    const sendMessage = async () => {
        if (selectedFriend && message.trim()) {
          const senderId = 1;  
          const channelId = 2;
      
          try {
            const response = await fetch(`http://localhost:8165/api/messages?senderId=${senderId}&channelId=${channelId}`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                content: message,  // The message content
              }),
            });
      
            const data = await response.json();
            if (data.status === 'success') {
              console.log('Message sent');
              setMessage('');  // Clear message after sending
            } else {
              console.error('Error sending message');
            }
          } catch (error) {
            console.error('Error sending message:', error);
          }
        }
      };
      
  
    return (
      <div className="main-content">
        <h2>Main Content</h2>
        {selectedFriend && (
          <div className="chat-box">
            <h3>Chat with {selectedFriend}</h3>
            <textarea
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="Type a message..."
            />
            <button onClick={sendMessage}>Send</button>
          </div>
        )}
      </div>
    );
  }
  