import useHeaderScript from "./HeaderScript";
import "./HeaderStyle.css";
import NavBar from '../nav/NavBar';
import 'bootstrap/dist/css/bootstrap.min.css';

const Header = () => {
    const { ShowMenu, isMenuOpen, UserActive } = useHeaderScript();

    console.log(UserActive)

    return (
        <>
            <header onClick={ShowMenu} className={isMenuOpen ? "headerContainer_open" : "headerContainer"}>
                <div className="container headerContent">
                    <div className="row w-100">
                        <div className="col-6 d-flex align-items-center">
                            <figure className="logoContainer">
                                <img src="/Logo.svg" alt="Logo" />
                            </figure>
                        </div>
                        <div className="col-6 d-flex justify-content-end align-items-center">
                            <div className="userBox">
                                <div className="containerUserName">
                                    <h4 className="userName">
                                        {UserActive.name + " " + UserActive.last_name}
                                    </h4>
                                    <p className="userDes">
                                        {UserActive.profession[0]}
                                    </p>
                                </div>
                                <figure>
                                    <img className="userPhoto" src={UserActive.photo} alt="" />
                                </figure>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            {isMenuOpen && <NavBar userActive={UserActive} />}
        </>
    );
}

export default Header;