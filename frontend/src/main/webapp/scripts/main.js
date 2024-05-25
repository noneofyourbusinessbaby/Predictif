const BACKEND_URL = "http://localhost:8080/ActionServelet?action=";

/**
 *
 * @param {string} action
 * @param {RequestInit} conf
 * @returns
 */
const MakeRequest = (action, conf) => {
  return fetch(`${BACKEND_URL}${action}`, conf);
};
