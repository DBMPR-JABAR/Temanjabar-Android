package id.go.jabarprov.dbmpr.features.webview.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import id.go.jabarprov.dbmpr.common.widget.LoadingDialog
import id.go.jabarprov.dbmpr.features.webview.databinding.FragmentWebViewBinding
import id.go.jabarprov.dbmpr.utils.extensions.showToast
import id.go.jabarprov.dbmpr.utils.utils.FileUtils

private const val TAG = "WebViewFragment"

class WebViewFragment : Fragment() {

    private val loadingDialog by lazy { LoadingDialog.create() }

    private lateinit var binding: FragmentWebViewBinding

    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null

    private var mCameraPhotoPath: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                chooseImageFile()
            } else {
                showToast("Izin Kamera Ditolak!")
            }
        }

    private val chooseImageActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            var results: Array<Uri>? = null

            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data == null || result.data?.dataString == null) {
                    if (mCameraPhotoPath != null) {
                        results = arrayOf(mCameraPhotoPath!!)
                    }
                } else {
                    val dataString = result.data?.dataString
                    if (!dataString.isNullOrEmpty()) {
                        results = arrayOf(Uri.parse(dataString))
                    }
                }
            }

            if (result != null) {
                mFilePathCallback?.onReceiveValue(results)
            }

            mFilePathCallback = null
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        loadUrl()
        onBackPressed()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI() {
        binding.apply {
            swipeRefreshLayout.apply {
                setOnRefreshListener {
                    binding.webView.reload()
                    isRefreshing = false
                }
            }

            webView.apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        loadingDialog.show(childFragmentManager)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        loadingDialog.dismiss()
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        mFilePathCallback = filePathCallback

                        if (isHasCameraPermission()) {
                            chooseImageFile()
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }

                        return true
                    }
                }

                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
            }
        }
    }

    private fun loadUrl() {
        binding.webView.loadUrl("https://tj.temanjabar.net/")
    }

    private fun isHasCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun chooseImageFile() {
        val takePictureIntent = createTakePictureIntent()
        val selectPictureIntent = createSelectPictureIntent()

        val listIntent = listOf(takePictureIntent, selectPictureIntent)

        val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, selectPictureIntent)
            putExtra(Intent.EXTRA_TITLE, "Pilih File Yang Akan Diupload")
            putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                listIntent.toTypedArray()
            )
        }

        chooseImageActivityLauncher.launch(chooserIntent)
    }

    private fun createTakePictureIntent(): Intent {
        val imageFile = FileUtils.createPictureCacheFile(requireContext())
        mCameraPhotoPath = FileUtils.getFileUri(requireActivity(), imageFile)
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra("PhotoPath", mCameraPhotoPath)
            putExtra(MediaStore.EXTRA_OUTPUT, mCameraPhotoPath)
        }
    }

    private fun createSelectPictureIntent(): Intent {
        return Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
    }

}