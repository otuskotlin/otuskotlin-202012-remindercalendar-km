package ru.otus.otuskotlin.remindercalendar.transport.model.event

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.otuskotlin.remindercalendar.transport.model.common.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ResponseEventDeleteTest {
    @Test
    fun serializationEventDeleteResponse() {
        val jsonRequest = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(Message::class) {
                    subclass(ResponseEventDelete::class, ResponseEventDelete.serializer())
                }

            }
            classDiscriminator = "type"
        }
        val dto: Message = ResponseEventDelete(
            responseId = "response-id",
            onRequestId = "request-id",
            startTime = "2021-02-13T11:59:00",
            endTime = "2021-02-13T12:00:00",
            status = ResponseStatusDto.SUCCESS,
            event = EventDto(
                id = "id",
                name = "День рождения жены",
                description = "Этот день самый главный праздник в году. Про него никак нельзя забыть",
                startSchedule = "2021-02-25T13:40:00",
                userId = "user-id",
                frequency = FrequencyDto.YEARLY,
                mobile = "+7123456789",
                permissions = setOf(ItemPermissionDto.UPDATE)

            ),
            deleted = true
        )
        val serializedString = jsonRequest.encodeToString(dto)
        assertEquals(
            """{
    "type": "ResponseEventDelete",
    "responseId": "response-id",
    "onRequestId": "request-id",
    "startTime": "2021-02-13T11:59:00",
    "endTime": "2021-02-13T12:00:00",
    "status": "SUCCESS",
    "event": {
        "id": "id",
        "name": "День рождения жены",
        "description": "Этот день самый главный праздник в году. Про него никак нельзя забыть",
        "startSchedule": "2021-02-25T13:40:00",
        "userId": "user-id",
        "frequency": "YEARLY",
        "mobile": "+7123456789",
        "permissions": [
            "UPDATE"
        ]
    },
    "deleted": true
}""",
            serializedString
        )
        val deserializedDto = jsonRequest.decodeFromString(Message.serializer(), serializedString)
        assertEquals(dto, deserializedDto as ResponseEventDelete)
    }
}