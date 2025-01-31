import { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import axios from 'axios';

function Register() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); 

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8165/users', {
          username,
          password
        })
        .then(response => {
          console.log(response.data);
          navigate('/Main');
        })
        .catch(error => {
          console.error('There was an error registering!', error);
        });
    };

    return (
        <div>
          <h2>Register</h2>
          <form onSubmit={handleSubmit}>
            <div>
              <label>Username:</label>
              <input
                type="text"
                value={username}
                onChange={e => setUsername(e.target.value)}
              />
            </div>
            <div>
              <label>Password:</label>
              <input
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
            </div>
            <button type="submit">Register</button>
          </form>
        </div>
    );
}

export default Register;
