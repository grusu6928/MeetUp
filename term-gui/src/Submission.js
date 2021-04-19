import React, { Component } from 'react';
import './App.css';
import './index.css';
import FriendList from './FriendList';
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
            attendeeList: ['george', 'amin', 'hamzah', 'ermias', 'amin', 'hamzah', 'ermias', 'amin', 'hamzah', 'ermias']
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
        // setInterval(
        //     () =>  {
        //         let currentDateTime = new Date()
        //         let endTime = sendEndTime("get", localStorage.getItem("user"), null)
        //         console.log("endTime: " + endTime)
        //         if(endTime !== null) {
        //             let hoursMinutes = endTime.split(":")
        //             if (currentDateTime.getHours() >= parseInt(hoursMinutes[0]) && currentDateTime.getMinutes() >= parseInt(hoursMinutes[1])) {
        //                 sendEndTime("set", localStorage.getItem("user"), null)
        //               }       
        //         }   
        //   },
        //     5000
        //   );
      }
      logout() {
        localStorage.clear()
        console.log(localStorage.getItem("user"))
        this.setState({user: null})
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
                <FriendList />
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h3 className="logout" onClick={() => this.logout()}>Logout</h3>
                </header>
                <p className="event-deets"> Here are your event details: </p>
                <div className="submission-div">
                <p className="text"> Event Privacy: <span className="inner-text"> {this.props.location.state[0].typeOfEvent} </span> </p> 
                    <p className="text"> Type of Activity: <span className="inner-text"> {this.props.location.state[0].typeOfActivity} </span></p> 
                    <p className="text"> Start Time: <span className="inner-text">{this.props.location.state[0].startTime}</span>  </p> 
                    <p className="text"> End Time: <span className="inner-text"> {this.props.location.state[0].endTime} </span></p> 
                    <p className="text"> Location: <span className="inner-text"> {this.props.location.state[0].location} </span></p>
                    {this.checkAttendees() ? (
                        <p className="text">  Attendees: <span className="inner-text"> {this.state.attendeeList.map((attendants, index) => (
                            <p>
                                {attendants}
                            </p>
                        ))} and <span className="text"> {this.props.location.state[0].numOfAttendees - this.state.attendeeList.length} </span> more open spots </span></p> 
                    ) : (
                        <p className="text"></p>
                    )}
                    </div>
            </div>
        );
            }
    }
}
export default Submission;