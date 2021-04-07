package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.retrofit.IUpdateUser
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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
            RequestBody.create("text/plain".toMediaType(), value)

    fun updateAvatar(
            file: File,
            onLoaded: (data: UploadAvatarRequest?, e: Throwable?) -> Unit,
    ) {
        getRetrofit().create<IUpdateUser>()
                .updateAvatar(
                        RequestBody.create("image/png".toMediaTypeOrNull(),
                                file),
                        toRequest("image/png"),
                )
        .enqueue(object : Callback<UploadAvatarRequest> {
            override fun onResponse(call: Call<UploadAvatarRequest>, response: Response<UploadAvatarRequest>) {
                onLoaded.invoke(response.body(), null)
            }

            override fun onFailure(call: Call<UploadAvatarRequest>, t: Throwable) {
                onLoaded.invoke(null, t)
                Log.e("updateAvatar", "error", t)
            }

        })
    }
}