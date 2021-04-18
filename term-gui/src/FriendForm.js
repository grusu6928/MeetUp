import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';

function FriendForm( {addFriend}) {
    const [value, setValue] = useState("");

    const requestFriends = (reqType, newFriend, removeFriend) => {
      // 0 = query friends 
      // 1 = add friend 
      // 2 = kill friend
      const toSend = {
          requestType = reqType,
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
              //setFL()
          })
          .catch(function (error) {
            console.log(error);
          });
        }
      

    const handleSubmit = e => {
      e.preventDefault();
      if (!value) return;
      addFriend(value);
      setValue("");
    };
    return (
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          className="input"
          value={value}
          onChange={e => setValue(e.target.value)}
        />
      </form>
    );
  }
export default FriendForm