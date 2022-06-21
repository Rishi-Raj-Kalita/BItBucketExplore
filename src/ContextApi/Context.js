import { useContext, createContext, useState } from "react";

const AppContext = createContext();

const AppProvider = ({ children }) => {
  const [filterArr, setFilterArr] = useState([]);

  const [loader, setLoader] = useState(false);

  const [totalResults, setToalResults] = useState(0);

  const [userResponse, setUserResponse] = useState({ total: 0, result: [] });

  const [keyword, setKeyword] = useState("");

  const baseUrl = "http://localhost:8080";

  const setArray = (filterArray) => {
    // console.log("context filter array", filterArray);
    setFilterArr(filterArray);
  };

  const setUserResFunct = (response) => {
    setUserResponse(response);
    setToalResults(response.total);
    var str = localStorage.getItem("contentKey");
    // localStorage.setItem("responseKey", response);

    if (str) {
      setKeyword(str);
    }
  };

  return (
    <AppContext.Provider
      value={{
        setArray,
        filterArr,
        baseUrl,
        setUserResFunct,
        userResponse,
        totalResults,
        keyword,
        setKeyword,
        loader,
        setLoader,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
