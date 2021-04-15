import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './App.css';
import Button from "./Button.js";
import TextInput from './TextInput';

function FriendsList(props) {
    const [friendList, setFL] = useState([])

    const friendListUI = document.getElementById("FL");
    
    const addFriend = (newFriend) => {
        setFL(friendList.concat(newFriend))
    }

    const removeFriend = (friendID) => {
        const index = friendList.indexOf(friendID);
        if (index > -1) {
            friendList.splice(index, 1);
          }
    }
    const renderFriends = () => {
        let len = friendList.length
        for (let i = 0; i < len; i++) {
            friendListUI.innerHTML += (`<li tabindex="0" class = "att">${friendListUI[i]}</li>`)
        }
    }
    // useEffect(() => {
    //     requestFriends
    // }, [friendList])


    const requestFriends = () => {
        const toSend = {
            userID: (props.currUser),
            userToAdd: (props.newFriend),
            userToRemove: (props.removedFriend)
        }
        let config = {
            headers: {
              "Content-Type": "application/json",
              'Access-Control-Allow-Origin': '*',
              }
            }
            axios.post('http://localhost:4567/FriendsList', toSend, config)
            .then(response => {
                // want to update event information
                //setFL()
            })
            .catch(function (error) {
              console.log(error);
            });
          }
          return (
            <div className="">
              <header className="">
                <title>Friends List</title>
              </header>
              <div className="friends-div">
              <p>Friends List</p>
              <ul id = "FL">
                <li>George</li>
                <li>Hamzah </li>
                <li>Ermias</li>
                <li>Amin</li>
                <li>Nick </li>
              </ul>
              </div>
            </div>
          );
}
export default FriendsList

// let friend = [];
// todoItems.push({index: 1, value: "learn react", done: false});
// todoItems.push({index: 2, value: "Go shopping", done: true});
// todoItems.push({index: 3, value: "buy flowers", done: true});

// class TodoList extends React.Component {
//   render () {
//     var items = this.props.items.map((item, index) => {
//       return (
//         <TodoListItem key={index} item={item} index={index} removeItem={this.props.removeItem} markTodoDone={this.props.markTodoDone} />
//       );
//     });
//     return (
//       <ul className="list-group"> {items} </ul>
//     );
//   }
// }
  
// class TodoListItem extends React.Component {
//   constructor(props) {
//     super(props);
//     this.onClickClose = this.onClickClose.bind(this);
//     this.onClickDone = this.onClickDone.bind(this);
//   }
//   onClickClose() {
//     var index = parseInt(this.props.index);
//     this.props.removeItem(index);
//   }
//   onClickDone() {
//     var index = parseInt(this.props.index);
//     this.props.markTodoDone(index);
//   }
//   render () {
//     var todoClass = this.props.item.done ? 
//         "done" : "undone";
//     return(
//       <li className="list-group-item ">
//         <div className={todoClass}>
//           <span className="glyphicon glyphicon-ok icon" aria-hidden="true" onClick={this.onClickDone}></span>
//           {this.props.item.value}
//           <button type="button" className="close" onClick={this.onClickClose}>&times;</button>
//         </div>
//       </li>     
//     );
//   }
// }

// class TodoForm extends React.Component {
//   constructor(props) {
//     super(props);
//     this.onSubmit = this.onSubmit.bind(this);
//   }
//   componentDidMount() {
//     this.refs.itemName.focus();
//   }
//   onSubmit(event) {
//     event.preventDefault();
//     var newItemValue = this.refs.itemName.value;
    
//     if(newItemValue) {
//       this.props.addItem({newItemValue});
//       this.refs.form.reset();
//     }
//   }
//   render () {
//     return (
//       <form ref="form" onSubmit={this.onSubmit} className="form-inline">
//         <input type="text" ref="itemName" className="form-control" placeholder="add a new todo..."/>
//         <button type="submit" className="btn btn-default">Add</button> 
//       </form>
//     );   
//   }
// }
  
// class TodoHeader extends React.Component {
//   render () {
//     return <h1>Todo list</h1>;
//   }
// }
  
// class TodoApp extends React.Component {
//   constructor (props) {
//     super(props);
//     this.addItem = this.addItem.bind(this);
//     this.removeItem = this.removeItem.bind(this);
//     this.markTodoDone = this.markTodoDone.bind(this);
//     this.state = {todoItems: todoItems};
//   }
//   addItem(todoItem) {
//     todoItems.unshift({
//       index: todoItems.length+1, 
//       value: todoItem.newItemValue, 
//       done: false
//     });
//     this.setState({todoItems: todoItems});
//   }
//   removeItem (itemIndex) {
//     todoItems.splice(itemIndex, 1);
//     this.setState({todoItems: todoItems});
//   }
//   markTodoDone(itemIndex) {
//     var todo = todoItems[itemIndex];
//     todoItems.splice(itemIndex, 1);
//     todo.done = !todo.done;
//     todo.done ? todoItems.push(todo) : todoItems.unshift(todo);
//     this.setState({todoItems: todoItems});  
//   }
//   render() {
//     return (
//       <div id="main">
//         <TodoHeader />
//         <TodoList items={this.props.initItems} removeItem={this.removeItem} markTodoDone={this.markTodoDone}/>
//         <TodoForm addItem={this.addItem} />
//       </div>
//     );
//   }
// }

// ReactDOM.render(<TodoApp initItems={todoItems}/>, document.getElementById('app'));