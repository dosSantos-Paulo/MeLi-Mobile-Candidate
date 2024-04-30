package com.dossantos.data.repository.category

import com.dossantos.data.model.category.MeLiCategoryDto
import com.dossantos.data.model.info.MeLiInfoCategoryDto
import com.dossantos.data.model.info.MeLiInfoDto
import com.dossantos.data.network.category.MeLiCategoryEndpoint
import com.dossantos.data.network.info.MeLiBrInfoEndpoint
import com.dossantos.domain.repository.category.CategoryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {

    private val categoryEndpoint: MeLiCategoryEndpoint = mockk()
    private val infoEndpoint: MeLiBrInfoEndpoint = mockk()
    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun setup() {
        categoryRepository = CategoryRepositoryImpl(categoryEndpoint, infoEndpoint)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get menuCategory with success`() = runBlocking {
        // Given
        coEvery { infoEndpoint.getMlbInfo() } returns meLiInfoDto()
        coEvery { categoryEndpoint.getCategory("categoryId") } returns meLiCategoryDto()

        // When
        categoryRepository.getMenuCategory().single().let { category ->

            // Then
            coVerify { infoEndpoint.getMlbInfo() }
            assert(category.first().categoryId == meLiInfoDto().categories.first().id)
        }
    }

    @Test
    fun `get menuCategory with category error`(): Unit = runBlocking {
        // Given
        coEvery { infoEndpoint.getMlbInfo() } returns meLiInfoDto()
        coEvery { categoryEndpoint.getCategory(any<String>()) } throws RuntimeException("crash")

        // When
        categoryRepository.getMenuCategory().catch { exception ->

            // Then
            coVerify { infoEndpoint.getMlbInfo() }
            coVerify { categoryEndpoint.getCategory("categoryId") }
            assert("crash" == exception.message)
        }
    }

    @Test
    fun `get menuCategory with error`(): Unit = runBlocking {
        // Given
        coEvery { infoEndpoint.getMlbInfo() } throws RuntimeException("crash")

        // When
        categoryRepository.getMenuCategory().catch { exception ->

            // Then
            coVerify { infoEndpoint.getMlbInfo() }
            coVerify { categoryEndpoint.getCategory("categoryId") }
            assert("crash" == exception.message)
        }
    }

    @Test
    fun `get category with success`() = runBlocking {
        // Given
        val categoryId = "categoryId"
        coEvery { categoryEndpoint.getCategory(categoryId) } returns meLiCategoryDto()

        // When
        categoryRepository.getCategory(categoryId).single().let { category ->

            // Then
            coVerify { categoryEndpoint.getCategory(categoryId) }
            assert(category.categoryId == categoryId)
        }
    }

    @Test
    fun `get category with error`(): Unit = runBlocking {
        // Given
        val categoryId = "categoryId"
        coEvery { categoryEndpoint.getCategory(categoryId) } throws RuntimeException("crash")

        // When
        categoryRepository.getMenuCategory().catch { exception ->

            // Then
            coVerify { categoryEndpoint.getCategory(categoryId) }
            assert("crash" == exception.message)
        }
    }

    private fun meLiInfoDto() = MeLiInfoDto(
        categories = listOf(
            MeLiInfoCategoryDto(
                "categoryId",
                "categoryName"
            )
        )
    )

    private fun meLiCategoryDto() = MeLiCategoryDto(
        "categoryId", "categoryName", "pic"
    )
}