package com.cajosaso.clima

import org.json.JSONObject

interface RestUpdater {
    fun executeUpdate(servResponse: JSONObject)
}