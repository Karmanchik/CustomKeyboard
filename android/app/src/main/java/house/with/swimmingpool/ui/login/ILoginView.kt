package house.with.swimmingpool.ui.login

interface ILoginView {

    fun showSmsCodeFragment(phone: String)

    fun onSmsCodeCorrect(smsCode: String)

    fun onLoginSuccess(name: String?)

    fun onAccessRecovery()
}