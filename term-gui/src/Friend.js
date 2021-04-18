import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';

function Friend({ friend, index }) {
    const [friends, setFriends] = useState([])
    
    const removeFriend = index => {
        const newFriends = [...friends];
        newFriends.splice(index, 1);
        setFriends(newFriends);
      };

    return (
      <div
        className="friend"
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
  export default Friend