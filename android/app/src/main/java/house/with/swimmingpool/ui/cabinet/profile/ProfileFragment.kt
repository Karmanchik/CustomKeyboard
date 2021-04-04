package house.with.swimmingpool.ui.cabinet.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentPasswordBinding
import house.with.swimmingpool.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var profileBinding: FragmentProfileBinding? = null

    companion object {
        private const val GALLERY_REQUEST = 1
    }

    private val user = App.setting.user

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(layoutInflater)

        return profileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileBinding?.apply {

            closeProfileButton.setOnClickListener {
                App.setting.token = null
                findNavController().navigate(R.id.action_cabinetFragment_to_navigation_home)
            }

            avatarImageView.setOnClickListener {
                val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, GALLERY_REQUEST)
            }

            loadUser()
        }
    }

    private fun loadUser(){
        profileBinding?.apply {
            user?.apply {
                nameEditText.value = name
                emailEditText.value = email
                phoneTest.setText(phone)
                Glide.with(this@ProfileFragment)
                        .load(avatar)
                        .circleCrop()
                        .placeholder(R.drawable.circle_placeholder)
                        .error(R.drawable.circle_placeholder)
                        .into(avatarImageView)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        profileBinding?.apply {
            if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
                CropImage.activity(data?.data)
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(requireContext(), this@ProfileFragment)
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {

                    Glide.with(this@ProfileFragment)
                            .load(result.uri)
                            .circleCrop()
                            .into(avatarImageView)
                }
            }
        }
    }

    override fun onDestroy() {
        profileBinding = null
        super.onDestroy()
    }
}