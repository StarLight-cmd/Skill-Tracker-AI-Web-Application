import api from "../api/axios";

export const testBackend = () => {
    return api.get("/api/test");
};