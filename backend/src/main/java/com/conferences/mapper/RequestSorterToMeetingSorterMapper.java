package com.conferences.mapper;

import com.conferences.factory.abstraction.IDateFilterFactory;
import com.conferences.factory.abstraction.ISortFactory;
import com.conferences.model.MeetingSorter;
import com.conferences.model.RequestSorter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RequestSorterToMeetingSorterMapper implements IMapper<RequestSorter, MeetingSorter> {

    private final IDateFilterFactory dateFilterFactory;
    private final ISortFactory sortFactory;

    @Autowired
    public RequestSorterToMeetingSorterMapper(IDateFilterFactory dateFilterFactory, ISortFactory sortFactory) {
        this.dateFilterFactory = dateFilterFactory;
        this.sortFactory = sortFactory;
    }

    @Override
    public MeetingSorter map(RequestSorter model) {
        log.info("Mapping {} to {}", RequestSorter.class.getTypeName(), MeetingSorter.class.getTypeName());

        MeetingSorter meetingSorter = new MeetingSorter();

        meetingSorter.setDateFilter(dateFilterFactory.getDateFilter(model.getSelect()));
        meetingSorter.setSort(sortFactory.getSort(model.getSortBy(), model.getSortOrder()));

        return meetingSorter;
    }
}
