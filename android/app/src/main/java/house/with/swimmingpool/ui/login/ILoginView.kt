package house.with.swimmingpool.ui.login

interface ILoginView {

    fun showSmsCodeFragment(phone: String)

    fun onSmsCodeCorrect()

    fun onLoginSuccess(name: String)
}