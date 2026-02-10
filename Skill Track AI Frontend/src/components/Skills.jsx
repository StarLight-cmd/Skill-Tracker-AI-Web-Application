import { useEffect, useState } from "react";
import { getSkills } from "../api/skillApi";
import Banner from "./Banner";

const Skills = () => {
  const [skills, setSkills] = useState([]);

  useEffect(() => {
    getSkills().then(res => setSkills(res.data));
  }, []);

  return (
    <>
    <Banner/>
     <div className="min-h-screen bg-gray-800 px-6 py-10">
      <div className="max-w-4xl mx-auto">
        <h2 className="text-2xl font-semibold text-gray-800 mb-6">Skills Library</h2>

        <div className="bg-white rounded-lg shadow p-6">
          <ul className="divide-y">
            {
              skills.map(skill => (
                <li className="py-3 text-gray-700 text-sm" key={skill.id}>
                  <p className="font-medium text-md">
                  {skill.name}
                </p>
                  <p className="text-sm text-gray-500">
                    {skill.description}
                  </p></li>
              ))
            }
          </ul>

          {skills.length === 0 && (
            <p className="text-sm text-gray-500">
              No skills available at the moment.
            </p>
          )}

        </div>
      </div>
    </div>
    </>
   
  );
}

export default Skills
