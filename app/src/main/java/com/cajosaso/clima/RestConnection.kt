package com.cajosaso.clima

import android.os.AsyncTask
import android.util.Log
import khttp.responses.Response
import org.json.JSONObject

class RestConnection constructor(updater: RestUpdater, url: String) : AsyncTask<Void, Void, JSONObject>() {
    private val TAG = "RestConnection"
    private val restUpdater: RestUpdater = updater
    private val restUrl: String = url

    override fun doInBackground(vararg params: Void?): JSONObject {
        val response : Response = khttp.get("http://clima-server.herokuapp.com/" + restUrl)
        val message : JSONObject = response.jsonObject
        Log.d(TAG, "Response: " + message.toString())
        return message
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: JSONObject) {
        super.onPostExecute(result)
        Log.d(TAG, "OnPostExecute: " + result.toString())
        restUpdater.executeUpdate(result)
        // ...
    }
}