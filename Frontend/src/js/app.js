import {createAuth0Client} from "@auth0/auth0-spa-js";

let auth0Client = null;

const configureClient = async () => {
    auth0Client = await createAuth0Client({
        domain: process.env.LBC_DOMAIN,
        clientId: process.env.LBC_CLIENT_ID
    });
};


window.onload = async () => {
    await configureClient();
    await updateUI();

    // NEW - check for the code and state parameters
    const query = window.location.search;
    if (query.includes("code=") && query.includes("state=")) {

        // Process the login state
        await auth0Client.handleRedirectCallback();

        await updateUI();

        // Use replaceState to redirect the user away and remove the querystring parameters
        window.history.replaceState({}, document.title, "/");
    }
};

const updateUI = async () => {
    const isAuthenticated = await auth0Client.isAuthenticated();
    const loginLogout = document.getElementById("login-logout");

    if (!isAuthenticated) {
        loginLogout.innerHTML = "Login";
        loginLogout.onclick = login;
        document.getElementById("reviews-user-made-button").style.display = 'none';
    }

    else if (isAuthenticated) {
        const user = await auth0Client.getUser();
        loginLogout.innerHTML = "Logout";
        loginLogout.onclick = logout;
        //display username if user is logged in
        document.getElementById('logged-in-welcome').innerHTML = `${user.nickname}`;
        document.getElementById("reviews-user-made-button").style.display = 'block';
        const accountLink = document.getElementById("account-link")
        accountLink.addEventListener("click", account);
        accountLink.style.display = 'block';
    }

};


const login = async () => {
    await auth0Client.loginWithRedirect({
        authorizationParams: {
            redirect_uri: window.location.origin
        }
    });
};

const logout = () => {
    auth0Client.logout({
        logoutParams: {
            returnTo: window.location.origin
        }
    });
};

const account = () => {
    document.getElementById("search-submission-forms").remove();
}