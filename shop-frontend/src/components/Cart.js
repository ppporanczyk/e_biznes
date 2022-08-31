import React, {useContext, useEffect, useState} from 'react';
import {createOrder} from "../services/orderService";
import UserContext from "../context/UserContext";

const CartComponent = ({productId,productName}) => {

    const userContext = useContext(UserContext);
    const [info, setInfo] = useState('');
    const [values, setValues] = useState({
        id: 0,
        address: '',
        description: productName,
        amount: 0,
        productId: productId,
        userId: userContext.userId
    });

    useEffect(() => {
    }, [userContext]);

    const confirmOrder = e =>{
        e.preventDefault();
        values.amount=parseInt(values.amount);
        createOrder(values).then(
            res => {
                if(res.status===200){
                    setInfo("Dokonano produkt od koszyka");
                }
            });
        userContext.toggleOrder();
    };

    const handleChange = name => e => {
        setValues({ ...values, [name]: e.target.value });
    };

    const cartSection = () => {
        return (
            <div>
                <form onSubmit={confirmOrder}>
                    <h2>Twój koszyk</h2>
                    <div className="form-group">
                        <div>
                            <h4>{values.description}</h4>
                        </div>
                        <div className="form-group">
                            <input
                                value={values.amount}
                                onChange={handleChange('amount')}
                                type="number"
                                min="1"
                                className="form-control"
                                placeholder="amount"
                            />
                        </div>
                        <div className="form-group">
                            <input
                                value={values.address}
                                onChange={handleChange('address')}
                                type="text"
                                className="form-control"
                                placeholder="address"
                            />
                        </div>
                    </div>
                    <div>
                        <input type="submit" className="btn btn-primary" value="Potwierdź"/>
                    </div>
                    {info}
                </form>
            </div>
        );
    }

    return <React.Fragment>
        {cartSection()}
    </React.Fragment>;
};

export default CartComponent;