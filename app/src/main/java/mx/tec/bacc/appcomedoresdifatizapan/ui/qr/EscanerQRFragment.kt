package mx.tec.bacc.appcomedoresdifatizapan.ui.qr

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import mx.tec.bacc.appcomedoresdifatizapan.R

class EscanerQRFragment : Fragment() {


    object escanerRes{
        var nombre : String = ""
        var curp : String = ""
        var fecha : String =""
        var apellidos : String = ""
        var consumo_tipo : String = ""
    }

    fun scanQRCode(view: View) {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setOrientationLocked(false) // Allow any orientation
        integrator.setPrompt("Scan a QR Code")
        integrator.initiateScan()
    }

    // This method is called when the scan is complete
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val scannedData = result.contents
                // Do something with the scanned data (e.g., store it in a variable)
                // You can now use 'scannedData' as the scanned QR code information
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


// Si el resultado es exitoso




    private val db = FirebaseFirestore.getInstance()


}