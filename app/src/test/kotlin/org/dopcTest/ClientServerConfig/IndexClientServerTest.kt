// package org.dopc.clientserverconfig.indexclientserver


// import io.ktor.client.request.*
// import io.ktor.client.statement.*
// import io.ktor.http.*
// import io.ktor.server.application.*
// import io.ktor.server.testing.*
// import kotlin.test.*

// class DopcProcessIndexTest {
//     @Test
//     fun testRoot() = testApplication {
//         application {
//             module()
//         }
//         val response = client.get("/")
//         assertEquals(HttpStatusCode.OK, response.status)
//         assertEquals("Hello, world!", response.bodyAsText())
//     }
// }