package com.yogi.ecommerce.core.base

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment


/**
 * Created by oohyugi on 2020-02-03.
 */
abstract class BaseFragment : Fragment() {


    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }




}