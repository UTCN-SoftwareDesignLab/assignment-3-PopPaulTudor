import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allConsultations() {
    return HTTP.get(BASE_URL + "/consultation", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  createConsultation(consultation) {
    return HTTP.post(BASE_URL + "/consultation", consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editConsultation(consultation) {
    return HTTP.patch(
      BASE_URL + "/consultation/" + consultation.id,
      consultation,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },

  deleteConsultation(consultation) {
    return HTTP.delete(BASE_URL + "/consultation/" + consultation.id, {
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
