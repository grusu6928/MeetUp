import React, { Component } from 'react';
import './App.css';
import './index.css';
import FriendsList from './FriendsList';
import axios from "axios";
import {Redirect} from 'react-router-dom'
import PlacesAutocomplete, {
    geocodeByAddress,
    getLatLng
  } from "react-places-autocomplete";  


const sendEvent = (selectedActivity, startTime, endTime, location, capacity) => {
    const toSend = {
        user: localStorage.getItem("user"),
        activity: selectedActivity,
        startTime: startTime,
        endTime: endTime,
        location: location, // 2D ARRAY [lat, long]
        capacity: capacity
    }
    let config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }
        axios.post('http://localhost:4567/events', toSend, config)
        .then(response => {
            console.log("success");
        })
        .catch(function (error) {
          console.log(error);
        });
      }

class Starter extends Component {
    constructor(props) {
        super(props);
        this.state = {
          selectedActivity: null,
          startTime: null,
          endTime: null,
          location: [], // or null
          numberOfAttendees: null,
          redirect: false,
          address: "" // name of location
        };
        this.handleActivityChange = this.handleActivityChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        let data = [];
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
    handleAttendees (e){
        this.setState({
            numberOfAttendees: e.target.value
        })
    }
    handleSubmit (e){
        e.preventDefault();
            console.log(this.state.selectedActivity);
            console.log(this.state.startTime);
            console.log(this.state.endTime);
            console.log(this.state.location);
            console.log(this.state.numberOfAttendees);

        this.data = [
            {
            typeOfActivity: this.state.selectedActivity,
            startTime: this.state.startTime,
            endTime: this.state.endTime,
            location: this.state.address,
            numOfAttendees: this.state.numberOfAttendees
            }
        ]
        sendEvent(this.state.selectedActivity, this.state.startTime, this.state.endTime,
            this.state.location, this.state.numberOfAttendees);
        // this.history.push('/starter-submission');

        const starterForm = document.getElementById('starter-form')
        starterForm.reset(); 
        alert ("Thank you for submitting this event, we'll let you know if others join!")
        this.setState({redirect: true});
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
                <FriendsList />
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h1 className="welcome"> Provide your event details: </h1> 
                </header>
                <div className="form-div">
                    <form id="starter-form" onSubmit={this.handleSubmit}>
                        
                        <div className="event">
                            <p className="text"> What are you up for?</p>
                            <div>
                                <input type="radio" value="meal" checked={this.state.selectedActivity === 'meal'} onChange={this.handleActivityChange}/>
                                <label for="meal"> Meal </label>
                                <input type="radio" value="study"checked={this.state.selectedActivity === 'study'} onChange={this.handleActivityChange}/>
                                <label for="study"> Study </label>
                            </div>
                            <div>
                                <input type="radio" value="sport" checked={this.state.selectedActivity === 'sport'} onChange={this.handleActivityChange}/>
                                <label for="sport"> Sport </label>
                                <input type="radio" value="chill" checked={this.state.selectedActivity === 'chill'} onChange={this.handleActivityChange}/>
                                <label for="chill"> Chill </label>
                            </div>
                            <div>
                                <input type="radio" value="prayer"checked={this.state.selectedActivity === 'prayer'} onChange={this.handleActivityChange}/>
                                <label for="prayer"> Prayer </label>
                                <input type="radio" value="other" checked={this.state.selectedActivity === 'other'} onChange={this.handleActivityChange}/>
                                <label for="other"> Other </label>
                            </div>
                        </div>
                        <div className="event"> 
                            <label for="start-time" className="text" > Start Time? </label>
                            <input id="start-time" type="time" onChange = {e => this.handleStartTime(e)}/>
                            <br/>
                            <br/>
                            <label for="end-time" className="text"> End Time? </label>
                            <input id="end-time" type="time" onChange = {e => this.handleEndTime(e)}/>
                        </div>
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
                        <div className="event">
                            <label for="number" className="text"> Max Capacity: </label>
                            <input id="number" type="number" onChange = {e => this.handleAttendees(e)}/>

                        </div>

                        
                        {/* <Link */}
                            {/* to={{
                                pathname: "/starter-submission",
                                state: this.data // your data array of objects
                            }}> */}
                            <button className="submit" type="submit" onSubmit = {this.handleSubmit}> Submit</button>
                        {/* </Link> */}

                    </form>
                </div>
            </div>
        );
                          }
    }
}

export default Starter;