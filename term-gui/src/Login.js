import axios from 'axios';
import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import { Redirect } from "react-router-dom";
import './App.css';
import Button from "./Button";
import { useAppContext } from './ContextUtil';


function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [redirect, setRedirect] = useState(false)
  const { userHasAuthenticated } = useAppContext();
  function validateForm() {
    return isValidEmail(email) && isValidPassword(password);
  }
  function isValidEmail(acct) {
      const emailRegex = RegExp('^[a-zA-Z0-9._%+-]+@brown+\.edu$')
      return emailRegex.test(acct)
  }
  function isValidPassword(pass) {
      return pass.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
    requestAuthentication(email, password);
      // if true, then we wanna redirect to another page (home) and save user in session 
  }
  const requestAuthentication = () => {
    const toSend = {
        email: email,
        pass: password,
    }
    let config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }
        axios.post('http://localhost:4567/login', toSend, config)
        .then(response => {
            if(response.data) {
              localStorage.setItem("user", toSend.email);
              setEmail(toSend.email);
              window.location.reload(true);
              console.log(localStorage.getItem("user"))
            } else {
                // rerender page, with alert
                setRedirect(false);
                console.log("failure");
            }
        })
        .catch(function (error) {
          console.log(error);
        });
      }
      console.log(localStorage.getItem("user"));
      if (localStorage.getItem("user") !== null) {
        return(
          <Redirect
          to={{
              pathname: "/home"
          }}
          />
        );}
      else{
  return (
    <div className="margins1">
      <p className="meet-up"> MeetUp!</p>
      <div className="login-div">
        <h1 className="login">
        Login
      </h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group size="lg" controlId="email">
          <Form.Label>Email: </Form.Label>
          <Form.Control
            autoFocus
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>
        <br/>
        <Form.Group size="lg" controlId="password">
          <Form.Label class="password">Password: </Form.Label>
          <Form.Control
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>
        <br/>
              <Button class="login-button" type="submit" text= "Login" disabled={!validateForm()}>
                  Login
              </Button>
      </Form>
      <p className="signup-text"> New to MeetUp?   
      <a class="signup-button" href="/signup">
              <Button type="submit" text="Create an Account">
                 Sign Up
              </Button>
              </a>
              </p>
      </div>
    </div>
  );
      }
}
export default Login;