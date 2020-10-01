import React from 'react';
// import logo from './assets/ways-save-movie-theater-tickets-2136x1427.JPG';
function Header() {
    return(
        <nav class="navbar navbar-dark bg-transparent mb-5 pt-5">
            {/* <div className="row col-12 d-flex justify-content-center text-dark"> */}
            <div className="row col-12 d-flex justify-content-center text-dark">
            <img src={require('/Users/pranamyavadlamani/Desktop/Academics/Third Sem/Software Engineering/prevue-frontend/src/assets/logo_size.jpg')} />
            {/* <span className="h3 bgstyle">PreVue</span> */}
            </div>
        </nav>
    )
}
export default Header;