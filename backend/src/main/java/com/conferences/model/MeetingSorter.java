package com.conferences.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingSorter {
    private Sort sort;
    private DateFilter dateFilter;
}
