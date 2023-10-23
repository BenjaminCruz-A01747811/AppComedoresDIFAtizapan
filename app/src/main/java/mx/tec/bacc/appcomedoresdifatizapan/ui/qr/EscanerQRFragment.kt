package mx.tec.bacc.appcomedoresdifatizapan.ui.qr
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import mx.tec.bacc.appcomedoresdifatizapan.R
import java.io.IOException

class QRScannerFragment : Fragment() {
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        resultTextView = view.findViewById(R.id.resultTextView)
        val surfaceView = view.findViewById<SurfaceView>(R.id.surfaceView)

        detector = BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.QR_CODE).build()
        cameraSource = CameraSource.Builder(context, detector)
            .setRequestedPreviewSize(640, 480)
            .setAutoFocusEnabled(true)
            .build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    cameraSource.start(holder)
                } catch (ie: IOException) {
                    ie.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        detector.setProcessor(object : com.google.android.gms.vision.Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: com.google.android.gms.vision.Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() > 0) {
                    val qrCode = barcodes.valueAt(0).displayValue
                    resultTextView.text = qrCode
                }
            }
        })

        return view
    }
}