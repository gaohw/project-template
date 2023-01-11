package com.ctsi.android.lib.im.db.dao

import androidx.room.*
import com.ctsi.android.lib.im.db.entity.UserEntity

/**
 * Class : UserDao
 * Create by GaoHW at 2023-1-10 9:43.
 * Description:
 */
@Dao
internal interface UserDao {

    @Query("select * from user")
    fun queryAllUser(): List<UserEntity>?

    @Query("select * from user where id=:id")
    fun queryUserById(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)
}