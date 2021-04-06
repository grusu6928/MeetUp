import React, { Component } from 'react';
import './App.css';
import './index.css';

class Home extends Component {
    render() {
        return (
            <div className="margins">
                <header>
                    <h1 className="home">
                        <a href="/"> Home </a>
                    </h1>
                    <h1 className="welcome"> Welcome to MeetUp! </h1> 
                </header>
                <h3>
                    <div className="starter">
                        <a href="Starter.js"> Start an Event </a>
                    </div>
                    <div className="looker">
                        <a href="Looker.js"> Look for an Event </a>
                    </div>
                </h3>
            </div>
        );
    }
}

export default Home;