package com.conferences.factory.abstraction;

import com.conferences.model.DateFilter;

public interface IDateFilterFactory {

    DateFilter getDateFilter(String select);
}
