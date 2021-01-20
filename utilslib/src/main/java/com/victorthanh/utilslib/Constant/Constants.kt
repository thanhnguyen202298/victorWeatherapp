package com.victorthanh.utilslib.Constant

object Constants {

    /** VIDEO */
    const val SIZE_PAGE = 1000
    const val BLOCK_DURATION = 2000
    const val VIDEO_PAGE = 0
    const val VIDEO_SORT = "createdDate,desc"
    const val VIDEO_SORT_LEARNING = "desc"
    const val LENGTH_DATE = 10
    const val MIN_UPDATE_INTERVAL = 2000L
    const val THUMNAIL_CONSTANT = "contextuals/"

    /** SEMANTIC */
    const val SEMANTIC_TIME_POINT = 0L
    const val SEMANTIC_RADIUS = 50

    object EventMessage {
        /** Control Player */
        const val EVENT_MOVING_OFFSET = "event moving offset"

        /** Control Player */
        const val EVENT_SEEK_TO_TIME_FROM_CONTROL_PLAYER =
            "event seek to time video from control player"

        const val EVENT_SEEK_TO_PLAYING =
            "event seek to playing"

        /** Semantic Block */
        const val EVENT_SEEK_TO_TIME_FROM_SEMANTIC_BLOCK =
            "event seek to time video from semantic block"

        /** Semantic Chapter */
        const val EVENT_SELECT_VIDEO_FROM_SCAFFOLD_CORE = "event select video from scaffold core"

        /** Video Player */
        const val EVENT_PLAY_FROM_VIDEO_PLAYER = "event play pause video from video player"

        /** Configuration Change */
        const val EVENT_CONFIGURATION_CHANGED = "event configuration changed"

        /** Jump play-bar */
        const val EVENT_CHANGE_PROGRESS = "event change progress"

        /** Scroll SB */
        const val EVENT_SCROLL_SEMANTIC_BLOCK = "event scroll semantic block"

        /** Scroll SB To View Sketch */
        const val EVENT_SCROLL_VIEW_SKETCH  = "event scroll semantic view sketch"

        /** Show Hide View Sketch */
        const val EVENT_SHOW_VIEW_SKETCH  = "event show view sketch"

        /** Fit Center CG */
        const val EVENT_FIT_CENTER_CG = "event fit center cg"

        /** Fit Center CG */
        const val EVENT_LOGOUT_USER = "event logout user"

        /** Pause video player when switch screen */
        const val EVENT_PAUSE_VIDEO_PLAYER_WHEN_SWITCH_SCREEN = "event pause video play when switch screen"

        /** Event mode video */
        const val EVENT_STATE_MODE_VIDEO = "event state mode video"


        /** MODE VIDEO */
        const val MODE_PLAY_VIDEO = "MODE_PLAY_VIDEO"
        const val MODE_PAUSE_VIDEO = "MODE_PAUSE_VIDEO"
        const val MODE_STATE_IDLE = "MODE_STATE_IDLE"
        const val MODE_STATE_BUFFERING = "MODE_STATE_BUFFERING"
        const val MODE_STATE_READY = "MODE_STATE_READY"



        const val INIT_EMPTY_TIME_VIDEO = -1L
    }

    object EventType {
        const val TYPE_EVENT_ON_SCROLL = "type_event_on_scroll"
        const val TYPE_EVENT_OCN_TOUCH = "type_event_on_touch"
    }

    object Chapter {
        const val DISTANCE_SCROLL_TO_ITEM = 100
        const val TOTAL_ITEM_IN_ONCE_COLUMN = 4
        const val TOTAL_ITEM_IN_FOUR_COLUMN = 24

        const val TYPE_SELECT_ITEM_BY_CHAPTER = "Chapter"
        const val TYPE_SELECT_ITEM_BY_START_TIME = "StartTime"

        const val TYPE_ATTACHMENT_ONLY = "ATTACHMENT_ONLY"
        const val TYPE_EPISODIC = "EPISODIC"
        const val TYPE_DEFAULT_CHAPTER = "DEFAULT_CHAPTER"
    }

    object TabletDisplayMetric {
        const val TABLET_S4_WIDTH_PX = 2560
        const val TABLET_S4_DP_HEIGHT_PX_TYPE_1 = 1600

        /**  FullScreen 100%, disable Navigation Type*/
        const val TABLET_S4_DP_HEIGHT_PX_TYPE_2 = 1566

        /**  Navigation Type: Enable FullScreen Gesture and Enable Gesture hints*/
        const val TABLET_S4_DP_HEIGHT_PX_TYPE_3 = 0

        /**  Navigation Type: Enable Navigation Buttons */

        const val TABLET_NEXUS_10_WIDTH_PX = 2560
        const val TABLET_NEXUS_10_HEIGHT_PX = 1504

        const val TABLET_TAB_A97_WIDTH_PX = 1024
        const val TABLET_TAB_A97_HEIGHT_PX = 768
    }

    object PhoneDisplayMetric {
        const val PHONE_A50_WIDTH_PX = 1080
        const val PHONE_A50_HEIGHT_PX = 2131
        const val PHONE_A50_ID = 1

        const val PHONE_NOTE_10_LITE_WIDTH_PX = 1080
        const val PHONE_NOTE_10_LITE_HEIGHT_PX = 2189
        const val PHONE_NOTE_10_ID = 2

        const val PHONE_A7_WIDTH_PX = 1080
        const val PHONE_A7_HEIGHT_PX = 2094
        const val PHONE_A7_ID = 3
    }

    object IndexMenuMore {
        const val INDEX_LEARNING = 0
        const val INDEX_MESSAGING = 1
        const val INDEX_PEOPLE = 2
        const val INDEX_BUSINESS = 3
    }

    object IndexTabView {
        const val INDEX_SC = 0
        const val INDEX_CG = 1
        const val INDEX_SB = 2
    }

    object TypeBookmark {
        const val TYPE_BM_RED = 0
        const val TYPE_BM_YELLOW = 1
        const val TYPE_BM_BLUE = 2
    }

    object TypeFilterScreen {
        const val FILTER_BY_HOME = 0
        const val FILTER_BY_NOTE = 1
        const val FILTER_BY_EXPLORING = 2
        const val FILTER_BY_LIBRARY = 3
        const val FILTER_BY_MORE = 4
        const val FILTER_BY_LEARNING = 5
    }



}