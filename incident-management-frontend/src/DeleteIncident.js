import React, { useState } from 'react';
import axios from 'axios';

const DeleteIncident = () => {
    const [id, setId] = useState('');
    const [loading, setLoading] = useState(false);

    const handleDelete = async () => {
        setLoading(true);
        try {
            await axios.delete(`/incidents/${id}`);
        } catch (error) {
            console.error('Error deleting incident:', error);
        } finally {
            setLoading(false);
            window.location.reload();
        }
    };

    return (
        <div>
            <h2>Delete Incident</h2>
            <input
                type="number"
                value={id}
                onChange={(e) => setId(e.target.value)}
                placeholder="Incident ID"
            />
            <button onClick={handleDelete} disabled={loading}>
                {loading? 'Deleting...' : 'Delete'}
            </button>
        </div>
    );
};

export default DeleteIncident;