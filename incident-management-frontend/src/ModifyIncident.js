import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ModifyIncident = () => {
    const [id, setId] = useState('');
    const [description, setDescription] = useState('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {}, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await axios.put(`/incidents/${id}`, { description });
        } catch (error) {
            console.error('Error modifying incident:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h2>Modify Incident</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="number"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                    placeholder="Incident ID"
                />
                <input
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="New Incident Description"
                />
                <button type="submit" disabled={loading}>
                    {loading? 'Modifying...' : 'Modify'}
                </button>
            </form>
        </div>
    );
};

export default ModifyIncident;