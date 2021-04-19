import React, { Component, useEffect } from 'react';
import './App.css';
import './index.css';
import FriendList from './FriendList';
import axios from "axios";
import {Redirect} from 'react-router-dom'
import Friend from './Friend';
import PlacesAutocomplete, {
    geocodeByAddress,
    getLatLng
  } from "react-places-autocomplete";  




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

const sendEndTime = (reqType, currUser, endTime) => {
    const toSend = {
        requestType: reqType,
        user: currUser,
        endTime: endTime
    }
    let config = {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
        }
    }
    axios.post('http://localhost:4567/endtime', toSend, config)
        .then(response => {
            console.log("starter sendEndTime response " + response.data)
            if (reqType === ("get")) {
                return response.data;
            } else if (reqType === "set") {
                console.log(response.data)
            }
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
            address: "",
            user: localStorage.getItem("user"),
            currentDateTime: new Date()
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
    checkEventTime() {
        let hour = parseInt(this.state.endTime.split(":")[0])
        let minutes = parseInt(this.state.endTime.split(":")[1])
        let endTime;
        console.log(typeof(this.state.currentDateTime))
        if (this.state.currentDateTime !== null || typeof(this.state.currentDateTime) !== 'undefined'){
          endTime = new Date(this.state.currentDateTime.getFullYear(), this.state.currentDateTime.getMonth(), this.state.currentDateTime.getDay(), hour, minutes, 0.0, 0.0)
        }
        if (this.state.currentDateTime.getHours >= endTime.getHours && this.state.currentDateTime.getMinutes() >= endTime.getMinutes) {
            console.log("removed")
            sendEndTime("set", localStorage.getItem("user"), null);
        }
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
                location: this.state.address,
            }
        ]
    localStorage.setItem("data", JSON.stringify(this.data))        
    sendEvent(this.state.selectedActivity, this.state.startTime, this.state.endTime,
            this.state.location);
        sendEndTime("set", localStorage.getItem("user"), this.state.endTime);
       
        //TODO: lookerForm?
        const starterForm = document.getElementById('starter-form')
        starterForm.reset();
        alert ("Thank you for submitting your preferences, we will now provide you with events that match your preferences!")
        // window.location.href = "/starter-submission"
        this.setState({redirect: true});
    }
    componentDidMount() { 
        setInterval(
          () =>  {this.setState({currentDateTime: new Date()});
          if (this.state.endTime != null) {
            this.checkEventTime();
          }  
        },
          5000
        );
        this.setState({currentDateTime: new Date()})
      }
      componentWillUnmount() {
        clearInterval(this.interval);
      }
      handleSelect = address => {
        console.log(address)
        geocodeByAddress(address)
          .then(results => getLatLng(results[0]))
          .then(latLng => this.setState({location: [latLng["lat"], latLng["lng"]]}))
          .catch(error => console.error('Error', error));
          this.setState({address});
        console.log("location state", this.state.location)
      };
    
      handleChange = address => {
        this.setState({address});
      };  

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
            if(sendEndTime("get", localStorage.getItem("user"), null) !== null) {
                console.log(localStorage.getItem("data"))
                console.log("endtime redirect")
                return (
                  <Redirect
                  to={{
                      pathname: "/submission",
                      state: JSON.parse(localStorage.getItem("data"))
                  }}
                  />
                  );
              }
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
                            <PlacesAutocomplete
                        value={this.state.address}
                        onChange={this.handleChange}
                        onSelect={this.handleSelect}
      >
        {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
          <div>
            <input
              {...getInputProps({
                placeholder: 'Search Places ...',
                className: 'location-search-input',
              })}
            />
            <div className="autocomplete-dropdown-container">
              {loading && <div>Loading...</div>}
              {console.log(suggestions)}
              {suggestions.map(suggestion => {
                const className = suggestion.active
                  ? 'suggestion-item--active'
                  : 'suggestion-item';
                // inline style for demonstration purpose
                const style = suggestion.active
                  ? { backgroundColor: '#fafafa', cursor: 'pointer' }
                  : { backgroundColor: '#ffffff', cursor: 'pointer' };
                return (
                  <div
                    {...getSuggestionItemProps(suggestion, {
                      className,
                      style,
                    })}
                  >
                    <span>{suggestion.description}</span>
                    {console.log("address" + this.state.address)}
                  </div>
                );
              })}
            </div>
          </div>
        )}
      </PlacesAutocomplete>
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