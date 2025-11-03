package com.example.composefisebaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar Firebase (por seguridad)
        FirebaseApp.initializeApp(this)

        setContent {
            FirebaseComposeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseComposeApp() {
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Compose + Firebase") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = grade,
                onValueChange = { grade = it },
                label = { Text("Grade") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotEmpty() && grade.isNotEmpty()) {
                        val data = hashMapOf(
                            "name" to name,
                            "grade" to grade
                        )
                        db.collection("students")
                            .add(data)
                            .addOnSuccessListener {
                                message = "Registro guardado correctamente ✅"
                                name = ""
                                grade = ""
                            }
                            .addOnFailureListener {
                                message = "Error al guardar ❌"
                            }
                    } else {
                        message = "Por favor llena todos los campos ⚠️"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = message)
        }
    }
}
