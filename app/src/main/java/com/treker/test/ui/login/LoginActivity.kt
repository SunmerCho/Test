package com.treker.test.ui.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.treker.test.ACCESS_TOKEN
import com.treker.test.R
import com.treker.test.Toast
import com.treker.test.TrekerSharedPreferences
import com.treker.test.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            viewBinding.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                viewBinding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                viewBinding.password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            viewBinding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        viewBinding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                viewBinding.username.text.toString(),
                viewBinding.password.text.toString()
            )
        }

        viewBinding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    viewBinding.username.text.toString(),
                    viewBinding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            viewBinding.username.text.toString(),
                            viewBinding.password.text.toString()
                        )
                }
                false
            }

            viewBinding.login.setOnClickListener {
                viewBinding.loading.visibility = View.VISIBLE
                loginViewModel.login( viewBinding.username.text.toString(),  viewBinding.password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast("$welcome $displayName")
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast(errorString)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}