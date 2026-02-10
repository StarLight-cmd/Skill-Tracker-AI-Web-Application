import { useState } from 'react'
import api from '../api/axios';
import { Link } from 'react-router-dom';
import Banner from './Banner';
import Banner_Auth from './Banner_Auth';
import { useNavigate } from 'react-router-dom';

const Register = () => {

    const navigate = useNavigate();

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError(null);
        setSuccess(false);

        try {
            await api.post("/api/users", {
                firstName,
                lastName,
                email,
                password
            });

            setSuccess(true);
            navigate("/");
            
        } catch (error) {
            setError(error.response?.data.message || "Registration failed");
        }
    };

    return (
        <>
        <Banner_Auth/>
        <div className='min-h-screen bg-gray-800 flex items-center justify-center px-4'>
            <div className='w-full max-w-md bg-white rounded-lg shadow p-8'>
                <h2 className='text-2xl font-semibold text-gray-800 mb-6 text-center'>Create your SkillTrack account</h2>
                {error && <p className='mb-4 text-sm text-red-600 bg-red-50 border border-red-200 rounded p-2'>{error}</p>}
                {success && <p className='mb-4 text-sm text-green-600 bg-green-50 border border-green-200 rounded p-2'>User registered successfully</p>}
                <form onSubmit={handleSubmit} className='space-y-4'>
                    <div>
                        <label className='block text-sm text-gray-600 mb-1'>First name</label>
                        <input
                            type="text"
                            placeholder='FirstName'
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            className='w-full border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500'
                        >

                        </input>
                    </div>

                    <div>
                        <label className='block text-sm text-gray-600 mb-1'>
                            Last name
                        </label>

                        <input
                            type="text"
                            placeholder='LastName  '
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            className='w-full border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500'
                        >

                        </input>
                    </div>

                    <div>
                        <label className='block text-sm text-gray-600 mb-1'>
                            Email
                        </label>

                        <input
                            type="email"
                            placeholder='Email'
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className='w-full border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500'

                        >

                        </input>
                    </div>

                    <div>
                        <label className='block text-sm text-gray-600 mb-1'>
                            Password
                        </label>

                        <input
                            type="password"
                            placeholder='Password'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className='w-full border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500'

                        >

                        </input>
                    </div>

                    <button type="submit" className='w-full bg-blue-600 text-white py-2 rounded-md text-sm font-medium hover:bg-blue-700 transition'>Register</button>
                </form>

                <p className='mt-6 text-sm text-center text-gray-600'>
                    Already have an account?{" "}
                    <Link to="/" className="text-blue-600 hover:underline font-medium">
                        Login
                    </Link>
                </p>
            </div>
        </div>
        </>
        
    )

    //if something affects what the user sees it must be state
}

export default Register
