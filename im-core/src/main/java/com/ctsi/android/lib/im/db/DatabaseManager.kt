package com.ctsi.android.lib.im.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.ctsi.android.lib.im.CtsiIM.DEFAULT_PATH
import com.ctsi.android.lib.im.db.dao.MessageDao
import com.ctsi.android.lib.im.db.dao.UserDao
import com.ctsi.android.lib.im.db.entity.MessageEntity
import com.ctsi.android.lib.im.db.entity.UserEntity
import kotlinx.coroutines.*

/**
 * Class : DatabaseManager
 * Create by GaoHW at 2023-1-3 11:14.
 * Description:
 */
internal object DatabaseManager {

    private var instance: MessageDatabase? = null
    private val dataScope by lazy { CoroutineScope(Dispatchers.IO) }

    fun connect(context: Context, userId: String) {
        val path = "$DEFAULT_PATH/$userId/db".apply { FileUtils.createOrExistsDir(this) }
        instance = Room.databaseBuilder(context, MessageDatabase::class.java, "$path/message.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    LogUtils.e("database onCreate! ${db.path}")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    LogUtils.e("database onOpen! ${db.path}")
                }
            }).build()
    }

    fun close() {
        dataScope.cancel("Database connection closed!!")
        instance?.close()
        instance = null
    }

    //room数据库无法冲主线程访问
    fun enqueue(block: (DatabaseManager) -> Unit) =
        dataScope.launch { block.invoke(this@DatabaseManager) }

    fun <T> execute(block: (DatabaseManager) -> T) =
        runBlocking(dataScope.coroutineContext) { block.invoke(this@DatabaseManager) }

    fun userDao(): UserDao? = instance?.userDao()

    fun messageDao(): MessageDao? = instance?.messageDao()

    @Database(
        entities = [UserEntity::class, MessageEntity::class], version = 1,
        exportSchema = false
    )
    internal abstract class MessageDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao

        abstract fun messageDao(): MessageDao
    }
}