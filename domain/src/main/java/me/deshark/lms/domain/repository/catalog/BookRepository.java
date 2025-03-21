package me.deshark.lms.domain.repository.catalog;

import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.model.catalog.vo.LowInventoryInfo;
import me.deshark.lms.domain.model.common.Page;

import java.util.List;

/**
 * @author DE_SHARK
 */
public interface BookRepository {

    boolean existsByIsbn(String isbn);

    BookMetadata findByIsbn(Isbn isbn);

    void save(BookMetadata bookMetadata);

    void delete(Isbn isbn);

    Page<BookMetadata> searchBooks(String keyword, int pageNumber, int pageSize);

    /**
     * 查找库存不足的图书ISBN列表
     * @param minCopyCount 最小副本数要求
     * @return 库存不足的图书ISBN列表
     */
    List<LowInventoryInfo> findBooksWithLowInventory(int minCopyCount);
}
