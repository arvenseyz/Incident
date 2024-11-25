import React, { useEffect, useState } from 'react';
import axios from 'axios';

const IncidentListComponent = () => {
    const [incidentData, setIncidentData] = useState({
        incidents: [],
        status: null,
        errorMessage: null
    });

    useEffect(() => {
        const fetchIncidents = async () => {
            try {
                const response = await axios.get('/incidents');
                const { data, status } = response;

                setIncidentData({
                    incidents: data.data,
                    status: status,
                    errorMessage: null
                });
            } catch (error) {
                setIncidentData({
                    incidents: [],
                    status: 500,
                    errorMessage: error.message
                });
            }
        };

        fetchIncidents();
    }, []);

    return (
        <div>
            {incidentData.status === 200? (
                <div>
                    <h2>Incident List</h2>
                    <ul>
                        {incidentData.incidents.map((incident) => (
                            <li key={incident.id}>{incident.description}</li>
                        ))}
                    </ul>
                </div>
            ) : incidentData.errorMessage? (
                <p>Error: {incidentData.errorMessage}</p>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default IncidentListComponent;