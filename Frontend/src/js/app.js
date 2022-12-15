let auth0Client = null;


const fetchAuthConfig = async () => await fetch("/auth_config.json");

const configureClient = async () => {
    const response = await fetchAuthConfig();
    const config = await response.json();

    auth0Client = await auth0.createAuth0Client({
        domain: config.domain,
        clientId: config.clientId
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