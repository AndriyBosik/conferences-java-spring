package com.conferences.service.abstraction;

import java.util.List;

public interface ITopicService {

    List<Integer> getSpeakerProposedTopicIds(int speakerId, int meetingId);
}
