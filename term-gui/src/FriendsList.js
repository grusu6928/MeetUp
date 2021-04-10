import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';
import Button from "./Button.js";
import TextInput from './TextInput';

function FriendsList(props) {
    const [friendList, setFL] = useState([])

    const friendListUI = document.getElementById("FL");
    
    const addFriend = (newFriend) => {
        setFL(friendList.concat(newFriend))
    }

    const removeFriend = (friendID) => {
        const index = friendList.indexOf(friendID);
        if (index > -1) {
            friendList.splice(index, 1);
          }
    }
    const renderFriends = () => {
        let len = friendList.length
        for (let i = 0; i < len; i++) {
            friendListUI.innerHTML += (`<li tabindex="0" class = "att">${friendListUI[i]}</li>`)
        }
    }
    useEffect(() => {
        requestFriends
    }, [friendList])


    const requestFriends = () => {
        const toSend = {
            userID: (props.currUser),
            userToAdd: (props.newFriend),
            userToRemove: (props.removedFriend)
        }
        let config = {
            headers: {
              "Content-Type": "application/json",
              'Access-Control-Allow-Origin': '*',
              }
            }
            axios.post('http://localhost:4567/FriendsList', toSend, config)
            .then(response => {
                // want to update event information
                //setFL()
            })
            .catch(function (error) {
              console.log(error);
            });
          }
          return (
            <div className="FL">
              <header className="FL-header">
                <title>Friends List</title>
                <ul id = "FL"></ul>
              </header>
            </div>
          );
}
export default FriendsList