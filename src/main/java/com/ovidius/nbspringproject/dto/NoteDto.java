package com.ovidius.nbspringproject.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NoteDto {
    Long id;
    String title;
    String content;
    String authorUsername;
}
