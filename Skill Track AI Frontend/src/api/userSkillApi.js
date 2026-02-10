import api from "./axios";

export const updateProgress = (userSkillId, progress, level) => {
    return api.patch(`/api/user-skills/${userSkillId}/progress`, null, {
        params: {
            progress,
            level
        }
    });
}

export const deleteUserSkill = (id) => api.delete(`/api/user-skills/${id}`);