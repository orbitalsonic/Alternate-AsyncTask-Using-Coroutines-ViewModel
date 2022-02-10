package com.orbitalsonic.alternateasynctask

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private lateinit var loadingViewModel: LoadingViewModel
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProgressDialog()
        loadingViewModel = ViewModelProvider(this)[LoadingViewModel::class.java]
        loadingViewModel.isLoading.observe(this) {
            if (it) {
                progressDialog.dismiss()
                showMessage("Loading Successfully Completed")

            } else {
                progressDialog.dismiss()
                showMessage("Oh no! Something Happened")
            }
        }

        findViewById<Button>(R.id.btn_loading).setOnClickListener {
            progressDialog.show()
            loadingViewModel.performSomething(this)
        }
    }

    private fun initProgressDialog() {
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCanceledOnTouchOutside(false)

        val progressCard:MaterialCardView = progressDialog.findViewById(R.id.progress_card)
        progressCard.requestLayout()
        progressCard.layoutParams.width =
            (ScreenUtils.getScreenWidth(this) * .90).toInt()
        progressCard.layoutParams.height =
            (ScreenUtils.getScreenHeight(this) * .10).toInt()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}