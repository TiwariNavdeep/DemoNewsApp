package com.example.demosample.data.mapper

import com.example.demosample.data.datasource.local.entity.NewsEntity
import com.example.demosample.data.model.NewsModelDto
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.utils.DateConvertorUtils

object NewsMapper {
    fun dtoToEntity(
        dto: NewsModelDto,
        category: NewsCategory
    ): NewsEntity {
        return NewsEntity(
            id = dto.url,
            title = dto.title,
            description = dto.description,
            imageUrl = dto.imageUrl,
            source = dto.source.name,
            publishedAt = dto.publishedAt,
            url = dto.url,
            category = category.value
        )
    }

    fun dtoToDomain(
        dto: NewsModelDto,
        category: NewsCategory
    ): NewsModel {
        return NewsModel(
            id = dto.url,
            title = dto.title,
            description = dto.description,
            imgUrl = dto.imageUrl,
            sourceName = dto.source.name,
            publishedAt = DateConvertorUtils.formatNewsDate(dto.publishedAt),
            url = dto.url,
            category = category.value
        )
    }

    fun entityToDomain(entity: NewsEntity): NewsModel {
        return NewsModel(
            id = entity.id,
            title = entity.title,
            description = entity.description?:"",
            imgUrl = entity.imageUrl?:"",
            sourceName = entity.source,
            publishedAt = DateConvertorUtils.formatNewsDate(entity.publishedAt),
            url = entity.url,
            category = entity.category
        )
    }
}