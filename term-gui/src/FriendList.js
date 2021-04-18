import './App.css';

import { BrowserRouter as Switch, Route, BrowserRouter } from "react-router-dom";
import './index.css';
import React, {useEffect, useState} from 'react';
import ReactSession from 'react-client-session';
import Friend from './Friend.js'
import FriendForm from './FriendForm'


function FriendList({ friend, index }) {
    const [friends, setFriends] = useState([])
    
    const removeFriend = index => {
        const newFriends = [...friends];
        newFriends.splice(index, 1);
        setFriends(newFriends);
      };

      const addFriend = friend => {
        const newFriends = [...friends, { friend }];
        setFriends(newFriends);
      };

return (
      <div className="friends-div">
        {friends.map((friend, index) => (
          <Friend
            key={index}
            index={index}
            friend={friend}
            removeFriend={removeFriend}
          />
        ))}
        <FriendForm addFriend={addFriend} />
      </div>
  );
}
export default FriendList