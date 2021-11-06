import React from "react";
import './ManagerHeader.css';

export default function ManagerHeader(){
    return(
        <>
            <div className="ManagerHeader">
                <span className="header-logo">
                    <img src="/headerlogo.png" alt="로고이미지" />
                </span>
                <span className="managerName">
                    <span className="haderlogo"><img src="/ic_message.png" alt="이미지" /></span>
                    <span className="haderlogo"><img src="/ic_message.png" alt="이미지" /></span>
                    <span>관리자 이름</span>
                </span>
            </div>
        </>
    );
};