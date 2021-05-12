import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPatients() {
    return HTTP.get(BASE_URL + "/patient", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  createPatients(patient) {
    return HTTP.post(BASE_URL + "/patient", patient, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editPatients(patient) {
    return HTTP.patch(BASE_URL + "/patient/" + patient.id, patient, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  deletePatients(patient) {
    return HTTP.delete(BASE_URL + "/patient/" + patient.id, {
      headers: authHeader(),
    }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
};
