import { useEffect, useState } from "react";
import api from "../api/axios";
import { deleteUserSkill, updateProgress } from "../api/userSkillApi";

function UserSkills({ userId }) {
    const [skills, setSkills] = useState([]);

    useEffect(() => {
        api.get(`/api/user-skills/${userId}`)
            .then(res => {

                console.log("USER SKILLS RESPONSE:", res.data);
                setSkills(res.data)

            });
    }, [userId]);

    const handleProgressChange = (id, value, level) => {
        updateProgress(id, value, level)
            .then(res => {
                setSkills(skills.map(skill =>
                    skill.id === id ? res.data : skill
                ));
            });
    };

    const handleLevelChange = (id, progress, level) => {
        updateProgress(id, progress, level)
            .then(res => {
                setSkills(skills.map(skill =>
                    skill.id === id ? res.data : skill
                ));
            });
    }

    const handleDelete = (id) => {
        deleteUserSkill(id).then(() => {
            setSkills(skills.filter(skill => skill.id !== id));
        });
    };

    return (
        <div className="space-y-4">

            {skills.map(us => (
                <div key={us.id} className="bg-white border rounded-lg shadow-sm p-5 flex flex-col gap-4">
                    <div className="flex justify-between items-center">
                        <h3 className="text-lg font-semibold text-gray-800">{us.skillName}</h3>

                        <button className="text-sm text-red-600 hover:text-red-700 font-medium"
                            onClick={() => handleDelete(us.id)}>Remove Skill
                        </button>
                    </div>

                    <div className="flex items-center gap-4">
                        <input
                            className="w-full accent-blue-600"
                            type="range"
                            min="0"
                            max="100"
                            value={us.progress}
                            onChange={(e) =>
                                handleProgressChange(us.id, e.target.value, us.level)
                            }
                        />
                        <span className="w-12 text-sm text-gray-700 text-right">{us.progress}%</span>
                    </div>

                    <div className="flex items-center gap-3">
                        <label className="text-sm text-gray-600">Level:</label>
                        <select
                            className="border rounded-md px-3 py-1 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                            value={us.level}
                            onChange={(e) => handleLevelChange(us.id, us.progress, e.target.value)}>
                            <option value="Beginner">Beginner</option>
                            <option value="Intermediate">Intermediate</option>
                            <option value="Advanced">Advanced</option>
                        </select>
                    </div>

                </div>
            ))}

            {skills.length === 0 && (
                <p className="text-gray-500 text-sm">No skills added yet.</p>
            )}
        </div>
    );

}

export default UserSkills