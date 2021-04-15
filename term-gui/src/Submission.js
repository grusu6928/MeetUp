import React, { Component } from 'react';
import './App.css';
import './index.css';
import FriendsList from './FriendsList';
// react context global state 


class Submission extends Component {
    constructor(props) {
        super(props);
        this.state = {
            attendeeCounter: 0,
            attendeeList: null
        };
        console.log((this.props.location.state[0]))
    }
    checkAttendees() {
        if (this.props.location.state[0].numOfAttendees != null) {
            return true;
        }
        return false;
    }
    


    render() {
        return (
            <div className="margins">
                <FriendsList />
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h1 > Event Privacy: {this.props.location.state[0].typeOfEvent} </h1> 
                    <h1 > Type of Activity: {this.props.location.state[0].typeOfActivity} </h1> 
                    <h1 > Start Time: {this.props.location.state[0].startTime} </h1> 
                    <h1 > End Time: {this.props.location.state[0].endTime} </h1> 
                    <h1 > Location: {this.props.location.state[0].location} </h1> 
                    {this.checkAttendees() ? (
                        <h1 > Attendees: and {this.props.location.state[0].numOfAttendees} more open spots</h1> 
                    ) : (
                        <h1></h1>
                    )}
                    
                </header>
               

                {/* <div className="form-div">
                    <form id="starter-form" onSubmit={this.handleSubmit}>
                        <div class="event">
                            <p className="text"> Type of event: </p>
                            <input type="radio" value="public" checked={this.state.selectedType === 'public'} onChange={this.handleTypeChange} />
                            <label for="public"> Public </label>
                            <input type="radio" value="private" checked={this.state.selectedType === 'private'} onChange={this.handleTypeChange} />
                            <label for="private"> Private </label>
                        </div>
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
                            <input id="start-time" type="time" />
                            to:
                            <input id="end-time" type="time" />
                        </div>
                        <br />
                        <div className="event">
                            <label for="location" className="text"> Location: </label>
                            <input id="location" type="text" />
                        </div>
                        <button className="submit" type="submit"> Submit</button>
                    </form>
                </div> */}
            </div>
        );
    }
}
export default Submission;