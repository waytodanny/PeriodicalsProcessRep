package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.dto.UserDto;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.services.UserSubscriptionsService;
import com.periodicals.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.PagesHolder.*;

public class ProcessSubscriptionCommand implements Command {
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if(!isUserLoggedIn(session)){
           return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if(Objects.nonNull(cart)){
            List<Periodical> subs = cart.getPeriodicals();
            BigDecimal paySum = cart.getQuantity();
            if(Objects.nonNull(subs) && Objects.nonNull(paySum)){
                UserDto user = (UserDto) session.getAttribute("user");
                try {
                    subsService.processSubscriptions(user.getUuid(), subs, paySum);
                    return new CommandResult(req, resp, REDIRECT, USER_SUBSCRIPTIONS_PAGE);
                } catch (Exception e) {
                    /*TODO log*/
                    return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
                }
            } else{
                /*TODO log*/
                return new CommandResult(req, resp, REDIRECT, ERROR_PAGE);
            }
        } else{
            /*TODO log*/
            return new CommandResult(req, resp, REDIRECT, ERROR_PAGE);
        }
    }
}
