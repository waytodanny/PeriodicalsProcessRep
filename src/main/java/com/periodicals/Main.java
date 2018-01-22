package com.periodicals;

import com.periodicals.dao.factories.AbstractDaoFactory;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.utils.uuid.UuidGenerator;


public class Main {

    public static void main(String[] args) throws DaoException, ServiceException {

        String requestURI = "admin.jsp";
        requestURI = requestURI.substring(0, requestURI.lastIndexOf(".jsp"));

        System.out.println(requestURI);
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
//        SubscriptionsService service = SubscriptionsService.getInstance();
//        String uuid = "1f940bd3-f7a5-11e7-93e6-a30f6152aa28";
//        Genre periodical = JdbcDaoFactory.getInstance().getGenresDao().getGenreByName("comics");
//        List<Periodical> subs = JdbcDaoFactory.getInstance().getPeriodicalsDao().getGenrePeriodicalsLimited(periodical, 0, 5);
//        try {
//            Payment payment1 = new Payment(uuid, new BigDecimal("22.2"));
////            JdbcDaoFactory.getInstance().getPaymentsDao().addNewIssue(payment);
//            Payment payment = JdbcDaoFactory.getInstance().getPaymentsDao().getById(1L);
//            JdbcDaoFactory.getInstance().getPaymentsDao().addPaymentPeriodicals(payment, subs);
////            service.processSubscriptions(uuid, subs, new BigDecimal("22.2"));
//        } catch (Exception e) {
//            System.out.println("кук");
//        }

//



//
//        List<User> users = usersJdbcDAO.getAllGenres();
//        System.out.println(users.size());
//
//        User user = new User();
//        user.setId(UuidGenerator.generateUuid());
//        user.setLogin("batman");
//        user.setEmail("batman@gmail.com");
//        user.setRole(new Role((byte)1, ""));
//        user.setPassword(new MD5Cryptographer().encrypt("batman"));
//
//        usersJdbcDAO.addNewIssue(user);
//
//        users = usersJdbcDAO.getAllGenres();
//        System.out.println(users.size());
//
//        User same = usersJdbcDAO.getByLogin("batman");
//        usersJdbcDAO.deletePeriodicalById(same.getId());
//
//        users = usersJdbcDAO.getAllGenres();
//        System.out.println(users.size());

//        PublishersJdbcDao publsDao = (PublishersJdbcDao) factory.getPublishersDao();
//
//        List<Publisher> publs = publsDao.getAllGenres();
//        System.out.println(publs.size());
//
//        Publisher periodical = new Publisher();
//        periodical.setName("Dark Horse");
//
//        publsDao.addNewIssue(periodical);
//
//        publs = publsDao.getAllGenres();
//        System.out.println(publs.size());


//        GenresJdbcDao perGenres =
//                (GenresJdbcDao) factory.getGenresDao();
//
//        List<Genre> periodical_issues = perGenres.getAllGenres();
//        System.out.println(periodical_issues.size());
//
//        Genre periodical = new Genre();
//        periodical.setName("fantastic1");
//        short id2 = perGenres.addNewIssue(periodical);
//
//        Genre periodical_issue2 = perGenres.getById(id2);
//
//        List<Genre> periodical_issues2 = perGenres.getAllGenres();
//        System.out.println(periodical_issues2.size());
//
//        perGenres.deletePeriodicalById(periodical_issue2.getId());
//
//        List<Genre> periodical_issues3 = perGenres.getAllGenres();
//        System.out.println(periodical_issues3.size());
////
//        PeriodicalsJdbcDao persDao =
//               (PeriodicalsJdbcDao) factory.getPeriodicalsDao();
//
//        System.out.println(persDao.getUserSubscriptionsCount(user));
//        List<Periodical> pers = persDao.getAllGenres();
//        System.out.println(pers.size());
//
//        Periodical per2 = persDao.getById(1);
//
//        System.out.println(per2);
//
//        List<Periodical> pers3 = persDao.getAllGenres();
//        System.out.println(pers3.size());

//        PeriodicalIssuesJdbcDao persIssDao =
//              (PeriodicalIssuesJdbcDao) factory.getPeriodicalIssuesDao();

//        List<PeriodicalIssue> issues = persIssDao.getAllGenres();
//        System.out.println(issues.size());
//
//        PeriodicalIssue grp = new PeriodicalIssue("rhino in town", 1);
//        grp.setIssueNo(2);
//        long id4 = persIssDao.addNewIssue(grp);
//
//        List<PeriodicalIssue> issues2 = persIssDao.getAllGenres();
//        System.out.println(issues2.size());
//
//        PeriodicalIssue grp2 = persIssDao.getById(id4);
//
//        persIssDao.deletePeriodicalById(grp2.getId());
//
//        List<PeriodicalIssue> issues3 = persIssDao.getAllGenres();
//        System.out.println(issues3.size());
////
//        PaymentsJdbcDao paysDao =
//                (PaymentsJdbcDao) factory.getPaymentsDao();
//
//        List<Payment> pays2 = paysDao.getAllGenres();
//        System.out.println(pays2.size());
//


////
////        Transaction.doTransaction(new Transaction() {
////            @Override
////            public void pass() throws DaoException {
////                paysDao.deletePaymentPeriodicals(payment.getId());
////                paysDao.deletePeriodicalById(payment);
////            }
////        }, conn);
////        pays2 = paysDao.getAllGenres();
////        System.out.println(pays2.size());
    }
}
