import axios from 'axios';

export const setHeaders = () =>{
    axios.defaults.headers.common= {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "*",
        "Access-Control-Allow-Origin": 'http://localhost:3000',
        "Access-Control-Allow-Headers": "Content-Type, Authorization"
    }
}


export const getProductByCategory = (categoryId) => {

    return axios.get('http://127.0.0.1:9000/product/category/' + categoryId)
        .then(res => res.data);
}

export const getProduct = (productId) => {

    return axios.get('http://127.0.0.1:9000/product/'+productId)
        .then(res => res.data);
}

export const getCategories = () => {

    return axios.get('http://127.0.0.1:9000/category')
        .then(res => res.data);
}
