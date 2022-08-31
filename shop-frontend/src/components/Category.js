import React, {useContext, useEffect, useState} from 'react';
import Product from "./Product";
import {getCategories} from "../services/productService";
import UserContext from "../context/UserContext";

const CategoryComponent = () => {
    const [categoryList, setCategoryList] = useState([]);
    const userContext = useContext(UserContext);

    useEffect(() => {
        getCategories().then(res => setCategoryList(res));
    }, [userContext]);

    const categorySection = () => {
        const categories = [];
        if (categoryList.length) {
            categoryList.forEach(item => {
                categories.push(item)
            })
            return (
                <div>
                     {categories.map((category, index) =>
                         <div key={index}>
                             <Product categoryId={category.id} categoryName={category.name}/>
                         </div>
                     )}
                </div>
            );
        }
    };

    return <React.Fragment>
        {categorySection()}
    </React.Fragment>;
};

export default CategoryComponent;