import { useState } from "react";
import api from '../api/axios';
import { Link, useNavigate } from "react-router-dom";
import Banner from "./Banner";
import Banner_Auth from "./Banner_Auth";

function Login({ setUser }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await api.post("/api/auth/login", {
                email: email,
                password: password
            });

          localStorage.setItem("user", JSON.stringify(response.data));
setUser(response.data);

            console.log("Login successful: ", response.data);
            navigate("/home");
        } catch (err) {
            setError("Invalid email or password");
        }
    };

    return (
        <>
            <Banner_Auth/>
            <div className="min-h-screen bg-gray-800 flex items-center justify-center px-4">
            <div className="w-full max-w-md bg-white rounded-lg shadow p-8">
                <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">Login</h2>
                {
                    error && <p className="mb-4 text-sm text-red-600 bg-red-50 border border-red-200 rounded p-2">
                        {error}
                    </p>
                }

                <form onSubmit={handleLogin} className="space-y-4">
                    <div>
                        <label className="block text-sm text-gray-600 mb-1">Email: </label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            className="w-full border rounded-md px-3 py-2 text-sm focus:online-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-600 mb-1">Password: </label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="w-full border rounded-md px-3 py-2 text-sm focus:online-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded-md font-medium hover:bg-blue-700 transition">
                        Login
                    </button>

                </form>

                <p className="mt-6 text-sm text-center text-gray-600">Don't have an account?{" "}
                    <Link to="/register"
                        className="text-blue-600 hover:underline font-medium">
                        Register
                    </Link>
                </p>
            </div>
        </div>
        </>
    );
}

export default Login;