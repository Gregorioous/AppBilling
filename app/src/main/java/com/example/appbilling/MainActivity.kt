package com.example.appbilling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import android.view.SurfaceControl.Transaction
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.example.appbilling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),BillingProcessor.IBillingHandler {
    private val TAG = MainActivity::class.java.simpleName
    private var bp: BillingProcessor? = null
    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        bp = BillingProcessor.newBillingProcessor(this,"здесь должен быть ключ ",this)
        bp?.initialize()

        binding?.buttonFirst?.setOnClickListener{
            bp?.purchase(this@MainActivity, "android.test.purchased")
        }

    }


    override fun onProductPurchased(productId: String, details: PurchaseInfo?) {
        Toast.makeText(applicationContext,"ID of product $productId, details ${details?.purchaseData?.purchaseState}", Toast.LENGTH_SHORT).show()
    }


    override fun onPurchaseHistoryRestored() {
        TODO("Not yet implemented")
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        Toast.makeText(applicationContext,"error ${error?.localizedMessage}, code $errorCode", Toast.LENGTH_LONG).show()
    }

    override fun onBillingInitialized() {
        Toast.makeText(applicationContext,"bp insializated", Toast.LENGTH_SHORT).show()
        Log.e(TAG,"biling inited")
    }
}