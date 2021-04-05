import './App.css';
import React, {useState} from 'react';
import Lookers from './Lookers';
import FriendsList from './FriendsList';

function App() {
  return (
    <div className="App">
      <header className="App-background">
        <title> MeetUp </title>
        <div className = "App-Background">
        <FriendsList/>
      </div>
      </header>
    </div>
  );
}

export default App;
