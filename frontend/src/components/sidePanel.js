import React, { useState, useEffect } from 'react';

export default function SidePanel({ selectedFriend, setSelectedFriend, setMessage }) {
  const [friends, setFriends] = useState([]);

  useEffect(() => {
    async function fetchFriends() {
      try {
        const response = await fetch('http://localhost:8165/api/friendships/1');
        const result = await response.json();
        const friendUsernames = result.data.map(friendship => ({
          user: friendship.user.username,
          friend: friendship.friend.username,
          userId: friendship.user.id,
          friendId: friendship.friend.id,
        }));
        setFriends(friendUsernames.flatMap(friend => [friend.user, friend.friend]));
      } catch (error) {
        console.error('Error fetching friends:', error);
      }
    }

    fetchFriends();
  }, []);

  const handleFriendClick = (friend) => {
    setSelectedFriend(friend);
    setMessage('');  // Clear message box when selecting a new friend
  };

  return (
    <div className="side-panel">
      <h2>Side Panel</h2>
      <ul>
        {friends.map((username, index) => (
          <li key={index} onClick={() => handleFriendClick(username)}>
            {username}
          </li>
        ))}
      </ul>
    </div>
  );
}
