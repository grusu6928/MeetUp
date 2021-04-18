import './App.css';

import { BrowserRouter as Switch, Route, BrowserRouter } from "react-router-dom";
import './index.css';
import React, {useEffect, useState} from 'react';
import ReactSession from 'react-client-session';
import Friend from './Friend.js'
import FriendForm from './FriendForm'


function FriendList({ friend, index }) {
    return (
        <div
          className="friend-list"
          // can use this for logged on/logged off 
          // style={{ textDecoration: todo.isCompleted ? "line-through" : "" }}
        >
          {friends}
          <div>
            <button onClick={() => removeFriend(index)}>x</button>
          </div>
        </div>
      );
    }
export default FriendList