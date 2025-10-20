package com.example.intentexample

import com.example.intentexample.ui.theme.IntentExampleTheme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


class ShareActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val origem: String
        val mensagem: String?

        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            origem = "App Externo"
            mensagem = intent.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            origem = "Este App"
            mensagem = intent.getStringExtra("mensagem")
        }

        setContent {
            IntentExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShareScreen(
                        origem = origem,
                        mensagem = mensagem ?: "",
                        onLongPress = {
                            // Retorna para o início do app
                            val intent = Intent(this, WelcomeActivity::class.java)
                            // limpa a pilha para evitar voltar de novo pra cá
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShareScreen(
    origem: String,
    mensagem: String,
    onLongPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() }
                )
            }
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = origem,
                onValueChange = {},
                label = { Text("Origem da mensagem") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            OutlinedTextField(
                value = mensagem,
                onValueChange = {},
                label = { Text("Mensagem recebida") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = "Pressione e segure em qualquer parte da tela para voltar ao início",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
