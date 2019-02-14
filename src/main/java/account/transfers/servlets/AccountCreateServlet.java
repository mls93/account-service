package account.transfers.servlets;

import account.transfers.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static account.transfers.servlets.ParseUtils.parseAccountId;
import static account.transfers.servlets.ParseUtils.parseMoney;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AccountCreateServlet extends HttpServlet {
    private final AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Long id = parseAccountId(req, "id");
        BigDecimal money = parseMoney(req, "balance");
        accountService.createAccount(id, money);
    }
}
