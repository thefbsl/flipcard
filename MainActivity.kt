package com.example.flipcards

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(){
    private var myCurrentPosition:Int = 1
    private var myCardWordList: ArrayList<CardWord>? = null

    lateinit var frontAnim: AnimatorSet
    lateinit var backAnim: AnimatorSet
    var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardFront: TextView = findViewById(R.id.card_front)
        val cardBack: TextView = findViewById(R.id.card_back)

        setCardWord()

        val scale:Float = applicationContext.resources.displayMetrics.density
        cardFront.cameraDistance = 8000 * scale
        cardBack.cameraDistance = 8000 * scale

        frontAnim = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet


        cardFront.setOnClickListener{
            if(isFront){
                frontAnim.setTarget(cardFront)
                backAnim.setTarget(cardBack)
                frontAnim.start()
                backAnim.start()
                isFront = false
            }else{
                frontAnim.setTarget(cardBack)
                backAnim.setTarget(cardFront)
                backAnim.start()
                frontAnim.start()
                isFront = true
            }
        }
    }

    private fun setCardWord(){
        isFront = true
        val cardFront: TextView = findViewById(R.id.card_front)
        val cardBack: TextView = findViewById(R.id.card_back)
        val btnNext: Button = findViewById(R.id.btn_next)
        val btnPrev: Button = findViewById(R.id.btn_prev)

        val myCardWordList = ConstantsWords.getCardWord()

        val word = myCardWordList!![myCurrentPosition - 1]

        cardFront.text = word.frontWord
        cardBack.text = word.backWord

        btnNext.setOnClickListener(){
            myCurrentPosition++
            isFront = true
            if(myCurrentPosition == myCardWordList!!.size) {Toast.makeText(this, "over", Toast.LENGTH_SHORT).show()}
            setCardWord()
        }
        btnPrev.setOnClickListener(){
            myCurrentPosition--
            isFront = true
            if(myCurrentPosition == 0) {Toast.makeText(this, "over", Toast.LENGTH_SHORT).show()}
            setCardWord()
        }

    }
}