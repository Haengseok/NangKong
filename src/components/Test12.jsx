import React, {useState} from "react";

const Test12 = () => {

    const user = ({
        email: '1',
        password: '2',
        passwordCheck: '3',
        managerName: '4',
        managerNum: '5',
        companyName: '6',
        companyId: '7',
        companyNum: '8',
    });

    const number = [1, 2, 3, 4, 5]

    const testlist = number.map((item, index) => (
        <li key={index}>{item.property}</li>
    ))

    const [checkinput, setcheckinput] = useState(true)
    const [count, setcount] = useState(0)

    const checkout = () => {
        
        setcount(0)
        
        if(user.email === '')setcount(1)
        if(user.password === '')setcount(2)
        if(user.passwordCheck === '')setcount(3)
        if(user.managerName === '')setcount(4)
        if(user.managerNum === '')setcount(5)
        if(user.companyName === '')setcount(6)
        if(user.companyId === '')setcount(7)
        if(user.companyNum === '')setcount(8)

        if(count == 0)setcheckinput(true)
        else setcheckinput(false)
    }

    

    
    

    return (
        <>
        <ul>
            {/* {testlist} */}
            <li>안녕</li>
            <li>안녕2</li>
        </ul>
        <button onClick={checkout}>버튼</button>
        </>
    )
}

export default Test12;