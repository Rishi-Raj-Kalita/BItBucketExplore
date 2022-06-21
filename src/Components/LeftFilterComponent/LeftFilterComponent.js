import { useContext, useEffect, useState } from "react";
import "./LeftFilterComponent.css";
import axios from "axios";

import plusIcon from "../../images/plus icon.png";
import crossIcon from "../../images/Multiply.png";
import { useGlobalContext } from "../../ContextApi/Context";

const LeftFilterComponent = () => {
  const { setArray } = useGlobalContext();

  const [showFileName, setShowFileName] = useState(false);
  const [showExtension, setShowExtension] = useState(false);
  const [showPath, setShowPath] = useState(false);

  const [fileName, setFileName] = useState("");
  const [extension, setExtension] = useState("");
  const [path, setPath] = useState("");

  const [shortFileName, setShortFileName] = useState("");
  const [shortExtension, setShortExtension] = useState("");
  const [shortPath, setShortPath] = useState("");

  const [finalFileName, setFinalFileName] = useState("");
  const [finalExtension, setFinalExtension] = useState("");
  const [finalPath, setFinalPath] = useState("");

  const [filter, setFilter] = useState([
    { type: "filename", keyword: "" },
    { type: "extension", keyword: "" },
    { type: "story", keyword: "" },
  ]);

  useEffect(() => {
    var arr = [];
    // console.log("filename", finalFileName);
    var fileObj = { type: "filename", keyword: finalFileName };
    var extObj = { type: "extension", keyword: finalExtension };
    var pathObj = { type: "path", keyword: finalPath };

    arr.push(fileObj);
    arr.push(extObj);
    arr.push(pathObj);

    setFilter(arr);
  }, [finalFileName, finalExtension, finalPath]);

  useEffect(() => {
    // console.log("left filter array", filter);
    setArray(filter);
  }, [filter]);

  useEffect(() => {
    var fName = localStorage.getItem("fileNameKey");
    if (fName !== null) {
      // console.log(fName);
      var str = fName.substring(0, 8);
      if (fName.length > 8) {
        str += "...";
      }
      setFinalFileName(fName);
      setShortFileName(str);
      setShowFileName(true);
    } else {
      fName = "";
    }

    var ExtName = localStorage.getItem("extensionKey");

    if (ExtName !== null) {
      // console.log(ExtName);
      var str = ExtName.substring(0, 8);
      if (ExtName.length > 8) {
        str += "...";
      }
      setFinalExtension(ExtName);
      setShortExtension(str);
      setShowExtension(true);
    } else {
      ExtName = "";
    }

    var PathName = localStorage.getItem("pathKey");

    if (PathName !== null) {
      // console.log(PathName);
      var str = PathName.substring(0, 8);
      if (PathName.length > 8) {
        str += "...";
      }
      setFinalPath(PathName);
      setShortPath(str);
      setShowPath(true);
    } else {
      PathName = "";
    }

    var arr = [];
    // console.log("filename", finalFileName);
    var fileObj = { type: "filename", keyword: fName };
    var extObj = { type: "extension", keyword: ExtName };
    var pathObj = { type: "path", keyword: PathName };

    arr.push(fileObj);
    arr.push(extObj);
    arr.push(pathObj);

    setFilter(arr);
  }, []);

  // useEffect(() => {
  //   axios.get("http://localhost:8080/hello").then((res) => {
  //     console.log("data from backend", res.data);
  //   });
  // }, []);

  const submitFileName = () => {
    // console.log(fileName);
    if (fileName === "") return;
    localStorage.setItem("fileNameKey", fileName);
    var str = fileName.substring(0, 8);
    if (fileName.length > 8) {
      str += "...";
    }
    setFileName("");
    setFinalFileName(fileName);
    setShortFileName(str);
    setShowFileName(true);
  };

  const cancelFile = () => {
    localStorage.removeItem("fileNameKey");
    setShortFileName("");
    setFileName("");
    setFinalFileName("");
    setShowFileName(false);
  };

  const submitExtension = () => {
    if (extension === "") return;

    localStorage.setItem("extensionKey", extension);
    var str = extension.substring(0, 8);
    if (extension.length > 8) {
      str += "...";
    }
    setFinalExtension(extension);
    setShortExtension(str);
    setExtension("");
    setShowExtension(true);
  };

  const cancelExtension = () => {
    localStorage.removeItem("extensionKey");
    setShortExtension("");
    setExtension("");
    setFinalExtension("");
    setShowExtension(false);
  };

  const submitPath = () => {
    if (path === "") return;

    localStorage.setItem("pathKey", path);
    var str = path.substring(0, 8);
    if (path.length > 8) {
      str += "...";
    }
    setFinalPath(path);
    setShortPath(str);
    setPath("");
    setShowPath(true);
  };

  const cancelPath = () => {
    localStorage.removeItem("pathKey");
    setShortPath("");
    setPath("");
    setFinalPath("");
    setShowPath(false);
  };
  return (
    <>
      <div className="filters">
        <div className="filtersText">Filename</div>
        <div className="filterInputContainer">
          <input
            className="filtersInput"
            placeholder="LdapPassword.java"
            type="text"
            value={fileName}
            onChange={(e) => {
              setFileName(e.target.value);
            }}
          ></input>
          <img
            src={plusIcon}
            alt="plus icon"
            className="plusIcon"
            onClick={() => {
              submitFileName();
            }}
          />
        </div>
        {showFileName ? (
          <div className="addedFilters">
            <div className="addedFiltersText">Name: {shortFileName}</div>
            <div className="crossIcon">
              <img
                src={crossIcon}
                alt="crossIcon"
                className="crossIconImg"
                onClick={() => {
                  cancelFile();
                }}
              />
            </div>
          </div>
        ) : (
          ""
        )}
      </div>
      <div className="filters">
        <div className="filtersText">Extensions</div>
        <div className="filterInputContainer">
          <input
            className="filtersInput"
            placeholder=".json .xml ...."
            type="text"
            value={extension}
            onChange={(e) => {
              setExtension(e.target.value);
            }}
          ></input>
          <img
            src={plusIcon}
            alt="plus icon"
            className="plusIcon"
            onClick={() => {
              submitExtension();
            }}
          />
        </div>
        {showExtension ? (
          <div className="addedFilters">
            <div className="addedFiltersText">Ext: {shortExtension} </div>
            <div className="crossIcon">
              <img
                src={crossIcon}
                alt="crossIcon"
                className="crossIconImg"
                onClick={() => {
                  cancelExtension();
                }}
              />
            </div>
          </div>
        ) : (
          ""
        )}
      </div>

      <div className="filters">
        <div className="filtersText">Path</div>
        <div className="filterInputContainer">
          <input
            className="filtersInput"
            placeholder="./src/*/records"
            type="text"
            value={path}
            onChange={(e) => {
              setPath(e.target.value);
            }}
          ></input>
          <img
            src={plusIcon}
            alt="plus icon"
            className="plusIcon"
            onClick={() => {
              submitPath();
            }}
          />
        </div>
        {showPath ? (
          <div className="addedFilters">
            <div className="addedFiltersText">Path: {shortPath}</div>
            <div className="crossIcon">
              <img
                src={crossIcon}
                alt="crossIcon"
                className="crossIconImg"
                onClick={() => {
                  cancelPath();
                }}
              />
            </div>
          </div>
        ) : (
          ""
        )}
      </div>
    </>
  );
};

export default LeftFilterComponent;
