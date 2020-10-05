import React, {useState} from 'react';
import axios from 'axios';
import './LoginForm.css';
import {API_BASE_URL, ACCESS_TOKEN_NAME} from '../../constants/apiConstants';
import { withRouter } from "react-router-dom";

function LoginForm(props) {
    const [state , setState] = useState({
        email : "",
        password : "",
        successMessage: null
    })
    const handleChange = (e) => {
        const {id , value} = e.target   
        setState(prevState => ({
            ...prevState,
            [id] : value
        }))
    }

    const handleSubmitClick = (e) => {
        e.preventDefault();
        let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if ( !re.test(state.email) ) { 
            props.showError("Please enter a valid email id");
        }
        else if(state.password.length == 0){
            props.showError("Please enter a password"); 
        } 
        else{
            
            const payload={
                "email":state.email,
                "password":state.password,
            }
        axios.post('http://localhost:8080/api/auth/login/', payload)
            .then(function (response) {
                if(response.status === 200){
                    setState(prevState => ({
                        ...prevState,
                        'successMessage' : 'Login successful. Redirecting to home page..'   
                    }))
                    localStorage.setItem(ACCESS_TOKEN_NAME,response.data.token);
                    redirectToHome();
                    props.showError(null)
                }
            })
            .catch(function (error) {
                // if(error.response.data.message === "Email already registered")
                // {
                //     props.showError("Email already registered");
                // }
                // if(error.response.data.message === "Incorrect Password")
                // {
                //     props.showError("Incorrect password entered. Please re check.");
                // }
                if(error.response.status === 401){
                    props.showError("Please check your password or email id");
                } 
                console.log(error);
            });
        }
        
    }

    const redirectToHome = () => {
        props.updateTitle('Home')
        props.history.push('/home');
    }

    const redirectToRegister = () => {
        props.history.push('/register'); 
        props.updateTitle('Register');
    }
    return(
        <div className="card col-12 col-lg-4 login-card mt-5 pt-3 hv-center">
            <form>
                <div className="form-group text-left">
                <label htmlFor="exampleInputEmail1">Email address</label>
                <input type="email"
                       className="form-control" 
                       id="email" 
                       aria-describedby="emailHelp" 
                       placeholder="Enter email" 
                       value={state.email}
                       onChange={handleChange}
                />
                </div>
                <div className="form-group text-left">
                <label htmlFor="exampleInputPassword1">Password</label>
                <input type="password" 
                       className="form-control" 
                       id="password" 
                       placeholder="Password"
                       value={state.password}
                       onChange={handleChange} 
                />
                </div>
                <div className="form-check">
                </div>
                <button 
                    type="submit" 
                    className="btn btn-dark mt-4"
                    onClick={handleSubmitClick}
                >Let's Go!</button>
            </form>
            <div className="alert alert-success mt-2" style={{display: state.successMessage ? 'block' : 'none' }} role="alert">
                {state.successMessage}
            </div>
            <div className="registerMessage">
                <span>Don't have an account? </span>
                <span className="loginText" onClick={() => redirectToRegister()}>Register</span> 
            </div>
        </div>
    )
}

export default withRouter(LoginForm);