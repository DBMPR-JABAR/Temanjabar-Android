package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentVideoPlayerBinding

private const val TAG = "VideoPlayerFragment"

class VideoPlayerFragment : Fragment() {

    private val videoPlayerArgs: VideoPlayerFragmentArgs by navArgs()

    private val videoUri by lazy { Uri.parse(videoPlayerArgs.videoUri) }

    private lateinit var binding: FragmentVideoPlayerBinding

    private val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }

    override fun onResume() {
        super.onResume()
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(requireActivity().window.decorView)
        windowInsetsController?.isAppearanceLightStatusBars = false
        Log.d(TAG, "onResume: ${windowInsetsController?.isAppearanceLightStatusBars}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initVideo()
    }

    private fun initUI() {
        binding.root.apply {
            controllerAutoShow = true
            setShowFastForwardButton(false)
            setShowNextButton(false)
            setShowRewindButton(false)
            setShowPreviousButton(false)
            setShowSubtitleButton(false)
            setControllerOnFullScreenModeChangedListener {

            }
            player = exoPlayer
        }
    }

    private fun initVideo() {
        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.addMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun onPause() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(requireActivity().window.decorView)
        windowInsetsController?.isAppearanceLightStatusBars = true
        Log.d(TAG, "onDestroy: ${windowInsetsController?.isAppearanceLightStatusBars}")
        super.onPause()
    }

}