package house.with.swimmingpool.api.config.controllers

import android.util.Log
import com.google.gson.JsonObject
import house.with.swimmingpool.api.config.interfaces.IAuthService
import house.with.swimmingpool.api.retrofit.APIKEY
import house.with.swimmingpool.api.retrofit.IAuthLogin
import house.with.swimmingpool.api.retrofit.IVideos
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl : IAuthService {

    override fun loginUser(phone: String, code: String, onLoaded: (data: AuthLoginData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .loginUser(APIKEY ,phone, code)
                .enqueue(object : Callback<AuthLogin> {
                    override fun onResponse(call: Call<AuthLogin>, response: Response<AuthLogin>) {
                        try{
                            onLoaded.invoke(response.body()?.data, null)
                        }catch (e:Exception){}
                    }

                    override fun onFailure(call: Call<AuthLogin>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                            Log.e("loginTest", "Error:", t)
                        }catch (e:Exception){}
                    }
                })
    }

    fun checkPhoneNumber(phone: String, onLoaded: (data: Int?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin ::class.java)
                .checkPhoneNumber(phone)
                .enqueue(object : Callback<Answer<Int>>{
                    override fun onResponse(call: Call<Answer<Int>>, response: Response<Answer<Int>>) {
                        onLoaded.invoke(response.body()?.data, null)
                    }

                    override fun onFailure(call: Call<Answer<Int>>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                            Log.e("phoneTesting", "Error:", t)
                        }catch (e:Exception){}
                    }
                })
    }

    fun getSettings(onLoaded: (data: JsonObject?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .getSettings()
                .enqueue(object : Callback<Answer<JsonObject>>{
                    override fun onResponse(call: Call<Answer<JsonObject>>, response: Response<Answer<JsonObject>>) {
                        try{
                            onLoaded.invoke(response.body()?.data, null)
                            Log.e("OkH", response.body().toString())
                        }catch (e:Exception){}
                    }

                    override fun onFailure(call: Call<Answer<JsonObject>>, t: Throwable) {
                        onLoaded.invoke(null, t)
                        Log.e("getSettings", "Error:", t)
                    }

                })
    }

    fun getSettingsPhone(onLoaded: (data: String?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
            .getSettingsPhone()
            .enqueue(object : Callback<Answer<String>>{
                override fun onResponse(call: Call<Answer<String>>, response: Response<Answer<String>>) {
                    try{
                        onLoaded.invoke(response.body()?.data, null)
                        Log.e("OkH", response.body().toString())
                    }catch (e:Exception){}
                }

                override fun onFailure(call: Call<Answer<String>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("getSettingsPhone", "Error:", t)
                }

            })
    }

    override fun registerUserFirst(phone: String, onLoaded: (data: AuthRegisterFirstData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .registerUser(APIKEY ,phone)
                .enqueue(object : Callback<Answer<AuthRegisterFirstData>> {
                    override fun onResponse(call: Call<Answer<AuthRegisterFirstData>>, response: Response<Answer<AuthRegisterFirstData>>) {
                        try{
                            onLoaded.invoke(response.body()?.data, null)
                            Log.e("OkH", response.body().toString())
                        }catch (e:Exception){}
                    }

                    override fun onFailure(call: Call<Answer<AuthRegisterFirstData>>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                            Log.e("registerTest", "Error:", t)
                        }catch (e:Exception){}
                    }
                })
    }

    override fun confirmBySmsCode(phone: String, code: String, onLoaded: (data: AuthRegisterSecondData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .confirmBySmsCode(phone, code)
                .enqueue(object : Callback<AuthRegisterSecond> {
                    override fun onResponse(call: Call<AuthRegisterSecond>, response: Response<AuthRegisterSecond>) {
                        try{
                            onLoaded.invoke(response.body()?.data, null)
                        }catch (e:Exception){}
                    }

                    override fun onFailure(call: Call<AuthRegisterSecond>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                            Log.e("RegisterSecond", "Error:", t)
                        }catch (e:Exception){}
                    }
                })
    }

    override fun getSmsCodeAgain(phone: String, onLoaded: (data: CodeData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .getSmsCodeAgain(phone)
                .enqueue(object : Callback<Code> {
                    override fun onResponse(call: Call<Code>, response: Response<Code>) {
                        try{
                            onLoaded.invoke(response.body()?.data, null)
                        }catch (e:Exception){}
                    }

                    override fun onFailure(call: Call<Code>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                            Log.e("registerTest", "SMSError:", t)
                        }catch (e:Exception){}
                    }
                })
    }

    override fun setPassword(newPassword: String, oldPassword: String, onLoaded: (data: UpdatedUser?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .setPassword(newPassword = newPassword, oldPassword = oldPassword)
                .enqueue(object : Callback<UpdatedUser>{
                    override fun onResponse(call: Call<UpdatedUser>, response: Response<UpdatedUser>) {
                        try{
                            onLoaded.invoke(response.body(), null)
                        }catch (e:java.lang.Exception){}
                    }

                    override fun onFailure(call: Call<UpdatedUser>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                        }catch (e: Exception){}
                    }

                })
    }

    fun resetPassword(phone: String, onLoaded: (data: AuthRegisterFirstData?, e: Int?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
            .resetPassword(phone)
            .enqueue(object : Callback<Answer<AuthRegisterFirstData>>{
                override fun onResponse(
                    call: Call<Answer<AuthRegisterFirstData>>,
                    response: Response<Answer<AuthRegisterFirstData>>
                ) {
                    onLoaded.invoke(response.body()?.data, response.body()?.error)
                }

                override fun onFailure(call: Call<Answer<AuthRegisterFirstData>>, t: Throwable) {
                    try{
                        onLoaded.invoke(null, 0)
                    }catch (e: Exception){}
                }
            })
    }
}
