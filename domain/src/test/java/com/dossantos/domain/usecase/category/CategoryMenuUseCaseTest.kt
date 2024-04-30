package com.dossantos.domain.usecase.category

import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.repository.category.CategoryRepository
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CategoryMenuUseCaseTest {
    private val categoryRepository: CategoryRepository = mockk()
    private lateinit var useCase: CategoryMenuUseCase

    @Before
    fun setup() {
        useCase = CategoryMenuUseCase(categoryRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get menu category with success`() = runBlocking {
        // Given
        coEvery { categoryRepository.getMenuCategory() } returns getCategoryMenu()

        // When
        useCase.getMenuCategory().single().let { category ->

            // Then
            coVerifyAll { categoryRepository.getMenuCategory() }
            assert(category.first().categoryId == "categoryId")
        }
    }

    @Test
    fun `get offers with error`(): Unit = runBlocking {
        // Given
        coEvery { categoryRepository.getMenuCategory() } throws RuntimeException("crash")

        // When
        try {
            useCase.getMenuCategory()
        } catch (exception: Exception) {

            // Then
            coVerifyAll { categoryRepository.getMenuCategory() }
            assert(exception.message == "crash")
        }
    }

    private fun getCategoryMenu() = flow {
        emit(listOf(MenuCategoryModel(categoryId = "categoryId")))
    }
}
