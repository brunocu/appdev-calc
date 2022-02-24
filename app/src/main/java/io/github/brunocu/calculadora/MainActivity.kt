package io.github.brunocu.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var hasPoint: Boolean = false
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberBtn(view: View) {
        if (view is Button) {
            val numberView = findViewById<TextView>(R.id.numberView)
            numberView.append(view.text)
            // TODO remove trailing zeros?
        }
    }

    fun pointBtn(view: View) {
        if (view is Button && !hasPoint) {
            val numberView = findViewById<TextView>(R.id.numberView)
            if (numberView.text.isNullOrEmpty()) {
                numberView.append("0.")
            } else {
                numberView.append(".")
            }
            hasPoint = true
        }
    }

    fun opBtn(view: View) {
        if (view is Button) {
            val numberView = findViewById<TextView>(R.id.numberView)
            val historyView = findViewById<TextView>(R.id.historyView)
        }
    }

    fun clearBtn(view: View) {
        val numberView = findViewById<TextView>(R.id.numberView)
        val historyView = findViewById<TextView>(R.id.historyView)
        numberView.text = ""
        historyView.text = ""
        hasPoint = false
    }

    fun eqBtn(view: View) {
        // tokenize?
        // what if: last button is opn
    }
}