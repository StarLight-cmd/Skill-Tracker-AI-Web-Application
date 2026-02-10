import { useEffect, useState } from "react";
import api from "../api/axios";
import Banner from "./Banner";
import { useNavigate } from "react-router-dom";

function AddSkill({ userId }) {
    const [skills, setSkills] = useState([]);
    const [selectedSkill, setSelectedSkill] = useState("");
    const [skillId, setSkillId] = useState("");
    const [level, setLevel] = useState("");

    useEffect(() => {
        api.get("/api/skills").then(res => {
            console.log("SKILLS RESPONSE:", res.data);
            setSkills(res.data)
        })
            .catch(err => console.error("Failed to load skills", err));
    }, []);

    const navigate = useNavigate();

    const addSkill = async () => {

        if (!selectedSkill) {
            alert("Please select a skill");
        }

        try {
            await api.post("/api/user-skills", null, {
                params: {
                    userId,
                    skillId: selectedSkill,
                    level
                }
            });
            alert("Skill added!");
            navigate("/home");
        } catch (err) {
            alert(err.response?.data || "Error adding skill");
        }

    };

    return (
        <>
        <Banner/>
        <div className="min-h-screen flex items-center justify-center bg-gray-800">
            <div className="w-full max-w-md bg-white rounded-xl shadow-md p-8">
                <h2 className="text-2xl font-semibold text-semibold text-gray-800 text-center mb-6">
                    Add Skill
                </h2>

                <div className="mb-4">

                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Skill
                    </label>

                    <select
                        value={selectedSkill}
                        onChange={(e) => setSelectedSkill(e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="">-- Select Skill --</option>
                        {
                            skills.map(skill => (
                                <option key={skill.id} value={skill.id}>{skill.name}</option>
                            ))
                        }

                    </select>
                </div>

                <div className="mb-6">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Level
                    </label>

                    <select
                        value={level}
                        onChange={(e) => setLevel(e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="">-- Select Level --</option>
                        <option value="Beginner">Beginner</option>
                        <option value="Intermediate">Intermediate</option>
                        <option value="Advanced">Advanced</option>
                    </select>

                </div>

                
            <button 
            onClick={addSkill}
            className="w-full bg-blue-600 text-white py-2 rounded-md font-medium hover:bg-blue-700 transition disabled:bg-gray-400"
            disabled={!selectedSkill || !level}
            >Add Skill</button>

            </div>

        </div>
        </>
        
    );
}

export default AddSkill;