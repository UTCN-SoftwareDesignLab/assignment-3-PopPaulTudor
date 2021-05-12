<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create consultation" : "Edit consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-if="!isNew" v-model="item.id" label="id" readonly />
          <v-text-field v-model="item.doctorId" label="Doctor" />
          <v-text-field v-model="item.patientId" label="Patient" />
          <v-text-field v-model="item.details" label="Details" />
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
  name: "ConsultationDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.consultation
          .createConsultation({
            id: this.item.id,
            doctorId: this.item.doctorId,
            patientId: this.item.patientId,
            details: this.item.details,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.consultation
          .editConsultation({
            id: this.item.id,
            doctorId: this.item.doctorId,
            patientId: this.item.patientId,
            details: this.item.details,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    del() {
      api.users
        .deleteConsultation({
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
