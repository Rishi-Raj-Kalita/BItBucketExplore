import "./NoResult.css";

import search from "../../images/Search.png";
import { useGlobalContext } from "../../ContextApi/Context";

const NoResult = () => {
  const { keyword } = useGlobalContext();
  //   console.log("keyword", keyword);
  return (
    <>
      <div className="NoResultContainer">
        <div>
          <img className="searchIcon" src={search} alt="searchIcon" />
        </div>
        {keyword !== "" ? (
          <div className="NoResultText">
            We couldn't find any Files matching given details
          </div>
        ) : (
          ""
        )}
      </div>
    </>
  );
};

export default NoResult;
