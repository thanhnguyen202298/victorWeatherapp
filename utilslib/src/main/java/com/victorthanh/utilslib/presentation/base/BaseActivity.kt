package com.victorthanh.utilslib.presentation.base


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.*
import android.transition.Slide
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.victorthanh.utilslib.R
import com.victorthanh.utilslib.presentation.base.lang.LocaleManager
import com.victorthanh.utilslib.data.preference.PreferenceKey
import com.victorthanh.utilslib.domain.model.PointScreen
import com.victorthanh.utilslib.domain.model.message.AppMessage
import com.victorthanh.utilslib.utils.helper.ThemeHelper
import com.victorthanh.utilslib.utils.delayExecute
import com.victorthanh.utilslib.presentation.widget.AlertDialogCustom
import io.reactivex.disposables.CompositeDisposable
import kotlin.system.exitProcess

abstract class BaseActivity : AppCompatActivity(), BaseActivityImp {

    companion object {
        private var currentFragment: Fragment? = null
        const val THEME_BLACK = 1
        const val THEME_LIGHT = 1
    }

    private lateinit var compositeDisposable: CompositeDisposable
    private var isActivityActive = false
    private var finishActivity = false
    private lateinit var loadingDialog: AlertDialog
    private var loadingDialogView: View? = null
    private var fragmentList = ArrayList<Fragment>()
    private var mDoubleBackToExit: Boolean = false

    var isEdit: Boolean = false
    var PLAYER_MODE: Int = -1
    var idEdit: Int = -1

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            LocaleManager.setLocale(
                newBase!!,
                PreferenceKey.KEY_LANGUAGE.toString(),
                PreferenceKey.KEY_COUNTRY_CODE.toString()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupLoadingDialog()
        compositeDisposable = CompositeDisposable()
    }

    private fun setupLoadingDialog() {
        if (loadingDialogView != null) return
        val dialogBuilder = AlertDialog.Builder(this)
        loadingDialogView = this.layoutInflater.inflate(R.layout.custom_loading, null)
        dialogBuilder.setView(loadingDialogView)
        dialogBuilder.setCancelable(true)
        loadingDialog = dialogBuilder.create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        loadingDialog.setCanceledOnTouchOutside(false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setupWindowAnimations() {
        val slide = Slide()
        slide.mode
        slide.slideEdge = Gravity.END
        slide.duration = 150
        window.enterTransition = slide
    }

    override fun onResume() {
        super.onResume()
        isActivityActive = true
    }

    override fun onPause() {
        super.onPause()
        isActivityActive = false
    }

    override fun onStop() {
        super.onStop()
        if (finishActivity) finish()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun visibleView(vararg view: View?) {
        view.forEach { it?.visibility = View.VISIBLE }
    }

    override fun goneView(vararg view: View?) {
        view.forEach { it?.visibility = View.GONE }
    }

    override fun hideView(vararg view: View?) {
        view.forEach { it?.visibility = View.INVISIBLE }
    }

    abstract fun restartApp()

    override fun navigateToHomeActivity() {
        /*Navigator.navigateActivity(
            this,
            MainActivity::class.java,
            intentFlags = *intArrayOf(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK)
        )*/
    }

    fun transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    open fun updateTheme() {
        if (ThemeHelper.getTheme(applicationContext) <= THEME_BLACK) {
            setTheme(R.style.AppTheme)
        } else if (ThemeHelper.getTheme(applicationContext) === THEME_LIGHT) {
            setTheme(R.style.AppTheme)
        }
    }

    override fun showSnackBar(messageInfo: String, holderSnackbar: View?) {
        AlertDialogCustom.showTopSnackbar(holderSnackbar, messageInfo, true)
    }

    override fun showErrorTextInputLayout(view: TextInputLayout, errorMessage: String) {
    }

    override fun hideErrorTextInputLayout(view: TextInputLayout) {
    }

    override fun showProgressLoading(message: String) {

        if (loadingDialog != null && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
        loadingDialog.show()
    }

    override fun hideProgressLoading() {
        loadingDialog.hide()
    }

    override fun showMessageToast(message: String?) {

    }

    override fun showSnackBarError(appMessage: AppMessage, holderSnackbar: View?) {

    }

    fun hideKeyboard(viewfocus:View?) {
        val view = viewfocus?: this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getCurrentFragment(): Fragment? {
        return currentFragment
    }

    private fun setCurrentFragment(frag: Fragment) {
        currentFragment = frag
    }

    private var blockAllTouchEvent = false
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return blockAllTouchEvent || super.dispatchTouchEvent(ev)
    }

    open fun blockAllTouchEventOnAnimation() {
        blockAllTouchEvent = true
        compositeDisposable.add(
            delayExecute(300)
                .doOnSubscribe { }
                .doOnNext { _ -> blockAllTouchEvent = false }
                .subscribe()
        )
    }

    open fun blockAllTouchEventOnAnimation(delayMillisecond: Int) {
        blockAllTouchEvent = true
        compositeDisposable.add(
            delayExecute(delayMillisecond)
                .doOnSubscribe {}
                .doOnNext { _ -> blockAllTouchEvent = false }
                .subscribe()
        )
    }

    protected fun replaceFragment(idparent: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val backStateName = fragment.javaClass.name

        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.replace(idparent, fragment, backStateName)
            fragmentList.add(fragment)
        }
        setCurrentFragment(fragment)
        fragmentList.forEach {
            if (it.javaClass.name != backStateName && it.isAdded) fragmentTransaction.hide(it)
        }
        fragmentTransaction.commit()
    }

    var pointScreen: PointScreen? = null
    fun getDisplayRatio(): PointScreen {
        if (pointScreen != null)
            return pointScreen!!
        val displayMetrics = resources.displayMetrics
        pointScreen = PointScreen(
            displayMetrics.widthPixels,
            displayMetrics.heightPixels,
            displayMetrics.density
        )
        return pointScreen!!
    }

    override fun onBackPressed() {
        if (mDoubleBackToExit) {
            super.onBackPressed()
            moveTaskToBack(true)
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
        mDoubleBackToExit = true
        Toast.makeText(this, getString(R.string.show_message_back_to_exit), Toast.LENGTH_SHORT)
            .show()
        Handler(Looper.myLooper()!!).postDelayed({ mDoubleBackToExit = false }, 1000)
    }

    abstract fun getActivity(screenSize: Int): AppCompatActivity
}