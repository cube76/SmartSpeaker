package com.mqa.smartspeaker.core.domain.model

import java.time.LocalDate


data class Event(val id: String, val text: String, val date: LocalDate,
//                 val timeStart: String, val timeFinish: String
                 )