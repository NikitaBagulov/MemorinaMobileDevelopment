package com.example.memorinamobiledevelopment

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var newGameButton: Button

    private var cardsArray = intArrayOf(
        1, 2, 3, 4, 5, 6,
        7, 8, 9, 10, 11, 12,
        1, 2, 3, 4, 5, 6,
        7, 8, 9, 10, 11, 12
    )

    private var imageArray = mutableMapOf<Int, Int>()
    private var selectedCards = mutableListOf<Int>()
    private var selectedButton = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        newGameButton = findViewById(R.id.newGameButton)

        newGameButton.setOnClickListener {
            resetGame()
        }

        initImages()
        startNewGame()
    }

    private fun initImages() {
        imageArray[1] = R.drawable.card1
        imageArray[2] = R.drawable.card2
        imageArray[3] = R.drawable.card3
        imageArray[4] = R.drawable.card4
        imageArray[5] = R.drawable.card5
        imageArray[6] = R.drawable.card6
        imageArray[7] = R.drawable.card7
        imageArray[8] = R.drawable.card8
        imageArray[9] = R.drawable.card9
        imageArray[10] = R.drawable.card10
        imageArray[11] = R.drawable.card11
        imageArray[12] = R.drawable.card12
    }

    private fun startNewGame() {
        selectedCards.clear()
        selectedButton.clear()
        gridLayout.removeAllViews()
        gridLayout.visibility = GridLayout.VISIBLE

        cardsArray.shuffle()
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val cardWidthPercentage = 23
        val cardHeightPercentage = 13

        val cardWidth = (screenWidth * cardWidthPercentage) / 100
        val cardHeight = (screenHeight * cardHeightPercentage) / 100
        for (i in 0 until 24) {
            val button = Button(this)
            button.setBackgroundResource(R.drawable.card_back)
            button.id = i
            button.setOnClickListener { view ->
                onCardClick(view as Button)
            }
            val layoutParams = GridLayout.LayoutParams()
            layoutParams.width = cardWidth
            layoutParams.height = cardHeight
            button.layoutParams = layoutParams
            gridLayout.addView(button)
        }
    }

    private fun onCardClick(button: Button) {
        val card = cardsArray[button.id]

        if (!selectedButton.contains(button) && selectedButton.size < 2) {
            selectedButton.add(button)
            selectedCards.add(card)
            button.setBackgroundResource(imageArray[card]!!)
        }

        if (selectedButton.size == 2) {
            Handler().postDelayed({
                checkCards()
            }, 1000)
        }
    }

    private fun checkCards() {
        if (selectedButton.size == 2) {
            val b1 = selectedButton[0]
            val b2 = selectedButton[1]

            val card1 = cardsArray[b1.id]
            val card2 = cardsArray[b2.id]

            if (card1 == card2) {
                b1.isEnabled = false
                b2.isEnabled = false
                selectedCards.clear()
                selectedButton.clear()

                var remainingCards = 0
                for (i in 0 until gridLayout.childCount) {
                    val child = gridLayout.getChildAt(i) as Button
                    if (child.isEnabled) {
                        remainingCards++
                    }
                }
                if (remainingCards == 0) {
                    showToast("Вы победили!")
                }
            } else {
                b1.setBackgroundResource(R.drawable.card_back)
                b2.setBackgroundResource(R.drawable.card_back)
                selectedCards.clear()
                selectedButton.clear()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun resetGame() {
        gridLayout.removeAllViews()
        startNewGame()
    }
}


