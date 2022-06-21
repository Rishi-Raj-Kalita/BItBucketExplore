import axios from "axios";
import { useDebugValue, useEffect, useState } from "react";
import { useGlobalContext } from "../../ContextApi/Context";
import "./Navbar.css";

const Navbar = () => {
  const [content, setContent] = useState("");
  const [after, setAfter] = useState([]);
  const {
    filterArr,
    baseUrl,
    setUserResFunct,
    setKeyword,
    keyword,
    setArray,
    loader,
    setLoader,
  } = useGlobalContext();
  const [response, setResponse] = useState({
    type: "content",
    keyword: "",
    size: 10,
    after: [],
  });

  useEffect(() => {
    setLoader(true);
    const str = localStorage.getItem("contentKey");
    // console.log("string", str);
    if (str) setContent(str);

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
      };
      res = data;
    } else {
      const data = {
        type: "content",
        keyword: str,
        size: 10,
        after: after,
      };

      // console.log("data2", data);
      res = data;
    }

    // console.log("Res", res);

    if (res.keyword === "") {
      setKeyword("");
      return;
    }
    axios
      .post(`${baseUrl}/search`, res, {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        // console.log("data from backend", res.data);
        setUserResFunct(res.data);
        setLoader(false);
      });
  }, []);

  const searchResults = () => {
    localStorage.setItem("contentKey", content);
    // console.log("filter array navbar", filterArr);
    // console.log(content);

    const arr = [];
    filterArr.map((res) => {
      if (res.keyword !== "") {
        arr.push(res);
      }
    });

    if (arr.length > 0) {
      const data = {
        type: "content",
        keyword: content,
        filter: arr,
        size: 10,
        after: after,
      };

      setResponse(data);
    } else {
      const data = {
        type: "content",
        keyword: content,
        size: 10,
        after: after,
      };

      setResponse(data);
    }
  };

  useEffect(() => {
    // console.log("response", response);
    if (response.keyword === "") {
      setKeyword("");
      return;
    }
    setLoader(true);
    axios
      .post(`${baseUrl}/search`, response, {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        // console.log("data from backend", res.data);
        setUserResFunct(res.data);
        setLoader(false);
      });
  }, [response]);

  return (
    <>
      <div className="navbar">
        <div className="productName">BitBucketExplorer</div>
        <div className="searchBarContainer">
          <div className="searchBar">
            <input
              type="text"
              className="inputBar"
              value={content}
              onChange={(e) => {
                setContent(e.target.value);
              }}
            ></input>
          </div>
          <div className="searchButton">
            <button
              onClick={() => {
                searchResults();
              }}
            >
              <div className="searchBtn">Search</div>
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Navbar;
