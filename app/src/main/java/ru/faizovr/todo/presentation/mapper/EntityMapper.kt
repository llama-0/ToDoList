package ru.faizovr.todo.presentation.mapper

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

//    fun mapToEntity(domainModel: DomainModel): Entity
}