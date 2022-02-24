package io.github.brunocu.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private val NUMBER = "NUMBERVIEW"
private val HISTORY = "HISTORYVIEW"

class MainActivity : AppCompatActivity() {
    private var numberView: TextView? = null
    private var historyView: TextView? = null
    private var hasPoint: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberView = findViewById(R.id.numberView)
        historyView = findViewById(R.id.historyView)
    }

    fun numberBtn(view: View) {
        if (view is Button) {
            // if last button = start new operation
            // short-circuit eval prevents crash if text is blank
            if (!historyView?.text.isNullOrBlank() && historyView?.text?.last() == '=') {
                historyView?.text = ""
                numberView?.text = ""
            }
            numberView?.append(view.text)
            historyView?.append(view.text)
            // TODO remove trailing zeros?
        }
    }

    fun pointBtn(view: View) {
        if (view is Button && !hasPoint) {
            if (!historyView?.text.isNullOrBlank() && historyView?.text?.last() == '=') {
                historyView?.text = ""
                numberView?.text = ""
            }
            if (numberView?.text.isNullOrEmpty()) {
                numberView?.append("0.")
                historyView?.append("0.")
            } else {
                numberView?.append(".")
                historyView?.append(".")
            }
            hasPoint = true
        }
    }

    fun opBtn(view: View) {
        if (view is Button) {
            // if last op = use result as first number
            if (!historyView?.text.isNullOrBlank() && historyView?.text?.last() == '=')
                historyView?.text = numberView?.text
            if (view.text == "x")
                historyView?.append("*")
            else
                historyView?.append(view.text)
            numberView?.text = ""
        }
    }

    fun clearBtn(view: View) {
        numberView?.text = ""
        historyView?.text = ""
        hasPoint = false
    }

    fun eqBtn(view: View) {
        // tokenize?
        // TODO unary operators
        // if last button was op, wait for number
        if (!historyView?.text.isNullOrBlank() && (historyView?.text?.last()
                ?.isDigit() == true || historyView?.text?.last() == '.')
        ) {
            var currNumber = ""
            var op: Char = '\r'
            var calc: Float = 0F
            for (character in historyView!!.text) {
                if (character.isDigit() || character == '.') {
                    // no PEMDAS - sequential interpreting
                    // (っ˘̩╭╮˘̩)っ
                    currNumber += character
                } else {
                    // do queued operation
                    when (op) {
                        '\r' -> calc = currNumber.toFloat()
                        '+' -> calc += currNumber.toFloat()
                        '-' -> calc -= currNumber.toFloat()
                        '*' -> calc *= currNumber.toFloat()
                        '/' -> calc /= currNumber.toFloat()
                    }
                    // queue next op
                    op = character
                    // clear working number
                    currNumber = ""
                }
            }
            // do final op
            // not DRY :c
            when (op) {
                '\r' -> calc = currNumber.toFloat()
                '+' -> calc += currNumber.toFloat()
                '-' -> calc -= currNumber.toFloat()
                '*' -> calc *= currNumber.toFloat()
                '/' -> calc /= currNumber.toFloat()
            }
            historyView!!.append("=")
            numberView?.text = calc.toString()
            hasPoint = false
        }
    }

    // save & recover values
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NUMBER, numberView?.text.toString())
        outState.putString(HISTORY, historyView?.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        numberView?.text = savedInstanceState.getString(NUMBER)
        historyView?.text = savedInstanceState.getString(HISTORY)
    }
}