import { createAsyncThunk } from "@reduxjs/toolkit"
import axios from "axios";


export const userLogin = createAsyncThunk('userLogin', async (obj) => {
    try {
        const { data } = await axios.post('https://back-end-latest-ggv4.onrender.com/auth/login', obj.data)
        console.log({data});
        
        localStorage.setItem('token', JSON.stringify(data.response.token))
        // localStorage.setItem('user', JSON.stringify(data.response.user))

        return {
            // user: data.response.user,
            token: data.response.token
        }
        
    } catch (error) {
        console.log(error)
        return {
            // user: null,
            token: null
        }
    }
})

export const userLogout = createAsyncThunk('userLogout', async () => {
    try {
        let url = ''
        let token = localStorage.getItem('token')
        let configs = { headers: { 'Authorization': `Bearer ${token}` } }
        axios.post(url, null, configs)

        localStorage.removeItem('token');
        localStorage.removeItem('user');

        return {
            user: null,
            token: null
        }
    } catch (error) {
        console.log(error);
        return false;
    }
})

export const userRegister = createAsyncThunk('userRegister', async (obj) => {
    try {
        const { data } = await axios.post('', obj)

        return {
            user: data.response

        }
    } catch (error) {
        console.log(error)
    }
})