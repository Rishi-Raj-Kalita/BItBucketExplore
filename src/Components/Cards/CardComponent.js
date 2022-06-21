import "./CardComponent.css";

import bitIcon from "../../images/bitbucket.png";
import expandIcon from "../../images/Expand.png";
import minus from "../../images/Minus.png";
import { useEffect, useState } from "react";

const Card = ({ res }) => {
  const [expand, setExpand] = useState(false);

  const func = (val) => {
    console.log("changing state", val);
    setExpand(val);
  };

  return (
    <>
      <div className="card">
        <div className="repoName">
          <img src={bitIcon} alt="bit bucket icon" className="bitIcon" />
          <div className="repoText">
            {res.projectName}/{res.repoName}/{res.fileName}
          </div>
          <div className="scoreText">score : {res._score}</div>
        </div>
        <div className="repoDescContainer">
          <div className="repoDescription">
            <div className="expandIcon">
              {expand === false ? (
                <img
                  src={expandIcon}
                  alt="expandIcon"
                  className="expandIconImg"
                  onClick={() => {
                    func(true);
                  }}
                />
              ) : (
                <img
                  src={minus}
                  alt="minusIcon"
                  className="expandImg"
                  onClick={() => {
                    func(false);
                  }}
                />
              )}
            </div>
            <div className="repoDescriptionText">
              {expand === false
                ? res.content
                    .substr(0, 300)
                    .split("\n\n")
                    .map((paragraph) => (
                      <p>
                        {paragraph.split("\n").map((line, index) =>
                          index > 0 ? (
                            <span>
                              <br />
                              {line}
                            </span>
                          ) : (
                            line
                          )
                        )}
                        <br />
                        ...
                      </p>
                    ))
                : res.content.split("\n\n").map((paragraph) => (
                    <p>
                      {paragraph.split("\n").map((line, index) =>
                        index > 0 ? (
                          <span>
                            <br />
                            {line}
                          </span>
                        ) : (
                          line
                        )
                      )}
                    </p>
                  ))}
            </div>
          </div>
        </div>

        <div className="matchesContainer">
          <div className="matches">
            <div className="matchesText">Type: {res.type}</div>
          </div>
          <div className="matches">
            <div className="matchesText">Size: {res.size}</div>
          </div>
          <div className="matches">
            <div className="matchesText">
              {res.extension[0] !== undefined
                ? `Ext: ${res.extension[0]}`
                : `Ext: [ ]`}
            </div>
          </div>
          <div className="matches">
            <div className="matchesText">Path: /{res.path}</div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Card;
