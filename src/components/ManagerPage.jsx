import React from "react";
import './ManagerPage.css';
import ManagerHeader from "./ManagerHeader";

const ManagerPage = () => {
    return(

        <>
            <ManagerHeader />

            {/* <div className="ChangePage">
                
            </div> */}

            <div className='Shape'>
                <div className="Frame">
                    <span><img src="/home.png" alt='logo' className="logo"></img></span>
                    <span className="Name">Home</span>
                </div>
                <div className="Frame">
                    <img src="\ic_invoice.png" alt='logo' className="logo"></img>
                    <span className="Name">사용자 관리</span>
                </div>
                <div className="Frame">
                    <img src="\home.png" alt='logo' className="logo"></img>
                    <span className="Name">ESL 기기 관리</span>
                </div>
                <div className="Frame">
                    <img src="\home.png" alt='logo' className="logo"></img>
                    <span className="Name">부스 관리</span>
                </div>
                <div className="Frame">
                    <img src="\ic_message.png" alt='logo' className="logo"></img>
                    <span className="Name">공지 사항</span>
                </div>
            </div>

            

            {/* <div className="MainFrame">
                안녕
            </div> */}
        </>
    );
};

export default ManagerPage;