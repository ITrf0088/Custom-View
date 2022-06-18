package org.rasulov.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.rasulov.customviews.databinding.PartButtonsBinding

enum class ButtonType {
    POSITIVE, NEGATIVE
}

typealias onBottomButtonsClickListener = (ButtonType) -> Unit

class BottomButtonsView : ConstraintLayout {

    private lateinit var binding: PartButtonsBinding
    private var attrs: AttributeSet? = null
    private var defStyleAttr: Int = 0
    private var defStyleRes: Int = 0

    private var listener: onBottomButtonsClickListener? = null

    constructor(context: Context) : super(context) {
        Log.d("it0088", "constructor 1: ")
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.attrs = attrs
        this.defStyleAttr = R.attr.bottomButtonsAttrStyle
        this.defStyleRes = R.style.myBottomButtonsResStyle
        Log.d("it0088", "constructor 2: ")
        init()
    }


    fun init() {
        Log.d("it0088", "init")
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(view)
        initAttributes()
        initListener()
    }

    private fun initAttributes() {
        Log.d("it0088", "initAttributes: $attrs")
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )
        val bottomNegativeButtonText =
            typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
        setNegativeButtonText(bottomNegativeButtonText)

        val bottomPositiveButtonText =
            typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
        setPositiveButtonText(bottomPositiveButtonText)

        val bottomNegativeButtonBGColor = typedArray.getColor(
            R.styleable.BottomButtonsView_bottomNegativeBackgroundColor,
            Color.WHITE
        )
        setNegativeButtonColor(bottomNegativeButtonBGColor)

        val bottomPositiveButtonBGColor = typedArray.getColor(
            R.styleable.BottomButtonsView_bottomPositiveBackgroundColor,
            Color.BLACK
        )
        setPositiveButtonColor(bottomPositiveButtonBGColor)


        val isProgressMode =
            typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)
        setProgressMode(isProgressMode)
        typedArray.recycle()

    }

    private fun initListener() {
        binding.negativeButton.setOnClickListener {
            listener?.invoke(ButtonType.NEGATIVE)
        }
        binding.positiveButton.setOnClickListener {
            listener?.invoke(ButtonType.POSITIVE)
        }
    }


    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "default cancel"
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "default OK"
    }

    fun setNegativeButtonColor(color: Int) {
        binding.negativeButton.backgroundTintList =
            ColorStateList.valueOf(color)

    }

    fun setPositiveButtonColor(color: Int) {
        binding.positiveButton.backgroundTintList =
            ColorStateList.valueOf(color)
    }

    fun setProgressMode(mode: Boolean) {
        with(binding) {
            if (mode) {
                positiveButton.visibility = GONE
                negativeButton.visibility = GONE
                progress.visibility = VISIBLE
            } else {
                positiveButton.visibility = VISIBLE
                negativeButton.visibility = VISIBLE
                progress.visibility = GONE
            }
        }
    }

    fun setListener(listener: onBottomButtonsClickListener) {
        this.listener = listener
    }

    override fun onSaveInstanceState(): Parcelable {
        val parcelable = super.onSaveInstanceState()!!
        val savedState = SavedState(parcelable)
        savedState.positiveButtonText = binding.positiveButton.text.toString()
        savedState.negativeButtonText = binding.negativeButton.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        val superState = savedState.superState
        super.onRestoreInstanceState(superState)
        binding.positiveButton.text = savedState.positiveButtonText
        binding.negativeButton.text = savedState.negativeButtonText

    }

    class SavedState : BaseSavedState {

        var positiveButtonText: String? = null
        var negativeButtonText: String? = null

        constructor(parcelable: Parcelable) : super(parcelable)

        constructor(parcel: Parcel) : super(parcel) {
            positiveButtonText = parcel.readString()
            negativeButtonText = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(positiveButtonText)
            out.writeString(negativeButtonText)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return Array(size) {
                        null
                    }
                }

            }
        }
    }
}

