import React, { useState, useEffect } from 'react';
import SidePanel from '../components/sidePanel';
import MainPanel from '../components/mainPanel';
import "./styles.css";

export default function Main() {
  const [selectedFriend, setSelectedFriend] = useState(null);
  const [message, setMessage] = useState('');

  return (
    <div className="container">
      <SidePanel 
        selectedFriend={selectedFriend}
        setSelectedFriend={setSelectedFriend}
        setMessage={setMessage}
      />
      <MainPanel 
        selectedFriend={selectedFriend}
        message={message}
        setMessage={setMessage}
      />
    </div>
  );
}
