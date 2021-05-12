<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="PatientList"
      :items="patientItem"
      :search="search"
      @click:row="editPatient"
    ></v-data-table>
    <PatientDialog
      :opened="patientDialogVisibility"
      :item="patientSelected"
      @refresh="refreshList"
    ></PatientDialog>
    <v-btn @click="addPatient">Add Patient</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import PatientDialog from "@/components/PatientDialog";
export default {
  name: "PatientList",
  components: { PatientDialog },
  data() {
    return {
      patientsItem: [],
      search: "",
      patientItem: [
        { text: "ID", align: "start", sortable: false, value: "id" },
        { text: "Username", value: "name" },
        { text: "Email", value: "email" },
      ],
      patientDialogVisibility: false,
      patientSelected: {},
    };
  },
  methods: {
    addPatient() {
      this.patientDialogVisibility = true;
    },
    editPatient(patient) {
      this.patientSelected = patient;
      this.patientDialogVisibility = true;
    },
    async refreshList() {
      this.patientItem = await api.patients.allPatients();
      this.patientDialogVisibility = false;
      this.patientSelected = {};
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
