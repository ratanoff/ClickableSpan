package ru.ratanov.clickablespan

import android.os.Bundle
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        link_label.makeLinks(
            Pair("Terms of Service", View.OnClickListener { toast("Terms of Service clicked") }),
            Pair("Privacy Policy", View.OnClickListener { toast("Privacy Policy clicked") })
        )
    }


    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val linkText = this.text
        val spannableString = SpannableString(linkText)

        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Selection.setSelection((widget as TextView).text as Spannable, 0)
                    widget.invalidate()
                    link.second.onClick(widget)
                }
            }

            val startIndexOfLink = this.text.toString().indexOf(link.first)
            spannableString.setSpan(
                clickableSpan,
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }

        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
}
