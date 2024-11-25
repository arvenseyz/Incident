import React from 'react';
import IncidentList from './IncidentList';
import CreateIncident from './CreateIncident';
import ModifyIncident from './ModifyIncident';
import DeleteIncident from './DeleteIncident';

const App = () => {
  return (
      <div>
        <IncidentList />
        <CreateIncident />
        <ModifyIncident />
        <DeleteIncident />
      </div>
  );
};

export default App;