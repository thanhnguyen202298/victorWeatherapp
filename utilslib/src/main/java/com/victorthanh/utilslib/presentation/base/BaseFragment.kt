package com.victorthanh.utilslib.presentation.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.victorthanh.utilslib.Constant.Constants
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    lateinit var activity: BaseActivity

    lateinit var compositeDisposable: CompositeDisposable

    var orientation: Boolean = false

    var title = ""

    //endregion
    companion object {
        var isSeark = false
        var isConfigChange = false
        var isStateConnection = false
        var itemMenuSelected = Constants.TypeFilterScreen.FILTER_BY_EXPLORING

        const val URI: String = "URI_TO_DO"
        const val POSITION_PLAYER: String = "POSITION_PLAYER_TO_DO"
        const val WINDOW_ID: String = "WINDOW_ID"
        const val IS_PLAYING: String = "IS_PLAYING"
        const val VIDEO_ID: String = "VIDEO_ID_TO_DO"
        const val VIDEO: String = "VIDEO_CONTENT"
        const val ITEM_SCAFFOLD_CORE: String = "ITEM_SCAFFOLD_CORE"
        const val STATEPLAYER: String = "PLAYER_STATE"
        const val TOUCH_CHANGE_STATE_PLAYER: String = "TOUCH_CHANGE_STATE_PLAYER"

        // Save State For
        var isStatePlaying = false
        var isShowSearch = true
        var selectedFirstVideo = false
        var isStatePlayingLearning = false
        var isConfigChangeSB = false

        var countLoadWeb = 0
        var isFirstLoadWeb = false

        const val SAVE_CONFIG_CHANGE: String = "SAVE_CONFIG_CHANGE"
        const val SAVE_VIDEO: String = "SAVE_VIDEO"
        const val SAVE_SEEK_TO: String = "SAVE_SEEK_TO"
        const val SAVE_STATE_PAUSING: String = "SAVE_STATE_PAUSING"
        const val SAVE_INDEX_TAB: String = "SAVE_INDEX_TAB"
        const val SAVE_INDEX_CG: String = "SAVE_INDEX_CG"
        const val SAVE_INDEX_SB: String = "SAVE_INDEX_SB"
        const val SAVE_CAPTURE_IMAGE: String = "SAVE_CAPTURE_IMAGE"
        const val SAVE_CAPTURE_SB: String = "SAVE_CAPTURE_SB"
        const val SAVE_VIEW_CREATED: String = "SAVE_VIEW_CREATED"
        const val SAVE_ROOT_CONTEXT: String = "SAVE_ROOT_CONTEXT"

        const val SAVE_HIGHLIGHT_POS: String = "SAVE_HIGHLIGHT_POS"
        const val SAVE_CHAPTER_POS: String = "SAVE_CHAPTER_POS"
        const val SAVE_TYPE_HIGHLIGHT: String = "SAVE_TYPE_HIGHLIGHT"

        const val SAVE_SEARCH_VIDEO_CONTENT: String = "SAVE_SEARCH_VIDEO_CONTENT"
        const val SAVE_INDEX_SEARCH_VIDEO_CONTENT: String = "SAVE_INDEX_SEARCH_VIDEO_CONTENT"

        const val SAVE_SEARK_BLOCK: String = "SAVE_SEARK_BLOCK"
        const val INDEX_SEARK_BLOCK: String = "INDEX_SEARK_BLOCK"
        const val PATTERN_SEARK_BLOCK: String = "PATTERN_SEARK_BLOCK"
        const val SAVE_STATE_SCROLL_IN_CG: String = "SAVE_STATE_SCROLL_IN_CG"
        const val SAVE_STATE_CONFIG_CHANGE_IN_CG: String = "SAVE_STATE_CONFIG_CHANGE_IN_CG"

        const val SAVE_VIDEO_UID: String = "SAVE_VIDEO_UID"
        const val SAVE_STATE_LOOP_CHAPTER: String = "SAVE_STATE_LOOP_CHAPTER"
        const val SAVE_STATE_LOOP_BOOKMARK: String = "SAVE_STATE_LOOP_BOOKMARK"
        const val SAVE_USER_COMPREHENSION: String = "SAVE_USER_COMPREHENSION"
    }

    //region Lifecycle
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Setup CompositeDisposable */
        compositeDisposable = CompositeDisposable()
    }

    override fun onResume() {
        super.onResume()
        orientation = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    open fun setupView() = Unit

    fun getDisplayMetricPhone(): Int {
        // If is portrait
        if (!orientation) {
            var pointScreen = (context as BaseActivity).getDisplayRatio()
            if (pointScreen.scrwidth == Constants.PhoneDisplayMetric.PHONE_NOTE_10_LITE_WIDTH_PX
                && pointScreen.scrheight == Constants.PhoneDisplayMetric.PHONE_NOTE_10_LITE_HEIGHT_PX
            ) {
                /** PHONE NOTE 10 LITE*/
                return Constants.PhoneDisplayMetric.PHONE_NOTE_10_ID
            } else if (pointScreen.scrwidth == Constants.PhoneDisplayMetric.PHONE_A50_WIDTH_PX
                && pointScreen.scrheight == Constants.PhoneDisplayMetric.PHONE_A50_HEIGHT_PX
            ) {
                /** PHONE A50 */
                return Constants.PhoneDisplayMetric.PHONE_A50_ID
            } else if (pointScreen.scrwidth == Constants.PhoneDisplayMetric.PHONE_A7_WIDTH_PX
                && pointScreen.scrheight == Constants.PhoneDisplayMetric.PHONE_A7_HEIGHT_PX
            ) {
                /** PHONE A7 */
                return Constants.PhoneDisplayMetric.PHONE_A7_ID
            }
        } else {
            var pointScreen = (context as BaseActivity).getDisplayRatio()
            if (pointScreen.scrwidth == Constants.PhoneDisplayMetric.PHONE_NOTE_10_LITE_HEIGHT_PX
                && pointScreen.scrheight == Constants.PhoneDisplayMetric.PHONE_NOTE_10_LITE_WIDTH_PX
            ) {
                /** PHONE NOTE 10 LITE*/
                return Constants.PhoneDisplayMetric.PHONE_NOTE_10_ID
            } else if (pointScreen.scrwidth == Constants.PhoneDisplayMetric.PHONE_A50_HEIGHT_PX
                && pointScreen.scrheight == Constants.PhoneDisplayMetric.PHONE_A50_WIDTH_PX
            ) {
                /** PHONE A50 */
                return Constants.PhoneDisplayMetric.PHONE_A50_ID
            } else if (pointScreen.scrwidth == Constants.PhoneDisplayMetric.PHONE_A7_HEIGHT_PX
                && pointScreen.scrheight == Constants.PhoneDisplayMetric.PHONE_A7_WIDTH_PX
            ) {
                /** PHONE A7 */
                return Constants.PhoneDisplayMetric.PHONE_A7_ID
            }
        }
        return -1
    }
}