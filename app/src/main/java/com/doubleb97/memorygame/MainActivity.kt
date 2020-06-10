package com.doubleb97.memorygame


import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = mutableListOf(R.drawable.ic_globu, R.drawable.ic_plane, R.drawable.ic_lighting, R.drawable.ic_laptop)
        //Add each images twice so we can create pair
        images.addAll(images)
        //Randomize the order
        images.shuffle()

        buttons = listOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8)

       cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!!")
                //update model
                updateModels(index)
                //update view
                updateViews()


            }

        }

        }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons [index]
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.ic_code)


        }

    }

    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error cheking
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid Move", Toast.LENGTH_SHORT).show()
            return

        }
        // Three cases
        // 0 cards previously fliped over => restore cards + flip over the selected card
        // 1 cards previously fliped over => flip over the selected card + chek if the images match
        // 2cards  previously fliped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null ) {
            // 0 or 2 selected card previously
            indexOfSingleSelectedCard == position
             } else  {
            // 1 card was selected previously
            chekForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard == null

        }
        card.isFaceUp = !card.isFaceUp

    }

private fun checkForMatch(position1: Int, position2: Int) {
    if (cards[position1].identifier == cards[position2].identifier) {
        Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
        cards[position1].isMatched = true
        cards[position2].isMatched = true
    }
}
}




