import React, { Component } from 'react';
import './App.css';
import FriendsList from './FriendsList';
import './index.css';

class Home extends Component {
    render() {
        return (
            <div className="margins">
                <FriendsList />
                <header>
                    <h1 className="home">
                        <a href="/"> Home </a>
                    </h1>
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

export default Home;