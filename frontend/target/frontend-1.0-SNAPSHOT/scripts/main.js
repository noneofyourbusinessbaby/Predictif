const BACKEND_URL = "http://localhost:8080/ActionServelet?action=";

/**
 *
 * @param {string} action
 * @param {FormData} form
 * @returns
 */
const MakeRequest = (action, form) => {
  const params = new URLSearchParams();

  form.forEach((value, key) => {
    params.append(key, value);
  });

  return fetch(`${BACKEND_URL}?${action}&${params}`, {
    method: "GET",
  });
};
