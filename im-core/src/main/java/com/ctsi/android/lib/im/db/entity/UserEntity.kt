package com.ctsi.android.lib.im.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class : UserEntity
 * Create by GaoHW at 2023-1-10 9:43.
 * Description:
 */
@Entity(tableName = "user")
internal class UserEntity {
    @PrimaryKey
    var id: String = ""
    var name: String? = null
    var avatar: String? = null
}