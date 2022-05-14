package com.mbobiosio.firebaseremoteconfig.util

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
object DefaultConfigs {
    fun getDefaultParams(): Map<String, Any> {
        return hashMapOf(
            ConfigKeys.FORCE_UPDATE to false,
            ConfigKeys.DESCRIPTION to "Check update"
        )
    }

    object ConfigKeys {
        const val FORCE_UPDATE = "force_update"
        const val DESCRIPTION = "message"
    }
}
