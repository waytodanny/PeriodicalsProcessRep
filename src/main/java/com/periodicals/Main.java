package com.periodicals;

import com.periodicals.dao.entities.Publisher;
import com.periodicals.dao.entities.Role;
import com.periodicals.dao.entities.User;
import com.periodicals.dao.factories.AbstractDaoFactory;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PublishersJdbcDao;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.utils.encryption.MD5_Cryptographer;
import com.periodicals.utils.uuid.UuidGenerator;

import java.util.List;


public class Main {

    public static void main(String[] args) throws DaoException, ServiceException {

//        UserSubscriptionsService service = UserSubscriptionsService.getInstance();
//        String uuid = "1f940bd3-f7a5-11e7-93e6-a30f6152aa28";
//        Genre genre = JdbcDaoFactory.getInstance().getGenresDao().getGenreByName("comics");
//        List<Periodical> subs = JdbcDaoFactory.getInstance().getPeriodicalsDao().getGenrePeriodicalsSublist(genre, 0, 5);
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

        AbstractDaoFactory factory = JdbcDaoFactory.getInstance();

//        UsersJdbcDao usersJdbcDAO = (UsersJdbcDao) factory.getUsersDao();
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
//        Publisher publisher = new Publisher();
//        publisher.setName("Dark Horse");
//
//        publsDao.add(publisher);
//
//        publs = publsDao.getAll();
//        System.out.println(publs.size());

//
////        GenresJdbcDao perGenres =
////                (GenresJdbcDao) factory.getDaoByClass(Genre.class, conn);
////
////        List<Genre> genres = perGenres.getAll();
////        System.out.println(genres.size());
////
////        Genre genre = new Genre();
////        genre.setName("fantastic");
////        short id2 = perGenres.add(genre);
////
////        Genre genre2 = perGenres.getById(id2);
////
////        List<Genre> genres2 = perGenres.getAll();
////        System.out.println(genres2.size());
////
////        perGenres.delete(genre2);
////
////        List<Genre> genres3 = perGenres.getAll();
////        System.out.println(genres3.size());
////
////        PeriodicalsJdbcDao persDao =
////               (PeriodicalsJdbcDao) factory.getDaoByClass(PeriodicalDto.class);
////
////        List<PeriodicalDto> pers = persDao.getAll();
////        System.out.println(pers.size());
////
////        PeriodicalDto per = new PeriodicalDto("TMNT", "story of ninja turtles", 5.60, (short) 1, 2);
////        int id3 = persDao.add(per);
////
////        List<PeriodicalDto> pers2 = persDao.getAll();
////        System.out.println(pers2.size());
////
////        PeriodicalDto per2 = persDao.getById(id3);
////
////        persDao.delete(per2);
////
////        List<PeriodicalDto> pers3 = persDao.getAll();
////        System.out.println(pers3.size());
////
////        PeriodicalIssuesJdbcDao persIssDao =
////              (PeriodicalIssuesJdbcDao) factory.getDaoByClass(PeriodicalIssue.class);
////
////        List<PeriodicalIssue> issues = persIssDao.getAll();
////        System.out.println(issues.size());
////
////        PeriodicalIssue grp = new PeriodicalIssue("rhino in town", 1);
////        int id4 = persIssDao.add(grp);
////
////        List<PeriodicalIssue> issues2 = persIssDao.getAll();
////        System.out.println(issues2.size());
////
////        PeriodicalIssue grp2 = persIssDao.getById(id4);
////
////        persIssDao.delete(grp2);
////
////        List<PeriodicalIssue> issues3 = persIssDao.getAll();
////        System.out.println(issues3.size());
////
////        PaymentsJdbcDao paysDao =
////                (PaymentsJdbcDao) factory.getDaoByClass(Payment.class, conn);
////
////        List<Payment> pays2 = paysDao.getAll();
////        System.out.println(pays2.size());
////        System.out.println(paysDao.getPaymentPeriodicals(2).size());
////
////        Payment payment = new Payment(1, 25.50);
////        payment.getPeriodicalIdList().add(1);
////        payment.getPeriodicalIdList().add(2);
////        payment.getPeriodicalIdList().add(3);
////
////        Transaction.doTransaction(new Transaction() {
////            @Override
////            public void pass() throws DaoException {
////                long id5 = paysDao.add(payment);
////                payment.setId(id5);
////                paysDao.addPaymentPeriodicals(payment);
////            }
////        }, conn);
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
