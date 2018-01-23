//package com.periodicals.command.admin;
//
//import com.periodicals.command.util.Command;
//import com.periodicals.command.util.CommandResult;
//import com.periodicals.command.util.CommandUtils;
//import com.periodicals.entities.Genre;
//import com.periodicals.entities.Periodical;
//import com.periodicals.entities.Publisher;
//import com.periodicals.services.GenresService;
//import com.periodicals.services.PeriodicalService;
//import com.periodicals.services.PublisherService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.math.BigDecimal;
//import java.util.UUID;
//
//import static com.periodicals.command.util.RedirectType.FORWARD;
//import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_ADD_PERIODICAL_PAGE;
//import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;
//
//public class EditPeriodicalCommand implements Command {
//    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
//    private static final PublisherService publisherService = PublisherService.getInstance();
//    private static final GenresService genreService = GenresService.getInstance();
//
//    @Override
//    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
//        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
//            UUID id = UUID.fromString(request.getParameter("id"));
//            String name = request.getParameter("name");
//            String description = request.getParameter("description");
//            String subscriptionCost = request.getParameter("subscription_cost");
//            String isLimited = request.getParameter("is_limited");
//            String issuesPerYear = request.getParameter("issues_per_year");
//            String genreId = request.getParameter("genre_id");
//            String publisherId = request.getParameter("publisher_id");
//
//            String[] requiredFields = {
//                    name,
//                    description,
//                    subscriptionCost,
//                    isLimited,
//                    issuesPerYear,
//                    genreId,
//                    publisherId
//            };
//
//            if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
//                try {
//                    Periodical upToEdit = periodicalService.getPeriodicalById(id.toString());
//                    upToEdit.setName(name);
//                    upToEdit.setDescription(description);
//                    upToEdit.setSubscriptionCost(new BigDecimal(subscriptionCost));
//                    upToEdit.setIssuesPerYear(Short.parseShort(issuesPerYear));
//                    upToEdit.setLimited(Boolean.valueOf(isLimited));
//
//                    Genre newGenre = genreService.getGenreById(genreId);
//                    upToEdit.setGenre(newGenre);
//
//                    Publisher newPublisher = publisherService.getPublisherById(publisherId);
//                    upToEdit.setPublisher(newPublisher);
//
//                    periodicalService.update(upToEdit);
//                    request.setAttribute("resultMessage", "Successfully changed periodical info");
//                } catch (Exception e) {
//                    request.setAttribute("resultMessage", "Failed to modify periodical");
//                }
//                return new CommandResult(FORWARD, ADMIN_DEFAULT_PAGE);
//            }
//        }
//        return new CommandResult(FORWARD, ADMIN_ADD_PERIODICAL_PAGE);
//    }
//}
