import api from "./axios";

export const getSkills = () => api.get("/api/skills");
export const createSkill = (skill) => api.post("/api/skills", skill);