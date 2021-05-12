<template>
  <v-card>
    <v-card-title>
      Consultations
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
      :headers="ConsultationList"
      :items="consultationItem"
      :search="search"
      @click:row="editConsultation"
    ></v-data-table>
    <ConsultationDialog
      :opened="consultationDialogVisibility"
      :item="consultationSelected"
      @refresh="refreshList"
    ></ConsultationDialog>
    <v-btn @click="addConsultation">Add Consultation</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import ConsultationDialog from "@/components/ConsultationDialog";
export default {
  name: "ConsultationList",
  components: { ConsultationDialog },
  data() {
    return {
      consultationsItem: [],
      search: "",
      consultationItem: [
        { text: "ID", align: "start", sortable: false, value: "id" },
        { text: "Doctor Id", value: "doctorId" },
        { text: "Patient Id", value: "patientId" },
      ],
      consultationDialogVisibility: false,
      consultationSelected: {},
    };
  },
  methods: {
    addConsultation() {
      this.consultationDialogVisibility = true;
    },
    editConsultation(consultation) {
      this.consultationSelected = consultation;
      this.consultationDialogVisibility = true;
    },
    async refreshList() {
      this.consultationItem = await api.consultations.allConsultations();
      this.consultationDialogVisibility = false;
      this.consultationSelected = {};
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
