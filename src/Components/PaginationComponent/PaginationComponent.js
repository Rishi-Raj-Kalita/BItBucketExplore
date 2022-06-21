import axios from "axios";

import rightCursor from "../../images/Chevron Right.png";
import leftCursor from "../../images/Chevron Left.png";

import "./Pagination.css";
import { useGlobalContext } from "../../ContextApi/Context";
import { useState } from "react";

const PaginationComponent = () => {
  const { totalResults, loader } = useGlobalContext();
  const [currPage, setCurrPage] = useState(1);
  const finalPage = Math.ceil(totalResults / 10);

  const { setLoader, baseUrl, setUserResFunct } = useGlobalContext();

  const prevPage = () => {
    if (currPage == 1) return;
    setLoader(true);
    setCurrPage(currPage - 1);
    const str = localStorage.getItem("contentKey");
    var next = localStorage.getItem("sort_before");

    var num = next.split(",");

    var after = [];

    for (let i = 0; i < num.length; i++) {
      after.push(parseFloat(num[i]));
    }
    // console.log("string", str);

    var fileKey = localStorage.getItem("fileNameKey");
    var extKey = localStorage.getItem("extensionKey");
    var pathKey = localStorage.getItem("pathKey");

    if (!fileKey) fileKey = "";
    if (!extKey) extKey = "";
    if (!pathKey) pathKey = "";

    var array = [];

    var fileObj = { type: "filename", keyword: fileKey };
    var extObj = { type: "extension", keyword: extKey };
    var pathObj = { type: "path", keyword: pathKey };

    array.push(fileObj);
    array.push(extObj);
    array.push(pathObj);

    const arr = [];

    array.map((res) => {
      if (res.keyword !== "") {
        arr.push(res);
      }
    });

    var res;
    if (arr.length > 0) {
      const data = {
        type: "content",
        keyword: str,
        filter: arr,
        size: 10,
        after: after,
        next: 1,
      };
      res = data;
    } else {
      const data = {
        type: "content",
        keyword: str,
        size: 10,
        after: after,
        next: 1,
      };

      // console.log("data2", data);
      res = data;
    }

    console.log("Res", res);

    axios
      .post(`${baseUrl}/search`, res, {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        // console.log("data from backend", res.data);
        setUserResFunct(res.data);
        setLoader(false);
      });
  };

  const nextPage = () => {
    if (currPage == finalPage) return;
    setLoader(true);
    setCurrPage(currPage + 1);
    const str = localStorage.getItem("contentKey");
    var next = localStorage.getItem("sort_after");

    var num = next.split(",");

    var after = [];

    for (let i = 0; i < num.length; i++) {
      after.push(parseFloat(num[i]));
    }
    // console.log("string", str);

    var fileKey = localStorage.getItem("fileNameKey");
    var extKey = localStorage.getItem("extensionKey");
    var pathKey = localStorage.getItem("pathKey");

    if (!fileKey) fileKey = "";
    if (!extKey) extKey = "";
    if (!pathKey) pathKey = "";

    var array = [];

    var fileObj = { type: "filename", keyword: fileKey };
    var extObj = { type: "extension", keyword: extKey };
    var pathObj = { type: "path", keyword: pathKey };

    array.push(fileObj);
    array.push(extObj);
    array.push(pathObj);

    const arr = [];

    array.map((res) => {
      if (res.keyword !== "") {
        arr.push(res);
      }
    });

    var res;
    if (arr.length > 0) {
      const data = {
        type: "content",
        keyword: str,
        filter: arr,
        size: 10,
        after: after,
        next: 0,
      };
      res = data;
    } else {
      const data = {
        type: "content",
        keyword: str,
        size: 10,
        after: after,
        next: 0,
      };

      // console.log("data2", data);
      res = data;
    }

    console.log("Res", res);

    axios
      .post(`${baseUrl}/search`, res, {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        // console.log("data from backend", res.data);
        setUserResFunct(res.data);
        setLoader(false);
      });
  };

  return (
    <>
      {totalResults > 0 && loader === false ? (
        <div className="PaginationContainer">
          <div
            className="next_prev"
            onClick={() => {
              prevPage();
            }}
          >
            <img src={leftCursor} alt="leftCursor" />
            <div>Previous</div>
          </div>

          <div className="paginationText">
            {currPage} of {finalPage}
          </div>
          <div className="next_prev" onClick={() => nextPage()}>
            <div>Next</div>
            <img src={rightCursor} alt="rightCursor" />
          </div>
        </div>
      ) : (
        ""
      )}
    </>
  );
};

export default PaginationComponent;
