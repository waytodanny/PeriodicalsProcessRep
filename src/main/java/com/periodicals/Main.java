package com.periodicals;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionPool;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.factories.AbstractDaoFactory;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.dao.transactions.Transaction;
import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.exceptions.TransactionException;
import com.periodicals.services.RoleService;

import java.util.List;


public class Main {

    public static void main(String[] args) throws DaoException, ServiceException {

        AbstractDaoFactory factory = JdbcDaoFactory.getInstance();

//        UsersJdbcDao usersJdbcDAO = (UsersJdbcDao) factory.getUsersDao();
//        for (int i = 0; i < 10; i++) {
//            new Thread( ()->{
//                try {
//                    Transaction.doTransaction( ()->{
//                        System.out.println(ConnectionManager.getConnectionWrapper().getConnection().hashCode());
//                        System.out.println(ConnectionManager.getConnectionWrapper().getConnection().hashCode());
//                    });
//                } catch (TransactionException e) {
//                    e.printStackTrace();
//                }
//            }).run();
//        }
//        UserSubscriptionsService service = UserSubscriptionsService.getInstance();
//        String uuid = "1f940bd3-f7a5-11e7-93e6-a30f6152aa28";
//        Genre periodical = JdbcDaoFactory.getInstance().getGenresDao().getGenreByName("comics");
//        List<Periodical> subs = JdbcDaoFactory.getInstance().getPeriodicalsDao().getGenrePeriodicalsSublist(periodical, 0, 5);
//        try {
//            Payment payment1 = new Payment(uuid, new BigDecimal("22.2"));
////            JdbcDaoFactory.getInstance().getPaymentsDao().add(payment);
//            Payment payment = JdbcDaoFactory.getInstance().getPaymentsDao().getById(1L);
//            JdbcDaoFactory.getInstance().getPaymentsDao().addPaymentPeriodicals(payment, subs);
////            service.processSubscriptions(uuid, subs, new BigDecimal("22.2"));
//        } catch (Exception e) {
//            System.out.println("кук");
//        }

//



//
//        List<User> users = usersJdbcDAO.getAll();
//        System.out.println(users.size());
//
//        User user = new User();
//        user.setId(UuidGenerator.generateUuid());
//        user.setLogin("batman");
//        user.setEmail("batman@gmail.com");
//        user.setRole(new Role((byte)1, ""));
//        user.setPassword(new MD5_Cryptographer().encrypt("batman"));
//
//        usersJdbcDAO.add(user);
//
//        users = usersJdbcDAO.getAll();
//        System.out.println(users.size());
//
//        User same = usersJdbcDAO.getByLogin("batman");
//        usersJdbcDAO.delete(same.getId());
//
//        users = usersJdbcDAO.getAll();
//        System.out.println(users.size());

//        PublishersJdbcDao publsDao = (PublishersJdbcDao) factory.getPublishersDao();
//
//        List<Publisher> publs = publsDao.getAll();
//        System.out.println(publs.size());
//
//        Publisher periodical = new Publisher();
//        periodical.setName("Dark Horse");
//
//        publsDao.add(periodical);
//
//        publs = publsDao.getAll();
//        System.out.println(publs.size());


//        GenresJdbcDao perGenres =
//                (GenresJdbcDao) factory.getGenresDao();
//
//        List<Genre> periodical_issues = perGenres.getAll();
//        System.out.println(periodical_issues.size());
//
//        Genre periodical = new Genre();
//        periodical.setName("fantastic1");
//        short id2 = perGenres.add(periodical);
//
//        Genre periodical_issue2 = perGenres.getById(id2);
//
//        List<Genre> periodical_issues2 = perGenres.getAll();
//        System.out.println(periodical_issues2.size());
//
//        perGenres.delete(periodical_issue2.getId());
//
//        List<Genre> periodical_issues3 = perGenres.getAll();
//        System.out.println(periodical_issues3.size());
////
//        PeriodicalsJdbcDao persDao =
//               (PeriodicalsJdbcDao) factory.getPeriodicalsDao();
//
//        System.out.println(persDao.getUserSubscriptionsCount(user));
//        List<Periodical> pers = persDao.getAll();
//        System.out.println(pers.size());
//
//        Periodical per2 = persDao.getById(1);
//
//        System.out.println(per2);
//
//        List<Periodical> pers3 = persDao.getAll();
//        System.out.println(pers3.size());

//        PeriodicalIssuesJdbcDao persIssDao =
//              (PeriodicalIssuesJdbcDao) factory.getPeriodicalIssuesDao();

//        List<PeriodicalIssue> issues = persIssDao.getAll();
//        System.out.println(issues.size());
//
//        PeriodicalIssue grp = new PeriodicalIssue("rhino in town", 1);
//        grp.setIssueNo(2);
//        long id4 = persIssDao.add(grp);
//
//        List<PeriodicalIssue> issues2 = persIssDao.getAll();
//        System.out.println(issues2.size());
//
//        PeriodicalIssue grp2 = persIssDao.getById(id4);
//
//        persIssDao.delete(grp2.getId());
//
//        List<PeriodicalIssue> issues3 = persIssDao.getAll();
//        System.out.println(issues3.size());
////
//        PaymentsJdbcDao paysDao =
//                (PaymentsJdbcDao) factory.getPaymentsDao();
//
//        List<Payment> pays2 = paysDao.getAll();
//        System.out.println(pays2.size());
//


////
////        Transaction.doTransaction(new Transaction() {
////            @Override
////            public void pass() throws DaoException {
////                paysDao.deletePaymentPeriodicals(payment.getId());
////                paysDao.delete(payment);
////            }
////        }, conn);
////        pays2 = paysDao.getAll();
////        System.out.println(pays2.size());
    }
}
