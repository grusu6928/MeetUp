import './App.css';

import Home from './Home';
import { BrowserRouter as Switch, Route, BrowserRouter } from "react-router-dom";
import Starter from './Starter';
import Submission from './Submission';
import Looker from './Looker';
import LookerSubmission from './LookerSubmission.js';
import Login from './Login';
import './index.css';
import React, {useEffect, useState} from 'react';
import Lookers from './Lookers';
import FriendsList from './FriendsList';
import ReactSession from 'react-client-session';
import Signup from './Signup.js'
import Friend from './Friend.js'
import FriendForm from './FriendForm'

import { AppContext } from './ContextUtil';

function App() {
  const [isAuth, changeAuth] = useState(false);
  const [friends, setFriends] = useState([])

    setInterval(() => {
        console.log("friends" + friends[0]);
    }, 4000)

  return (
    <div className="App">
      <header>
        <title> MeetUp </title>
      </header>
      <div className="friends-div">
        {friends.map((friend, index) => (
          <Friend
            key={index}
            index={index}
            friend={friend}
          />
        ))}
        <FriendForm friendsList ={friends} setFriends = {setFriends}/>
        </div>
      <BrowserRouter>
        <Switch>
          <AppContext.Provider value =  {{isAuth, changeAuth}}>
        <Route exact path="/submission" component={Submission}/>
          <Route exact path="/starter"> 
            <Starter />
          </Route>
          <Route exact path="/looker" > 
            <Looker/>
          </Route>
          <Route exact path="/signup" > 
            <Signup/>
          </Route>
          <Route exact path="/home"> 
            <Home />
          </Route>
          <Route exact path="/">
          <Login />
        </Route>
        </AppContext.Provider>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
