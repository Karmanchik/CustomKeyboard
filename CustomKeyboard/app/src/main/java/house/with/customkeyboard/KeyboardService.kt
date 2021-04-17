package house.with.customkeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.Log
import android.view.KeyEvent
import android.view.View


class KeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    override fun onCreateInputView(): View? {
        val mKeyboardView = layoutInflater.inflate(R.layout.keyboard, null) as KeyboardView
        val mKeyboard = Keyboard(this, R.xml.keys_definition_ru)
//        mKeyboard.isShifted = isCapsOn //приводим клавиатуру к верхнему регистру, если шифт нажат включен
        mKeyboardView.keyboard = mKeyboard
        mKeyboardView.setOnKeyboardActionListener(this)
        return mKeyboardView
    }

    override fun onPress(p0: Int) {

    }

    override fun onRelease(p0: Int) {
    }

    override fun onKey(primaryCode: Int, ints: IntArray?) {
        Log.d("keyboard", "onKey $primaryCode")
        val ic = currentInputConnection
//        playClick(primaryCode)
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> ic.deleteSurroundingText(1, 0)
//            Keyboard.KEYCODE_SHIFT -> handleShift()
            Keyboard.KEYCODE_DONE -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
//            Keyboard.KEYCODE_ALT -> handleSymbolsSwitch()
//            Keyboard.KEYCODE_MODE_CHANGE -> handleLanguageSwitch()
            else -> {
                var code = primaryCode.toChar()
                if (Character.isLetter(code)) {
                    code = Character.toUpperCase(code)
                }
                ic.commitText(code.toString(), 1)
            }
        }
    }

    override fun onText(p0: CharSequence?) {
    }

    override fun swipeLeft() {
    }

    override fun swipeRight() {
    }

    override fun swipeDown() {
    }

    override fun swipeUp() {
    }

}