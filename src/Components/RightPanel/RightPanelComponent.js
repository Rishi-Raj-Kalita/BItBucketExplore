import CardContainerComponent from "../CardContainerComponent/CardContainerComponent";
import FilterComponent from "../FilterContainerComponent/FilterComponent";
import PaginationComponent from "../PaginationComponent/PaginationComponent";

import "./rightPanel.css";

const RightPanelComponent = () => {
  return (
    <>
      <div className="searchContainer">
        <FilterComponent />
      </div>
      <div className="cards">
        <CardContainerComponent />
      </div>
      <div className="pagination">
        <PaginationComponent />
      </div>
    </>
  );
};

export default RightPanelComponent;
