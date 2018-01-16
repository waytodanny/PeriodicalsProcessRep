package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.dto.PaymentDto;
import com.periodicals.dto.PeriodicalDto;
import com.periodicals.dto.RoleDto;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.ArrayList;
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

    public List<PaymentDto> getUserPaymentsDtoSublist(User user, int skip, int take) throws ServiceException {
        List<PaymentDto> dtoList = new ArrayList<>();
        try {
            List<Payment> entityList = payDao.getUserPaymentsSublist(user, skip, take);
            fillPaymentsDtoList(entityList, dtoList);
        } catch (DaoException | ServiceException e) {
            /*TODO log*/
            throw new ServiceException(e.getMessage());
        }
        return dtoList;
    }


    public int getUserPaymentsCount(User user) {
        int result = 0;
        try {
            result = payDao.getUserPaymentsCount(user);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return result;
    }

    private static void fillPaymentsDtoList(List<Payment> entityList, List<PaymentDto> dtoList) throws ServiceException {
        for (Payment entity : entityList) {
            PaymentDto dto = getDtoByEntity(entity);
            dtoList.add(dto);
        }
    }

    /*TODO checking for null*/
    private static PaymentDto getDtoByEntity(Payment entity) throws ServiceException {
        PaymentDto dto = new PaymentDto();

        dto.setId(entity.getId());
        dto.setPaymentSum(entity.getPaymentSum());
        dto.setPaymentTime(entity.getPaymentTime());
        dto.setUserId(entity.getUserId());

        List<PeriodicalDto> periodicalDtos = new ArrayList<>();
        try {
            List<Periodical> perEntities = perService.getPaymentPeriodicals(entity);
            PeriodicalService.fillPeriodicalsDto(perEntities, periodicalDtos);

            dto.setPeriodicals(periodicalDtos);
            return dto;
        } catch (ServiceException e) {
            throw new ServiceException("failed to obtain user payment: " + e.getMessage());
        }
    }
}
