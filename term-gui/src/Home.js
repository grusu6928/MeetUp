import React, { Component } from 'react';
import './App.css';
import Button from './Button';
import FriendList from './FriendList';
import './index.css';
import Login from './Login';
import {Redirect} from 'react-router-dom'

class Home extends Component {
    componentDidMount() {
        this.setState({});
      }    
    constructor(props) {
        super(props);
        this.state = {
          user: localStorage.getItem("user"),
        };
    }
    logout() {
        localStorage.clear()
        console.log(localStorage.getItem("user"))
        this.setState({user: null})
    }
    render() {
        if(localStorage.getItem("user") == null) {
            return (
                <Redirect
                to={{
                    pathname: "/",
                }}
                />
                );
        }
        else {
            console.log("hi")
            console.log(localStorage.getItem("user"));
            return (
                <div className="margins">
                    {/*<FriendList />*/}
                    <header>
                        <h1 className="home">
                            <a href="/"> Home </a>
                        </h1>
                        <h3 className="logout" onClick={() => this.logout()}>Logout</h3>
                        <h1 className="welcome"> Welcome to MeetUp! </h1>
                    </header>
                    <h3>
                        <div className="starter">
                            <a href="starter"> Start an Event </a>
                        </div>
                        <div className="looker">
                            <a href="looker"> Look for an Event </a>
                        </div>
                    </h3>
                </div>
            );
    
        }
    }
}

export default Home;