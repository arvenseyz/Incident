import React, { useState } from 'react';
import axios from 'axios';

const CreateIncident = () => {
    const [description, setDescription] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await axios.post('/incidents', { description });
            setDescription('');
        } catch (error) {
            console.error('Error creating incident:', error);
        } finally {
            setLoading(false);
            window.location.reload();
        }
    };

    return (
        <div>
            <h2>Create Incident</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Incident Description"
                />
                <button type="submit" disabled={loading}>
                    {loading? 'Creating...' : 'Create'}
                </button>
            </form>
        </div>
    );
};

export default CreateIncident;