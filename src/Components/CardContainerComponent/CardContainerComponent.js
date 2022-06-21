import Card from "../Cards/CardComponent";
import "./CardContainer.css";

import { useEffect, useState } from "react";
import NoResult from "../NoResultComponent/NoResult";
import { useGlobalContext } from "../../ContextApi/Context";
import Loader from "../Loader/Loader";

const CardContainerComponent = () => {
  const [noResult, setNoResult] = useState(true);
  const { userResponse } = useGlobalContext();
  const [userResArr, setUserResArr] = useState([]);

  const { loader, setLoader } = useGlobalContext();

  useEffect(() => {
    console.log("userResponse", userResponse);
    if (userResponse.total) {
      setNoResult(false);
      setUserResArr(userResponse.result);
    } else setNoResult(true);
  }, [userResponse]);

  if (loader) {
    return (
      <div className="loaderContainer">
        <Loader />
      </div>
    );
  }
  return (
    <>
      {noResult ? (
        <div className="NoResultsContainer">
          <NoResult />
        </div>
      ) : (
        <div className="cardsContainer">
          <div className="card">
            {userResArr.map((res, id) => {
              return (
                <div>
                  <Card res={res} key={id} />
                  {id !== userResArr.length - 1 ? (
                    <div>
                      {id == 0
                        ? localStorage.setItem("sort_before", res.sort)
                        : ""}
                      <div className="seperator"></div>
                    </div>
                  ) : (
                    localStorage.setItem("sort_after", res.sort)
                  )}
                </div>
              );
            })}
          </div>
        </div>
      )}
    </>
  );
};

export default CardContainerComponent;
