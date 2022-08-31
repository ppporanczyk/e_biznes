import axios from 'axios';


export const createPayment = (payment) => {

    return axios.post('http://127.0.0.1:9000/payment',  {
        id: payment.id,
        creditCardNumber: payment.creditCardNumber,
        cvvNumber: payment.cvvNumber,
        expirationDate:payment.expirationDate,
        totalValue: payment.totalValue,
        orderId: payment.orderId
    });
}

export const createOrder = (order) => {

    return axios.post('http://127.0.0.1:9000/order',  {
        id: order.id,
        address: order.address,
        description: order.description,
        amount: order.amount,
        productId: order.productId,
        userId: order.userId
    });
}

export const getOrderByUser = (userId) => {

    return axios.get('http://127.0.0.1:9000/order/user/'+userId)
        .then(res => res.data);
}

export const getUser = (userId) => {

    return axios.get('http://127.0.0.1:9000/user/'+userId)
        .then(res => res.data);
}

export const getOrder = (orderId) => {

    return axios.get('http://127.0.0.1:9000/order/'+orderId)
        .then(res => res.data);
}

export const getPaymentByOrder = (orderId) => {

    return axios.get('http://127.0.0.1:9000/payment/order/'+orderId);
}

export const deleteOrder = (orderId) => {

    return axios.get('http://127.0.0.1:9000/order/delete/'+orderId)
        .then(res => res.data);
}