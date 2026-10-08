package com.example.kartumahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kartumahasiswa.ui.theme.KartuMahasiswaTheme
import kotlinx.coroutines.launch

data class Profil(val nama: String, val nim: String, val prodi: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KartuMahasiswaTheme {
                ProfilMahasiswaScreen()
            }
        }
    }
}

// Parent (stateful): menyimpan semua state
@Composable
fun ProfilMahasiswaScreen() {
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("") }
    var profil by remember { mutableStateOf<Profil?>(null) }

    val namaValid = nama.trim().length >= 3
    val nimValid = nim.isNotEmpty() && nim.all { it.isDigit() }
    val prodiValid = prodi.isNotBlank()
    val formValid = namaValid && nimValid && prodiValid

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Form Profil Mahasiswa",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            ProfilForm(
                nama = nama,
                nim = nim,
                prodi = prodi,
                namaError = nama.isNotEmpty() && !namaValid,
                nimError = nim.isNotEmpty() && !nimValid,
                onNamaChange = { nama = it },
                onNimChange = { nim = it },
                onProdiChange = { prodi = it },
                simpanAktif = formValid,
                onSimpan = {
                    profil = Profil(nama.trim(), nim, prodi.trim())
                    scope.launch {
                        snackbarHostState.showSnackbar("Profil berhasil disimpan")
                    }
                }
            )

            KartuProfil(profil)
        }
    }
}

// Child (stateless): hanya menerima nilai dan mengirim event
@Composable
fun ProfilForm(
    nama: String,
    nim: String,
    prodi: String,
    namaError: Boolean,
    nimError: Boolean,
    onNamaChange: (String) -> Unit,
    onNimChange: (String) -> Unit,
    onProdiChange: (String) -> Unit,
    simpanAktif: Boolean,
    onSimpan: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = nama,
            onValueChange = onNamaChange,
            label = { Text("Nama") },
            isError = namaError,
            supportingText = { if (namaError) Text("Nama minimal 3 karakter") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nim,
            onValueChange = onNimChange,
            label = { Text("NIM") },
            isError = nimError,
            supportingText = { if (nimError) Text("NIM hanya boleh berisi angka") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = prodi,
            onValueChange = onProdiChange,
            label = { Text("Program Studi") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSimpan,
            enabled = simpanAktif,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun KartuProfil(profil: Profil?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (profil == null) {
                Text("Belum ada data. Isi form lalu tekan Simpan.")
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = profil.nama.first().uppercase(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = profil.nama,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("NIM: ${profil.nim}")
                Text("Program Studi: ${profil.prodi}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilMahasiswaPreview() {
    KartuMahasiswaTheme {
        ProfilMahasiswaScreen()
    }
}