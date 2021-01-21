package com.victorthanh.weather.domain.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class City(): RealmObject(){
    @PrimaryKey
    var name: String=""
}