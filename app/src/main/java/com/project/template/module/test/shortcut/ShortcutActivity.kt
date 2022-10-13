package com.project.template.module.test.shortcut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.framework.base.BaseActivity
import com.ctsi.vip.lib.framework.base.BaseViewModel
import com.project.template.R
import com.project.template.databinding.ActivityShortcutBinding

/**
 * Created by GaoHW at 2022-7-4.
 *
 * Desc:
 */
class ShortcutActivity : BaseActivity<BaseViewModel, ActivityShortcutBinding>() {

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        mBinding.btnCreateDynamic.setOnClickListener {
            val shortcut = ShortcutInfoCompat.Builder(this, "shortcut_d_1")
                .setShortLabel("动态快捷方式")
                .setIntent(
                    Intent("com.project.template.module.test.shortcut")
                        .putExtra("next", "shortcut")
                )
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher_round))
                .build()
            ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
        }
        mBinding.btnRemoveDynamic.setOnClickListener {
            ShortcutManagerCompat.removeDynamicShortcuts(this, listOf("shortcut_d_1"))
        }
        mBinding.btnCreatePinned.setOnClickListener { createPinnedShortcut(this) }
    }

    private fun createPinnedShortcut(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val shortcutManager = getSystemService(ShortcutManager::class.java)
                if (shortcutManager?.isRequestPinShortcutSupported == true) {
                    // Assumes there's already a shortcut with the ID "my-shortcut".
                    // The shortcut must be enabled.

                    val compat = ShortcutInfoCompat.Builder(context, "shortcut_p_1")
                        .setShortLabel("固定快捷方式")
                        .setIntent(
                            Intent("com.project.template.module.test.shortcut")
                                .putExtra("next", "shortcut")
                        )
                        .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
                        .build()

                    // Create the PendingIntent object only if your app needs to be notified
                    // that the user allowed the shortcut to be pinned. Note that, if the
                    // pinning operation fails, your app isn't notified. We assume here that the
                    // app has implemented a method called createShortcutResultIntent() that
                    // returns a broadcast intent.
                    val shortcutInfo = compat.toShortcutInfo()
                    val callbackIntent = shortcutManager.createShortcutResultIntent(shortcutInfo)

                    // Configure the intent so that your app's broadcast receiver gets
                    // the callback successfully.For details, see PendingIntent.getBroadcast().
                    val successCallback = PendingIntent.getBroadcast(context, 0, callbackIntent, 0)
                    shortcutManager.requestPinShortcut(shortcutInfo, successCallback.intentSender)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastUtils.showShort("桌面方式创建失败")
            }
        } else {
            ToastUtils.showShort("系统版本过低，无法创建桌面快捷方式")
        }
    }
}