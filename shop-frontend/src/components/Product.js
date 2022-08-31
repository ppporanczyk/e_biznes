import React, {useContext, useEffect, useState} from 'react';
import Cart from "./Cart";
import {getProductByCategory} from "../services/productService";
import UserContext from "../context/UserContext";


const ProductComponent = ({categoryId, categoryName}) => {
    const [newCart, setNewCart]= useState(false);
    const [productList, setProductList] = useState([]);
    const userContext = useContext(UserContext);

    useEffect(() => {
        getProductByCategory(categoryId).then(res => setProductList(res));
        if(newCart)
            setNewCart(false);
    }, [userContext]);

    const showButtonIfLogIn = (value) => {
        if(userContext.userId > 0 ){
            return(
                <button type="button" onClick={() => setNewCart(value)}>
                    Dodaj do koszyka
                </button>
            )
        }
    }

    const productsSection = () => {
        const products = [];
        if (productList.length) {
            productList.forEach(item => {
                products.push(item)
            })
            return (
                <div>
                    <h2>{categoryName}</h2>
                    <table style={{width:'100%'}}>
                        <tbody>
                        { products.map((value, index) =>
                            <tr key={index} >
                                <td style={{width:'40%'}}><b>{value.name}</b> <i>({value.price}$)</i></td>
                                <td>{value.description}
                                    {showButtonIfLogIn(value)}
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            );
        }
    };

    const showCartSec = () => {
        if(newCart) {
            return (
                <div>
                    <Cart productId={newCart.id} productName={newCart.name}/>
                    <button type="button" onClick={() => setNewCart(false)}>
                        Zamknij
                    </button>
                </div>
            );
        }
    };

    return <React.Fragment>
        {productsSection()}
        {showCartSec()}
    </React.Fragment>;
};

export default ProductComponent;