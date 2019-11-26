package com.x.popularpeople.ui.popular_people

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.x.popularpeople.R
import com.x.popularpeople.ui.person_details.PersonDetailsActivity
import kotlinx.android.synthetic.main.activity_popular_people.*

class PopularPeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_people)

//        hello_world.setOnClickListener {
//            val intent = Intent(this, PersonDetailsActivity::class.java)
//            intent.putExtra("id", 2)
//            this.startActivity(intent)
//        }

    }
}
