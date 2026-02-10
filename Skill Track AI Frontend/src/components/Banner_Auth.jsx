import React from 'react'
import skt from '../assets/skt.png'

const Banner_Auth = () => {


    return (
        <header className='bg-white shadow-md'>
            <div className='max-w-7xl mx-auto px-6 py-4 flex items-center justify-center'>
                <div className='flex items-center gap-3'>

                    <div className='w-20 h-20 bg-blue-600 text-white flex items-center justify-center rounded-full font-bold'>
                                            <img src={skt} alt="Logo" />
                                        </div>
                    <span className='text-xl font-semibold text-gray-800'>
                        SkillTracker
                    </span>

                </div>

            </div>

        </header>
    )
}

export default Banner_Auth
