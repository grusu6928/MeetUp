import React, { Component } from 'react';
import './App.css';
import './index.css';
import FriendList from './FriendList';

class LookerSubmission extends Component {
    constructor() {
        super();
        this.state = {
            selectedType: null,
            selectedActivity: null
        };

        this.handleTypeChange = this.handleTypeChange.bind(this);
        this.handleActivityChange = this.handleActivityChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleTypeChange(e) {
        this.setState({
            selectedType: e.target.value
        })
    }
    handleActivityChange(e) {
        this.setState({
            selectedActivity: e.target.value
        })
    }
    handleSubmit(e) {
        e.preventDefault();
        // this.setState({
        //     selectedType: '',
        //     selectedActivity: ''
        // })
        const starterForm = document.getElementById('starter-form')
        starterForm.reset();
        alert("Thank you for submitting your preferences, we will now provide you with events that match your preferences!")
    }

    render() {
        return (
            <div className="margins">
                <FriendList />
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h1 className="welcome"> Provide your event preferences: </h1>
                </header>
                <div className="form-div">
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
                </div>
            </div>
        );
    }
}
export default LookerSubmission;