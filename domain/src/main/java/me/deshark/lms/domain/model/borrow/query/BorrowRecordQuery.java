package me.deshark.lms.domain.model.borrow.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Getter
@RequiredArgsConstructor
public class BorrowRecordQuery {
    private final UUID recordId;
    private final String isbn;
    private final String title;
    private final String author;
    private final OffsetDateTime borrowDate;
    private final OffsetDateTime dueDate;
    private final OffsetDateTime returnDate;
    private final String status;
}
