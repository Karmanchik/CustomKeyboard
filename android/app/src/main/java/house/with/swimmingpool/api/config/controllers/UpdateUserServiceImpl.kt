package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.retrofit.IUpdateUser
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.File


class UpdateUserServiceImpl {

    fun updateUserInfo(
            user: User,
            onLoaded: (data: User?, e: Throwable?) -> Unit,
    ) {
        getRetrofit().create<IUpdateUser>()
                .updateUserInfo(user)
                .enqueue(object : Callback<UpdatedUser> {
                    override fun onResponse(
                            call: Call<UpdatedUser>,
                            response: Response<UpdatedUser>,
                    ) {
                        onLoaded.invoke(response.body()?.data, null)
                    }

                    override fun onFailure(call: Call<UpdatedUser>, t: Throwable) {
                        onLoaded.invoke(null, t)
                        Log.e("updateUserError", "error", t)
                    }
                })
    }

    fun toRequest(value: String): RequestBody? =
            RequestBody.create("multipart/form-data".toMediaType(), value)

    fun updateAvatar(
            file: File,
            onLoaded: (data: User?, e: Throwable?) -> Unit,
    ) {
        val filePart = MultipartBody.Part
                .createFormData("file", file.name, RequestBody.create("image/*".toMediaTypeOrNull(), file))

        getRetrofit().create<IUpdateUser>()
                .updateAvatar(filePart)
                .enqueue(object : Callback<Answer<User>> {
                    override fun onResponse(call: Call<Answer<User>>, response: Response<Answer<User>>) {
                        onLoaded.invoke(response.body()?.data, null)
                    }

                    override fun onFailure(call: Call<Answer<User>>, t: Throwable) {
                        onLoaded.invoke(null, t)
                        Log.e("updateAvatar", "error", t)
                    }

                })
    }

    fun getUser(onLoaded: (data: User?, e: Throwable?) -> Unit) {
        getRetrofit().create<IUpdateUser>()
                .getUserInfo()
                .enqueue(object : Callback<UpdatedUser> {
                    override fun onResponse(
                            call: Call<UpdatedUser>,
                            response: Response<UpdatedUser>,
                    ) {
                        onLoaded.invoke(response.body()?.data, null)
                    }

                    override fun onFailure(call: Call<UpdatedUser>, t: Throwable) {
                        onLoaded.invoke(null, t)
                        Log.e("updateUserError", "error", t)
                    }
                })
    }
}