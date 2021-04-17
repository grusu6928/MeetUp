import './App.css';

import Home from './Home';
import { BrowserRouter as Switch, Route, BrowserRouter } from "react-router-dom";
import Starter from './Starter';
import Submission from './Submission';
import Looker from './Looker';
import LookerSubmission from './LookerSubmission.js';
import Login from './Login';
import './index.css';
import React, {useState} from 'react';
import Lookers from './Lookers';
import FriendsList from './FriendsList';
import ReactSession from 'react-client-session';
import Signup from './Signup.js'
import { AppContext } from './ContextUtil';

function App() {
  const [isAuth, changeAuth] = useState(false);

  return (
    <div className="App">
      <header>
        <title> MeetUp </title>
      </header>
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
          <Route exact path="/login">
          <Login />
        </Route>
        </AppContext.Provider>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
