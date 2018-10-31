package dk.sensormanager.pro5.stepdetector

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var mSensorManager:SensorManager? = null
    private var mStepDetector : Sensor ?= null
    private var counter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check whether we're recreating a previously destroyed instance
        //update the value if we are, otherwise, just initialize it to 0.
        counter = savedInstanceState?.getInt("STATE_STEPS") ?: 0

        setContentView(R.layout.activity_main)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mStepDetector= mSensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        if(mStepDetector == null)
        {
            Toast.makeText(this, "No Step Detector sensor was found!",
                Toast.LENGTH_LONG).show()
        }
        else
        {
            mSensorManager?.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_UI)
        }

        txtWalk.text = counter.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState)
        outState.putInt("STATE_STEPS", counter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSensorManager?.unregisterListener(this)

    }
    override fun onSensorChanged(event: SensorEvent?) {
                    counter+= 1
                    txtWalk.text = counter.toString()
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
