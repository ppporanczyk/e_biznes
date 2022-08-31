import React, {useContext, useEffect, useState} from 'react';
import {createPayment} from "../services/orderService";
import UserContext from "../context/UserContext";

const PaymentComponent = ({orderId, totalValue}) => {

    const userContext = useContext(UserContext);
    const [info, setInfo] = useState('');
    const [values, setValues] = useState({
        id: 0,
        creditCardNumber: '',
        cvvNumber: '',
        expirationDate: '',
        totalValue: totalValue,
        orderId: orderId
    });

    useEffect(() => {
    }, [userContext]);

    const handleSubmit = (e) => {
        e.preventDefault();
        createPayment(values).then(
            res => {
                if(res.status===200){
                    setInfo("Dokonano platnosci");
                }
            });
        userContext.toggleOrder();
    };

    const handleChange = name => e => {
        setValues({ ...values, [name]: e.target.value });
    };

    const paymentForm = () => {
        return (
            <form onSubmit={handleSubmit}>
                <h2>Credit Card</h2>
                <div className="form-group">
                    <input
                        value={values.creditCardNumber}
                        onChange={handleChange('creditCardNumber')}
                        type="text"
                        className="form-control"
                        placeholder="Credit Card Number"
                    />
                </div>
                <div className="form-group">
                    <input
                        value={values.cvvNumber}
                        onChange={handleChange('cvvNumber')}
                        type="text"
                        className="form-control"
                        placeholder="CVV Number"
                    />
                </div>

                <div className="form-group">
                    <input
                        value={values.expirationDate}
                        onChange={handleChange('expirationDate')}
                        type="text"
                        className="form-control"
                        placeholder="Expiration Date"
                    />
                </div>
                    {values.totalValue}$
                <div>
                    <input type="submit" className="btn btn-primary" value="Zapłać"/>
                </div>
                {info}
            </form>
        );
    };

    return <React.Fragment>
        {paymentForm()}
    </React.Fragment>;
};

export default PaymentComponent;