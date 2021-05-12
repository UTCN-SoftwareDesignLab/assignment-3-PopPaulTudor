<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create patient" : "Edit patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-if="!isNew" v-model="item.id" label="id" readonly />
          <v-text-field v-model="item.name" label="Username" />
          <v-text-field v-model="item.email" label="Email" />
          <v-text-field v-model="item.password" label="Password" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" v-on:click="del">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "PatientDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.patient
          .createPatient({
            id: this.item.id,
            username: this.item.name,
            email: this.item.email,
            password: this.item.password,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.patient
          .editPatient({
            id: this.item.id,
            username: this.item.name,
            email: this.item.email,
            password: this.item.password,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    del() {
      api.patient
        .deletePatient({
          id: this.item.id,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
