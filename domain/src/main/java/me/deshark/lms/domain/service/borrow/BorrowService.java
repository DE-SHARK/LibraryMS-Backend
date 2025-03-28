package me.deshark.lms.domain.service.borrow;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.exception.domain.DomainServiceException;
import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;
import me.deshark.lms.domain.model.borrow.entity.Patron;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.BookCopyStatus;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.borrow.BorrowRepository;
import me.deshark.lms.domain.repository.borrow.PatronRepository;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:16
 */
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookCopyRepository bookCopyRepository;
    private final PatronRepository patronRepository;

    /**
     * 借阅图书
     * @param patronName 借阅者
     * @param isbn 图书ISBN
     * @return 借阅记录
     */
    public LoanRecord borrow(String patronName, String isbn) {

        // 1. 检查用户是否可以借阅
        Patron patron = patronRepository.findByUsername(patronName);
        patron.canBorrow();

        // 2. 检查图书是否可借
        Isbn vaildIsbn = new Isbn(isbn);
        if (bookCopyRepository.countAvailableCopies(vaildIsbn) < 1) {
            throw new DomainServiceException("该图书可用数量不足");
        }

        // 2.5 检查是否已借阅同一ISBN书籍且未归还
        if (borrowRepository.existsByPatronAndIsbn(patron.getId(), vaildIsbn.toString())) {
            throw new DomainServiceException("您已借阅该ISBN的书籍且尚未归还");
        }

        // 3. 获取可用的图书副本
        BookCopy bookCopy = bookCopyRepository.findAvailableBookCopy(vaildIsbn);

        // 4. 创建借阅记录
        LoanRecord loanRecord = new LoanRecord(bookCopy, patron);

        // 5. 保存借阅记录
        borrowRepository.save(loanRecord);

        // 6. 更新图书副本状态
        bookCopy.setStatus(BookCopyStatus.BORROWED);
        bookCopyRepository.updateBookCopyStatus(bookCopy);

        return loanRecord;
    }

    /**
     * 续借图书
     * @return 更新后的借阅记录
     */
    public LoanRecord renew(LoanRecord loanRecord) {
        loanRecord.renew();
        borrowRepository.update(loanRecord);
        return loanRecord;
    }

    /**
     * 归还图书
     * @return 更新后的借阅记录
     */
    public LoanRecord returnBook(LoanRecord loanRecord) {
        // 1. 更新借阅记录状态
        loanRecord.returnBook();

        bookCopyRepository.updateBookCopyStatus(loanRecord.getBookCopy());

        borrowRepository.update(loanRecord);

        return loanRecord;
    }
}
