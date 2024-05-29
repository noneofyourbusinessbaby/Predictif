const BACKEND_URL = "http://localhost:8080/frontend/ActionServelet";

const LOCAL_STORAGE_KEY = "personneId";

/**
 *
 * @param {string} personneId
 * @param {boolean} isClient
 */
const Login = (personneId, isClient) => {
  localStorage.setItem(
    LOCAL_STORAGE_KEY,
    JSON.stringify({
      isClient,
      personneId,
    })
  );

  window.location.href = "PageAcceuilConnect.html";
};

const Logout = () => {
  localStorage.removeItem(LOCAL_STORAGE_KEY);
  window.location.href = "PageAcceuilNonConnect.html";
};

/**
 *
 * @param {string} action
 * @param {FormData} form
 * @returns
 */
const MakeRequest = async (action, form) => {
  const params = new URLSearchParams();

  form.forEach((value, key) => {
    params.append(key, value);
  });
  
  params.append("action", action)

  try {
    return await fetch(`${BACKEND_URL}${params}`, {
      method: "GET",
    });
  } catch (error) {
    alert(error);
  }
};
