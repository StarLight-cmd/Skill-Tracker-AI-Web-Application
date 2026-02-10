import React from 'react'
import { Link } from 'react-router-dom'
import UserSkills from './UserSkills'
import Banner from './Banner'
import ChatBot from './ChatBot'

const Home = () => {

  const user = JSON.parse(localStorage.getItem("user"))
  return (
    <>
      <Banner />
      <div className='min-h-screen bg-gray-800 px-6 py-6'>
        <div>
          <h1 className='text-2xl font-semibold text-gray-800 mb-6'>SkillTrack Dashboard</h1>

          <div className='flex gap-4 mb-8'>

            <Link to="/skills" className='px-5 py-2 rounded-md bg-blue-600 text-white text-sm font-medium hover:bg-blue-700 transition'>Skills</Link>

            <Link to="/add-skill" className='px-5 py-2 rounded-md bg-green-600 text-white text-sm font-medium hover:bg-green-700 transition'>Add Skill</Link>

            <Link to="/reports" className='px-5 py-2 rounded-md bg-gray-700 text-white text-sm font-medium hover:bg-gray-800 transition'>Reports</Link>

            <Link to="/chatbot" className='px-5 py-2 rounded-md bg-indigo-700 text-white text-sm font-medium hover:bg-indigo-800 transition'>SkillTrack AI</Link>

          </div>

          <div className='bg-white rounded-lg shadow p-6'>
            <h2 className='text-lg font-semibold text-gray-700 mb-4'>
              Your Skills
            </h2>
            <UserSkills userId={user.id} />
          </div>
        </div>
      </div>
    </>
  )
}

export default Home
