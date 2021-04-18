import React, { Component, useEffect } from 'react';
import './App.css';
import './index.css';
import FriendList from './FriendList';
import axios from "axios";
import {Redirect} from 'react-router-dom'
import Friend from './Friend';



const sendEvent = (activity, startTime, endTime, location) => {
    const toSend = {
        user: localStorage.getItem("user"),
        activity: activity,
        startTime: startTime,
        endTime: endTime,
        location: location
    }
    let config = {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
        }
    }
    axios.post('http://localhost:4567/looker', toSend, config)
        .then(response => {
            console.log("success");
        })
        .catch(function (error) {
            console.log(error);
        });
}

class Looker extends Component {
    constructor() {
        super();
        this.state = {
            selectedActivity: null,
            startTime: null,
            endTime: null,
            location: null,
            redirect: false,
            user: localStorage.getItem("user")
        };

        this.handleActivityChange = this.handleActivityChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        let data = []
    }

    componentDidMount() {
    }
    
    handleActivityChange (e){
        this.setState({
            selectedActivity: e.target.value
        })
    }
    handleStartTime (e){
        this.setState({
            startTime: e.target.value
        })
    }
    handleEndTime (e){
        this.setState({
            endTime: e.target.value
        })
    }
    handleLocation (e){
        this.setState({
            location: e.target.value
        })
    }

    handleSubmit (e){
        e.preventDefault();
        console.log(this.state.selectedType);
        console.log(this.state.selectedActivity);
        console.log(this.state.startTime);
        console.log(this.state.endTime);
        console.log(this.state.location);
        this.data = [
            {
                typeOfActivity: this.state.selectedActivity,
                startTime: this.state.startTime,
                endTime: this.state.endTime,
                location: this.state.location,
            }
        ]
        sendEvent(this.state.selectedActivity, this.state.startTime, this.state.endTime,
            this.state.location);
       
        //TODO: lookerForm?
        const starterForm = document.getElementById('starter-form')
        starterForm.reset();
        alert ("Thank you for submitting your preferences, we will now provide you with events that match your preferences!")
        // window.location.href = "/starter-submission"
        this.setState({redirect: true});
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
        } else {
        if (this.state.redirect) {
            return (
            <Redirect
            to={{
                pathname: "/submission",
                state: this.data
            }}
            />
            );
        }
        return (
            <div className="margins">
                <FriendList />
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h3 className="logout" onClick={() => this.logout()}>Logout</h3>
                    <h1 className="welcome"> Provide your event preferences: </h1>
                </header>
                <div className="form-div">
                    <form id="starter-form" onSubmit={this.handleSubmit}>
                        <div className="event">
                            <p className="text"> What are you up for?</p>
                            <div>
                                <input type="radio" value="meal" checked={this.state.selectedActivity === 'meal'} onChange={this.handleActivityChange} />
                                <label for="meal"> Meal </label>
                                <input type="radio" value="study" checked={this.state.selectedActivity === 'study'} onChange={this.handleActivityChange} />
                                <label for="study"> Study </label>
                            </div>
                            <div>
                                <input type="radio" value="sport" checked={this.state.selectedActivity === 'sport'} onChange={this.handleActivityChange} />
                                <label for="sport"> Sport </label>
                                <input type="radio" value="chill" checked={this.state.selectedActivity === 'chill'} onChange={this.handleActivityChange} />
                                <label for="chill"> Chill </label>
                            </div>
                            <div>
                                <input type="radio" value="prayer" checked={this.state.selectedActivity === 'prayer'} onChange={this.handleActivityChange} />
                                <label for="prayer"> Prayer </label>
                                <input type="radio" value="other" checked={this.state.selectedActivity === 'other'} onChange={this.handleActivityChange} />
                                <label for="other"> Other </label>
                            </div>
                        </div>
                        <div className="event">
                            <label for="start-time" className="text"> When are you free? </label>
                            <br />
                            <br />
                            from:
                            <input id="start-time" type="time" onChange = {e => this.handleStartTime(e)} />
                            to:
                            <input id="end-time" type="time" onChange = {e => this.handleEndTime(e)} />
                        </div>
                        <br />
                        <div className="event">
                            <label for="location" className="text"> Location: </label>
                            <input id="location" type="text" onChange = {e => this.handleLocation(e)} />
                        </div>
                        <button className="submit" type="submit" onSubmit = {this.handleSubmit}> Submit</button>
                    </form>
                </div>
            </div>
        );
        }
    }
}
export default Looker;