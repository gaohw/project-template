package com.ctsi.vip.lib.framework.utils.cache

import com.ctsi.vip.lib.framework.AppContext

/**
 * Class : CacheFactory
 * Create by GaoHW at 2022-10-13 11:41.
 * Description:
 */
object CacheFactory : Cache.Factory {

    override fun <K, V> build(type: CacheType?): Cache<K, V?> {
        val application = AppContext.getApplication()
        return when (type?.getCacheTypeId()) {
            CacheType.EXTRAS_TYPE_ID, CacheType.ACTIVITY_CACHE_TYPE_ID, CacheType.FRAGMENT_CACHE_TYPE_ID ->
                IntelligentCache<V>(type.calculateCacheSize(application))
            else -> LruCache<K, V?>(type?.calculateCacheSize(application) ?: 100)
        } as Cache<K, V?>
    }
}