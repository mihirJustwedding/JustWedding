package com.example.justweddingpro.ui.Fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.databinding.FragmentEventsBinding
import com.example.justweddingpro.ui.MyEventDetailsActivity.Companion.IsBackpress
import com.example.justweddingpro.ui.Response.EventListResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.CalenderEventAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale


class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private val currentCalender = Calendar.getInstance(Locale.getDefault())
    private val dateFormatForDisplaying: SimpleDateFormat =
        SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault())

    private var localDate: LocalDate? = null

    private var mEventDetailList = ArrayList<EventListResponse.EventDetail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onResume() {
        super.onResume()
        if (IsBackpress) {
            IsBackpress = false
            mApiCalling()
        }
    }

    private fun monthFormat(date: LocalDate): String? {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY)
        var mutableBookings: ArrayList<String> = ArrayList()
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT

        binding.compactCalendarView.setUseThreeLetterAbbreviation(true)
        binding.compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY)
        binding.compactCalendarView.setIsRtl(false)
        binding.compactCalendarView.displayOtherMonthDays(true)
        binding.compactCalendarView.setCurrentSelectedDayTextColor(Color.WHITE)
        binding.compactCalendarView.setCurrentDayTextColor(Color.WHITE)
        binding.compactCalendarView.shouldScrollMonth(false)

        val calendar = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM yyyy")
        val month_name = month_date.format(calendar.time)
        binding.tvMonth.text = month_name

        val formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy")
        val month_date1 = SimpleDateFormat("dd/MMMM/yyyy")
        val month_name1 = month_date1.format(calendar.time)
        localDate = LocalDate.parse(month_name1, formatter)

        binding.showPreviousMonthBut.setOnClickListener(View.OnClickListener {
            localDate = localDate?.minusMonths(1)
            binding.tvMonth.text = monthFormat(localDate!!)
            binding.compactCalendarView.scrollLeft()
        })

        binding.showNextMonthBut.setOnClickListener(View.OnClickListener {
            localDate = localDate?.plusMonths(1)
            binding.tvMonth.text = monthFormat(localDate!!)
            binding.compactCalendarView.scrollRight()
        })

        mApiCalling()
//        loadEvents()
//        loadEventsForYear(2017)
        binding.compactCalendarView.invalidate()

        logEventsByMonth(binding.compactCalendarView)
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        val ev1 = Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.")
        binding.compactCalendarView.addEvent(ev1)
        val ev2 = Event(Color.GREEN, 1433704251000L)
        binding.compactCalendarView.addEvent(ev2)
        val events: List<Event> =
            binding.compactCalendarView.getEvents(1433701251000L) // can also take a Date object


//        Handler().postDelayed({
//            var dateClicked = Date()
//            val inputPattern = "yyyy-MM-dd"
//            val inputFormat = SimpleDateFormat(inputPattern)
//            val mCurrentDate = inputFormat.format(dateClicked)
//            for (i in mEventDetailList.indices) {
//                var mDate = inputFormat.parse(mEventDetailList[i].eventDate!!)
//                if (mCurrentDate == mDate!!.time) {
//                    binding.rvList.smoothScrollToPosition(i)
//                    break
//                }
//            }
//        }, 500)

        binding.compactCalendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
//                val events: List<Event> = binding.compactCalendarView.getEvents(dateClicked)
//                Log.d("TAG", "Day was clicked: $dateClicked with events $events")
//                val bookingsFromMap: List<Event> =
//                    binding.compactCalendarView.getEvents(dateClicked)
//                Log.d("TAG", "inside onclick " + dateFormatForDisplaying.format(dateClicked))
//                if (bookingsFromMap != null) {
//                    Log.d("TAG", bookingsFromMap.toString())
//                    mutableBookings.clear()
//                    for (booking in bookingsFromMap) {
//                        mutableBookings.add((booking.data as String?)!!)
//                    }
////                    adapter.notifyDataSetChanged()
//                }

                val inputPattern = "yyyy-MM-dd"
                val inputFormat = SimpleDateFormat(inputPattern)
                var mDate = inputFormat.format(dateClicked)
                for (i in mEventDetailList.indices) {
                    var mDate = inputFormat.parse(mEventDetailList[i].eventDate!!)
                    if (dateClicked.time == mDate!!.time) {
                        binding.rvList.scrollToPosition(i)
                    }
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
            }
        })

    }

    private fun addEventDetail(): List<Event> {
        val events: ArrayList<Event> = java.util.ArrayList()
        for (i in mEventDetailList.indices) {
            val bean: EventListResponse.EventDetail = mEventDetailList.get(i)
            val givenDateString = bean.eventDate
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            try {
                val mDate = sdf.parse(givenDateString!!)
                val timeInMilliseconds = mDate!!.time
                events.add(
                    i,
                    Event(
                        Color.parseColor(bean.color),
                        timeInMilliseconds,
                        bean
                    )
                )
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return events
    }

    private fun addEvents(month: Int, year: Int) {
        currentCalender.time = Date()
        currentCalender[Calendar.DAY_OF_MONTH] = 1
        val firstDayOfMonth = currentCalender.time
        for (i in 0..5) {
            currentCalender.time = firstDayOfMonth
            if (month > -1) {
                currentCalender[Calendar.MONTH] = month
            }
            if (year > -1) {
                currentCalender[Calendar.ERA] = GregorianCalendar.AD
                currentCalender[Calendar.YEAR] = year
            }
            currentCalender.add(Calendar.DATE, i)
            setToMidnight(currentCalender)
            val timeInMillis = currentCalender.timeInMillis
            val events: List<Event> = getEvents(timeInMillis, i)!!
            binding.compactCalendarView.addEvents(events)
        }
    }

    private fun getEvents(timeInMillis: Long, day: Int): List<Event>? {
        return if (day < 2) {
            Arrays.asList(
                Event(
                    Color.argb(255, 169, 68, 65),
                    timeInMillis,
                    "Event at " + Date(timeInMillis)
                )
            )
        } else if (day > 2 && day <= 4) {
            Arrays.asList(
                Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + Date(timeInMillis)),
                Event(
                    Color.argb(255, 100, 68, 65),
                    timeInMillis,
                    "Event 2 at " + Date(timeInMillis)
                )
            )
        } else {
            Arrays.asList(
                Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + Date(timeInMillis)),
                Event(
                    Color.argb(255, 100, 68, 65),
                    timeInMillis,
                    "Event 2 at " + Date(timeInMillis)
                ),
                Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + Date(timeInMillis))
            )
        }
    }

    private fun setToMidnight(calendar: Calendar) {
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
    }

    private fun loadEvents() {
        addEvents(-1, -1)
        addEvents(Calendar.DECEMBER, -1)
        addEvents(Calendar.AUGUST, -1)
    }

    private fun loadEventsForYear(year: Int) {
        addEvents(Calendar.DECEMBER, year)
        addEvents(Calendar.AUGUST, year)
    }

    private fun logEventsByMonth(compactCalendarView: CompactCalendarView) {
        currentCalender.time = Date()
        currentCalender[Calendar.DAY_OF_MONTH] = 1
        currentCalender[Calendar.MONTH] = Calendar.AUGUST
        val dates: MutableList<String> = ArrayList()
        for (e in compactCalendarView.getEventsForMonth(Date())) {
            dates.add(dateFormatForDisplaying.format(e.timeInMillis))
        }
        Log.d("TAG", "Events for Aug with simple date formatter: $dates")
        Log.d(
            "TAG",
            "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(
                currentCalender.time
            )
        )
    }

    private fun mApiCalling() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()
            ?.GET_CalenderEventList(
                PreferenceManager.getPref(
                    Constants.Preference.PREF_CLIENT_USERID,
                    ""
                )!!
            )
            ?.enqueue(object :
                Callback<ResponseBase<EventListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<EventListResponse>?>?,
                    response: Response<ResponseBase<EventListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            mEventDetailList =
                                response.body()!!.mData!!.getEventDetails() as ArrayList<EventListResponse.EventDetail>

                            val mCalenderEventAdapter = CalenderEventAdapter(
                                requireActivity(),
                                response.body()!!.mData!!.getEventDetails() as ArrayList<EventListResponse.EventDetail>
                            )

                            val linearLayoutManager = LinearLayoutManager(
                                requireActivity(),
                                RecyclerView.VERTICAL,
                                false
                            )

                            binding.rvList.layoutManager = linearLayoutManager
                            binding.rvList.adapter = mCalenderEventAdapter
                            binding.compactCalendarView.addEvents(addEventDetail())

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<EventListResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

}