<template>
  <div>
    <h2>Asset Request</h2>
    <form @submit.prevent="requestAsset">
      <label for="assetId">Select Asset:</label>
      <select v-model="request.assetId" id="assetId" required>
        <option v-for="asset in assets" :key="asset.id" :value="asset.id">
          {{ asset.name }}
        </option>
      </select>

      <label for="purpose">Purpose:</label>
      <input v-model="request.purpose" type="text" id="purpose" required />

      <label for="time">Requested Time:</label>
      <input v-model="request.time" type="text" id="time" required />

      <button type="submit">Request</button>
    </form>
  </div>
</template>
  
  <script>
export default {
  name: "AssetRequest",
  data() {
    return {
      request: {
        assetId: "",
        purpose: "",
        time: "",
      },
      assets: [
        { id: 1, name: "Microscope" },
        { id: 2, name: "Projector" },
      ],
    };
  },
  methods: {
    requestAsset() {
      // Here we would normally send this data to a server
      console.log("Requesting asset:", this.request);
      // Reset form
      this.request = {
        assetId: "",
        purpose: "",
        time: "",
      };
    },
  },
};
</script>
  
  <style>
/* Add your styles here */
</style>
  