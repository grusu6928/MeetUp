import { AwesomeButton } from "react-awesome-button";
import './App.css';

function Button(props) {
    return (
        <AwesomeButton className="button-ting" type="primary" onPress={props.method}>
            {props.text}
        </AwesomeButton>
    );
  }
  
  export default Button;

   