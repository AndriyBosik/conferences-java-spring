package com.conferences.mapper;

public interface IMapper<T, U> {

    U map(T model);
}
