package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clearAllButton.setOnClickListener {
            allClearAction()
        }

        binding.backspaceButton.setOnClickListener {
            backSpaceAction()
        }

        binding.equalButton.setOnClickListener {
            equalAction()
        }

        binding.minusButton.setOnClickListener {
            operationAction(it)
        }

        binding.plusButton.setOnClickListener {
            operationAction(it)
        }

        binding.multiplyButton.setOnClickListener {
            operationAction(it)
        }

        binding.divisionButton.setOnClickListener {
            operationAction(it)
        }

        binding.percentButton.setOnClickListener {
            //
        }

        binding.dotButton.setOnClickListener {
            addDot()
        }

        binding.zeroButton.setOnClickListener {
            numberAction(it)
        }

        binding.oneButton.setOnClickListener {
            numberAction(it)
        }

        binding.twoButton.setOnClickListener {
            numberAction(it)
        }

        binding.threeButton.setOnClickListener {
            numberAction(it)
        }

        binding.fourButton.setOnClickListener {
            numberAction(it)
        }

        binding.fiveButton.setOnClickListener {
            numberAction(it)
        }

        binding.sixButton.setOnClickListener {
            numberAction(it)
        }

        binding.sevenButton.setOnClickListener {
            numberAction(it)
        }

        binding.eightButton.setOnClickListener {
            numberAction(it)
        }

        binding.nineButton.setOnClickListener {
            numberAction(it)
        }
    }

    private fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == getString(R.string.dot)) {
                if (canAddDecimal) {
                    binding.inputTextView.append(view.text)
                }

                canAddDecimal = false
            } else {
                binding.inputTextView.append(view.text)
            }

            canAddOperation = true
        }
    }

    private fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            binding.inputTextView.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    private fun allClearAction() {
        binding.inputTextView.text = ""
        binding.resultTextView.text = ""
    }

    private fun backSpaceAction() {
        val length = binding.inputTextView.length()

        if (length > 0) {
            binding.inputTextView.text = binding.inputTextView.text.subSequence(0, length - 1)
        } else {
            binding.resultTextView.text = ""
        }
    }

    private fun equalAction() {
        binding.resultTextView.text = calculateResults()
    }

    private fun calculateResults(): String {
        val digitsOperator = digitsOperators()

        if (digitsOperator.isEmpty()) {
            return ""
        }

        val timesDivision = timesDivisionCalculate(digitsOperator)

        if (timesDivision.isEmpty()) {
            return ""
        }

        val result = addSubtractCalculate(timesDivision)

        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float

                if (operator.toString() == getString(R.string.plus)) {
                    result += nextDigit
                }

                if (operator.toString() == getString(R.string.minus)) {
                    result -= nextDigit
                }
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList

        while (list.contains(getString(R.string.multiply).single())
            || list.contains(getString(R.string.division).single())
        ) {
            list = calculateTimesDivision(list)
        }

        return list
    }

    private fun calculateTimesDivision(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()

        var restartIndex = passedList.size

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val previousDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float

                when (operator.toString()) {
                    getString(R.string.multiply) -> {
                        newList.add(previousDigit * nextDigit)
                        restartIndex = i + 1
                    }

                    getString(R.string.division) -> {
                        newList.add(previousDigit / nextDigit)
                        restartIndex = i + 1
                    }

                    else -> {
                        newList.add(previousDigit)
                        newList.add(operator)
                    }
                }
            }

            if (i > restartIndex) {
                newList.add(passedList[i])
            }
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""

        for (character in binding.inputTextView.text) {
            if (character.isDigit() || character.toString() == getString(R.string.dot)) {
                currentDigit += character
            } else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if (currentDigit != "") {
            list.add(currentDigit.toFloat())
        }

        return list
    }

    private fun addDot() {
        binding.inputTextView.append(".")
    }
}