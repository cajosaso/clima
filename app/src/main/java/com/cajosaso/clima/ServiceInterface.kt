package com.cajosaso.clima

import org.json.JSONObject

interface ServiceInterface {
    fun get(path: String, completionHandler: (response: JSONObject?) -> Unit)
}