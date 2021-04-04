package ru.otus.otuskotlin.remindercalendar.business.logic.pipelines

import ru.otus.otuskotlin.remindercalendar.business.logic.helpers.validation
import ru.otus.otuskotlin.remindercalendar.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.remindercalendar.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.remindercalendar.business.logic.operations.stubs.EventCreateStub
import ru.otus.otuskotlin.remindercalendar.common.backend.context.Context
import ru.otus.otuskotlin.remindercalendar.common.backend.model.FrequencyModel
import ru.otus.otuskotlin.remindercalendar.common.mp.ValidatorStringFieldNotEmpty
import ru.otus.otuskotlin.remindercalendar.common.mp.test.ru.otus.otuskotlin.remindercalendar.common.mp.ValidatorFrequency
import ru.otus.otuskotlin.remindercalendar.common.mp.test.ru.otus.otuskotlin.remindercalendar.common.mp.ValidatorSchedule
import ru.otus.otuskotlin.remindercalendar.pipelines.Operation
import ru.otus.otuskotlin.remindercalendar.pipelines.pipeline
import java.time.LocalDateTime

object EventCreate : Operation<Context> by pipeline({
    execute(InitializePipeline)

    execute(EventCreateStub)

    validation {
        validate<String?> {
            validator(ValidatorStringFieldNotEmpty("You must provide non-empty name for schedule event"))
            on { requestEvent.name }
        }
        validate<String?> {
            validator(ValidatorStringFieldNotEmpty("You must provide non-empty mobile number for schedule event"))
            on { requestEvent.mobile }
        }
        validate<String?> {
            validator(ValidatorStringFieldNotEmpty("You must provide user for schedule event"))
            on { requestEvent.userId.id }
        }
        validate<FrequencyModel> {
            validator(ValidatorFrequency("You must provide frequency for schedule event"))
            on { requestEvent.frequency }
        }
        validate<LocalDateTime> {
            validator(ValidatorSchedule("You must provide schedule time for schedule event"))
            on { requestEvent.startSchedule }
        }
    }

    execute(CompletePipeline)
})