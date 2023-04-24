package ru.hutao.bioniclemetryapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import ru.hutao.bioniclemetryapp.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this@MainActivity, executor,
        object:BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            val text = "Ошибка авторизации $errString"
                binding.authstatus.text = text
                showToast((text))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                val text = "Сбой"
                binding.authstatus.text = text
                showToast((text))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val text = "Авторизация саксес"
                binding.authstatus.text = text
                showToast((text))
            }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Биометрическая авторизация")
            .setSubtitle("Вход с помощью отпечатка пальцев или фото лица")
            .setNegativeButtonText("Использовать пароль")
            .build()

        binding.btnauth.setOnClickListener {
        biometricPrompt.authenticate(promptInfo)
        }

    }
        private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}