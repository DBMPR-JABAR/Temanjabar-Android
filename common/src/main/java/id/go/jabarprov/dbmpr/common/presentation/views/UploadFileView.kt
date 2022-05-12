package id.go.jabarprov.dbmpr.common.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import id.go.jabarprov.dbmpr.common.R
import id.go.jabarprov.dbmpr.core_views.R as CoreViewR

class UploadFileView(context: Context, attrs: AttributeSet? = null) :
    MaterialCardView(context, attrs) {

    private val mTypedArray by lazy {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.UploadFileView,
            0,
            0
        )
    }
    private val mUploadType by lazy { mTypedArray.getInt(R.styleable.UploadFileView_uploadType, 0) }
    private lateinit var mImageView: ImageView
    private lateinit var mImageViewIcon: ImageView
    private lateinit var mTextDescription: TextView

    init {
        initView()
    }

    private fun initView() {
        isClickable = true
        isFocusable = true
        radius = context.resources.getDimension(CoreViewR.dimen.medium_radius)
        elevation = 0f

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_upload_file_view, this, true)

        mImageView = view.findViewById(R.id.image_view_upload)
        mImageViewIcon = view.findViewById(R.id.image_view_icon_upload)
        mTextDescription = view.findViewById(R.id.text_view_desc_upload)

        setUpIcon()
        setUpDescription()
    }

    private fun setUpIcon() {
        when (mUploadType) {
            1 -> mImageViewIcon.setImageResource(R.drawable.ic_folder)
            2 -> mImageViewIcon.setImageResource(R.drawable.ic_camera)
            3 -> mImageViewIcon.setImageResource(R.drawable.ic_video)
        }
    }

    private fun setUpDescription() {
        when (mUploadType) {
            1 -> mTextDescription.text = "Upload file anda disini"
            2 -> mTextDescription.text = "Upload gambar anda disini"
            3 -> mTextDescription.text = "Upload video anda disini"
        }
    }
}