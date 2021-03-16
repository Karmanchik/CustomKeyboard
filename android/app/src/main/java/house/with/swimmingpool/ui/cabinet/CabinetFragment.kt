package house.with.swimmingpool.ui.cabinet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import house.with.swimmingpool.R

class CabinetFragment : Fragment(R.layout.fragment_cabinet) {

    companion object {
        private const val GALLERY_REQUEST = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.toLoginButton).setOnClickListener {
            findNavController().navigate(R.id.action_cabinetFragment_to_loginFragment)
        }
        val avatar = view.findViewById<ImageView>(R.id.avatar)
        avatar.setOnClickListener {
            val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, GALLERY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            CropImage.activity(data?.data)
                    .setAspectRatio(1, 1)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(requireContext(), this)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {

                view?.findViewById<ImageView>(R.id.avatar)?.let {
                    Glide.with(this)
                            .load(result.uri)
                            .circleCrop()
                            .into(it)
                }
            }
        }
    }

}