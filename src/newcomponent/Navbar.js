import React, {useState} from 'react'
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import {Link} from 'react-router-dom';
import {SidebarData} from './SidebarData';
import './Navbar.css';
import {IconContext} from 'react-icons'

function Navbar() {
    
    const [sidebar, setsidebar] = useState(false);

    const showSidebar = () => setsidebar(!sidebar);

    return (
        <>
        <IconContext.Provider value={{color: '#060b26'}}>
                <div className="navbar">
                    <span>
                        <Link to="#" className="menu-bars">
                            <FaIcons.FaBars onClick={showSidebar} />
                        </Link>
                    </span>
                    <span><img src="/headerlogo.png" alt="로고이미지" /></span>
                    <span className="manager-name">
                        <span><FaIcons.FaRegCommentAlt /></span>
                        <span>홍길동</span>
                    </span>
                </div>
            <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
                <ui className='nav-menu-items' onClick={showSidebar}>
                    <li className="navbar-toggle">
                        <Link to="#" className='menu-bars'>
                            <AiIcons.AiOutlineClose />
                        </Link>
                    </li>
                    {SidebarData.map((item, index) => {
                        return(
                            <li key={index} className={item.cName}>
                                <Link to={item.path}>
                                    {item.icon}
                                    <span>{item.title}</span>
                                </Link>
                            </li>
                        )
                    })}
                </ui>
            </nav>
            </IconContext.Provider>
        </>
    )
}

export default Navbar
