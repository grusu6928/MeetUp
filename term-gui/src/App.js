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
      <header className="App-background">
        <title> MeetUp </title>
        <div className = "App-Background">
        <Login/>
      </div>
      </header>
      <BrowserRouter>
        <Switch>
        <Route exact path="/Login.js">
          <Login />
        </Route>
          <Route exact path="/Starter.js"> 
            <Starter />
          </Route>
          <Route exact path="/Looker.js"> 
            <Looker />
          </Route>
          <Route exact path="/"> 
            <Home />
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
