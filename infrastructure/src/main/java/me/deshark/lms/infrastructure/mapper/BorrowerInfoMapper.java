package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.borrow.entity.Patron;
import me.deshark.lms.infrastructure.entity.BorrowerInfoDO;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Mapper
public interface BorrowerInfoMapper {

    @Insert("INSERT INTO borrower_info (user_id, max_borrow_limit, current_loans) " +
            "VALUES (#{userId}, #{maxBorrowLimit}, #{currentLoans})")
    void insert(BorrowerInfoDO borrowerInfoDO);

    @Update("UPDATE borrower_info SET " +
            "max_borrow_limit = #{maxBorrowLimit}, " +
            "current_loans = #{currentLoans}  " +
            "WHERE user_id = #{userId}")
    void update(BorrowerInfoDO borrowerInfoDO);

    @Delete("DELETE FROM borrower_info WHERE user_id = #{userId}")
    void delete(@Param("userId") UUID userId);

    @Select("SELECT * FROM borrower_info WHERE user_id = #{userId}")
    @Results(id = "borrowerInfoMap", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "maxBorrowLimit", column = "max_borrow_limit"),
            @Result(property = "currentLoans", column = "current_loans")
    })
    Optional<BorrowerInfoDO> findByUserId(@Param("userId") UUID userId);

    @Update("UPDATE borrower_info SET current_loans = current_loans + #{delta} WHERE user_id = #{userId}")
    void updateCurrentLoans(@Param("userId") UUID userId, @Param("delta") int delta);

    @Select("SELECT max_borrow_limit FROM borrower_info WHERE user_id = #{userId}")
    int getBorrowLimit(@Param("userId") UUID userId);

    static Patron convertToPatron(BorrowerInfoDO borrowerInfoDO) {
        return Patron.builder()
                .id(borrowerInfoDO.getUserId())
                .maxBorrowLimit(borrowerInfoDO.getMaxBorrowLimit())
                .currentBorrows(borrowerInfoDO.getCurrentLoans())
                .build();
    }
}
