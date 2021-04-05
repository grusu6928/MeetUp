import React, {useState} from 'react';
import axios from 'axios';
import './App.css';
import TextInput from './TextInput';

function Lookers(props) {
    const [eventType, setEventType] = useState("")
    const [privacy, setPrivacy] = useState(true)
    const [location, setLocation] = useState([])
    const [startTime, setStartTime] = useState(0)
    const [endTime, setEndTime] = useState(0)
    const [attendees, setAttendees] = useState(["George", "Hamzah", "Ermias", "Amin", "Nick"])
    const attendeeUI = document.getElementById("attendeeList");
    

    const updateEventInfo = (eType, ePrivacy, eLocation, eStart, eEnd, eAttendees) => {
        setEventType(eType)
        setPrivacy(ePrivacy)
        setLocation(eLocation)
        setStartTime(eStart)
        setEndTime(eEnd)
        setAttendees(eAttendees)
    }
    const getPrivacy = (priv) => {
        if (priv === true) {
            return "Private Event"
        } else {
            return "Public Event"
        }
    }
    const listAttendees = () => {
        let len = attendees.length
        attendeeUI.innerHTML = "";
        for (let i = 0; i < len; i++) {
            attendeeUI.innerHTML += (`<li tabindex="0" class = "att">${attendees[i]}</li>`)
        }
    }

    const requestEvents = () => {
        const toSend = {
            lookerID: (props.currUser)
        }
        let config = {
            headers: {
              "Content-Type": "application/json",
              'Access-Control-Allow-Origin': '*',
              }
            }
            axios.post('http://localhost:4567/lookers', toSend, config)
            .then(response => {
                // want to update event information
                //updateEventInfo()
                listAttendees();
            })
            .catch(function (error) {
              console.log(error);
            });
          }
          return (
            <div className="Lookers">
              <header className="Lookers-header">
                <title>Looker: After Joining Event Friend Is In</title>
              </header>
            Event Type: Soccer!! {/*  {eventType}*/} <br/> 
            Event Privacy: {getPrivacy(privacy)}<br/>
            Location: Main Green!! {/*{location}*/}<br/>
            Start Time: 19:00 {/*{startTime}*/}<br/>
            End Time: 21:00 {/*{endTime}*/}<br/>
            {/* need to find a way to keep track of total number of people we want for this event, 
            so I can display how many more peoploe can sign up here e.g. "8 more spots open!!" */}
            Attendees: <br/>
            <ul id = "attendeeList">
            </ul>
        </div>
          );
}
export default Lookers