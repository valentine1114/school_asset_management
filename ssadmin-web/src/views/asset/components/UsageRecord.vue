<template>
  <div>
    <h2>Usage Record</h2>
    <ul>
      <li v-for="record in records" :key="record.id">
        <strong>{{ record.assetName }}</strong> used by {{ record.user }} on
        {{ record.date }} for {{ record.purpose }}
      </li>
    </ul>
  </div>
</template>
  
  <script>
export default {
  name: "UsageRecord",
  data() {
    return {
      records: [
        {
          id: 1,
          assetName: "Microscope",
          user: "Dr. Smith",
          date: "2024-05-01",
          purpose: "Research",
        },
        {
          id: 2,
          assetName: "Projector",
          user: "Prof. Johnson",
          date: "2024-05-02",
          purpose: "Lecture",
        },
      ],
    };
  },
};
</script>
  
  <style>
/* Add your styles here */
</style>
  