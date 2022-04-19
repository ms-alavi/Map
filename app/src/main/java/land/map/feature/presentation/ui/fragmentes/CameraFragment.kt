package land.map.feature.presentation.ui.fragmentes

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import land.map.amir.R
import land.map.amir.databinding.FragmentCameraBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor


class CameraFragment : Fragment() {
    private val Image_Capture_Code = 1

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var mBinding: FragmentCameraBinding
    private lateinit var imageCapture: ImageCapture
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
             {
                try {
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture!!.get()
                    startCameraX(cameraProvider)
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }, getExecutor()
        )

        mBinding.btnGetCapture.setOnClickListener {

            capturePhoto()
        }

    }

    //This code is from android developer section camerax
    private fun capturePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val pattern = "yyyy-MM-dd"

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(pattern, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("TAG", "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d("TAG", msg)
                }
            }
        )
    }



    private fun getExecutor(): Executor {
        return ContextCompat.getMainExecutor(requireContext())

    }

    private fun startCameraX(cameraProvider: ProcessCameraProvider) {
        //For starting camera first of all we have to unbind all the previous use case bindings
        cameraProvider.unbindAll()
        //select the default camera
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        //implement preview use case
        val preview = Preview.Builder()
            .build()

        preview.setSurfaceProvider(mBinding.preview.surfaceProvider)//attach a surface provider to preview

        //Image capture use case
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageCapture)


    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CameraFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}