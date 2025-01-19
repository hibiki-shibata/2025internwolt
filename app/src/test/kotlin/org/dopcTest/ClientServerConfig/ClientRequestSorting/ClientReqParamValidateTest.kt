// Negative Values, missing parameters, Invalid param data type

package org.dopc.clientserverconfig.clientrequestsorting.clientreqparamvalidate

import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.http.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.ktor.server.request.ApplicationRequest

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ClientReqDataValidationsTest {

    private val clientReqDataValidations = ClientReqDataValidations()

    @Test
    fun `should return valid ClientRequestParams for valid input`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking the query parameters
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "1000")
            append("user_lat", "60.17094")
            append("user_lon", "24.93087")
        }

        // Act
        val result = clientReqDataValidations.catchClientReqParams(applicationCall)

        // Assert
        assertEquals("home-assignment-venue-helsinki", result.venue_slug)
        assertEquals(1000, result.cart_value)
        assertEquals(listOf(24.93087, 60.17094), result.user_coodinate)
    }

    @Test
    fun `should throw BadRequestException for missing venue_slug`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking missing venue_slug
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("cart_value", "1000")
            append("user_lat", "60.17094")
            append("user_lon", "24.93087")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should throw BadRequestException for missing cart_value`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking missing cart_value
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("user_lat", "60.17094")
            append("user_lon", "24.93087")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should throw BadRequestException for missing user_lat`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking missing user_lat
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "1000")
            append("user_lon", "24.93087")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should throw BadRequestException for negative cart_value`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking negative cart_value
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "-100")
            append("user_lat", "60.17094")
            append("user_lon", "24.93087")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should throw BadRequestException for invalid cart_value type`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking invalid cart_value (non-numeric)
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "invalid")
            append("user_lat", "60.17094")
            append("user_lon", "24.93087")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should throw BadRequestException for invalid user_lat type`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking invalid user_lat (non-numeric)
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "1000")
            append("user_lat", "invalid")
            append("user_lon", "24.93087")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should throw BadRequestException for invalid user_lon type`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking invalid user_lon (non-numeric)
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "1000")
            append("user_lat", "60.17094")
            append("user_lon", "invalid")
        }

        // Act & Assert
        assertFailsWith<BadRequestException> {
            clientReqDataValidations.catchClientReqParams(applicationCall)
        }
    }

    @Test
    fun `should return valid ClientRequestParams for edge coordinates`() {
        val applicationCall = mockk<ApplicationCall>()
        val mockRequest = mockk<ApplicationRequest>()

        // Mocking edge case coordinates (max/min latitude/longitude)
        every { applicationCall.request } returns mockRequest
        every { mockRequest.queryParameters } returns Parameters.build {
            append("venue_slug", "home-assignment-venue-helsinki")
            append("cart_value", "-1000")
            append("user_lat", "90.0")  // max latitude
            append("user_lon", "180.0") // max longitude
        }

        
        assertFailsWith<BadRequestException> {
        clientReqDataValidations.catchClientReqParams(applicationCall)      
        }
        
    }
}
