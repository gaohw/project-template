package com.ctsi.android.lib.im.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ctsi.android.lib.im.db.entity.MessageEntity

/**
 * Class : MessageDao
 * Create by GaoHW at 2023-1-10 9:12.
 * Description:
 */
@Dao
internal interface MessageDao {

    @Query("select * from message")
    fun queryAllMessage(): List<MessageEntity>?

    @Query(
        "SELECT m1.* FROM message m1 " +
                "INNER JOIN ( SELECT MAX(cid) AS cid FROM message GROUP BY chatId ) m2 " +
                "ON m1.cid = m2.cid order by cid desc"
    )
    fun queryChatList(): List<MessageEntity>?

    @Query("select count(*) from message where chatId=:chatId and read=0")
    fun queryUnreadCount(chatId: String): Int

    @Query("select * from message where chatId=:chatId order by cid desc")
    fun queryMessageByChatId(chatId: String): List<MessageEntity>

    @Query("select * from message where chatId=:chatId order by cid desc limit :limit offset :offset")
    fun queryMessageByChatId(chatId: String, limit: Int, offset: Int): List<MessageEntity>?

    @Query("select * from message where id=:id")
    fun queryMessageById(id: String): MessageEntity?

    @Insert
    fun insertMessage(message: MessageEntity)

    @Update
    fun updateMessage(message: MessageEntity)

    @Query("update message set read=:status where chatId=:chatId")
    fun updateReadStatusInChat(chatId: String, status: Int)

    @Query("update message set send=:status where chatId=:chatId")
    fun updateSendStatusInChat(chatId: String, status: Int)

    @Query("update message set read=:status where id=:msgId")
    fun updateReadStatus(msgId: String, status: Int)

    @Query("update message set send=:status where id=:msgId")
    fun updateSendStatus(msgId: String, status: Int)
}