import React from "react";
import * as AiIcons from "react-icons/ai";
import * as IoIcons from "react-icons/io";
import { GiProcessor } from "react-icons/gi";

export const SidebarData = [
    {
        title: 'Home',
        path: '/',
        icon: <AiIcons.AiFillHome />,
        cName: 'nav-text'
    },
    {
        title: '사용자 관리',
        path: '/user',
        icon: <IoIcons.IoIosPaper />,
        cName: 'nav-text'
    },
    {
        title: 'ESL 기기 관리',
        path: '/esl',
        icon: <AiIcons.AiOutlineConsoleSql />,
        cName: 'nav-text'
    },
    {
        title: '부스 관리',
        path: '/booth',
        icon: <GiProcessor />,
        cName: 'nav-text'
    },
    {
        title: '공지 사항',
        path: '/massages',
        icon: <AiIcons.AiOutlineCarryOut />,
        cName: 'nav-text'
    },
]