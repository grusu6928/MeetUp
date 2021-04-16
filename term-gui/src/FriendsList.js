import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';
import Button from "./Button.js";
import TextInput from './TextInput';
import { AppContext } from './ContextUtil';


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
    // useEffect(() => {
    //     requestFriends
    // }, [friendList])


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
            <div className="">
              <header className="">
                <title>Friends List</title>
              </header>
              <div className="friends-div">
              <p>Friends List</p>
              <ul id = "FL">
                <li>George</li>
                <li>Hamzah </li>
                <li>Ermias</li>
                <li>Amin</li>
                <li>Nick </li>
              </ul>
              </div>
            </div>
          );
}
export default FriendsList