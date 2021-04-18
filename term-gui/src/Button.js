import { AwesomeButton } from "react-awesome-button";
import "react-awesome-button/dist/styles.css";

function Button(props) {
    return (
        <AwesomeButton type="primary" onPress={props.method}>
            {props.text}
        </AwesomeButton>
    );
  }
  
  export default Button;

   