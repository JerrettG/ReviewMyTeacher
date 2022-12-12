const express = require("express");
const { join } = require("path");
const app = express();

// Serve static assets from the /public folder
app.use(express.static(join(__dirname, "src")));
app.use("/css", express.static(join(__dirname, "src")));
app.use("/js", express.static(join(__dirname, "src")));
app.use("/pages", express.static(join(__dirname, "src")));
app.use("/util", express.static(join(__dirname, "src")));

// Endpoint to serve the configuration file
app.get("/auth_config.json", (req, res) => {
    res.sendFile(join(__dirname, "auth_config.json"));
});

app.get("/", (_, res) => {
    res.sendFile(join(__dirname, "/src/index.html"));
});

// Listen on port 8080
app.listen(8080);