package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.retrofit.APIKEY
import house.with.swimmingpool.api.retrofit.IAuthLogin
import house.with.swimmingpool.api.retrofit.IVideos
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl {

    fun loginUser(phone: String, code: String, onLoaded: (data: AuthLoginData?, e: Throwable?) -> Unit){
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

    fun registerUserFirst(phone: String, onLoaded: (data: AuthRegisterFirstData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .registerUser(APIKEY ,phone)
                .enqueue(object : Callback<AuthRegisterFirst> {
                    override fun onResponse(call: Call<AuthRegisterFirst>, response: Response<AuthRegisterFirst>) {
                        try{
                            onLoaded.invoke(response.body()?.data, null)
                        }catch (e:Exception){}
                    }

                    override fun onFailure(call: Call<AuthRegisterFirst>, t: Throwable) {
                        try{
                            onLoaded.invoke(null, t)
                            Log.e("registerTest", "Error:", t)
                        }catch (e:Exception){}
                    }
                })
    }

    fun confirmBySmsCode(phone: String, code: String, onLoaded: (data: AuthRegisterSecondData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .confirmBySmsCode(APIKEY ,phone, code)
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

    fun getSmsCodeAgain(phone: String, onLoaded: (data: CodeData?, e: Throwable?) -> Unit){
        getRetrofit().create(IAuthLogin :: class.java)
                .getSmsCodeAgain(APIKEY ,phone)
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
}
