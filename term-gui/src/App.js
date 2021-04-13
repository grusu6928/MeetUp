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
      <BrowserRouter>
        <Switch>
          <Route exact path="/starter" component = {FriendsList}> 
            <Starter component = {FriendsList} />
          </Route>
          <Route exact path="/looker" > 
            <Looker component = {FriendsList}/>
          </Route>
          <Route exact path="/home" component = {FriendsList}> 
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
