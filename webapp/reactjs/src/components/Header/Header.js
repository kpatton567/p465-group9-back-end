import React, {useState} from 'react';
import logo from '../../assets/logo_size.jpg';
import {API_BASE_URL, ACCESS_TOKEN_NAME} from '../../constants/apiConstants';
import { withRouter } from "react-router-dom";
import axios from 'axios';

function Header(props) {
    const [state , setState] = useState({

    })
    const handleLogoutClick = (e) => {
        e.preventDefault();
        var token = localStorage.getItem(ACCESS_TOKEN_NAME)
        const body = {};
        axios.post('http://localhost:8080/api/auth/logout/', body, {
        headers: {
            'Authorization': 'Bearer ' + token
        }
        }).then(function (response) {
                if(response.status === 200){
                    setState(prevState => ({
                        ...prevState,
                        'successMessage' : 'Logged out' ,
                    }))
                    localStorage.clear();
                    redirectToLogin();
                    props.showError(null)
                }
                else if(response.code === 204){
                    props.showError("Username and password do not match");
                }
                else{
                    props.showError("Username does not exists");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    const redirectToLogin = () => {
        props.updateTitle('Login')
        props.history.push('/login'); 
    }
    return(
        <nav class="navbar navbar-dark bg-transparent mb-5 pt-5">
            <div className="row col-12 d-flex justify-content-center text-dark">
            <img src={logo} alt="" />

            <button 
                    type="logout" 
                    className="btn btn-light mt-4 justify-content-between"
                    onClick={handleLogoutClick}
                    style={{display: localStorage.getItem(ACCESS_TOKEN_NAME) ? 'block' : 'none' }}
                >Logout</button>
            </div>
        </nav>
    )
}
export default withRouter(Header);