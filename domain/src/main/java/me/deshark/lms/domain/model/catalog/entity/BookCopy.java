package me.deshark.lms.domain.model.catalog.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.catalog.vo.BookCopyStatus;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.Date;
import java.util.UUID;

/**
 * 领域实体：图书副本
 * @author DE_SHARK
 * @date 2025/2/16 14:44
 */
@Data
@Builder
public class BookCopy {
    private final UUID copyId;
    private final Isbn isbn;
    private BookCopyStatus status;
    private Date acquisitionDate;

    public static BookCopy copyOf(Isbn isbn) {
        return BookCopy.builder()
                .copyId(GUID.v7())
                .isbn(isbn)
                .status(BookCopyStatus.AVAILABLE)
                .acquisitionDate(new Date())
                .build();
    }

    public boolean isAvailable() {
        return status == BookCopyStatus.AVAILABLE;
    }
}
