package com.zootopia.presentation.sendletter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.letter.unplacedletter.UnplacedLetterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemSendLetterBinding

// todo : dto 생성
private const val TAG = "SendLetterAdapter_HeartPath"
class SendLetterAdapter(
    var unplacedLetterList: MutableList<UnplacedLetterDto> = mutableListOf(),
) : RecyclerView.Adapter<SendLetterAdapter.SendLetterViewHolder>() {
    private lateinit var spanBuilder: SpannableStringBuilder

    // Span 객체 생성
    val lightSpan = StyleSpan(R.style.Black_8_Light_NanumText)
    val sizeSpan = RelativeSizeSpan(0.8f)

    inner class SendLetterViewHolder(val binding: ItemSendLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(unplacedLetterDto: UnplacedLetterDto) = with(binding) {
            // 적용할 text
            spanBuilder = SpannableStringBuilder("to.${unplacedLetterDto.receiver}")
            // spanBuilder에 Span 적용 (여기서는 한 번 View를 생성하면 text를 수정할 일이 없기 떄문에 설정 상수를 SPAN_EXCLUSIVE_EXCLUSIVE로 한다.)
            spanBuilder.apply {
                setSpan(lightSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(sizeSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            textviewReceiveUser.text = spanBuilder

            // Click Event
            linearlayoutLetter.setOnClickListener {
                itemClickListener.itemClick(it, unplacedLetterDto, layoutPosition)
            }
        }
    }

    interface ItemClickListener {
        fun itemClick(view: View, unplacedLetterDto: UnplacedLetterDto, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendLetterViewHolder {
        return SendLetterViewHolder(
            ItemSendLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    fun submitData(unplacedLetterList: MutableList<UnplacedLetterDto>) {
        this.unplacedLetterList = unplacedLetterList
    }

    override fun onBindViewHolder(holder: SendLetterViewHolder, position: Int) {
        holder.bindInfo(unplacedLetterList[position])
    }

    override fun getItemCount(): Int {
        return unplacedLetterList.size
    }
}
