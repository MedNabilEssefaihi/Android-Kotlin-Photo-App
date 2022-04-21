package com.example.login

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.login.data.Login
import com.example.login.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var login: Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // new instance
        login = Login()
        binding.login = login

        // event listener
        binding.btnLogin.setOnClickListener {
            checkLogin(it)
            hideKeyboard(it)
        }
    }

    private fun checkLogin(view: View) {
        val isEmail = checkEmail(binding.email.text.toString())
        val isPassword = checkPassword(binding.password.text.toString())

        if (!isEmail && !isPassword) {
            warning(true, "Email and password are invalid")
            return
        }
        if (!isEmail) {
            warning(true, "Email is invalid")
            return
        }
        if (!isPassword) {
            warning(true, "Password is invalid, 8 characters minimum")
            return
        }

        warning(false)
        Snackbar.make(view, "Login Success", Snackbar.LENGTH_LONG).show()


    }

    private fun checkEmail(email: String): Boolean {
        val check = !(email.isEmpty() || !isValidEmail(email))
        when (check) {
            false -> binding.email.setTextColor(Color.parseColor("#E74C3C"))
            else -> binding.email.setTextColor(Color.parseColor("#17202A"))
        }
        return check
    }

    private fun checkPassword(password: String): Boolean {
        val check = (password.length < 8)
        when (check) {
            true -> binding.password.setTextColor(Color.parseColor("#E74C3C"))
            else -> binding.password.setTextColor(Color.parseColor("#17202A"))
        }
        return !check
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun hideKeyboard(view: View?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun warning(visibility: Boolean, Message: String = "") {
        if (!visibility) {
            binding.warning.visibility = View.GONE
            return
        }

        binding.warning.visibility = View.VISIBLE
        binding.warning.text = Message
    }
}