<template>
  <div>
    <h2>Asset Browse</h2>
    <ul>
      <li v-for="asset in assets" :key="asset.id">
        <strong>{{ asset.name }}</strong> ({{ asset.type }}) -
        {{ asset.location }} - Available: {{ asset.availability }}
      </li>
    </ul>
  </div>
</template>
  
  <script>
export default {
  name: "AssetBrowse",
  data() {
    return {
      assets: [
        {
          id: 1,
          name: "Microscope",
          type: "Lab Equipment",
          location: "Building A, Room 101",
          availability: "Weekdays 9am-5pm",
        },
        {
          id: 2,
          name: "Projector",
          type: "Audio-Visual",
          location: "Building B, Room 202",
          availability: "Weekends 10am-4pm",
        },
      ],
    };
  },
};
</script>
  
  <style>
/* Add your styles here */
</style>
  