import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';

function FriendForm( {addFriend, request}) {
    const [value, setValue] = useState("");

    const handleSubmit = e => {
      e.preventDefault();
      if (!value) return;
      request("insert", value, "")
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