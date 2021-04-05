package house.with.swimmingpool.ui.cabinet.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.UpdateUserServiceImpl
import house.with.swimmingpool.databinding.FragmentProfileBinding
import house.with.swimmingpool.models.User
import house.with.swimmingpool.ui.popups.PopupActivity

class ProfileFragment : Fragment() {

    private var profileBinding: FragmentProfileBinding? = null

    companion object {
        private const val GALLERY_REQUEST = 1
        private const val SIGN_OUT_REQUEST = 2
    }

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
                startActivityForResult(
                        Intent(requireContext(), PopupActivity :: class.java).apply {
                            putExtra(App.TYPE_OF_POPUP, App.SIGN_OUT)
                        },
                        SIGN_OUT_REQUEST
                )
            }

            avatarImageView.setOnClickListener {
                val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, GALLERY_REQUEST)
            }

            surnameEditText.getField()?.setOnFocusChangeListener { v, hasFocus ->
                if(!hasFocus){
                    updateUserInfo()
                    Log.e("onFocusChanged", "surnameEditText")
                }
            }

            emailEditText.getField()?.setOnFocusChangeListener { v, hasFocus ->
                if(!hasFocus) {
                    updateUserInfo()
                    Log.e("onFocusChanged", "emailEditText")
                }
            }

            nameEditText.getField()?.setOnFocusChangeListener { v, hasFocus ->
                if(!hasFocus){
                    updateUserInfo()
                    Log.e("onFocusChanged", "nameEditText")
                }
            }

            phoneTest.setOnFocusChangeListener { v, hasFocus ->
                if(!hasFocus){
                    updateUserInfo()
                    Log.e("onFocusChanged", "phoneTest")
                }
            }

            loadUser()
        }
    }

    private fun updateUserInfo(){
        profileBinding?.apply {
            UpdateUserServiceImpl().updateUserInfo(
                    User(
                            avatar = App.setting.user?.avatar,
                            context = App.setting.user?.context,
                            email = emailEditText.value,
                            id = App.setting.user?.id,
                            surname = surnameEditText.value,
                            login = App.setting.user?.login,
                            name = nameEditText.value,
                            phone = phoneTest.text.toString()
                            ),
            ){data, e ->
                if(data != null && e == null) {
                    App.setting.user = data
                    loadUser()
                }
            }
        }
    }

    override fun onResume() {
        loadUser()
        super.onResume()
    }

    private fun loadUser(){
        profileBinding?.apply {
            App.setting.user?.apply {
                nameEditText.value = name
                surnameEditText.value = surname
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
            if (requestCode == SIGN_OUT_REQUEST) {
                if (data?.getBooleanExtra(App.IS_SIGN_OUT, false) == true) {
                    App.setting.token = null
                    findNavController().navigate(R.id.action_cabinetFragment_to_navigation_home)
                }
            }
        }
    }

    override fun onDestroy() {
        profileBinding = null
        super.onDestroy()
    }
}