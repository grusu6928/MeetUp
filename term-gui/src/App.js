import './App.css';

import Home from './Home';
import { BrowserRouter as Switch, Route, BrowserRouter } from "react-router-dom";
import Starter from './Starter';
import Looker from './Looker';
import Login from './Login';
import './index.css';
import React, {useState} from 'react';
import Lookers from './Lookers';
import FriendsList from './FriendsList';
import { AppContext } from "./Contexts";
import ReactSession from 'react-client-session';


function App() {
  const [isAuth, changeAuth] = useState(false);

  return (
    <div className="App">
      <header>
        <title> MeetUp </title>
      </header>
      <FriendsList />
      <BrowserRouter>
        <Switch>
          <Route exact path="/Starter.js"> 
            <Starter />
          </Route>
          <Route exact path="/Looker.js"> 
            <Looker />
          </Route>
          <Route exact path="/Home.js"> 
            <Home />
          </Route>
          <Route exact path="/">
          <Login />
        </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
