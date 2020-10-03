import React from 'react';
import logo from '../../assets/logo_size.jpg';
function Header() {
    return(
        <nav class="navbar navbar-dark bg-transparent mb-5 pt-5">
            <div className="row col-12 d-flex justify-content-center text-dark">
            <img src={logo} alt="" />

            <button 
                    type="logout" 
                    className="btn btn-light mt-4 justify-content-between"
                    // onClick={handleSubmitClick}
                >Logout</button>
            </div>
        </nav>
    )
}
export default Header;