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
      personneId
    })
  );
   if (isClient)
   {
       window.location.href = "PageAcceuilConnecte.html";
   }
   else {
        window.location.href = "PageAcceuilConnecteEmploye.html";
   }
};

const Logout = () => {
  localStorage.removeItem(LOCAL_STORAGE_KEY);
  window.location.href = "PageSeConnecter.html";
};

/**
 *
 * @param {string} action
 * @param {FormData} form
 * @returns
 */
const MakeRequest = async (action, form) => {
  const params = new URLSearchParams();

  params.append("action", action)

  form.forEach((value, key) => {
    params.append(key, value);
  });
  
  try {
    return await fetch(`${BACKEND_URL}?${params}`, {
      method: "GET",
    });
  } catch (error) {
    alert(error);
  }
};
