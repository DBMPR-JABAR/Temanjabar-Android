package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.common.presentation.widgets.ConfirmationDialog
import id.go.jabarprov.dbmpr.feature.report.R
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentListPhotoBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.PhotoAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.ListPhotoViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store.ListPhotoAction
import id.go.jabarprov.dbmpr.utils.extensions.showToast
import kotlinx.coroutines.launch
import id.go.jabarprov.dbmpr.common.R as CR

private const val TAG = "ListPhotoFragment"

@AndroidEntryPoint
class ListPhotoFragment : Fragment() {

    private val listPhotoFragmentArgs: ListPhotoFragmentArgs by navArgs()

    private val listPhotoViewModel by viewModels<ListPhotoViewModel>()

    private lateinit var binding: FragmentListPhotoBinding

    private val initialListPhoto by lazy { listPhotoFragmentArgs.listPhoto }

    private val photoAdapter by lazy {
        PhotoAdapter(3, 16).apply {
            setOnClickListener {
                if (listPhotoViewModel.uiState.value.isModeDeleteImage) {
                    val action = if (it.isSelected) {
                        ListPhotoAction.UnselectPhoto(it)
                    } else {
                        ListPhotoAction.SelectPhoto(it)
                    }
                    listPhotoViewModel.processAction(action)
                } else {
                    showToast("SHOW IMAGE")
                }
            }
            setOnLongClickListener {
                listPhotoViewModel.processAction(ListPhotoAction.SelectPhoto(it))
                listPhotoViewModel.processAction(ListPhotoAction.SetModeDeleteImage(true))
            }
        }
    }

    private val confirmationDialog by lazy {
        ConfirmationDialog
            .Builder()
            .setTitle(getString(R.string.hapus_foto))
            .setDescription(getString(R.string.hapus_foto_deskripsi))
            .setOnPositiveButtonClickListener {
                listPhotoViewModel.processAction(ListPhotoAction.DeleteSelectedImage)
            }
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeListPhotoState()
        initListPhoto()
    }

    private fun initUI() {
        binding.apply {
            recyclerViewPhoto.apply {
                adapter = photoAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val position = parent.getChildAdapterPosition(view)
                        outRect.apply {
                            if (position % 3 == 2) {
                                right = 16
                            }
                            left = 16
                            bottom = 16
                        }
                    }
                })
            }

            buttonCancel.setOnClickListener {
                listPhotoViewModel.processAction(ListPhotoAction.SetModeDeleteImage(false))
            }

            buttonDelete.setOnClickListener {
                confirmationDialog.show(childFragmentManager)
            }
        }
    }

    private fun initListPhoto() {
        listPhotoViewModel.processAction(ListPhotoAction.InitializePhoto(initialListPhoto.toList()))
    }

    private fun observeListPhotoState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                listPhotoViewModel.uiState.collect {
                    setSelectableMode(it.isModeDeleteImage)
                    updateListPhoto(it.currentListPhoto)
                }
            }
        }
    }

    private fun setSelectableMode(enable: Boolean) {
        if (enable) {
            requireActivity().window?.statusBarColor =
                ContextCompat.getColor(requireContext(), CR.color.color_secondary_alpha_low)
            binding.apply {
                appBarLayoutDefaultMode.isVisible = false
                appBarLayoutSelectableImageMode.isVisible = true
            }
        } else {
            requireActivity().window?.statusBarColor =
                ContextCompat.getColor(requireContext(), android.R.color.white)
            binding.apply {
                appBarLayoutDefaultMode.isVisible = true
                appBarLayoutSelectableImageMode.isVisible = false
            }
        }
    }

    private fun updateListPhoto(listPhoto: List<PhotoModel>) {
        if (listPhoto.none { it.isSelected }) {
            listPhotoViewModel.processAction(ListPhotoAction.SetModeDeleteImage(false))
        }
        binding.textViewTotalImage.text = "${listPhoto.filter { it.isSelected }.size} Terpilih"
        photoAdapter.submitList(listPhoto)
    }

    override fun onDestroy() {
        requireActivity().window?.statusBarColor =
            ContextCompat.getColor(requireContext(), android.R.color.white)
        super.onDestroy()
    }
}