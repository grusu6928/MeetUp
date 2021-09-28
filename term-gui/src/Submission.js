// react context global state 
import axios from 'axios';
import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import './App.css';
import './index.css';

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
            attendeeList: [],
            algoStr: ""
        };
        this.handleAttendeeList = this.handleAttendeeList.bind(this);
    }
    handleAttendeeList(newList, str) {
        this.setState({
            attendeeList: newList,
            algoStr: str
        } 
        )
    }
    checkAttendees() {
        return this.props.location.state[0].numOfAttendees != null;

    }
    
getAttendees () {
    const toSend = {
        user: localStorage.getItem("user"),
        userType: this.props.location.state[0].userType
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
            this.handleAttendeeList(response.data, response.data[0])
            console.log("str " + response.data[0])
        })
        .catch(function (error) {
          console.log(error);
        });
      }

    componentDidMount() {
        console.log("before initial")
        this.getAttendees()
        console.log("after initial")
         setInterval(
          () => {
              if (this.state.algoStr === "We are working on matching you with other users!") {
                  console.log("called")
                  this.getAttendees()
              }
            },
          5000
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
        let listStr = "";
        if (this.state.algoStr === "We are working on matching you with other users!" || this.state.algoStr === "Sorry but we weren't able to find you any matches") {
            listStr = "";
        } else {
            console.log("numAttendees " + this.props.location.state[0].numOfAttendees)
            console.log("len " + this.state.attendeeList.length)
            let spots = this.props.location.state[0].numOfAttendees  - this.state.attendeeList.length
            listStr = "and " + spots + " more open spot(s)"
        }
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
                {/*<FriendList />*/}
                <header>
                    <h1 className="home">
                        <a href="/home"> Home </a>
                    </h1>
                    <h3 className="logout" onClick={() => this.logout()}>Logout</h3>
                </header>
                <p className="event-deets"> Here are your event details: </p>
                <div className="submission-div">
                    <p className="text"> Type of Activity: <span className="inner-text"> {this.props.location.state[0].typeOfActivity} </span></p> 
                    <p className="text"> Start Time: <span className="inner-text">{this.props.location.state[0].startTime}</span>  </p> 
                    <p className="text"> End Time: <span className="inner-text"> {this.props.location.state[0].endTime} </span></p> 
                    <p className="text"> Location: <span className="inner-text"> {this.props.location.state[0].location} </span></p>
                    {this.checkAttendees() ? (
                        <p className="text">  Attendees: <span className="inner-text"> {this.state.attendeeList.map((attendants, index) => (
                            <p>
                                {attendants}
                            </p>
                        ))} <span className="text"> {listStr} </span></span></p>
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