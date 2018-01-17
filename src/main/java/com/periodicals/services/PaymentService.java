package com.periodicals.services;

import com.periodicals.entities.Payment;
import com.periodicals.entities.User;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.List;

public class PaymentService {
    private static PaymentService paymentService = new PaymentService();
    private static PaymentsJdbcDao payDao =
            (PaymentsJdbcDao) JdbcDaoFactory.getInstance().getPaymentsDao();

    private static PeriodicalService perService = PeriodicalService.getInstance();

    private PaymentService() {

    }

    public static PaymentService getInstance() {
        return paymentService;
    }

    public List<Payment> getUserPaymentsSublist(User user, int skip, int take) throws ServiceException {
        try {
            List<Payment> entityList = payDao.getUserPaymentsSublist(user, skip, take);
            return entityList;
        } catch (Exception e) {
            /*TODO log*/
            throw new ServiceException(e.getMessage());
        }
    }

    public long getUserPaymentsCount(User user) {
        long result = 0;
        try {
            result = payDao.getUserPaymentsCount(user);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }
}
