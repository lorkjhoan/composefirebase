const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");

const app = express();
app.use(cors());
app.use(bodyParser.json());

let usuarios = [];

// Ruta para obtener usuarios
app.get("/usuarios", (req, res) => {
  res.json(usuarios);
});

// Ruta para agregar usuario
app.post("/usuarios", (req, res) => {
  const { name, email } = req.body;
  if (!name || !email) {
    return res.status(400).json({ message: "Faltan datos" });
  }
  usuarios.push({ name, email });
  res.json({ message: "Usuario agregado", usuarios });
});

app.listen(3000, () => console.log("Servidor corriendo en http://localhost:3000"));
