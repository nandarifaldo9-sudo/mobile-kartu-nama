package com.example.kartumahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kartumahasiswa.ui.theme.KartuMahasiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KartuMahasiswaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KartuMahasiswa()
                }
            }
        }
    }
}

@Composable
fun KartuMahasiswa() {
    val nama = "RIfaldo Ikhwan Nanda"
    var salam by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = nama.first().toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = nama,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("NIM: 2411026")
                Text("Program Studi: Informatika")
                Text("Status: Mahasiswa Aktif")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { salam = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tampilkan Salam")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (salam) "Halo, $nama! Selamat datang." else "Tekan tombol untuk melihat salam.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KartuMahasiswaPreview() {
    KartuMahasiswaTheme {
        KartuMahasiswa()
    }
}