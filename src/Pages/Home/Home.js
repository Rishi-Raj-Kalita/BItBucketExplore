import LeftPanelComponent from "../../Components/LeftPanel/LeftPanelComponent";
import Navbar from "../../Components/NavbarComponent/Navbar";
import RightPanelComponent from "../../Components/RightPanel/RightPanelComponent";

import "./home.css";

const Home = () => {
  return (
    <>
      <div className="navbar">
        <Navbar />
      </div>
      <div className="homeContainer">
        <div className="leftContainer">
          <LeftPanelComponent />
        </div>
        <div className="rightContainer">
          <RightPanelComponent />
        </div>
      </div>
    </>
  );
};

export default Home;
