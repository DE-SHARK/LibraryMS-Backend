package me.deshark.lms.domain.service;

import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.model.borrowing.entity.Patron;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import me.deshark.lms.domain.repository.BorrowRepository;

import java.util.Date;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:16
 */
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * 借阅图书
     * @param patron 借阅者
     * @param isbn 图书ISBN
     * @return 借阅记录
     */
    public BorrowTransaction borrow(Patron patron, String isbn) {

        // 1. 检查用户是否可以借阅
        if (!patron.canBorrow()) {
            throw new IllegalArgumentException("Patron is not can borrow");
        }

        // 2. 检查图书是否可借
        Isbn vaildIsbn = new Isbn(isbn);
        if (bookRepository.countAvailableCopies(vaildIsbn) < 1) {
            throw new IllegalArgumentException("Book is not available");
        }

        // 3. 获取可用的图书副本
        BookCopy bookCopy = bookRepository.findAvailableBookCopy(vaildIsbn);

        // 4. 创建借阅记录
        Date now = new Date();
        BorrowTransaction borrowTransaction = new BorrowTransaction(bookCopy.getBookCopyId(), patron, now);
        borrowTransaction.setStatus("BORROWED");
        borrowTransaction.setDueDate(calculateDueDate(now));

        // 5. 保存借阅记录
        borrowRepository.save(borrowTransaction);

        // 6. 更新图书副本状态
        bookCopy.setStatus("BORROWED");
        borrowRepository.updateBookCopyStatus(bookCopy);

        return borrowTransaction;
    }

    /**
     * 续借图书
     * @return 更新后的借阅记录
     */
    public BorrowTransaction renew() {
        // 1. 获取借阅记录
        // 2. 检查是否可以续借
        // 3. 更新到期时间
        // 4. 保存更新后的借阅记录
        return null;
    }

    /**
     * 归还图书
     * @return 更新后的借阅记录
     */
    public BorrowTransaction returnBook() {
        // 1. 获取借阅记录
        // 2. 更新借阅记录状态
        // 3. 更新图书副本状态
        // 4. 更新借阅者状态
        return null;
    }

    /**
     * 计算到期时间（默认借阅期限为14天）
     */
    private Date calculateDueDate(Date startDate) {
        return new Date(startDate.getTime() + 14L * 24 * 60 * 60 * 1000);
    }
}
