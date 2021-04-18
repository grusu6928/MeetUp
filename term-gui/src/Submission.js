import React, { Component } from 'react';
import './App.css';
import './index.css';
import FriendsList from './FriendsList';
// react context global state 
import axios from 'axios';
import { Redirect } from 'react-router-dom';

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
            console.log("submission sendEndTime response " + response.data)
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

class Submission extends Component {
    constructor(props) {
        super(props);
        this.state = {
            attendeeList: ['george', 'amin', 'hamzah', 'ermias']
        };
        this.handleAttendeeList = this.handleAttendeeList.bind(this);
    }
    handleAttendeeList(newList) {
        this.setState({
            attendeeList: newList
        } 
        )
    }
    checkAttendees() {
        if (this.props.location.state[0].numOfAttendees != null) {
            return true;
        }
        return false;
    }
    getAttendees () {
    const toSend = {
        user: localStorage.getItem("user"),
    }
    let config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }
        axios.post('http://localhost:4567/attendees', toSend, config)
        .then(response => {
            console.log("success");
            this.handleAttendeeList(response.data)
        })
        .catch(function (error) {
          console.log(error);
        });
      }

    componentDidMount() {
         setInterval(
          () => this.getAttendees(),
          10000
        );
        setInterval(
            () =>  {
                let currentDateTime = new Date()
                let endTime = sendEndTime("get", localStorage.getItem("user"), null)
                console.log("endTime: " + endTime)
                if(endTime !== null) {
                    let hoursMinutes = endTime.split(":")
                    if (currentDateTime.getHours() >= parseInt(hoursMinutes[0]) && currentDateTime.getMinutes() >= parseInt(hoursMinutes[1])) {
                        sendEndTime("set", localStorage.getItem("user"), null)
                      }       
                }   
          },
            5000
          );
      }
    
    render() {
        console.log(this.props.location.state[0])
        if(localStorage.getItem("user") == null) {
            return (
                <Redirect
                to={{
                    pathname: "/",
                }}
                />
                );
        } else {
        return (
            <div className="margins">
                <FriendsList />
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h1 > Type of Activity: {this.props.location.state[0].typeOfActivity} </h1> 
                    <h1 > Start Time: {this.props.location.state[0].startTime} </h1> 
                    <h1 > End Time: {this.props.location.state[0].endTime} </h1> 
                    <h1 > Location: {this.props.location.state[0].location} </h1> 
                    {this.checkAttendees() ? (
                        <h1 > Attendees: {this.state.attendeeList.map((attendants, index) => (
                            <h1>
                                {attendants}
                            </h1>
                        ))} and {this.props.location.state[0].numOfAttendees - this.state.attendeeList.length} more open spots</h1> 
                    ) : (
                        <h1></h1>
                    )}
                </header>
               
            </div>
        );
            }
    }
}
export default Submission;