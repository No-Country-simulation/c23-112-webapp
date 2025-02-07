import { useDispatch, useSelector } from 'react-redux';
import { useState } from 'react';
import { userLogin } from '../../store/action/userAction';


const LoginScript = () => 
{
    const dispatch = useDispatch();

    const [loginData, setLoginData] = useState({
        email:'', 
        password:''
    });

    const testo = useSelector(store => store.userReducer);
    const [isLoading, setIsLoading] = useState(false);

    const handleInput = async (event) => 
    {
        setLoginData({
            ...loginData,
            [event.target.name]: event.target.value
        });
    };

    const handleLogin = async (event) => 
    {
        event.preventDefault();
        setIsLoading(true);
        try {
            await dispatch(userLogin({
                data: loginData
            }));
        } catch (error) {
            console.log('Error en la acción userLogin:', error);
        } finally {
            setIsLoading(false);
        }
    }

    return {
        handleInput,
        handleLogin,
        isLoading
    }
}

export default LoginScript;