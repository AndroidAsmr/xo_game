package com.example.xogame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.example.xogame.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
    //binding
    private lateinit var binding : ActivityMainBinding
    //Determining the turn of the first and second person
    private var flag = 0
    // To Calculate the number of selected tiles
    private var counter = 0
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initViews
        binding.apply {
            //reset Click listener
            btnReset.setOnClickListener {
                //Resetting the score of both players to zero
                newScore(TAG_RESET)
                //Clearing all tiles for a new game
                newGame()
            }
        }
        
    }
    
    fun oxClick(view : View)
    {
        val btn = view as ImageView
        binding.apply {
            //The tile selected by the user should not have been previously selected
            if (btn.drawable == null)
            {
                //one move
                counter ++
                //Assigning the turn  to the player
                when(flag)
                {
                    0 ->{
                        //The turn is for the first player
                        btn.setImageResource(R.drawable.ic_o)
                        btn.tag = TAG_O
                        //Displaying the green border
                        cardO.strokeWidth = 0
                        cardX.strokeWidth = 2
                        //changing the turn
                        flag = 1
                    }
                    1 ->{
                        //The turn is for the first player
                        btn.setImageResource(R.drawable.ic_x)
                        btn.tag = TAG_X
                        //Displaying the green border
                        cardO.strokeWidth = 2
                        cardX.strokeWidth = 0
                        //changing the turn
                        flag = 0
                    }
                }
                //using a lifeCycle method to introduce a delay
                lifecycleScope.launch {
                    //checking the pre-determined conditions for winning the XO Game
                    if (iv1.tag == iv2.tag && iv2.tag == iv3.tag && iv3.tag != null)
                    {
                        //we find the winner of the game through the selected image view tag
                        newScore(iv1.tag.toString())
                        withAnimation(iv1,iv2,iv3)
                    }else  if (iv4.tag == iv5.tag && iv5.tag == iv6.tag && iv6.tag != null)
                    {
                        //we find the winner of the game through the selected image view tag
                        newScore(iv4.tag.toString())
                        withAnimation(iv4,iv5,iv6)
                    }else  if (iv7.tag == iv8.tag && iv8.tag == iv9.tag && iv9.tag != null)
                    {
                        //we find the winner of the game through the selected image view tag
                        newScore(iv7.tag.toString())
                        withAnimation(iv7,iv8,iv9)
                    }else  if (iv1.tag == iv5.tag && iv5.tag == iv9.tag && iv9.tag != null)
                    {
                        //we find the winner of the game through the selected image view tag
                        newScore(iv1.tag.toString())
                        withAnimation(iv1,iv5,iv9)
                    }else
                        if (iv3.tag == iv5.tag && iv5.tag == iv7.tag && iv7.tag != null)
                        {
                            //we find the winner of the game through the selected image view tag
                            newScore(iv3.tag.toString())
                            withAnimation(iv3,iv5,iv7)
                        }else  if (iv1.tag == iv4.tag && iv4.tag == iv7.tag && iv7.tag != null)
                        {
                            //we find the winner of the game through the selected image view tag
                            newScore(iv1.tag.toString())
                            withAnimation(iv1,iv4,iv7)
                        }else  if (iv2.tag == iv5.tag && iv5.tag == iv8.tag && iv8.tag != null)
                        {
                            //we find the winner of the game through the selected image view tag
                            newScore(iv2.tag.toString())
                            withAnimation(iv2,iv5,iv8)
                        }else  if (iv3.tag == iv6.tag && iv6.tag == iv9.tag && iv9.tag != null)
                        {
                            //we find the winner of the game through the selected image view tag
                            newScore(iv3.tag.toString())
                            withAnimation(iv3,iv6,iv9)
                        }else if(counter == 9)
                        {
                            //if all the tiles are filled and there is no winner , we empty the tiles for new game
                            Toast.makeText(this@MainActivity , "This game has no winner" , Toast.LENGTH_SHORT).show()
                            newGame()
                        }
                }
            }
        }
    }
    
    private suspend fun withAnimation(viewOne:View,viewTwo:View,viewThree:View)
    {
        //changing the color of the correct tiles to green one by one
        viewOne.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        viewTwo.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        viewThree.setBackgroundResource(R.drawable.board_back_green)
        newGame()
    }
    
    @SuppressLint("SetTextI18n")
    private fun newScore(tag:String)
    {
        //we check whether the score of which user should be changed or whether both scores should be reset
        when(tag)
        {
            TAG_X->{
                //getting the current score of user x
                val xPoint = binding.boardXCount.text.toString().toInt()
                //Increasing the score
                binding.boardXCount.text = (xPoint + 1).toString()
            }
            TAG_O ->{
                //getting the current score of user o
                val oPoint = binding.boardOCount.text.toString().toInt()
                //Increasing the score
                binding.boardOCount.text = (oPoint + 1).toString()
            }
            TAG_RESET ->{
                //resetting the score of both users on the panel
                binding.boardOCount.text = "0"
                binding.boardXCount.text = "0"
            }
        }
    }
    
    private fun newGame(){
        //changing the turn
        flag = 0
        //Resetting the number of moves made
        counter = 0
        //find all imageViews and clear drawable and tags
        binding.grid.children.filterIsInstance<ImageView>().forEach { iv ->
            iv.setImageDrawable(null)
            iv.tag = null
            iv.setBackgroundResource(R.drawable.board_back)
        }
        //Adding a green border to the user whose turn it is to play
        binding.cardO.strokeWidth = 2
        binding.cardX.strokeWidth = 0
    }
}












