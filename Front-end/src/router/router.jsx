import { createBrowserRouter } from 'react-router-dom';

import Home from '../pages/home/Home';
import Login from '../pages/login/Login';
import Register from '../pages/register/Register';

import Main from '../main/Main';
import UserHome from '../pages/userHome/UserHome';
import UserProfile from '../pages/userProfile/UserProfile';

import Dashboard from '../pages/Dashboard/Dashboard'; 

import ProtectedRoute from './ProtectedRoute';

const router = createBrowserRouter([
    {
        path: "/",
        element: (
            <ProtectedRoute allowedRoles={"anyone"} path={"/app/userHome"}>
                <Home />
            </ProtectedRoute>
        )
    },
    {
        path: "/login",
        element: (
            <ProtectedRoute allowedRoles={"anyone"} path={"/app/userHome"}>
                <Login />
            </ProtectedRoute>
        )
    },
    {
        path: "/register",
        element: (
            <ProtectedRoute allowedRoles={"anyone"} path={"/app/userHome"}>
                <Register />
            </ProtectedRoute>
        )
    },
    {
        path: "/app",
        element: (
            <ProtectedRoute allowedRoles={['user', 'admin']} path={"/"}>
                <Main />
            </ProtectedRoute>
        ),
        children: [
            {
                path: "userHome",
                element: (
                    <ProtectedRoute allowedRoles={['user', 'admin']} path={"/404"}>
                        <UserHome />
                    </ProtectedRoute>
                )
            },
            {
                path: "userProfile/:id",
                element: (
                    <ProtectedRoute allowedRoles={['user', 'admin']} path={"/404"}>
                        <UserProfile />
                    </ProtectedRoute>
                )
            },
            {
                path: "dashboard",
                element: (
                    <ProtectedRoute allowedRoles={['admin']} path={"/404"}>
                        <Dashboard />
                    </ProtectedRoute>
                )
            }
        ]
    },
    {
        path: '/404',
        element: <h1>404</h1>
    },
    {
        path: '*',
        element: <h1>Nada que mostrar</h1>
    }
]);

export default router;
