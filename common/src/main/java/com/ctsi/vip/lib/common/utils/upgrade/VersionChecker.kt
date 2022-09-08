package com.ctsi.vip.lib.common.utils.upgrade

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.common.utils.download.DownloadListener
import com.ctsi.vip.lib.common.utils.download.DownloadManager
import java.io.File

/**
 * Created by GaoHW at 2022-7-5.
 *
 * Desc: apk版本检测与更新
 */

object VersionChecker {

    private fun upgrade(builder: Builder, listener: VersionUpgradeListener?) {
        val version = builder.version
        if (version.url.isNullOrEmpty()) {
            listener?.onUpgradeFailed("无效的下载地址，请检查")
            return
        }
        //已经在下载则返回
        if (DownloadManager.hasTask(version.getRemoteUrl())) {
            listener?.onUpgradeFailed("应用文件下载中")
            return
        }
        val targetFile = File(version.getStoragePath())
        val apkFilePath =
            if (targetFile.isFile) {
                FileUtils.createOrExistsDir(targetFile.parentFile)
                targetFile.absolutePath
            } else {
                FileUtils.createOrExistsDir(targetFile)
                "${targetFile.absolutePath}${File.separator}${version.code}-release.apk"
            }
        //弹出版本更新dialog
        val dialogBuilder = AlertDialog.Builder(builder.context)
            .setTitle("版本更新")
            .setMessage("版本：${version.code}\n\n更新内容：\n${version.desc}")
        dialogBuilder.setPositiveButton("立即更新") { _, _ ->
            DownloadManager.enqueue(version.getRemoteUrl(), apkFilePath, object : DownloadListener {

                override fun onDownloadStart(key: String) {
                    ToastUtils.showShort("应用下载中...")
                }

                override fun onDownloadSuccess() {
                    //下载成功自动安装，
//                    <provider
//                    android:name = "androidx.core.content.FileProvider"
//                    android:authorities = "com.project.template.upgrage.fileprovider"
//                    android:exported = "false"
//                    android:grantUriPermissions = "true"
//                    tools:replace = "android:authorities">
//                    <meta - data
//                    android:name = "android.support.FILE_PROVIDER_PATHS"
//                    android:resource = "@xml/file_paths" />
//                    </provider>

                    try {
                        AppUtils.installApp(apkFilePath)
//                        val apkFile = File(apkFilePath)
//                        val intent = Intent(Intent.ACTION_VIEW)
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        val uri: Uri =
//                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                                Uri.fromFile(apkFile)
//                            } else {
//                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                                FileProvider.getUriForFile(
//                                    Utils.getApp(), Utils.getApp().packageName + ".upgrage.fileprovider", apkFile
//                                )
//                            }
//                        intent.setDataAndType(uri, "application/vnd.android.package-archive")
//                        builder.context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        listener?.onUpgradeFailed("应用安装失败，请重试或手动安装")
                    }
                }

                override fun onDownloadFailed(cause: String?) {
                    listener?.onUpgradeFailed(cause)
                }

                override fun onDownloadProgress(curCount: Long, totalCount: Long) {
                    super.onDownloadProgress(curCount, totalCount)
                    when (builder.notifyType) {
                        NotifyType.Dialog.code -> {
                            //todo show update dialog
                        }
                        NotifyType.Notification.code -> {
                            //todo show update notification
                        }
                    }
                    listener?.onUpgradeProgress(curCount, totalCount)
                }
            })
        }
        if (version.isForce) {
            dialogBuilder.setCancelable(false)
        } else {
            dialogBuilder.setCancelable(true)
            dialogBuilder.setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
        }
        dialogBuilder.create().show()
    }

    enum class NotifyType(val code: Int) {
        None(0), Dialog(1), Notification(2)
    }

    class Builder(internal val context: Context) {

        internal val version = Version()
        internal var notifyType: Int = NotifyType.None.code

        fun forceUpgrade(force: Boolean): Builder {
            version.isForce = force
            return this
        }

        fun setNotifyType(type: NotifyType): Builder {
            this.notifyType = type.code
            return this
        }

        fun setUrl(url: String): Builder {
            version.url = url
            return this
        }

        fun setVersionCode(code: String): Builder {
            version.code = code
            return this
        }

        fun setVersionDesc(desc: String): Builder {
            version.desc = desc
            return this
        }

        fun setStoragePath(path: String): Builder {
            version.targetPath = path
            return this
        }

        fun upgrade(listener: VersionUpgradeListener? = null) = VersionChecker.upgrade(this, listener)
    }
}