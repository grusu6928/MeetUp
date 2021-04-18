import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';

function Friend({ friend, index, removeFriend, request }) {
    return (
      <div
        className="friend"
        // can use this for logged on/logged off 
        // style={{ textDecoration: todo.isCompleted ? "line-through" : "" }}
      >
        {friend.friend}
        <div>
          <button onClick={() => removeFriend(index)}>x</button>
        </div>
      </div>
    );
  }
  export default Friend