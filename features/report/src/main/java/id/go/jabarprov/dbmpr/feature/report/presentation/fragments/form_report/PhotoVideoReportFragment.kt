package id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentPhotoVideoReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.ThumbnailPhotoAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.VideoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.MakeReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportAction
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

    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>

    private lateinit var takeVideoLauncher: ActivityResultLauncher<Uri>

    private lateinit var takePicturePermissionLauncher: ActivityResultLauncher<String>

    private lateinit var takeVideoPermissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var imageFileUri: Uri

    private lateinit var videoFileUri: Uri

    private val thumbnailPhotoAdapter by lazy {
        ThumbnailPhotoAdapter {
            val action =
                id.go.jabarprov.dbmpr.feature.report.presentation.fragments.MakeReportFragmentDirections.actionReportFragmentToListPhotoFragment(
                    makeReportViewModel.uiState.value.currentListPhoto.toTypedArray()
                )
            findNavController().navigate(action)
        }
    }

    private lateinit var binding: FragmentPhotoVideoReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
                if (isSuccess) {
                    makeReportViewModel.processAction(MakeReportAction.AddPhoto(imageFileUri))
                }
            }

        takeVideoLauncher = registerForActivityResult(CaptureLimitedVideo(15)) { isSuccess ->
            if (isSuccess) {
                makeReportViewModel.processAction(MakeReportAction.AddVideo(videoFileUri))
            }
        }

        takePicturePermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    takePicture()
                } else {
                    showToast("Akses Kamera Ditolak")
                }
            }

        takeVideoPermissionLauncher =
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
    }

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
        onResultListPhotoFragment()
    }

    private fun initUI() {
        binding.apply {
            recyclerViewPhoto.apply {
                adapter = thumbnailPhotoAdapter
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

            buttonClearVideo.setOnClickListener {
                makeReportViewModel.processAction(MakeReportAction.ClearVideo)
            }

            buttonChangeVideo.setOnClickListener {
                takeVideo()
            }

            uploadFileViewVideo.setOnClickListener {
                if (makeReportViewModel.uiState.value.currentVideo != null) {
                    val action =
                        id.go.jabarprov.dbmpr.feature.report.presentation.fragments.MakeReportFragmentDirections.actionReportFragmentToVideoPlayerFragment(
                            makeReportViewModel.uiState.value.currentVideo?.uri.toString()
                        )
                    findNavController().navigate(action)
                } else {
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

    private fun onResultListPhotoFragment() {
        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
            get<List<PhotoModel>>("NEW_LIST_PHOTO")?.let {
                makeReportViewModel.processAction(MakeReportAction.UpdateListPhoto(it))
            }
            remove<List<PhotoModel>>("NEW_LIST_PHOTO")
        }
    }

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                makeReportViewModel.uiState.collect {
                    processListPhoto(it.currentListPhoto)
                    processVideo(it.currentVideo)
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

        thumbnailPhotoAdapter.submitList(listPhoto)
    }

    private fun processVideo(video: VideoModel?) {
        binding.apply {
            if (video != null) {
                uploadFileViewVideo.loadVideo(video.uri)
            } else {
                uploadFileViewVideo.clear()
            }
            buttonChangeVideo.isVisible = video != null
            buttonClearVideo.isVisible = video != null
        }
    }

}