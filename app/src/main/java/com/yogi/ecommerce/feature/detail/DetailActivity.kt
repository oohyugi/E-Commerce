package com.yogi.ecommerce.feature.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.yogi.ecommerce.MainActivity
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.base.BaseActivity
import com.yogi.ecommerce.core.models.ProductPromoItemMdl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {


    companion object {
        val EXTRA_PRODUCT = "product"
        fun startThisActivity(context: Context, item: ProductPromoItemMdl?) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, item)
            context.startActivity(intent)

        }
    }

    var isFavorite = false

    var args: ProductPromoItemMdl? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        args = intent.getSerializableExtra(EXTRA_PRODUCT) as ProductPromoItemMdl
        tvName?.text = args?.title
        tvKet?.text = args?.description
        Glide.with(this).load(args?.imageUrl).into(ivDetail)
        tvPrice.text = args?.price
        isFavorite = args?.loved != 0
        if (args?.loved == 0) {
            ivFavorite?.setImageResource(R.drawable.ic_favorite_border_24dp)
        } else {
            ivFavorite?.setImageResource(R.drawable.ic_favorite_24dp)
        }

        ivFavorite?.setOnClickListener {

            isFavorite = !isFavorite
            if (!isFavorite) {
                ivFavorite?.setImageResource(R.drawable.ic_favorite_border_24dp)
            } else {
                ivFavorite?.setImageResource(R.drawable.ic_favorite_24dp)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.act_share) {
            shareItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareItem() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, args?.title)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
