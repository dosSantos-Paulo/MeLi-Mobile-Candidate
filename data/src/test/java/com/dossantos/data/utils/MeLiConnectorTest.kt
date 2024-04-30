package com.dossantos.data.utils

import org.junit.Test

class MeLiConnectorTest {

    @Test
    fun `test MeLiConnector getInstance() on want to get same instance`() {
        // Given
        val connectorInstanceA = MeLiConnector.getInstance()
        val connectorInstanceB = MeLiConnector.getInstance()

        // Then
        assert(connectorInstanceA === connectorInstanceB)
    }
}
