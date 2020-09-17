package com.example.kotlin.ui.splash

import com.example.kotlin.ui.base.BaseActivity
import com.example.kotlin.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val model: SplashViewModel by viewModel()

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        model.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }

}

