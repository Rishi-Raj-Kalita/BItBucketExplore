import { useState } from "react";
import { useGlobalContext } from "../../ContextApi/Context";
import "./filterComponent.css";

const FilterComponent = () => {
  const { userResponse, totalResults, loader } = useGlobalContext();

  return (
    <>
      <div className="filterCotainer">
        <div className="results">
          <div className="searchResults">
            {totalResults > 0 && loader === false ? (
              <div>{totalResults} File Results</div>
            ) : (
              ""
            )}
          </div>
        </div>
        <div className="filterButtonContainer">
          <button className="filterButton">
            <div className="btnText">Type : Files</div>
          </button>
        </div>
      </div>
      <div className="seperator"></div>
    </>
  );
};

export default FilterComponent;
