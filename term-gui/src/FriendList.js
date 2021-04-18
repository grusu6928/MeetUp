import './App.css';

import { BrowserRouter as Switch, Route, BrowserRouter } from "react-router-dom";
import './index.css';
import React, {useEffect, useState} from 'react';
import axios from "axios";
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
      const requestFriends = (reqType, newFriend, removeFriend) => {
        // 0 = query friends
        // 1 = add friend
        // 2 = kill friend
        const toSend = {
            requestType: reqType,
            userID: localStorage.getItem("user"),
            userToAdd: newFriend,
            userToRemove: removeFriend
        }
        let config = {
            headers: {
              "Content-Type": "application/json",
              'Access-Control-Allow-Origin': '*',
              }
            }
            axios.post('http://localhost:4567/friends', toSend, config)
            .then(response => {
                // want to update event information
                setFriends([response.data])
            })
            .catch(function (error) {
              console.log(error);
            });
          }

return (
      <div className="friends-div">
        {friends.map((friend, index) => (
          <Friend
            key={index}
            index={index}
            friend={friend}
            removeFriend={removeFriend}
            request = {requestFriends}
          />
        ))}
        <FriendForm addFriend={addFriend} request = {requestFriends} />
      </div>
  );
}
export default FriendList