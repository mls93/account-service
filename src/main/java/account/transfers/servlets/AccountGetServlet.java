package account.transfers.servlets;

import account.transfers.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor(onConstructor = @__({ @Inject}))
public class AccountGetServlet extends HttpServlet {
    private final AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long accountId = Long.valueOf(req.getParameter("account"));
        BigDecimal value = accountService.getBalance(accountId);
        resp.getWriter().append(value.toString());
    }
}
