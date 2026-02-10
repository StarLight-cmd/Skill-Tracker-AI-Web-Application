import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import skt from '../assets/skt.png'

const Banner = () => {


    const navigate = useNavigate();
    const handleLogout = () => {
        localStorage.removeItem("user");
        navigate("/");
    }

    return (
        <header className='bg-white shadow-md'>
            <div className='max-w-7xl mx-auto px-6 py-4 flex items-center justify-between'>
                <div className='flex items-center gap-3'>

                    <div className='w-20 h-20 bg-blue-600 text-white flex items-center justify-center rounded-full font-bold'>
                        <img src={skt} alt="Logo" />
                    </div>
                    <span className='text-xl font-semibold text-gray-800'>
                        SkillTracker
                    </span>

                </div>

                <nav className='flex items-center gap-6'>
                    <Link
                        to="/home"
                        className='text-gray-700 hover:text-blue-600 font-medium border border-gray-400 px-4 py-2 rounded-md'
                    >
                        Home
                    </Link>

                    <Link
                        to="/skills"
                        className='text-gray-700 hover:text-blue-600 font-medium border border-gray-400 px-4 py-2 rounded-md'
                    >
                        Skills
                    </Link>
                    <Link
                        to="/add-skill"
                        className='text-gray-700 hover:text-blue-600 font-medium border border-gray-400 px-4 py-2 rounded-md'
                    >
                        Add Skill
                    </Link>
                    <Link
                        to="/reports"
                        className='text-gray-700 hover:text-blue-600 font-medium border border-gray-400 px-4 py-2 rounded-md'
                    >
                        Reports
                    </Link>
                    <Link
                        to="/chatbot"
                        className='text-gray-700 hover:text-blue-600 font-medium border border-gray-400 px-4 py-2 rounded-md'
                    >
                        SkillTrack AI
                    </Link>

                </nav>
                
                <button
                    onClick={handleLogout}
                    className='bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition'
                >
                    Logout
                </button>

            </div>

        </header>
    )
}

export default Banner
