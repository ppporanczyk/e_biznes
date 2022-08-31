import React, {useContext, useEffect, useState} from 'react';
import Order from "./Order";
import {getOrderByUser} from "../services/orderService";
import UserContext from "../context/UserContext";

const OrdersComponent = () => {
    const userContext = useContext(UserContext);
    const [orderList, setOrderList] = useState([]);

    useEffect(() => {
        getOrderByUser(userContext.userId).then(res => setOrderList(res));
    }, [userContext]);

    const ordersSection = () => {
        const orders = [];
        if (orderList.length) {
            orderList.forEach(item => {
                orders.push(item)
            })
        }
        return(
            <div>
            {orders.map((order, index) =>
                <div key={index}>
                    <Order orderId={order.id} productId={order.productId}/>
                </div>
            )}
            </div>)

    }
    return <React.Fragment>
        {ordersSection()}
    </React.Fragment>;
};

export default OrdersComponent;