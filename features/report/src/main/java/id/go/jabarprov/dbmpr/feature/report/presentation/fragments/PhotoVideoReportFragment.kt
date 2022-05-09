package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentPhotoVideoReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.PhotoAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.MakeReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store.MakeReportAction
import id.go.jabarprov.dbmpr.utils.contract.CaptureLimitedVideo
import id.go.jabarprov.dbmpr.utils.extensions.getUri
import id.go.jabarprov.dbmpr.utils.extensions.showToast
import id.go.jabarprov.dbmpr.utils.utils.FileUtils.Companion.createPictureCacheFile
import id.go.jabarprov.dbmpr.utils.utils.FileUtils.Companion.createVideoCacheFile
import kotlinx.coroutines.launch

private const val TAG = "PhotoVideoReportFragmen"

@AndroidEntryPoint
class PhotoVideoReportFragment : Fragment() {

    private val makeReportViewModel by viewModels<MakeReportViewModel>({
        requireParentFragment()
    })

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                makeReportViewModel.processAction(MakeReportAction.AddPhoto(imageFileUri))
            }
        }

    private val takeVideoLauncher = registerForActivityResult(CaptureLimitedVideo(15)) {

    }

    private val takePicturePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                takePicture()
            } else {
                showToast("Akses Kamera Ditolak")
            }
        }

    private val takeVideoPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                it[Manifest.permission.CAMERA] == true && it[Manifest.permission.RECORD_AUDIO] == true -> {
                    takeVideo()
                }

                it[Manifest.permission.CAMERA] == false -> {
                    showToast("Akses Kamera Ditolak")
                }

                it[Manifest.permission.RECORD_AUDIO] == false -> {
                    showToast("Akses Audio Ditolak")
                }
            }
        }

    private lateinit var imageFileUri: Uri

    private lateinit var videoFileUri: Uri

    private val photoAdapter by lazy { PhotoAdapter() }

    private lateinit var binding: FragmentPhotoVideoReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoVideoReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeMakeReportState()
    }

    private fun initUI() {
        binding.apply {

            recyclerViewPhoto.apply {
                adapter = photoAdapter
                layoutManager = object : GridLayoutManager(requireContext(), 5) {
                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                        lp?.width = (width - (20 * 4)) / spanCount
                        lp?.height = lp?.width ?: height
                        return true
                    }
                }
            }

            uploadFileViewPicture.setOnClickListener {
                if (isCameraPermissionGranted()) {
                    takePicture()
                } else {
                    takePicturePermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }

            uploadFileViewVideo.setOnClickListener {
                if (isCameraPermissionGranted() && isAudioPermissionGranted()) {
                    takeVideo()
                } else {
                    takeVideoPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                        )
                    )
                }
            }
        }
    }

    private fun takePicture() {
        imageFileUri = createPictureCacheFile(requireContext()).getUri(requireActivity())
        takePictureLauncher.launch(imageFileUri)
    }

    private fun takeVideo() {
        videoFileUri = createVideoCacheFile(requireContext()).getUri(requireActivity())
        takeVideoLauncher.launch(videoFileUri)
    }

    private fun isCameraPermissionGranted() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun isAudioPermissionGranted() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                makeReportViewModel.uiState.collect {
                    processListPhoto(it.listPhoto)
                }
            }
        }
    }

    private fun processListPhoto(listPhoto: List<PhotoModel>) {
        if (listPhoto.isEmpty()) {
            binding.apply {
                textViewTitleSectionAllPhoto.isVisible = false
                recyclerViewPhoto.isVisible = false
            }
        } else {
            binding.apply {
                textViewTitleSectionAllPhoto.isVisible = true
                recyclerViewPhoto.isVisible = true
            }
        }

        photoAdapter.submitList(listPhoto)
    }


}