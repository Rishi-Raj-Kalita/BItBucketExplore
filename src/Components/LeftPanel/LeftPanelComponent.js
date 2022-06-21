import "./leftPanel.css";

import LeftFilterComponent from "../LeftFilterComponent/LeftFilterComponent";
const LeftPanelComponent = () => {
  return (
    <>
      <div className="LeftFilterContainer">
        <LeftFilterComponent />
      </div>
    </>
  );
};

export default LeftPanelComponent;
