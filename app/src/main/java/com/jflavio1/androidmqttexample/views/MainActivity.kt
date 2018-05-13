package com.jflavio1.androidmqttexample.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.jflavio1.androidmqttexample.R
import com.jflavio1.androidmqttexample.model.CustomLightSensor
import com.jflavio1.androidmqttexample.presenters.SensorsListPresenter
import com.jflavio1.androidmqttexample.presenters.SensorsListPresenterImpl

/**
 * MainActivity
 *
 * @author Jose Flavio - jflavio90@gmail.com
 * @since  6/5/17
 */
class MainActivity : AppCompatActivity(), SensorsListView {

    lateinit var presenter : SensorsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SensorsListPresenterImpl(this)
    }

    override fun onDestroy() {
        this.presenter.stopMqttService()
        super.onDestroy()
    }

    override fun setSensorPresenter(presenter: SensorsListPresenter) {
        this.presenter = presenter
        this.presenter.initMqttService()
    }

    override fun onMqttConnected() {
        this.presenter.getTemperatures()
    }

    override fun onMqttError(errorMessage: String) {
        Toast.makeText(this, "Error on connection: $errorMessage", Toast.LENGTH_SHORT).show()
    }

    override fun onMqttDisconnected() {
        Toast.makeText(this, "Disconnected from server...", Toast.LENGTH_SHORT).show()
    }

    override fun onMqttStopped() {
    }

    override fun setSensorsTemperature(sensors: ArrayList<CustomLightSensor>) {
        // TODO update UI
        for(i in sensors.indices) {
            Log.d("SensorsActivity", sensors[i].name)
        }
    }

    override fun getViewContext() = this

}
