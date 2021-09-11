package com.conferences.repository;

import com.conferences.entity.Meeting;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IMeetingRepository extends PagingAndSortingRepository<Meeting, Integer> {
}
