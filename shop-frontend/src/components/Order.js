import React, {useContext, useEffect, useState} from 'react';
import {getProduct} from "../services/productService";
import {deleteOrder, getOrder, getPaymentByOrder} from "../services/orderService";
import Payment from "./Payment";
import UserContext from "../context/UserContext";


const OrderComponent = ({orderId,productId}) => {

    const [order, setOrder] = useState(0);
    const [total, setTotal] = useState(0.0);
    const [paid, setPaid] = useState(false);
    const [showPayment, setShowPayment] = useState(false);
    const userContext = useContext(UserContext);

    let totalValueToPay = 0.0;
    useEffect(() => {
        if(showPayment)
            setShowPayment(false);
        getOrder(orderId).then(res => setOrder(res));
        getProduct(productId).then(res => setTotal(res.price));
        if(!paid) {
            getPaymentByOrder(orderId)
                .then(res => {
                    if (res.data.length >0) {
                        setPaid(true)
                    }
                })
        }
    }, [userContext]);

    const handlePay = () => {
        setShowPayment(true)
    };

    function handleDeleteOrder(id) {
        deleteOrder(id);
        userContext.toggleOrder();
    }

    const orderSection = () => {
        totalValueToPay = order.amount * total;
        if(!paid){
            return (
                <div>
                    <h4>Zamówiono: {order.description} x{order.amount } </h4>
                    <table style={{width:'100%'}}>
                        <tbody>
                            <tr>
                                <td>Adres: {order.address}</td>
                                <td>Suma: {totalValueToPay}$ </td>
                                <td>
                                    <button type="button" onClick={() => handlePay()}>
                                        Płać
                                    </button>
                                    <button type="button" onClick={() => handleDeleteOrder(order.id)}>
                                        Usuń
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            );
        }else{
            return (
                <div>
                    <h4>Zamówiono: {order.description} x{order.amount } </h4>
                    <table style={{width:'100%'}}>
                        <tbody>
                        <tr>
                            <td>Adres: {order.address}</td>
                            <td>Suma: {totalValueToPay}$ </td>
                            <td>Opłacone</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            );
        }
    };

    const showPaymentForm = () => {
        if(showPayment) {
            return (
                <div>
                    <Payment orderId={order.id} totalValue={totalValueToPay}/>
                    <button type="button" onClick={() => setShowPayment(false)}>
                        Zamknij
                    </button>
                </div>
            )
        }
    }

    return( <React.Fragment>
        {orderSection()}
        {showPaymentForm()}
    </React.Fragment>);
};

export default OrderComponent;