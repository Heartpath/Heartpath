package com.zootopia.presentation.receiveletter

import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.letter.ReceiveLetterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemReceiveLetterBinding
import com.zootopia.presentation.util.convertDateFormat

@RequiresApi(Build.VERSION_CODES.O)
class ReceiveLetterAdapter(val list: MutableList<ReceiveLetterDto>) :
    RecyclerView.Adapter<ReceiveLetterAdapter.ReceiveLetterViewHolder>() {
    private lateinit var spanBuilder: SpannableStringBuilder

    // Span 객체 생성
    val lightSpan = StyleSpan(R.style.Black_8_Light_NanumText)
    val sizeSpan = RelativeSizeSpan(0.8f)

    inner class ReceiveLetterViewHolder(val binding: ItemReceiveLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(receiveLetter: ReceiveLetterDto) = with(binding) {
            if (receiveLetter.read) {
                constraintlayoutLetter.setBackgroundResource(R.drawable.image_open_letter)
            } else {
                constraintlayoutLetter.setBackgroundResource(R.drawable.image_unopen_letter)
            }

            // 적용할 text
            spanBuilder = SpannableStringBuilder("from.${receiveLetter.sender}")
            // spanBuilder에 Span 적용 (여기서는 한 번 View를 생성하면 text를 수정할 일이 없기 떄문에 설정 상수를 SPAN_EXCLUSIVE_EXCLUSIVE로 한다.)
            spanBuilder.apply {
                setSpan(lightSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(sizeSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            textviewSendUser.text = spanBuilder
            textviewSendTime.text = convertDateFormat(receiveLetter.time)

            // Click Event
            linearlayoutLetter.setOnClickListener {
                itemClickListener.itemClick(it, layoutPosition)
            }
        }
    }

    interface ItemClickListener {
        fun itemClick(view: View, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveLetterViewHolder {
        return ReceiveLetterViewHolder(
            ItemReceiveLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: ReceiveLetterViewHolder, position: Int) {
        holder.bindInfo(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private const val TAG = "ReceiveLetterAdapter_HP"
    }
}
