package akhil.resolver.resolver

import akhil.resolver.resolver.Constants.AUTHORITY
import akhil.resolver.resolver.Constants.KEY_CONTENT
import akhil.resolver.resolver.Constants.KEY_ID
import akhil.resolver.resolver.Constants.KEY_TITLE
import akhil.resolver.resolver.Constants.TABLE_NAME
import android.content.ContentValues
import android.content.Context
import android.net.Uri

class ContentResolverHelper(private val context: Context) {
//    private val uri = "content://$AUTHORITY/$TABLE_NAME/$CODE_SINGLE_ITEM"
//    private val contentUri = Uri.parse(uri)
    private val contentResolver = context.contentResolver
    val getAllRoomItems: ArrayList<RoomItem>
        get() {
            val getAllRoomItems: ArrayList<RoomItem> = ArrayList<RoomItem>()
            val projection = arrayOf<String>(KEY_ID, KEY_TITLE, KEY_CONTENT)
            val uri = "content://$AUTHORITY/$TABLE_NAME/all"
            uri.loge()
            val cursor = contentResolver.query(Uri.parse(uri), projection, null, null, null)
            if (cursor != null && cursor.count > 0) {
                cursor.count.loge()
                while (cursor.moveToNext()) {
                    val roomItem = RoomItem(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2)
                    )
                    getAllRoomItems.add(roomItem)
                }
                cursor.close()
            }
            return getAllRoomItems
        }

    fun getRoomItem(id: Int): RoomItem {
        val projection = arrayOf<String>(KEY_ID, KEY_TITLE, KEY_CONTENT)
        val selection: String = "$KEY_ID=?"
        val selectionArgs = arrayOf(id.toString())
        val sortOrder: String? = null
        var roomItem = RoomItem()
        val uri = "content://$AUTHORITY/$TABLE_NAME/single"
        uri.loge()
        val cursor = contentResolver.query(Uri.parse(uri), projection, selection, selectionArgs, null)
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                roomItem = roomItem.copy(
                    id = cursor.getInt(0),
                    title = cursor.getString(1),
                    content = cursor.getString(2)
                )
            }
        }
        return roomItem
    }

    fun insertRoomRecord() {
        val dummyRoomRecord = RoomItem(1000, "Remote insertion", "This is inserted from remote app")
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, dummyRoomRecord.id)
        contentValues.put(KEY_TITLE, dummyRoomRecord.title)
        contentValues.put(KEY_CONTENT, dummyRoomRecord.content)
        val uri = "content://$AUTHORITY/$TABLE_NAME"
        uri.loge()
        contentResolver.insert(Uri.parse(uri), contentValues)
    }

}

data class RoomItem(
    val id: Int = 0,
    val title: String = "",
    val content: String = ""
)