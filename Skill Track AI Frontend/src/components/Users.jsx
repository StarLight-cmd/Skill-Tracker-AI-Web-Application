import {useEffect, useState} from "react";
import api from '../api/axios';

function Users(){
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUsers = async () => {
            try{
                const response = await api.get("/api/users");
                setUsers(response.data);
            }catch(err){
                setError("Failed to load users");
            }finally{
                setLoading(false);
            }
        };

        fetchUsers();
    }, [])

    if(loading) return <p>Loading users...</p>;
    if(error) return <p style={{color : "red"}}>{error}</p>;

    return(
        <div>
            <h2>Users</h2>
            <ul>
                {users.map(user => (
                    <li key={user.id}>
                        {user.firstName}{user.lastName}-{user.email}
                    </li>
                ))}
            </ul>
        </div>
    );

}

export default Users;