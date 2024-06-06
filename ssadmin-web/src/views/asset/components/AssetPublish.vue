<template>
  <div>
    <h2>Asset Publish</h2>
    <form @submit.prevent="publishAsset">
      <label for="name">Asset Name:</label>
      <input v-model="asset.name" type="text" id="name" required />

      <label for="type">Type:</label>
      <input v-model="asset.type" type="text" id="type" required />

      <label for="location">Location:</label>
      <input v-model="asset.location" type="text" id="location" required />

      <label for="availability">Availability:</label>
      <input
        v-model="asset.availability"
        type="text"
        id="availability"
        required
      />

      <button type="submit">Publish</button>
    </form>
  </div>
</template>
  
  <script>
export default {
  name: "AssetPublish",
  data() {
    return {
      asset: {
        name: "",
        type: "",
        location: "",
        availability: "",
      },
    };
  },
  methods: {
    publishAsset() {
      // Here we would normally send this data to a server
      console.log("Publishing asset:", this.asset);
      // Reset form
      this.asset = {
        name: "",
        type: "",
        location: "",
        availability: "",
      };
    },
  },
};
</script>
  
  <style>
/* Add your styles here */
</style>
  