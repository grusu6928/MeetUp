function TextInput(props) {
    return (
        <div>
            <label>{props.label}</label>
            <input className="inputBox" type={"text"} onChange={(event) => props.change(event.target.value)}></input>
        </div>
    );
  }
  
  export default TextInput;

  