<template>
  <div>
    <h2>Appointment Management</h2>
    <ul>
      <li v-for="appointment in appointments" :key="appointment.id">
        <strong>{{ appointment.assetName }}</strong> - {{ appointment.time }} -
        Requested by {{ appointment.requestedBy }}
      </li>
    </ul>
  </div>
</template>
  
  <script>
export default {
  name: "AppointmentManagement",
  data() {
    return {
      appointments: [
        {
          id: 1,
          assetName: "Microscope",
          time: "Weekdays 10am-12pm",
          requestedBy: "Dr. Smith",
        },
        {
          id: 2,
          assetName: "Projector",
          time: "Weekends 1pm-3pm",
          requestedBy: "Prof. Johnson",
        },
      ],
    };
  },
};
</script>
  
  <style>
/* Add your styles here */
</style>
  